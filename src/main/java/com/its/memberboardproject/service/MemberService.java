package com.its.memberboardproject.service;

import com.its.memberboardproject.dto.MemberDTO;
import com.its.memberboardproject.entity.MemberEntity;
import com.its.memberboardproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long save(MemberDTO memberDTO) throws IOException {
        MultipartFile memberProFile = memberDTO.getMemberProFile();
        String memberProFileName = memberProFile.getOriginalFilename();
        memberProFileName = System.currentTimeMillis() + "_" + memberProFileName;
        String savePath = "D:\\springboot_img\\" + memberProFileName;
        if(!memberProFile.isEmpty()) {
            memberProFile.transferTo(new File(savePath));
        }
        memberDTO.setMemberProFileName(memberProFileName);

        MemberEntity memberEntity = MemberEntity.entitySave(memberDTO);
        return memberRepository.save(memberEntity).getId();
    }
    public MemberDTO findById(Long id) {
        Optional<MemberEntity> om = memberRepository.findById(id);
        if(om.isPresent()){
            MemberEntity memberEntity = om.get();
            return MemberDTO.saveDTO(memberEntity);
        } else {
            return null;
        }
    }

    public MemberDTO login(MemberDTO memberDTO) {
        MemberEntity member = loginCheck(memberDTO.getMemberEmail());
        if(member.getMemberPassword().equals(memberDTO.getMemberPassword())){
            return MemberDTO.saveDTO(member);
        } else{
            return null;
        }
    }
    public MemberEntity loginCheck(String memberEmail){
        Optional<MemberEntity> member = memberRepository.findByMemberEmail(memberEmail);
        if(member.isPresent()) {
            return member.get();
        } else {
            return null;
        }
    }

    public String idCheck(String id) {
        Optional<MemberEntity> member = memberRepository.findByMemberEmail(id);
        if(member.isPresent()){
            return "no";
        } else {
            return "ok";
        }
    }

    public List<MemberDTO> findAll(MemberDTO memberDTO) {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for(MemberEntity memberEntity: memberEntityList){
            memberDTOList.add(MemberDTO.saveDTO(memberEntity));
        }
        return memberDTOList;
    }

    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.entityUpdate(memberDTO));
    }
}
