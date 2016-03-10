package bbs.beans;

import java.io.Serializable;

public class InsertDateList implements Serializable {
	private static final long serialVersionUID = 1L;

	private String from = "2016/03/09 00:00:00";
	private String to;

	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}

}
