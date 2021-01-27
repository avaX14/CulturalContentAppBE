package  com.kts.cultural_content.service.comment;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import  com.kts.cultural_content.dto.*;
import com.kts.cultural_content.model.Comment;

public interface CommentService {
    Comment addComment(CommentDTO comment);
    Page<Comment> getAllComments(Long id, Pageable pageable );
    void deleteComment(Long id) throws Exception;
}
