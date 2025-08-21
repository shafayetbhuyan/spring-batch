package com.shafayet.spring_batch.event;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobCompletionEventListner implements JobExecutionListener {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED){
            applicationEventPublisher.publishEvent(new BatchFinishedEvent("studentBatchJob", BatchStatus.COMPLETED.toString(), "", ""));
        }else {
            applicationEventPublisher.publishEvent(new BatchFinishedEvent("studentBatchJob", BatchStatus.FAILED.toString(), "", ""));
        }
    }
}
