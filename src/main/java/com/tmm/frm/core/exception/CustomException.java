package com.tmm.frm.core.exception;

/**
 * Custom Exception object to handle speific exceptions that we want to throw
 * 
 * @author robert.hinds
 */
public class CustomException extends Exception {

	private static final long serialVersionUID = -7989054432428690026L;
	private CustomExceptionCode exceptionCode;
	private Throwable cause;
	private String contextualMessage;

	public CustomException(CustomExceptionCode theExceptionCode, Throwable theCause) {
		super(theExceptionCode.getDescription());
		setExceptionCode(theExceptionCode);
		setCause(theCause);
	}

	public CustomException(CustomExceptionCode theExceptionCode, String theContextualMessage) {
		super(theExceptionCode.getDescription() + (theContextualMessage != null ? theContextualMessage : "."));
		setExceptionCode(theExceptionCode);
		setContextualMessage(theContextualMessage);
	}

	public CustomException(CustomExceptionCode theExceptionCode, String theContextualMessage, Throwable theCause) {
		this(theExceptionCode, theContextualMessage);
		setCause(theCause);
	}

	public CustomExceptionCode getExceptionCode() {
		return exceptionCode;
	}

	private void setExceptionCode(CustomExceptionCode exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getContextualMessage() {
		return contextualMessage;
	}

	private void setContextualMessage(String contextualMessage) {
		this.contextualMessage = contextualMessage;
	}

	public Throwable getCause() {
		return cause;
	}

	private void setCause(Throwable cause) {
		this.cause = cause;
	}
}
