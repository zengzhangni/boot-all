package com.zzn.pay.enums;

import lombok.Getter;


@Getter
public enum PayChannelType {

    /**
     * 支付目标类
     */
    tl("TlPayService" , "通联支付");
    /**
     * 支付调用目标类
     */
    private String targetClass;

    /**
     * 目标类说明
     */
    private String details;

    PayChannelType(String targetClass, String details) {
        this.targetClass = targetClass;
        this.details = details;
    }
}
