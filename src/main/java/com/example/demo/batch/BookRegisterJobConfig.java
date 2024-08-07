package com.example.demo.batch;

import com.example.demo.dto.AladinApiDTO;
import com.example.demo.dto.AladinItemDTO;
import com.example.demo.mapper.BookMapper;
import com.example.demo.service.AladinApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BookRegisterJobConfig {
//    JobRepository는 Spring Batch의 핵심 객체이다.
//    배치 작업의 실행 상태, 진행 상황, 이력 등을 저장하고 관리한다.
//    JobRepository는 DB에 배치작업에 관련된 여러 데이터를 저장한다.
    private final JobRepository jobRepository;
//    스프링에서 트랜재션을 관리하는 인터페이스
//    배치작업에서 트랜잭션을 관리해야하므로 이 객체의 도움을 받는다.
    private final PlatformTransactionManager transactionManager;

    private final BookMapper bookMapper;
    private final AladinApiService aladinApiService;

    @Bean
    public ItemReader<AladinItemDTO> apiItemReader(){
        return new AladinItemReader(aladinApiService);
    }
    @Bean
    public ItemProcessor<AladinItemDTO , AladinItemDTO> apiItemProcessor(){
        return item -> item;
    }

    @Bean
    public ItemWriter<AladinItemDTO> apiItemWriter(){
//        return items -> items.forEach(bookMapper::insertBook);
        return items -> {
            for (AladinItemDTO item : items) {
                bookMapper.insertBook(item);
            }
        };
    }

    @Bean
    public Step apiStep(){

        return new StepBuilder("apiStep" , jobRepository)
                .<AladinItemDTO, AladinItemDTO>chunk(10, transactionManager)
                .reader(apiItemReader())
                .processor(apiItemProcessor())
                .writer(apiItemWriter())
                .build();
    }

    @Bean
    public Job apiJob(){
        return new JobBuilder("apiJob" , jobRepository)
                .start(apiStep())
                .preventRestart() // 배치 작업이 실패했을 때 재시작하지 못하도록 설정
                .build();
    }
}
