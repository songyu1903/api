package com.example.demo.batch;


import com.example.demo.dto.AladinItemDTO;
import org.springframework.batch.item.ItemProcessor;

public class AladinItemProcessor implements ItemProcessor<AladinItemDTO, AladinItemDTO> {
    @Override
    public AladinItemDTO process(AladinItemDTO item) throws Exception {
        // 중복 처리
        // Mapper를 주입받아 고유값으로(isbn) select 쿼리를 실행한다
        // 쿼리의 결과가 null이면 DB에 없는 책정보이므로 Return한다.
        // 만약  null이 아니면 DB에 있는 책 정보 이므로 return null 한다.

        return null;
    }
}
