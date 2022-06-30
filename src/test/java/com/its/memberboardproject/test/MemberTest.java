package com.its.memberboardproject.test;

import com.its.memberboardproject.dto.MemberDTO;
import com.its.memberboardproject.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.stream.IntStream;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MemberTest {
    @Autowired
    private MemberService memberService;

    public MemberDTO newMember(int i){
        MemberDTO memberDTO = new MemberDTO("asdf12" + i, "asd12!"+i, "asd" + i, "aaa" + i,
                "010" + i);
        return memberDTO;
    }

    @Test
    @Transactional
    @Rollback
    public void saveTest() throws IOException {
        Long id = memberService.save(newMember(1));
        MemberDTO saveDTO = memberService.findById(id);
        assertThat(newMember(1).getMemberEmail()).isEqualTo(saveDTO.getMemberEmail());
    }
}
