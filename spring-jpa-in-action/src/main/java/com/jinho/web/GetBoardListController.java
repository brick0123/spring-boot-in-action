package com.jinho.web;

import com.jinho.domain.BoardResponse;
import com.jinho.domain.GetBoardListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetBoardListController {

    private final GetBoardListRepository getBoardListRepository;

    @GetMapping("/boards")
    public Page<BoardResponse> getV1(
        @RequestParam Integer page,
        @RequestParam Integer size
    ) {
        return getBoardListRepository.getV1(1L, PageRequest.of(page, size));
    }

}
