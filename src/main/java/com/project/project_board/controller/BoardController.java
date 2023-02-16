package com.project.project_board.controller;

import com.project.project_board.dto.BoardRequestDto;
import com.project.project_board.dto.BoardResponseDto;
import com.project.project_board.entity.Board;
import com.project.project_board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request) {
        return boardService.createBoard(boardRequestDto, request);

    }

    @GetMapping("/boards")
    public List<Board> getBoard() {
        return boardService.getBoard();
    }

    @GetMapping("/board/{id}")
    public BoardResponseDto selectGetBoard(@PathVariable Long id) {

        return boardService.selectGetBoard(id);

    }

    @PutMapping("/board/{id}")
    public String selectUpdateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request) {
        return boardService.selectUpdateBoard(id, boardRequestDto, request);

    }

    @DeleteMapping("/board/{id}")
    public String selectDeleteBoard(@PathVariable Long id, HttpServletRequest request) {
        return boardService.selectDeleteBoard(id, request);
    }

}

