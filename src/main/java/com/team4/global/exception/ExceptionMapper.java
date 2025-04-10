package com.team4.global.exception;

import com.team4.domain.member.exception.LoginFailureException;
import com.team4.domain.member.exception.MemberDuplicatedException;
import com.team4.domain.member.exception.MemberNotFoundException;
import com.team4.domain.travel.exception.TravelAuthException;
import com.team4.domain.travel.exception.TravelNotFoundException;
import com.team4.global.jwt.JwtException;
import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.Map;

public class ExceptionMapper { // 예외 객체 -> 예외 상태로 바꿔주는 mapper

    private static final Map<Class<? extends Exception>, ExceptionSituation> mapper = new LinkedHashMap<>();

    static {
        setUpAuthException();
        setUpMemberException();
        setUpTravelException();
    }

    private static void setUpMemberException() {
        mapper.put(MemberNotFoundException.class,
                ExceptionSituation.of("해당 사용자가 존재하지 않습니다.", HttpStatus.NOT_FOUND, 8001));
        mapper.put(MemberDuplicatedException.class,
                ExceptionSituation.of("이미 존재하는 닉네임입니다..", HttpStatus.CONFLICT, 8002));
    }

    private static void setUpAuthException() {
        mapper.put(JwtException.class,
                ExceptionSituation.of("토큰에 문제가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR, 9001));
        mapper.put(LoginFailureException.class,
                ExceptionSituation.of("로그인에 실패했습니다.", HttpStatus.BAD_REQUEST, 9002));
    }

    private static void setUpTravelException() {
        mapper.put(TravelNotFoundException.class,
                ExceptionSituation.of("해당 여행정보가 존재하지 않습니다.", HttpStatus.NOT_FOUND, 2001));
        mapper.put(TravelAuthException.class,
                ExceptionSituation.of("여행정보에 접근할 권한이 없습니다.", HttpStatus.UNAUTHORIZED, 2002));
    }

    private static void setUpPostException() {
        mapper.put(TravelNotFoundException.class,
                ExceptionSituation.of("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND, 3001));
        mapper.put(TravelAuthException.class,
                ExceptionSituation.of("게시글에 접근할 권한이 없습니다.", HttpStatus.UNAUTHORIZED, 3002));
    }


    public static ExceptionSituation getSituationOf(Exception exception) {
        return mapper.get(exception.getClass());
    }

}
