package com.kts.cultural_content.controller;

import javax.validation.Valid;

import com.kts.cultural_content.dto.CommentDTO;
import com.kts.cultural_content.model.Comment;
import com.kts.cultural_content.service.comment.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired  CommentService commentService;

    @CrossOrigin()
    @GetMapping("/{id}")
    public ResponseEntity<Page<CommentDTO>> getAllComments(@PathVariable("id") Long id, @PageableDefault(size = 10000) Pageable pageable){
        Page<CommentDTO> comments = commentService.getAllComments( id, pageable).map(x -> new CommentDTO(x));
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<CommentDTO> addComment(@Valid @RequestBody CommentDTO commentDto){
        Comment comment = commentService.addComment(commentDto);
        
        return new ResponseEntity<>(new CommentDTO(comment), HttpStatus.OK);
    }

    
}
