package com.shafayet.spring_batch.dto;

import java.time.LocalDateTime;

public record StudentDto(
          Long id
        , String firstName
        , String lastName
        , Integer age
        , LocalDateTime dateOfBirth
        , String email
        , String phoneNumber
        , LocalDateTime created
        , LocalDateTime updated
        , String createdBy
        , String updatedBy ) {

}
