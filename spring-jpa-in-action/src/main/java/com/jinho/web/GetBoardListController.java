package com.jinho.web;

import com.jinho.domain.BoardResponse;
import com.jinho.domain.GetBoardListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetBoardListController {

    private final GetBoardListRepository getBoardListRepository;

    @GetMapping("/api/v1/boards/v1")
    public Page<BoardResponse> getV1(
        @RequestParam Integer page,
        @RequestParam Integer size
    ) {
        return getBoardListRepository.getV1(1L, PageRequest.of(page, size));
    }

    @GetMapping("/api/v2/boards/")
    public Slice<BoardResponse> getV2(
        @RequestParam Integer page,
        @RequestParam Integer size
    ) {
        return getBoardListRepository.getV2(1L, PageRequest.of(page, size));
    }
}
