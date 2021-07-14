package com.jinho.web;

import com.jinho.domain.BoardRepository;
import com.jinho.domain.BoardResponse;
import com.jinho.domain.GetBoardListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GetBoardListController {

    private final GetBoardListRepository getBoardListRepository;
    private final BoardRepository boardRepository;

    @GetMapping("/api/v1/boards")
    public Page<BoardResponse> getV1(
        @RequestParam final Integer page,
        @RequestParam final Integer size
    ) {
        return getBoardListRepository.getV1(PageRequest.of(page, size));
    }

    @GetMapping("/api/v2/boards")
    public Slice<BoardResponse> getV2(
        @RequestParam final Integer page,
        @RequestParam final Integer size
    ) {
        return getBoardListRepository.getV2(PageRequest.of(page, size));
    }

    @GetMapping("/api/v3/boards")
    public Slice<BoardResponse> getV3(
        @RequestParam final Integer page,
        @RequestParam final Integer size
    ) {
        return boardRepository.find(PageRequest.of(page, size)).map(BoardResponse::new);
    }
}
