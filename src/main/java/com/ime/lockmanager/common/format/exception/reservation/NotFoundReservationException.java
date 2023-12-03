package com.ime.lockmanager.common.format.exception.reservation;

import com.ime.lockmanager.common.format.exception.ApplicationRunException;
import com.ime.lockmanager.common.format.exception.ErrorEnumCode;

import static com.ime.lockmanager.common.format.exception.reservation.errorCode.ReservationErrorCode.NOT_FOUND_RESERVATION;

public class NotFoundReservationException extends ApplicationRunException {
    private final static ErrorEnumCode CODE=NOT_FOUND_RESERVATION;

    private NotFoundReservationException(ErrorEnumCode errorEnumCode) {
        super(errorEnumCode);
    }
    public NotFoundReservationException(){
        this(CODE);
    }
}
