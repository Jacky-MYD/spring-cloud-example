/**
 * @authour Jacky
 * @data Dec 21, 2019
 */
package com.example.project.Entity;

/**
 * @author Jacky
 *
 */
public class Token {
	private String token;
	
	//token创建时间
    private Long tokenCreatedTime;
 
    //失效时间
    private Long tokenExpiryTime;
 
    private String isLogin;

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the tokenCreatedTime
	 */
	public Long getTokenCreatedTime() {
		return tokenCreatedTime;
	}

	/**
	 * @param tokenCreatedTime the tokenCreatedTime to set
	 */
	public void setTokenCreatedTime(Long tokenCreatedTime) {
		this.tokenCreatedTime = tokenCreatedTime;
	}

	/**
	 * @return the tokenExpiryTime
	 */
	public Long getTokenExpiryTime() {
		return tokenExpiryTime;
	}

	/**
	 * @param tokenExpiryTime the tokenExpiryTime to set
	 */
	public void setTokenExpiryTime(Long tokenExpiryTime) {
		this.tokenExpiryTime = tokenExpiryTime;
	}

	/**
	 * @return the isLogin
	 */
	public String getIsLogin() {
		return isLogin;
	}

	/**
	 * @param isLogin the isLogin to set
	 */
	public void setIsLogin(String isLogin) {
		this.isLogin = isLogin;
	}
}
