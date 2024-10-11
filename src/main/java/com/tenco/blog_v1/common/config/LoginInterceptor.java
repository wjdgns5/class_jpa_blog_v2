package com.tenco.blog_v1.common.config;

import com.tenco.blog_v1.common.errors.Exception401;
import com.tenco.blog_v1.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

// IOC 안한 상태 이다. (스프링이 실행될 때 메모리에 안띄움)
public class LoginInterceptor  implements HandlerInterceptor {


    /**
     * 컨트롤러 메서드 호출 전에 실행 되는 메서드 이다.
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler chosen handler to execute, for type and/or instance evaluation
     * @return
     * @throws Exception
     */
    // 컨트롤러 타기전에 미리 동작한다.
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("LoginInterceptor preHandle 실행");
        // 로그인 여부 판단을 한다.
        HttpSession session = request.getSession(false); // 기존 만들어진 세션이 없다면 null을 반환 한다.
        if(session == null) {
            throw new Exception401("로그인이 필요 합니다");
        }

        // 키 - 값 --> 세션 메모리지에 저장 방식은 map 구조로 저장한다. (sessionUser) 문자열 사용 중
        User sessionUser =  (User) session.getAttribute("sessionUser");
        if(sessionUser == null) {
            throw  new Exception401("로그인이 필요 합니다");
        }

        // return false <- 이면 컨트롤러에 들어가지 않는다.
        return true;
      //  return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    /**
     * 컨트롤러 실행 후, 뷰 렌더링되기(리졸버 타기) 전에 실행되는 메서드
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler the handler (or {@link HandlerMethod}) that started asynchronous
     * execution, for type and/or instance examination
     * @param modelAndView the {@code ModelAndView} that the handler returned
     * (can also be {@code null})
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 뷰가 렌더링 된 후 실행되는 메서드 
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler the handler (or {@link HandlerMethod}) that started asynchronous
     * execution, for type and/or instance examination
     * @param ex any exception thrown on handler execution, if any; this does not
     * include exceptions that have been handled through an exception resolver
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
