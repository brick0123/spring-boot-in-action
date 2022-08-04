package com.jinho.job.parameter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

@Getter
public class RequestDatetimeJobParameter {

    private LocalDateTime requestDate;

    @Value("#{jobParameters[requestDate]}")
    public void setLocalDate(String requestDate) {
        if (StringUtils.hasText(requestDate)) {
            this.requestDate = LocalDateTime.parse(requestDate, DateTimeFormatter.ISO_DATE_TIME);
        }
    }
}
