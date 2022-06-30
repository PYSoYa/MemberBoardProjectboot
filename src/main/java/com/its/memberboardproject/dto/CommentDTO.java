package com.its.memberboardproject.dto;

import com.its.memberboardproject.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;

    private Long boardNumber;
    private String commentWriter;
    private String commentContents;
    private LocalDateTime createdDate;

    public CommentDTO(String commentWriter, String commentContents) {
        this.commentWriter = commentWriter;
        this.commentContents = commentContents;
    }

    public static CommentDTO findDTO(CommentEntity commentEntity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setBoardNumber(commentEntity.getBoardNumber());
        commentDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setCreatedDate(commentEntity.getCreatedTime());
        return commentDTO;
    }

}
