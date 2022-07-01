package com.its.memberboardproject.controller;

import com.its.memberboardproject.dto.CommentDTO;
import com.its.memberboardproject.entity.CommentEntity;
import com.its.memberboardproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/save")
    public String save(@ModelAttribute CommentDTO commentDTO, Model model) {
        System.out.println("commentDTO = " + commentDTO);
        commentService.save(commentDTO);
        return "redirect:/board/"+commentDTO.getBoardNumber();
    }
    @GetMapping("/delete/{id}/{id1}")
    public String delete(@PathVariable("id") Long id,
                         @PathVariable("id1") Long id1){
        commentService.delete(id);
        return "redirect:/board/"+ id1;
    }
//    @GetMapping("/findAll")
//    public String findAll(Model model){
//        List<CommentDTO> commentDTOList = commentService.findAll();
//        model.addAttribute("commentList", commentDTOList);
//        return "boardPages/detail";
//    }
}
