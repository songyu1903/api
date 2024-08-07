package com.example.demo.service;

import com.example.demo.dto.AladinApiDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Service
public class AladinApiService {
    @Value("${api.aladin.key}")
    private String apiKey;

    public AladinApiDTO findBestSeller(){
        WebClient wc = WebClient.builder()
                .baseUrl("http://www.aladin.co.kr")
                .build();

        AladinApiDTO result = wc.get()
                .uri(uriBuilder -> uriBuilder
                        .path("//ttb/api/ItemList.aspx")
                        .queryParam("ttbkey", apiKey)
                        .queryParam("QueryType", "Bestseller")
                        .queryParam("SearchTarget", "Book")
                        .queryParam("output", "js")
                        .queryParam("version", "20131101")
                        .queryParam("Start", 1)
                        .queryParam("MaxResults", 50)
                        .build()
                ).retrieve()
                .bodyToMono(AladinApiDTO.class)
                .block();

        log.info("result : {}" , result);
        return result;
    }
}
