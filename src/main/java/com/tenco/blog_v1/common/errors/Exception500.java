package com.tenco.blog_v1.common.errors;

public class Exception500 extends RuntimeException {
    // throw new Exception500("야, 너 잘못 던졌어"); <-- 사용하는 시점에 호출 모습
    public Exception500(String msg) {
        super(msg);
    }
}
