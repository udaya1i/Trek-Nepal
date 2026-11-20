package com.trek.trekker.service;

import com.trek.core.constants.SearchEntityType;
import com.trek.core.dto.response.DestinationSearchable;
import com.trek.core.dto.response.SearchDestinationProjection;
import com.trek.core.dto.response.TrekStorySearchable;
import com.trek.core.entity.TrekStory;
import com.trek.core.trekker.service.PublicDestinationDetailQueryService;
import lombok.AllArgsConstructor;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JaroSearchFallbackService {

    private static final double THRESHOLD = 0.70;
    private static final int LIMIT = 10;

    private final PublicDestinationDetailQueryService publicDestinationDetailQueryService;
    private final JaroWinklerSimilarity jaro = new JaroWinklerSimilarity();

    public List<SearchDestinationProjection.Response> searchDestination(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return List.of();
        }

        String normalised = keyword.toLowerCase().trim();

        return publicDestinationDetailQueryService.getAllNames().stream()
                .collect(Collectors.groupingBy(DestinationSearchable::getId))
                .values().stream()
                .filter(rows -> !rows.isEmpty())
                .map(rows -> bestMatch(rows, normalised))
                .filter(e -> e.getValue() >= THRESHOLD)
                .sorted(Comparator.comparingDouble(Map.Entry<MatchResult, Double>::getValue).reversed())
                .limit(LIMIT)
                .map(e -> SearchDestinationProjection.Response.from(
                        e.getKey().row(),
                        e.getKey().type()
                ))
                .toList();
    }

    private Map.Entry<MatchResult, Double> bestMatch(List<DestinationSearchable> rows, String kw) {
        return rows.stream()
                .map(row -> scoreRow(row, kw))
                .max(Comparator.comparingDouble(MatchResult::score))
                .map(best -> Map.entry(best, best.score()))
                .orElseThrow(() -> new IllegalStateException("Empty row group for id=" + rows.getFirst().getId()));
    }

    private MatchResult scoreRow(DestinationSearchable row, String kw) {
        MatchResult best = new MatchResult(row, SearchEntityType.DESTINATION, 0.0);

        best = better(best, tryScore(row, row.getName(), SearchEntityType.DESTINATION, kw));
        best = better(best, tryScore(row, row.getRegion(), SearchEntityType.REGION, kw));
        best = better(best, tryScore(row, row.getStrategyName(), SearchEntityType.STRATEGY, kw));
        best = better(best, tryScore(row, row.getWaypointName(), SearchEntityType.WAYPOINT, kw));

        return best;
    }

    private static MatchResult better(MatchResult current, MatchResult candidate) {
        return candidate.score() > current.score() ? candidate : current;
    }

    private MatchResult tryScore(DestinationSearchable row, String field,
                                 SearchEntityType type, String kw) {
        if (field == null) return new MatchResult(row, type, 0.0);
        double score = jaro.apply(kw, field.toLowerCase());
        return new MatchResult(row, type, score);
    }

    private record MatchResult(DestinationSearchable row, SearchEntityType type, double score) {
    }
}