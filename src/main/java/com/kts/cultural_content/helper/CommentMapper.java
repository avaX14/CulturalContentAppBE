package com.kts.cultural_content.helper;

import com.kts.cultural_content.dto.CommentDTO;
import com.kts.cultural_content.model.Comment;

public class CommentMapper {
    
    private CommentMapper(){

    }

    public static Comment toEntity(CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setText(commentDTO.getText());
        comment.setDate(commentDTO.getDate());
        comment.setAuthor(UserMapper.toEntity(commentDTO.getAuthor()));

        return comment;
    }
}
