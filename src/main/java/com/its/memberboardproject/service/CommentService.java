package com.its.memberboardproject.service;

import com.its.memberboardproject.dto.BoardDTO;
import com.its.memberboardproject.dto.CommentDTO;
import com.its.memberboardproject.entity.BoardEntity;
import com.its.memberboardproject.entity.CommentEntity;
import com.its.memberboardproject.entity.MemberEntity;
import com.its.memberboardproject.repository.BoardRepository;
import com.its.memberboardproject.repository.CommentRepository;
import com.its.memberboardproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentDTO commentDTO) {
        Optional<MemberEntity> optionalMemberEntity =
                memberRepository.findByMemberEmail(commentDTO.getCommentWriter());
        Optional<BoardEntity> optionalBoardEntity =
                boardRepository.findById(commentDTO.getBoardNumber());
        if(optionalMemberEntity.isPresent() && optionalBoardEntity.isPresent()){
            MemberEntity memberEntity = optionalMemberEntity.get();
            BoardEntity boardEntity = optionalBoardEntity.get();
            Long id = commentRepository.save(CommentEntity.entitySave(commentDTO, boardEntity, memberEntity)).getId();
            return id;
        } else {
            return null;
        }

    }
    public CommentDTO findById(Long id){
        Optional<CommentEntity> oc = commentRepository.findById(id);
        if(oc.isPresent()){
            CommentEntity commentEntity = oc.get();
            return CommentDTO.findDTO(commentEntity);
        } else {
            return null;
        }
    }

    public List<CommentDTO> findAll(Long id) {
        List<CommentEntity> commentEntityList = commentRepository.findByBoardNumber(id);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for(CommentEntity commentEntity: commentEntityList) {
            commentDTOList.add(CommentDTO.findDTO(commentEntity));
        }
        return commentDTOList;
    }
}
