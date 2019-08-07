--MEMBER 테이블
CREATE TABLE MEMBER (
    ID       VARCHAR2(50) PRIMARY KEY,   -- 사용자 아이디(기본키)
    PWD      VARCHAR2(50),               -- 사용자 비밀번호
    NICKNAME VARCHAR2(20),               -- 사용자 닉네임
    TEL      VARCHAR2(20),               -- 사용자 전화번호
    EMAIL    VARCHAR2(30),               -- 사용자 이메일
    LOGIN    NUMBER(2) DEFAULT 0,                  -- 로그인 여부(로그인 1, 로그아웃 0)        -- 0 
    O_CNT    NUMBER(30) DEFAULT 0,                -- 맞춘개수                              
    X_CNT    NUMBER(30) DEFAULT 0,                -- 틀린개수
    WIN_CNT  NUMBER(30) DEFAULT 0
);