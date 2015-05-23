package com.xtuone.base.util.akka.msg;

import java.io.Serializable;

public class GrowthMsg implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int behaviorId;
	
	private int studentId;
	
	public GrowthMsg(int studentId,int behaviorId){
		this.behaviorId = behaviorId;
		this.studentId = studentId;
	}

	public int getBehaviorId() {
		return behaviorId;
	}

	public void setBehaviorId(int behaviorId) {
		this.behaviorId = behaviorId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

}
