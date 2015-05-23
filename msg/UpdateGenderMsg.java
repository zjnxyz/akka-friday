package com.xtuone.base.util.akka.msg;

import java.io.Serializable;

/**
 * 更新用户性别的消息
 * @author Zz
 *
 */
public class UpdateGenderMsg implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public int studentId;
	
	public int gender;

	public UpdateGenderMsg(int studentId, int gender) {
		super();
		this.studentId = studentId;
		this.gender = gender;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}
	
	

}
