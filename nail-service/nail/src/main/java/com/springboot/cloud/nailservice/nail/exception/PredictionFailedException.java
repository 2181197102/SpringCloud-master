package com.springboot.cloud.nailservice.nail.exception;

import com.springboot.cloud.common.core.exception.BaseException;

public class PredictionFailedException extends BaseException {

    public PredictionFailedException() {
        super(PredictionErrorType.PREDICTION_FAILED);
    }

    public PredictionFailedException(String message) {
        super(PredictionErrorType.PREDICTION_FAILED, message);
    }
}
