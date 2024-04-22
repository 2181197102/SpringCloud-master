package com.springboot.cloud.nailservice.nail.exception;

import com.springboot.cloud.common.core.exception.ErrorType;
import lombok.Getter;

@Getter
public enum PredictionErrorType implements ErrorType {

    FIND_NO_IMAGE_PATH_FROM_REDIS("050100", "在redis中未找到图片路径！"),
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
