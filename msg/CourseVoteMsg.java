package com.xtuone.base.util.akka.msg;

import java.io.Serializable;

public class CourseVoteMsg implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public int id;
	
	public CourseVoteMsg(int id){
		this.id = id;
	}

}
