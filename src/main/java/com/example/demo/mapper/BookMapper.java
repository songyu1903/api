package com.example.demo.mapper;

import com.example.demo.dto.AladinItemDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookMapper {
    void insertBook(AladinItemDTO aladinItemDTO);
}
