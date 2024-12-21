package com.gompnag.tutorial1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
  @GetMapping("/home/main")
  @ResponseBody
  public String showHome() {
    return "반갑습니다.";
  }

  @GetMapping("/home/main2")
  @ResponseBody
  public String showHome2() {
    return "어서오세요";
  }

  @GetMapping("/home/main3")
  @ResponseBody
  public String showHome3() {
    return "스프링부트 획기적이다.";
  }

}
