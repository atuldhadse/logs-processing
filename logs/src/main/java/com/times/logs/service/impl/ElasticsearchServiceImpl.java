package com.times.logs.service.impl;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.times.logs.dto.LogEvent;
import com.times.logs.service.IElasticsearchService;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ElasticsearchServiceImpl implements IElasticsearchService {

	@Autowired
	private ElasticsearchClient client;

	@Override
	public void indexLog(LogEvent logEvent) {

		try {
			String index = "logs-" + DateTimeFormatter.ofPattern("yyyy.MM.dd")
				.format(logEvent.getTimestamp().atZone(ZoneOffset.UTC));
			boolean exists = client.indices().exists(e -> e.index(index)).value();
			if (Boolean.FALSE.equals(exists)) {
				createIndex(index);
			}
			indexLogEvent(index, logEvent);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("error encoutered while indexing the log event {}", e.getMessage());
		}

	}

	@Override
	public Page<LogEvent> searchLogs(String level, String keyword, Pageable page) {
		try {
			Query finalQuery = getFinalQuery(level, keyword);
			List<SortOptions> sortOptions = new ArrayList<>();
			for (Sort.Order order : page.getSort()) {
				sortOptions.add(SortOptions.of(s -> s.field(f -> f.field(order.getProperty())
						.order(order.isAscending() ? SortOrder.Asc : SortOrder.Desc))));
			}
			SearchResponse<LogEvent> response = client.search(s -> s
		                    .index("logs-*")
		                    .from((int) page.getOffset())
		                    .size(page.getPageSize())
		                    .query(finalQuery)
		                    .sort(sortOptions),
		            LogEvent.class
		    );
			List<LogEvent> logs = response.hits().hits().stream().map(Hit::source).toList();
			long totalHits = response.hits().total() != null ? response.hits().total().value() : logs.size();
			return new PageImpl<>(logs, page, totalHits);
		} catch (Exception e) {
			log.info("error while searching the logs {}", e.getMessage());
		}
		return Page.empty(page);
	}

	private void createIndex(String index) throws ElasticsearchException, IOException {
		client.indices().create(c -> c
            .index(index)
            .mappings(m -> m
                .properties("timestamp", p -> p.date(d -> d))
                .properties("level", p -> p.keyword(k -> k))
                .properties("service", p -> p.keyword(k -> k))
                .properties("message", p -> p.text(t -> t))
            )
        );
	}
	
	private void indexLogEvent(String index, LogEvent logEvent) 
			throws ElasticsearchException, IOException {
		client.index(i -> i
            .index(index)
            .document(logEvent)
        );
	}

	private Query getFinalQuery(String level, String keyword) {
		List<Query> predicates = new ArrayList<>();
		if (StringUtils.isNotBlank(level)) {
			predicates.add(MatchQuery.of(m -> m.field("level").query(level))._toQuery());
		}
		if (StringUtils.isNotBlank(keyword)) {
			predicates.add(MatchQuery.of(m -> m.field("message").query(keyword))._toQuery());
		}
		return predicates.isEmpty() ? MatchAllQuery.of(m -> m)._toQuery()
				: BoolQuery.of(b -> b.must(predicates))._toQuery();
	}

}
