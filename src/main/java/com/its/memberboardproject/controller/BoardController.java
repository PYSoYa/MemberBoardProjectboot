package com.its.memberboardproject.controller;

import com.its.memberboardproject.common.PagingConst;
import com.its.memberboardproject.dto.BoardDTO;
import com.its.memberboardproject.dto.CommentDTO;
import com.its.memberboardproject.service.BoardService;
import com.its.memberboardproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/save-form")
    public String saveForm(){
        return "boardPages/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO, Model model) throws IOException {
        boardService.save(boardDTO);
        return "redirect:/board";
    }
    @GetMapping("/findAll")
    public String findAll(@ModelAttribute BoardDTO boardDTO, Model model) {
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "boardPages/detail";
    }
    @GetMapping
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<BoardDTO> boardList = boardService.paging(pageable);
        model.addAttribute("boardList", boardList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < boardList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : boardList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "boardPages/list";
    }
    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        List<CommentDTO> commentDTOList = commentService.findAll(id);
        model.addAttribute("board", boardDTO);
        model.addAttribute("comment", commentDTOList);
        return "boardPages/detail";
    }
    @GetMapping("/update-form/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "boardPages/update";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO) {
        boardService.update(boardDTO);
        return "redirect:/board/" + boardDTO.getId();
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.delete(id);
        return "redirect:/board";
    }
    @GetMapping("/search")
    public String search(@RequestParam("q") String q, Model model) {
        List<BoardDTO> boardDTOList = boardService.search(q);
        model.addAttribute("boardList", boardDTOList);
        return "boardPages/search";
    }
}
