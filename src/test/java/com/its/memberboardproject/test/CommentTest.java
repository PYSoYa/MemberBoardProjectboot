package com.its.memberboardproject.test;

import com.its.memberboardproject.dto.CommentDTO;
import com.its.memberboardproject.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import static org.assertj.core.api.Assertions.*;


@SpringBootTest
public class CommentTest {
    @Autowired
    private CommentService commentService;

    public CommentDTO newComment(int i){
        CommentDTO commentDTO = new CommentDTO("aaa"+i, "sss"+i);
        return commentDTO;
    }

    @Test
    @Transactional
    @Rollback
    public void commentSaveTest(){
        Long id = commentService.save(newComment(1));
        CommentDTO saveDTO = commentService.findById(id);
        assertThat(newComment(1).getCommentWriter()).isEqualTo(saveDTO.getCommentWriter());
    }
}
