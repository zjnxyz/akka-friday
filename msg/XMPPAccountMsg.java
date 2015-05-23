package com.xtuone.base.util.akka.msg;

import java.io.Serializable;

public class XMPPAccountMsg implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3456384838471479788L;
	
	public XMPPAccountMsg(){
		
	}
	
	public XMPPAccountMsg(int studentId,String superId){
		this.studentId = studentId;
		this.superId = superId;
	}
	
	private int studentId;
	
	private String superId;

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getSuperId() {
		return superId;
	}

	public void setSuperId(String superId) {
		this.superId = superId;
	}

	
	

}
