package com.xtuone.base.util.akka.msg;

import java.io.Serializable;

public class TreeholeVoteMsg implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;

	public TreeholeVoteMsg(int id) {
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
