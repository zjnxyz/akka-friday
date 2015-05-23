package com.xtuone.base.util.akka.msg;

import java.io.Serializable;

public class CourseCommentMsg implements Serializable {

	private static final long serialVersionUID = 1L;

	public int id;
	public CourseCommentMsg(int id){
		this.id = id;
	}
}
