package com.jinho.job.parameter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class RequestDateParam {

    private LocalDate requestDate;

    @Value("#{jobParameters[requestDate]}")
    public void setLocalDate(String requestDate) {
        this.requestDate = LocalDate.parse(requestDate, DateTimeFormatter.ISO_DATE);
    }
}
