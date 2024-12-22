package com.gompnag.tutorial1.boundedContext.member.controller;

import com.gompnag.tutorial1.base.rsData.RsData;
import com.gompnag.tutorial1.boundedContext.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class MemberController {

  private final MemberService memberService;

  public MemberController() {
    memberService = new MemberService();

  }

  @GetMapping("/member/login")
  @ResponseBody
  public RsData login(String username, String password){
    return memberService.tryLogin(username, password);
  }

}
