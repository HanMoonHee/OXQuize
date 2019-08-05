--MEMBER 테이블
CREATE TABLE MEMBER (
    MEMBER_ID       VARCHAR2(50) PRIMARY KEY,   -- 사용자 아이디(기본키)
    MEMBER_PWD      VARCHAR2(50),               -- 사용자 비밀번호
    MEMBER_NAME     VARCHAR2(10),               -- 사용자 이름
    MEMBER_NICKNAME VARCHAR2(20),               -- 사용자 닉네임
    MEMBER_TEL      VARCHAR2(20),               -- 사용자 전화번호
    MEMBER_EMAIL    VARCHAR2(30),               -- 사용자 이메일
    MEMBER_LOGIN    NUMBER(2)                   -- 로그인 여부(로그인 1, 로그아웃 0)
);

--QUESTION 테이블
CREATE TABLE QUESTION (
    QUSET_NO    NUMBER(5) PRIMARY KEY,          -- 문제 번호(기본키)
    QUESTION    VARCHAR2(100),                  -- 문제 내용
    ANSWER      VARCHAR2(10)                    -- 정답(O, X)   
);

--GAME 테이블
CREATE TABLE GAME (
    MEMBER_ID   VARCHAR2(50) PRIMARY KEY,       -- 사용자 아이디
    WINCOUNT    NUMBER(10),                     -- 1등 횟수
    O_COUNT     NUMBER(10),                     -- 맞춘 개수
    X_COUNT     NUMBER(10)                      -- 틀린 개수
);

--ROOM 테이블
CREATE TABLE ROOM (
    ROOM_NO     NUMBER(10) PRIMARY KEY,         -- 게임방 번호
    ROOM_NAME   VARCHAR2(50),                   -- 게임방 이름
    ROOM_PWD    VARCHAR2(50),                   -- 게임방 비밀번호
    ROOM_LEADER VARCHAR2(20),                   -- 게임방 리더(리더가 게임시작)
    ROOM_LIMIT  NUMBER(5)                       -- 게임방 최대 인원수
);
