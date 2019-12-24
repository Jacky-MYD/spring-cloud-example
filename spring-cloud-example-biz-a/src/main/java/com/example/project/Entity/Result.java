/**
 * @authour Jacky
 * @data Dec 20, 2019
 */
package com.example.project.Entity;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Jacky
 *
 */
public class Result<T> {
	private Integer errCode;
	
	private String errMsg;
	
	private T data;
	
	/**
	 * @return the errCode
	 */
	public Integer getErrCode() {
		return errCode;
	}

	/**
	 * @param errCode the errCode to set
	 */
	public void setErrCode(Integer errCode) {
		this.errCode = errCode;
	}

	/**
	 * @return the errMsg
	 */
	public String getErrMsg() {
		return errMsg;
	}

	/**
	 * @param errMsg the errMsg to set
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}
	
	@Autowired
	public String toString() {
		return "Result{" +
				"errCode=" + errCode +
				", errMessage='" + errMsg + '\'' +
				", data=" + data +
				'}';
	}
}
