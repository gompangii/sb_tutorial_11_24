package com.gompnag.tutorial1.boundedContext.member.repository;

import com.gompnag.tutorial1.boundedContext.member.dto.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository  // 컴포넌트랑 같다
public class MemberRepository {
  List<Member> members;
  public MemberRepository() {
    members = new ArrayList<>();

    members.add(new Member("user1", "1234"));
    members.add(new Member("abc", "1235"));
    members.add(new Member("test", "1236"));
    members.add(new Member("bbc", "1237"));
    members.add(new Member("love", "1238"));
    members.add(new Member("hello", "1239"));
    members.add(new Member("user2", "12310"));
    members.add(new Member("user3", "12311"));
    members.add(new Member("good", "12312"));
    members.add(new Member("like", "12313"));
  }


  public Member findByUserName(String username) {
    return members.stream()
        .filter(member -> member.getUsername().equals(username))
        .findFirst()
        .orElse(null);
  }
}
