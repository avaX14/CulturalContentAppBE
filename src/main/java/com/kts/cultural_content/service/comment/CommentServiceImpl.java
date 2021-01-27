package com.kts.cultural_content.service.comment;

import com.kts.cultural_content.dto.CommentDTO;
import com.kts.cultural_content.helper.CommentMapper;
import com.kts.cultural_content.model.Comment;
import com.kts.cultural_content.repository.CommentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

	@Override
	public Comment addComment(CommentDTO commentDTO) {
		Comment comment = CommentMapper.toEntity(commentDTO);
	
        commentRepository.save(comment);

        return comment;
	}

	@Override
	public Page<Comment> getAllComments(Long id, Pageable pageable ) {
		return commentRepository.findAllByOfferId(id, pageable);
	}

	@Override
	public void deleteComment(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}
    
}
