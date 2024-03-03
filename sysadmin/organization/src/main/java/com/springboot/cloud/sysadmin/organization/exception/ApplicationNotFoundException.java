package com.springboot.cloud.sysadmin.organization.exception;

import com.springboot.cloud.common.core.exception.BaseException;

public class ApplicationNotFoundException extends BaseException {
    public ApplicationNotFoundException() {
        super(OrganizationErrorType.APPLICATION_NOT_FOUND);
    }

    public ApplicationNotFoundException(String message) {
        super(OrganizationErrorType.APPLICATION_NOT_FOUND, message);
    }
}
