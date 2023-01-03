package com.example.fastcampusmysql.util;

public record CursorRequest(Long key, int size) {
    // 커서키는 중복키가 없는 유니크한 값으로 + 인덱스를 고려
    public static final Long NONE_KEY = -1L;
    public Boolean hasKey() {
        return key != null;
    }
    public CursorRequest next(Long key){
        return new CursorRequest(key, size);
    }
}
