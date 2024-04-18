package com.springboot.cloud.nailservice.nail.exception;

import com.springboot.cloud.common.core.exception.BaseException;

public class FileTypeNotAllowedException extends BaseException {
    public FileTypeNotAllowedException() {
        super(FileTypeErrorType.FILE_TYPE_NOT_ALLOWED);
    }

    public FileTypeNotAllowedException(String message) {
        super(FileTypeErrorType.FILE_TYPE_NOT_ALLOWED, message);
    }
}
