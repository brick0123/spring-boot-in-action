package com.jinho.job.parameter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

@Getter
public class RequestDateJobParameter {

    private LocalDate requestDate;

    @Value("#{jobParameters[requestDate]}")
    public void setLocalDate(String requestDate) {
        if (StringUtils.hasText(requestDate)) {
            this.requestDate = LocalDate.parse(requestDate, DateTimeFormatter.ISO_DATE);
        }
    }
}
