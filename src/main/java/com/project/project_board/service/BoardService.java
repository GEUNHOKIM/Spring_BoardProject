package com.project.project_board.service;

import com.project.project_board.dto.BoardRequestDto;
import com.project.project_board.dto.BoardResponseDto;
import com.project.project_board.entity.Board;
import com.project.project_board.entity.JwtUtil;
import com.project.project_board.entity.User;
import com.project.project_board.entity.UserRoleEnum;
import com.project.project_board.repository.BoardRepository;
import com.project.project_board.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null) {

            if(jwtUtil.validateToken(token)) {

                claims = jwtUtil.getUserInfoFromToken(token);
            } else {

                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            UserRoleEnum userRoleEnum = user.getRole();

            if(userRoleEnum == UserRoleEnum.USER) {

                Board board = new Board(boardRequestDto,user);
                boardRepository.save(board);
                BoardResponseDto boardResponseDto = new BoardResponseDto(board);

                return boardResponseDto;

            } else {

                throw new IllegalArgumentException("사용자 권한이 없습니다.");
            }

        } else {

            return null;
        }

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

    @Transactional
    public String selectUpdateBoard(Long id, BoardRequestDto boardRequestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null) {

            if(jwtUtil.validateToken(token)) {

                claims = jwtUtil.getUserInfoFromToken(token);
            } else {

                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다")
            );

            UserRoleEnum userRoleEnum = user.getRole();

            Optional<Board> board = boardRepository.findByIdAndUser(id, user);

            if(board.isEmpty() && userRoleEnum == UserRoleEnum.USER) {
                throw new IllegalArgumentException("일치하는 게시물이 없습니다");
            }

            board.get().update(boardRequestDto,user);

        }

        return "저장이 완료되었습니다.";
    }

    @Transactional
    public String selectDeleteBoard(Long id, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null) {

            if(jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다")
            );

            UserRoleEnum userRoleEnum = user.getRole();

            Optional<Board> board = boardRepository.findByIdAndUser(id, user);

            if(board.isEmpty() && userRoleEnum == UserRoleEnum.USER) {
                throw new IllegalArgumentException("일치하는 게시물이 없습니다");
            }

            boardRepository.deleteById(id);

        } else {

            return null;
        }

        return "삭제가 완료 되었습니다";
    }

}
