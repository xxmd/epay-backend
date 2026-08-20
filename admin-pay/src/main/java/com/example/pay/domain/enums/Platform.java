package com.example.pay.domain.enums;

import com.example.common.model.annotation.ExportEnum;
import com.example.common.model.enums.WithLabelEnum;

@ExportEnum
public enum Platform implements WithLabelEnum {

    ANDROID("安卓"),
    IOS("苹果"),
    WINDOWS("Windows"),
    ;
    private String label;

    Platform(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
