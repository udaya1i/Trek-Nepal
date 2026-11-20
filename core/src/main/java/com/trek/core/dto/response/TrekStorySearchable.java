package com.trek.core.dto.response;

import com.trek.core.constants.SearchEntityType;
import lombok.Builder;
import lombok.Getter;

public interface TrekStorySearchable {
    Long getId();
    String getTitle();
    String getShortDescription();
    String getWriterFullName();
    String getWriterUsername();
    String getDestinationName();
    String getDestinationRegion();

    @Getter
    @Builder
    public static class Response {
        private Long id;
        private String title;
        private String shortDescription;
        private String writerFullName;
        private String writerUsername;
        private String destinationName;
        private String destinationRegion;
        private SearchEntityType matchedOn;

        public static Response from(TrekStorySearchable row, SearchEntityType type) {
            return Response.builder()
                    .id(row.getId())
                    .destinationRegion(row.getDestinationRegion())
                    .title(row.getTitle())
                    .shortDescription(row.getShortDescription())
                    .writerFullName(row.getWriterFullName())
                    .writerUsername(row.getWriterUsername())
                    .destinationName(row.getDestinationName())
                    .matchedOn(type)
                    .build();
        }
    }

}
