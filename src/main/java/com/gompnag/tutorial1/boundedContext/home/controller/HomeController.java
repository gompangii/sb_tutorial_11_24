package com.gompnag.tutorial1.boundedContext.home.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;

@Controller
public class HomeController {
  int num;
  List<Person> people;


  public HomeController() {
    num = -1;
    people = new ArrayList<>();
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
  public int showPlus(@RequestParam(defaultValue = "0") int a, int b) {
    return a + b;
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
    List<Integer> list = new ArrayList<>() {{
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

  @GetMapping("/home/ReturnArticle")
  @ResponseBody
  public Article showReturnArticle() {
    Article article = new Article(1, "제목1", "내용1", "김철수", new ArrayList<>() {{
      add(1);
      add(2);
      add(3);
    }});

    return article;
  }

  @GetMapping("/home/ReturnArticle2")
  @ResponseBody
  public Article2 showReturnArticle2() {
    Article2 article = new Article2(1, "제목1", "내용1", "김철수", new ArrayList<>() {{
      add(1);
      add(2);
      add(3);
    }});

    return article;
  }

  @GetMapping("/home/ReturnArticleMapList")
  @ResponseBody
  public List<Map<String, Object>> showReturnArticleMapList() {
    Map<String, Object> articleMap1 = new LinkedHashMap<>() {{
      put("id", 1);
      put("subject", "제목1");
      put("content", "내용1");
      put("writerName", "김철수");
      put("articleNo", new ArrayList<>() {{
        add(1);
        add(2);
        add(3);
      }});
    }};

    Map<String, Object> articleMap2 = new LinkedHashMap<>() {{
      put("id", 2);
      put("subject", "제목2");
      put("content", "내용2");
      put("writerName", "신짱구");
      put("articleNo", new ArrayList<>() {{
        add(4);
        add(5);
        add(6);
      }});
    }};

    List<Map<String, Object>> list = new ArrayList<>();
    list.add(articleMap1);
    list.add(articleMap2);
    return list;
  }

  @GetMapping("/home/ReturnArticleList")
  @ResponseBody
  public List<Article2> showReturnArticleList() {
    Article2 article1 = new Article2(1, "제목1", "내용1", "김철수", new ArrayList<>() {{
      add(1);
      add(2);
      add(3);
    }});

    Article2 article2 = new Article2(2, "제목2", "내용2", "신짱구", new ArrayList<>() {{
      add(4);
      add(5);
      add(6);
    }});

    List<Article2> list = new ArrayList<>();
    list.add(article1);
    list.add(article2);

    return list;
  }

  @GetMapping("/home/personTestcase")
  @ResponseBody
  public String personTestcase() {
    people.add(new Person("홍길동", 11));
    people.add(new Person("홍길순", 22));
    people.add(new Person("임꺽정", 33));

    return "테스트 케이스 추가";
  }
  @GetMapping("/home/addPerson")
  @ResponseBody
  public String addPerson(String name, int age){
    Person p = new Person(name, age);
    System.out.println("p : " + p);

    people.add(p);


    return "%d번 사람이 추가되었습니다.".formatted(p.getId());
  }

  @GetMapping("/home/removePerson")
  @ResponseBody
  public String removePerson(int id){
    /*
    Person target = null;

    for(Person p : people) {
      if(p.getId() == id) {
        target = p;
        break;
      }
    }
    if(target == null) {
      return "%d번 사람이 존재하지 않습니다.".formatted(id);
    }
    //System.out.println(target);
    people.remove(target);
    */
    boolean removed = people.removeIf(person -> person.getId() == id);
    if(!removed) {
      return "%d번 사람이 존재하지 않습니다.".formatted(id);
    }

    return "%d번 사람이 삭제되었습니다.".formatted(id);
  }

  @GetMapping("home/modifyPerson")
  @ResponseBody
  public String modifyPerson(int id, String name, int age) {
    /*
    Person found = null;
    for(Person p : people) {
      if(p.getId() == id) {
        found = p;
        break;
      }
    }
    if(found == null) {
      return "%d번 사람이 존재하지 않습니다.".formatted(id);
    }
    */
    Person found = people.stream()
        .filter(p -> p.getId() == id)  // 해당 녀석이 참인 것만 필터링
        .findFirst() // 찾은 것 중에 하나만 남는데, 그 하나 남은 것을 필터링
        .orElse(null); // 없으면 null을 리턴

    //System.out.println(found);
    if(found == null) {
      return "%d번 사람이 존재하지 않습니다.".formatted(id);
    }
    found.setName(name);
    found.setAge(age);

    return "%d번 사람이 수정되었습니다.".formatted(id);
  }

  @GetMapping("/home/showPeople")
  @ResponseBody
  public List<Person> showPeople(){
    return people;
  }

  @GetMapping("/home/cookie/increase")
  @ResponseBody
  public int showCookieIncrease(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    //int age = Integer.parseInt(req.getParameter("age"));
    //resp.getWriter().append("Hello, you are %d years old".formatted(age));
    //resp.addCookie(new Cookie("age", "15"));
    int countInCookie = 0;
    if(req.getCookies() != null) {
      countInCookie = Arrays.stream(req.getCookies())
          .filter(cookie -> cookie.getName().equals("count"))
          .map(cookie -> cookie.getValue())
          .mapToInt(Integer::parseInt)
          .findAny()
          .orElse(0);
    }
    int newCountInCookie = countInCookie + 1;
    resp.addCookie(new Cookie("count", newCountInCookie + ""));

    return newCountInCookie;
  }

}

class Article {
  private final int id;
  private final String subject;
  private final String content;
  private final String writerName;
  private final List<Integer> articleNo;

  public int getId() {
    return id;
  }

  public String getSubject() {
    return subject;
  }

  public String getContent() {
    return content;
  }

  public String getWriterName() {
    return writerName;
  }

  public List<Integer> getArticleNo() {
    return articleNo;
  }


  public Article(int id, String subject, String content, String writerName, List<Integer> articleNo) {
    this.id = id;
    this.subject = subject;
    this.content = content;
    this.writerName = writerName;
    this.articleNo = articleNo;
  }

  @Override
  public String toString() {
    return "Article{" +
        "id=" + id +
        ", subject='" + subject + '\'' +
        ", content='" + content + '\'' +
        ", writerName='" + writerName + '\'' +
        ", articleNo=" + articleNo +
        '}';
  }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class Article2 {
  int id;
  String subject;
  String content;
  String writerName;
  List<Integer> articleNo;
}

@AllArgsConstructor
@Getter
@ToString
class Person {
  private static int lastId;
  private final int id;
  @Setter
  private String name;
  @Setter
  private int age;

  static {
    lastId = 0;
  }

  public Person(String name, int age) {
    this(++lastId, name, age);
  }
}