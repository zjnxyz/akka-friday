package com.xtuone.base.util.akka.msg;

import java.io.Serializable;

public class CourseCommentUpdateMsg implements Serializable {

	private static final long serialVersionUID = 1L;

	public int id;
	public CourseCommentUpdateMsg(int id){
		this.id = id;
	}
}
