package com.gompnag.tutorial1.boundedContext.member.controller;

import com.gompnag.tutorial1.base.rs.Rq;
import com.gompnag.tutorial1.base.rsData.RsData;
import com.gompnag.tutorial1.boundedContext.member.dto.Member;
import com.gompnag.tutorial1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@AllArgsConstructor
@Controller
public class MemberController {
  private final MemberService memberService;
  private final Rq rq;

  /*** @AllArgsConstructor  Lombok 어노테이션을 사용하면 아래 내용없어도 됨
  // 생성자 주입
  public MemberController(MemberService memberService) {
    //memberService = new MemberService();
    this.memberService = memberService;
  }
  */
  /*
  // 생성자 주입
  @Autowired
  public MemberController(MemberService memberService) {
    this.memberService = memberService;

  }
  */

  @GetMapping("/member/login")
  @ResponseBody
  public RsData login(String username, String password) {
    if(username == null || username.trim().isEmpty()) {
      return RsData.of("F-3", "username(을)를 입력해주세요.");
    }
    if(password == null || password.trim().isEmpty()) {
      return RsData.of("F-4", "비밀번호를 입력해주세요.");
    }
    RsData rsData = memberService.tryLogin(username, password);
    if(rsData.isSuccess()) {
      //long memberId = (long)rsData.getData();
      Member member = (Member) rsData.getData();
      rq.setCookie("loginedMemberId", member.getId());
      //resp.addCookie(new Cookie("loginedMemberId", memberId + ""));
    }
    return rsData;
  }

  @GetMapping("/member/logout")
  @ResponseBody
  public RsData logout() {
    boolean cookieRemoved = rq.removeCookie("loginedMemberId");

    if(!cookieRemoved) {
      return RsData.of("F-1", "이미 로그아웃 상태입니다.");
    }
    /*  rq.removeCookie로 이동
    if(req.getCookies() != null) {
      Arrays.stream(req.getCookies())
          .filter(cookie -> cookie.getName().equals("loginedMemberId"))
          .forEach(cookie -> {
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
          });
    }
    */
    return RsData.of("S-1", "로그아웃 되었습니다.");
  }
  @GetMapping("/member/me")
  @ResponseBody
  public RsData showMe(){
    long loginedMemberId = rq.getCookieAsLong("loginedMemberId", 0);

    /*  아래 부분은 rq.getCookieAsLong 메서드로 이동
    if(req.getCookies() != null) {
      loginedMemberId = Arrays.stream(req.getCookies())
          .filter(cookie -> cookie.getName().equals("loginedMemberId"))
          .map(Cookie::getValue)  // .map(cookie -> cookie.getValue);
          .mapToLong(Long::parseLong)
          .findFirst()
          .orElse(0);
    } */

    boolean isLogined = loginedMemberId > 0;
    if(!isLogined) {
      return RsData.of("F-1", "로그인 후 이용해주세요.");
    }
    Member member = memberService.findById(loginedMemberId);

    return RsData.of("S-1", "당신의 username(은)는 %s 입니다.".formatted(member.getUsername()));
  }

}
