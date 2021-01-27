package com.kts.cultural_content.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kts.cultural_content.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository  extends JpaRepository<Comment, Long> {
    
    Page<Comment> findAll(Pageable page);
    Page<Comment> findAllByOfferId(Long id, Pageable pageable );
    
    
}
