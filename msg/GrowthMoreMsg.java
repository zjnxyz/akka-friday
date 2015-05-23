package com.xtuone.base.util.akka.msg;

import java.io.Serializable;

public class GrowthMoreMsg implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String behaviorIds;
	
	private int studentId;
	
	

	public GrowthMoreMsg(String behaviorIds, int studentId) {
		super();
		this.behaviorIds = behaviorIds;
		this.studentId = studentId;
	}

	public String getBehaviorIds() {
		return behaviorIds;
	}

	public void setBehaviorIds(String behaviorIds) {
		this.behaviorIds = behaviorIds;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	

}
