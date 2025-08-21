package com.shafayet.spring_batch.event;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchFinishedEventListner {

    private final Logger log = LoggerFactory.getLogger(BatchFinishedEventListner.class);

    @EventListener
    public void listner(BatchFinishedEvent event){

        log.info("\n\n  ===> ({}) has been successfully finished! \n\t\t\t Status: ({}). \n\t\t\t Total Rows: ({}). \n\t\t\t Time Taken: ({}) ", event.name(), event.status(), event.totalRows(), event.time());

    }

}
