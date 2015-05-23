package com.xtuone.base.util.akka.msg;

import java.io.Serializable;

public class TreeholeScoreInsertMsg implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public int id;
	public TreeholeScoreInsertMsg(int id){
		this.id = id;
	}

}
