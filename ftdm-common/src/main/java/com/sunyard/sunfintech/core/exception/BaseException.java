/**
 * 
 */
package com.sunyard.sunfintech.core.exception;

/**
 * 基础异常
 * @author heroy
 * @version 20170401
 */
public abstract class BaseException extends RuntimeException {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public BaseException() {
	}

	public BaseException(Throwable ex) {
		super(ex);
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(String message, Throwable ex) {
		super(message, ex);
	}

}
