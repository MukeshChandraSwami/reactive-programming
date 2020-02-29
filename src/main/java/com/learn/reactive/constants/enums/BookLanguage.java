package com.learn.reactive.constants.enums;

import lombok.Getter;

@Getter
public enum BookLanguage {

    EN("English"),HI("Hindi");

    BookLanguage(String lang) {
        this.lang = lang;
    }

    private String lang;
}
