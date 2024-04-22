package com.springboot.cloud.nailservice.nail.exception;

import com.springboot.cloud.common.core.exception.BaseException;

public class FindNoImagePathFromRedisException extends BaseException {

        public FindNoImagePathFromRedisException() {
            super(PredictionErrorType.FIND_NO_IMAGE_PATH_FROM_REDIS);
        }

        public FindNoImagePathFromRedisException(String message) {
            super(PredictionErrorType.FIND_NO_IMAGE_PATH_FROM_REDIS, message);
        }
}
