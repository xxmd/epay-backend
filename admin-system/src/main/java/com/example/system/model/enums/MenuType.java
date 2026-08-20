package com.example.system.model.enums;

import com.example.common.model.annotation.ExportEnum;
import com.example.common.model.enums.WithLabelEnum;

@ExportEnum
public enum MenuType implements WithLabelEnum {
    CATEGORY("目录"), MENU("菜单"), BUTTON("按钮");
    private String label;

    MenuType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
