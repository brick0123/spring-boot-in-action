package com.jinho.domain;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@Slf4j
public class RepositoryHelper {

    public static <T> Slice<T> toSlice(List<T> content, Pageable pageable) {
        return new SliceImpl<>(content, pageable, true);
    }
}
