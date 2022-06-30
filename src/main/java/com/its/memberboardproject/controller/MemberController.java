package com.its.memberboardproject.controller;

import com.its.memberboardproject.dto.BoardDTO;
import com.its.memberboardproject.dto.MemberDTO;
import com.its.memberboardproject.entity.MemberEntity;
import com.its.memberboardproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/save-form")
    public String saveForm() {
        return "memberPages/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) throws IOException {
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);
        return "memberPages/login";
    }

    @GetMapping("/login-form")
    public String loginForm(){
        return "memberPages/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginDTO = memberService.login(memberDTO);
        if (loginDTO != null) {
            session.setAttribute("loginId", loginDTO.getId());
            session.setAttribute("loginEmail", loginDTO.getMemberEmail());
            session.setAttribute("password",loginDTO.getMemberPassword());
            return "redirect:/board";
        } else {
            return "memberPages/login";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
    @PostMapping("/idCheck")
    public @ResponseBody String idCheck(@RequestParam("id") String id) {
       String result = memberService.idCheck(id);
        return result;
    }
    @GetMapping("/findAll")
    public String findAll(@ModelAttribute MemberDTO memberDTO ,Model model){
       List<MemberDTO> memberDTOList = memberService.findAll(memberDTO);
        model.addAttribute("member", memberDTOList);
        return "memberPages/list";
    }
    @GetMapping("/admin")
    public String admin(){
        return "redirect:/member/findAll";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, HttpSession session){
        memberService.delete(id);
        if (session.getAttribute("loginEmail") != "admin") {
            session.invalidate();
        }
        return "index";
    }
    @GetMapping("/myPage/{id}")
    public String myPage(@PathVariable("id") Long id, Model model){
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "memberPages/myPage";
    }
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model){
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "memberPages/update";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO, Model model) {
        memberService.update(memberDTO);
        model.addAttribute("member", memberService.findById(memberDTO.getId()));
        return "redirect:/member/myPage/" + memberDTO.getId();
    }
}
