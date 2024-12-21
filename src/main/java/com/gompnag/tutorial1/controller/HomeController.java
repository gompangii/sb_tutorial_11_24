package com.gompnag.tutorial1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class HomeController {
  int num;

  public HomeController() {
    num = -1;
  }
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

  @GetMapping("/home/increase")
  @ResponseBody
  public int showIncrease() {
    num++;
    return num;
  }

  @GetMapping("/home/plus")
  @ResponseBody
  public int showPlus(@RequestParam(defaultValue = "0") int a, int b){
    return a+b;
  }

  @GetMapping("/home/ReturnBoolean")
  @ResponseBody
  public boolean showReturnBoolean() {
    return true;
  }

  @GetMapping("/home/ReturnDouble")
  @ResponseBody
  public Double showReturnDouble() {
    return Math.PI;
  }

  @GetMapping("/home/ReturnArray")
  @ResponseBody
  public int[] showReturnArray() {
    int[] arr = new int[]{10, 20, 30};

    return arr;
  }

  @GetMapping("/home/ReturnIntList")
  @ResponseBody
  public List<Integer> showReturnIntList() {
    List<Integer> list = new ArrayList<>(){{
      add(10);
      add(20);
      add(30);
    }};

    /*
    List<Integer> list = new ArrayList<>();
    list.add(10);
    list.add(20);
    list.add(30);
     */

    return list;
  }

  @GetMapping("/home/ReturnMap")
  @ResponseBody
  public Map<String, Object> showReturnMap() {
    Map<String, Object> map = new LinkedHashMap<>() {{
      put("id", 1);
      put("subject", "제목1");
      put("content", "내용1");
      put("writerName", "김철수");
      put("artticleNo", new ArrayList<>() {{
        add(1);
        add(2);
        add(3);
      }});
    }};

    return map;
  }



}
