package com.my.document.generator.enums;

public enum UserAgentEnum {

    IPHONE("Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1");

    private final String value;
    UserAgentEnum(String value){
        this.value = value;
    }

    public String value(){
        return this.value;
    }

}