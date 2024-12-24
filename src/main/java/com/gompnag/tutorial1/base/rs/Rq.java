package com.gompnag.tutorial1.base.rs;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Arrays;

@Component
@RequestScope
@AllArgsConstructor
public class Rq {
  private final HttpServletRequest req;
  private final HttpServletResponse resp;


  public void setCookie(String name, long value) {
    setCookie(name, value +"");
  }
  public void setCookie(String name, String value) {
    resp.addCookie(new Cookie(name, value));
  }

  public boolean removeCookie(String name) {
    Cookie cookie = Arrays.stream(req.getCookies())
        .filter(c-> c.getName().equals(name))
        .findFirst()
        .orElse(null);

    if(cookie != null) {
      cookie.setMaxAge(0);
      resp.addCookie(cookie);
      return true;
    }

    /*  위내용으로 코딩 변경  31강
    if(req.getCookies() != null) {
      Arrays.stream(req.getCookies())
          .filter(cookie -> cookie.getName().equals(name))
          .forEach(cookie -> {
            cookie.setMaxAge(0);  // 쿠키 만료
            resp.addCookie(cookie);
          });
      return Arrays.stream(req.getCookies()).anyMatch(cookie -> cookie.getName().equals(name));
    }*/
    return false;
  }

  public long getCookieAsLong(String name, long defaultValue) {
    String value = getCookie(name, null);
    if(value == null) return defaultValue;

    try {
      return Long.parseLong(value);
    }
    catch (NumberFormatException e) {
      return  defaultValue;
    }
  }

  private String getCookie(String name, String defaultValue) {
    if(req.getCookies() == null) return defaultValue;

    return Arrays.stream(req.getCookies())
        .filter(cookie -> cookie.getName().equals(name))
        .map(Cookie::getValue)
        .findFirst()
        .orElse(defaultValue);
  }

  private String getSessionAsStr(String name, String defaultValue) {
    try {
      String value = (String) req.getSession().getAttribute(name);

      if(value == null) return defaultValue;

      return value;
    } catch (Exception e) {
      return defaultValue;
    }
  }
  public long getSessionAsLong(String name, long defaultValue) {
    try {
      long value = (long) req.getSession().getAttribute(name);

      return value;
    } catch (Exception e) {
      return defaultValue;
    }
  }
  public void setSession(String name, long value) {
    HttpSession session = req.getSession();
    session.setAttribute(name, value);

    //System.out.println(getSessionDebugInfo());
  }

  public boolean removeSession(String name) {
    HttpSession session = req.getSession();

    // 세션을 가져왔는데 없으면 삭제를 못했다는 의미
    if(session.getAttribute(name) == null) return false;

    session.removeAttribute(name);

    return true;
  }

  public String getSessionDebugInfo() {
    HttpSession session = req.getSession();
    String sessionId = session.getId();

    var attributeNames = session.getAttributeNames();

    StringBuilder sessionInfo = new StringBuilder("Session ID: " + sessionId + "\nAttributes:\n");
    while (attributeNames.hasMoreElements()) {
      String name = attributeNames.nextElement();
      Object value = session.getAttribute(name);
      sessionInfo.append(name).append(": ").append(value).append("\n");
    }

    return sessionInfo.toString();
  }

  public boolean isLogined() {
    long loginedMemberId = getSessionAsLong("loginedMemberId", 0);

    return loginedMemberId > 0;
  }

  public boolean isLogout() {
    return  !isLogined();
  }


  public long getLoginedMember() {
    return getSessionAsLong("loginedMemberId", 0);
  }
}
