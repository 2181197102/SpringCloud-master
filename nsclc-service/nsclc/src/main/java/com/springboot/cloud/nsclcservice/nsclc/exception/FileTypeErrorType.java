package com.springboot.cloud.nsclcservice.nsclc.exception;

import com.springboot.cloud.common.core.exception.ErrorType;
import lombok.Getter;

@Getter
public enum FileTypeErrorType implements ErrorType {

    FILE_TYPE_NOT_ALLOWED("040100", "不支持的上传文件类型！"),

    FILE_SAVE_ERROR("040101","保存文件失败");

    /**
     * 错误类型码
     */
    private String code;
    /**
     * 错误类型描述信息
     */
    private String mesg;

    FileTypeErrorType(String code, String mesg) {
        this.code = code;
        this.mesg = mesg;
    }
}
