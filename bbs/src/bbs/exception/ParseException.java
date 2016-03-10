package bbs.exception;

import java.io.IOException;

public class ParseException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ParseException(IOException cause) {
		super(cause);
	}

}
