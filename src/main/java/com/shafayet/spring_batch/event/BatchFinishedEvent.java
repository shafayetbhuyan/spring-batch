package com.shafayet.spring_batch.event;

public record BatchFinishedEvent(String name,
                                 String status,
                                 String time,
                                 String totalRows) {
}
