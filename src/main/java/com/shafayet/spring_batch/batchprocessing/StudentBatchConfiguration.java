package com.shafayet.spring_batch.batchprocessing;

import com.shafayet.spring_batch.dao.StudentRepository;
import com.shafayet.spring_batch.dto.StudentCsvDto;
import com.shafayet.spring_batch.dto.StudentDto;
import com.shafayet.spring_batch.entity.Student;
import com.shafayet.spring_batch.event.JobCompletionEventListner;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.validation.BindException;

import java.time.ZoneId;

@Configuration
@RequiredArgsConstructor
public class StudentBatchConfiguration {

    private final StudentItemProcessor studentItemProcessor;
    private final StudentRepository studentRepository;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final JobCompletionEventListner completionEventListner;

    @Bean
    public FlatFileItemReader<StudentCsvDto> reader() {
        return new FlatFileItemReaderBuilder<StudentCsvDto>()
                .name("studentItemReader")
                .resource(new ClassPathResource("files/sample-data.csv"))
                .delimited()
                .names("firstName", "lastName", "age", "dateOfBirth", "email", "phoneNumber")
                .fieldSetMapper(new FieldSetMapper<StudentCsvDto>() {
                    @Override
                    public StudentCsvDto mapFieldSet(FieldSet fieldSet) throws BindException {
                        return new StudentCsvDto(
                                fieldSet.readString("firstName"),
                                fieldSet.readString("lastName"),
                                fieldSet.readInt("age"),
                                fieldSet.readDate("dateOfBirth").toInstant()
                                        .atZone(ZoneId.systemDefault()).toLocalDate(),
                                fieldSet.readString("email"),
                                fieldSet.readString("phoneNumber")
                        );
                    }
                })
                .linesToSkip(1)
                .build();
    }

    @Bean
    public RepositoryItemWriter<Student> writer(){
        RepositoryItemWriter<Student> repositoryItemWriter = new RepositoryItemWriter<>();
        repositoryItemWriter.setRepository(studentRepository);
        return repositoryItemWriter;
    }

    @Bean
    public TaskExecutor executor(){
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
        executor.setConcurrencyLimit(10);
        return executor;
    }

    @Bean
    public Step step(){
        return new StepBuilder("studentStep1", jobRepository)
                .<StudentCsvDto, Student>chunk(500, platformTransactionManager)
                .reader(reader())
                .processor(studentItemProcessor)
                .writer(writer())
                .taskExecutor(executor())
                .build();
    }

    @Bean
    public Job runJob(){
        return  new JobBuilder("studentBatchJob", jobRepository)
                .listener(completionEventListner)
                .start(step())
                .build();
    }

}
