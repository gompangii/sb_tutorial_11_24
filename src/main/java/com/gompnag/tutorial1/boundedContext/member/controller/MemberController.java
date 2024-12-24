package com.gompnag.tutorial1.boundedContext.member.controller;

import com.gompnag.tutorial1.base.rs.Rq;
import com.gompnag.tutorial1.base.rsData.RsData;
import com.gompnag.tutorial1.boundedContext.member.dto.Member;
import com.gompnag.tutorial1.boundedContext.member.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
  //@ResponseBody
  public String showLogin(String username, String password) {
    return "usr/member/login";
    /*  /resources/static/usr/member/login.html 로 대체
    if (rq.isLogined()) {
      return """
          <h1>이미 로그인 되었습니다.</h1>
          """.stripIndent();
    }
    return """
        <h1>로그인</h1>
        <form action="doLogin">
          <input type="text" name="username" placeholder="아이디" />
          <input type="password" name="password" placeholder="비밀번호" />
          <button type="submit">로그인</button>
        </form>
        """;
     */
  }


  //@GetMapping("/member/doLogin")
  @PostMapping("/member/doLogin")
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
      rq.setSession("loginedMemberId", member.getId());
      //rq.setCookie("loginedMemberId", member.getId());  //쿠키에 저장
      //resp.addCookie(new Cookie("loginedMemberId", memberId + ""));
    }
    return rsData;
  }

  @GetMapping("/member/logout")
  @ResponseBody
  public RsData logout() {
    // boolean cookieRemoved = rq.removeCookie("loginedMemberId");
    boolean cookieRemoved = rq.removeSession("loginedMemberId");

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
    //long loginedMemberId = rq.getCookieAsLong("loginedMemberId", 0);
    long loginedMemberId = rq.getSessionAsLong("loginedMemberId", 0);

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

  @GetMapping("/member/session")
  @ResponseBody
  public String showSession(){
    return rq.getSessionDebugInfo().replaceAll("\n", "<br>");
  }
}
