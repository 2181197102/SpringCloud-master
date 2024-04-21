package com.springboot.cloud.nailservice.nail.exception;

import com.springboot.cloud.common.core.exception.ErrorType;

public enum PredictionErrorType implements ErrorType {
    PREDICTION_FAILED("050400", "预测失败！");

    /**
     * 错误类型码
     */
    private String code;
    /**
     * 错误类型描述信息
     */
    private String mesg;

    PredictionErrorType(String code, String mesg) {
        this.code = code;
        this.mesg = mesg;
    }
}
