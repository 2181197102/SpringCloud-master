package com.springboot.cloud.nsclcservice.nsclc.exception;

import com.springboot.cloud.common.core.exception.BaseException;

public class FileSaveErrorException extends BaseException {
    public FileSaveErrorException() {
        super(FileTypeErrorType.FILE_SAVE_ERROR);
    }

    public FileSaveErrorException(String message) {
        super(FileTypeErrorType.FILE_SAVE_ERROR, message);
    }
}
