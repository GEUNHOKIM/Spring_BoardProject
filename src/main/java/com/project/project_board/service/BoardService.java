package com.project.project_board.service;

import com.project.project_board.dto.BoardRequestDto;
import com.project.project_board.dto.BoardResponseDto;
import com.project.project_board.entity.Board;
import com.project.project_board.entity.User;
import com.project.project_board.entity.UserRoleEnum;
import com.project.project_board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
public class BoardService {

    private final BoardRepository boardRepository;

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, User user) {

        Board board = new Board(boardRequestDto,user);
        boardRepository.save(board);
        BoardResponseDto boardResponseDto = new BoardResponseDto(board);

        return boardResponseDto;

    }

    @Transactional(readOnly = true)
    public List<Board> getBoard() {

        return boardRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional(readOnly = true)
    public BoardResponseDto selectGetBoard(Long id) {

        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다"));

        return new BoardResponseDto(board);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @Transactional
    public BoardResponseDto selectUpdateBoard(Long id, BoardRequestDto boardRequestDto, User user) {

        Optional<Board> board = boardRepository.findById(id);

        if(board.isEmpty()) {
            throw new IllegalArgumentException("게시글이 없습니다");
        }

        Optional<Board> found = boardRepository.findByIdAndUser(id, user);

        if(found.isPresent() || user.getRole() == UserRoleEnum.ADMIN) {
            board.get().update(boardRequestDto, user);
        } else {
            throw new IllegalArgumentException("유저가 일치하지 않습니다");
        }

        return new BoardResponseDto(board.get());
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @Transactional
    public String selectDeleteBoard(Long id, User user) {

        Optional<Board> board = boardRepository.findById(id);

        if(board.isEmpty()) {
            throw new IllegalArgumentException("게시글이 없습니다");
        }

        Optional<Board> found = boardRepository.findByIdAndUser(id, user);

        if(found.isPresent() || user.getRole() == UserRoleEnum.ADMIN) {
            boardRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("유저가 일치하지 않습니다");
        }

        return "삭제가 완료 되었습니다";
    }

}
