package com.xtuone.base.util.akka.msg;

import com.xtuone.treehole.redisserver.modules.global.utils.DataTransferer;

public class BaseMsg {
	
	public final long id;
	
	public final DataTransferer dataTransferer;
	
	public BaseMsg(final long id,final DataTransferer dataTransferer){
		this.id = id;
		this.dataTransferer = dataTransferer;
		
	}

}
