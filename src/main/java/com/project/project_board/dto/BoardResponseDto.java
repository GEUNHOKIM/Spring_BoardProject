package com.project.project_board.dto;


import com.project.project_board.entity.Board;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardResponseDto {
    private Long id;
    private String username;
    private String contents;
    private String title;
    private LocalDateTime createAt;
    private LocalDateTime modifyAt;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.username = board.getUser().getUsername();
        this.contents = board.getContents();
        this.title = board.getTitle();
        this.createAt = board.getCreatedAt();
        this.modifyAt = board.getModifiedAt();
    }

}
