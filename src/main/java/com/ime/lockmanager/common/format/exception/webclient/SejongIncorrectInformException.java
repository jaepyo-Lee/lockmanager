package com.ime.lockmanager.common.format.exception.webclient;

import com.ime.lockmanager.common.format.exception.ApplicationRunException;
import com.ime.lockmanager.common.format.exception.ErrorEnumCode;
import com.ime.lockmanager.common.format.exception.webclient.errorCode.WebClientErrorCode;
import net.bytebuddy.asm.Advice;

import static com.ime.lockmanager.common.format.exception.webclient.errorCode.WebClientErrorCode.SEJONG_INCORRECT_INFORM;

public class SejongIncorrectInformException extends ApplicationRunException {
    private static final ErrorEnumCode CODE = SEJONG_INCORRECT_INFORM;
    public SejongIncorrectInformException(){
        this(CODE);
    }
    private SejongIncorrectInformException(ErrorEnumCode CODE){
        super(CODE);
    }
}
