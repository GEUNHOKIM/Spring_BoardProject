package com.project.project_board.service;

import com.project.project_board.dto.BoardRequestDto;
import com.project.project_board.dto.BoardResponseDto;
import com.project.project_board.entity.Board;
import com.project.project_board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto) {

        Board board = new Board(requestDto);
        boardRepository.save(board);

        BoardResponseDto boardResponseDto = new BoardResponseDto(board);

        return boardResponseDto;

    }

    @Transactional
    public List<Board> getBoard() {

        return boardRepository.findAllByOrderByModifiedAtDesc();

    }

    @Transactional
    public BoardResponseDto selectGetBoard(Long id) {

        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다"));

        return new BoardResponseDto(board);

    }

    @Transactional
    public String selectUpdateBoard(Long id, BoardRequestDto boardRequestDto) {

        Board board = boardRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("아이디가 존재하지 않습니다.") ) ;

        if(!board.getPassword().equals(boardRequestDto.getPassword())) {
            return "비밀번호가 일치하지 않습니다.";
        }

        board.update(boardRequestDto);

        return "저장이 완료되었습니다.";

    }

    @Transactional
    public String selectDeleteBoard(Long id, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("아이디가 존재하지 않습니다.") );

        if(!board.getPassword().equals(boardRequestDto.getPassword())) {
            return "비밀번호가 일치하지 않습니다.";
        }

        boardRepository.deleteById(id);

        return "삭제가 완료되었습니다.";
    }

}
