package com.project.project_board.controller;

import com.project.project_board.dto.BoardRequestDto;
import com.project.project_board.dto.BoardResponseDto;
import com.project.project_board.entity.Board;
import com.project.project_board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/api/boards")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto) {
        return boardService.createBoard(requestDto);

    }

    @GetMapping("/api/get/boards")
    public List<Board> getBoard() {
        return boardService.getBoard();
    }

    @GetMapping("api/get/board/{id}")
    public BoardResponseDto selectGetBoard(@PathVariable Long id) {

        return boardService.selectGetBoard(id);

    }

    @PutMapping("api/put/board/{id}")
    public String selectUpdateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto) {
        return boardService.selectUpdateBoard(id, boardRequestDto);
    }

    @DeleteMapping("/api/delete/board/{id}")
    public String selectDeleteBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto) {
        return boardService.selectDeleteBoard(id, boardRequestDto);
    }

}

