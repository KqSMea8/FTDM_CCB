package com.sunyard.sunfintech.core.exception;


/**
 * 数据转换异常
 * @author heroy
 * @version 20170401
 */
public class DataParseException extends BaseException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public DataParseException() {
	}

	public DataParseException(Throwable ex) {
		super(ex);
	}

	public DataParseException(String message) {
		super(message);
	}

	public DataParseException(String message, Throwable ex) {
		super(message, ex);
	}

}
