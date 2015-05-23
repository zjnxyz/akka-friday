package com.xtuone.base.util.akka.msg;

import java.io.Serializable;

/**
 * 用户成长值
 * @author Zz
 *
 */
public class StudentGrowthMsg implements Serializable{

	private static final long serialVersionUID = -4665669539823324020L;
	
	public int studentId;
	
	public StudentGrowthMsg(int studentId){
		this.studentId = studentId;
	}

}
