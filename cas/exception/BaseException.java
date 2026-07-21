package com.itechro.cas.exception;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.exception.impl.AppsErrorMessage;

import java.util.List;


public interface BaseException {
	List<AppsErrorMessage> getAppsErrorMessages();

	int getHttpStatus();

	AppsConstants.ResponseStatus getResponseStatus();

	Boolean containsErrorCode(String errorCode);

}
