package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
@NoArgsConstructor
public class AladinApiDTO {
    private String version;
    private String logo;
    private String title;
    private String pubDate;
    private Long totalResults;
    private int startIndex;
    private int itemsPerPage;
    private List<AladinItemDTO> item;
}
