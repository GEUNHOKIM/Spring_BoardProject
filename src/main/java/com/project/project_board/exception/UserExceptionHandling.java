package com.project.project_board.exception;

import com.project.project_board.dto.MessageResponseDto;
import com.project.project_board.entity.enumset.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserExceptionHandling {

    public static ResponseEntity<Object> responseException(ErrorType error) {
        MessageResponseDto messageResponseDto = MessageResponseDto.builder()
                .statusCode(error.getStatusCode())
                .msg(error.getMsg())
                .build();

        return ResponseEntity.badRequest().body(messageResponseDto);
    }

    public static ResponseEntity<Object> responseException(HttpStatus status, String message) {
        MessageResponseDto responseDto = MessageResponseDto.builder()
                .statusCode(status.value())
                .msg(message)
                .build();

        return ResponseEntity.badRequest().body(responseDto);   // ResponseEntity 에 status : bad request, body : responseDto 넣어서 반환
    }
}
