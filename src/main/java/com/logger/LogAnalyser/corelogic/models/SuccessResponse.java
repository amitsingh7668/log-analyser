package com.logger.LogAnalyser.corelogic.models;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class SuccessResponse {

	HttpStatus httpstatus;
	Date date;
	String status;
	Object data;
	String error;

	public SuccessResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SuccessResponse(HttpStatus httpstatus, Date date, String status, Object data,String error) {
		super();
		this.httpstatus = httpstatus;
		this.date = date;
		this.status = status;
		this.data = data;
		this.error= error;
	}

	public HttpStatus getHttpstatus() {
		return httpstatus;
	}

	public void setHttpstatus(HttpStatus httpstatus) {
		this.httpstatus = httpstatus;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	

}
