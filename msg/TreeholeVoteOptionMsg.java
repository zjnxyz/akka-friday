package com.xtuone.base.util.akka.msg;

import java.io.Serializable;

public class TreeholeVoteOptionMsg implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	

	public TreeholeVoteOptionMsg(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

}
