package com.xtuone.base.util.akka.msg;

import java.io.Serializable;
import java.util.Date;

/**
 * 更新登录时间的消息
 * @author Zz
 *
 */
public class UpdateLoginTimeMsg implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	public int studentId;
	
	public Date lastLoginTime;

	public UpdateLoginTimeMsg(int studentId, Date lastLoginTime) {
		super();
		this.studentId = studentId;
		this.lastLoginTime = lastLoginTime;
	}
	
	
	
	

}
