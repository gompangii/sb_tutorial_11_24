package com.gompnag.tutorial1.boundedContext.member.service;

import com.gompnag.tutorial1.base.rsData.RsData;
import com.gompnag.tutorial1.boundedContext.member.entity.Member;
import com.gompnag.tutorial1.boundedContext.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

// 아래 클래스는 Ioc 컨테이너에 의해 생사소멸이 관리된다.
@Service //@Component : @Componet와 같은 의미, 가독성 때문에 이렇게 표기
@AllArgsConstructor
public class MemberService {
  public final MemberRepository memberRepository;

  public RsData tryLogin(String username, String password) {
    Member member = memberRepository.findByUsername(username).orElse(null);

    if(member == null){
      return RsData.of("F-2", "%s(은)는 존재하지 않는 회원입니다.".formatted(username));
    }

    if(!member.getPassword().equals(password)) {
      return RsData.of("F-1", "비밀번호가 일치 하지 않습니다.");
    }

    return RsData.of("S-1", "%s님 환영합니다.".formatted(username), member);
  }

  public Member findByUsername(String username) {
    return memberRepository.findByUsername(username).orElse(null);
  }

  public Member findById(long id) {
    return memberRepository.findById(id).orElse(null);  // SELECT * `member` WHERE id = ?
  }

  public RsData join(String username, String password) {
    Member member = Member.builder()
        .username(username)
        .password(password)
        .build();

    memberRepository.save(member);

    return RsData.of("S-1", "회원 가입되었습니다.", member);
  }
}
