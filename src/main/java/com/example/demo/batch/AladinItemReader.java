package com.example.demo.batch;


import com.example.demo.dto.AladinApiDTO;
import com.example.demo.dto.AladinItemDTO;
import com.example.demo.service.AladinApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.List;

@RequiredArgsConstructor
public class AladinItemReader implements ItemReader<AladinItemDTO> {
    private final AladinApiService aladinApiService;

    private int nextIdx = 0;
    private List<AladinItemDTO> items;

    @Override
    public AladinItemDTO read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if(items == null) {
            AladinApiDTO aladinApiDTO = aladinApiService.findBestSeller();
            items = aladinApiDTO.getItem();
        }
        AladinItemDTO nextItemDTO = null;

        if(nextIdx < items.size()) {
            nextItemDTO = items.get(nextIdx);
            nextIdx++;
        }else {
            items = null;
            nextIdx = 0;
        }

        return nextItemDTO;
    }
}
