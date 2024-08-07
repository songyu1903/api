package com.example.demo.schedule;

import com.example.demo.batch.BookRegisterJobConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiBatchSchedule {
    private final JobLauncher jobLauncher;
    private final BookRegisterJobConfig bookRegisterJobConfig;

    @Scheduled(cron = "0/10 * * * * ?")
    public void bookRegister() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        try {
            jobLauncher.run(bookRegisterJobConfig.apiJob(), jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
