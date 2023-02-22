package com.project.project_board.controller;

import com.project.project_board.dto.BoardRequestDto;
import com.project.project_board.dto.BoardResponseDto;
import com.project.project_board.entity.Board;
import com.project.project_board.security.UserDetailsImpl;
import com.project.project_board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.createBoard(boardRequestDto, userDetails.getUser());

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
    public BoardResponseDto selectUpdateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.selectUpdateBoard(id, boardRequestDto, userDetails.getUser());

    }

    @DeleteMapping("/board/{id}")
    public String selectDeleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.selectDeleteBoard(id, userDetails.getUser());
    }

}

