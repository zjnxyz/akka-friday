package com.xtuone.base.util.akka.msg;

import java.io.Serializable;

import com.xtuone.treehole.redisserver.modules.curriculum.task.dao.ITreeholeSpamTaskRedisDao;
import com.xtuone.treehole.redisserver.modules.curriculum.treeholeSpam.dao.ITreeholeSpamRedisDao;
import com.xtuone.treehole.redisserver.modules.curriculum.treeholeSpam.mapper.TreeholeSpamRedisMapper;
import com.xtuone.treehole.redisserver.modules.global.utils.DataTransferer;

public class TreeholeSpamMsg extends BaseMsg implements Serializable{
	private static final long serialVersionUID = 1L;

	public final ITreeholeSpamTaskRedisDao treeholeSpamTaskRedisDao;
	
	public final ITreeholeSpamRedisDao treeholeSpamRedisDao;
	
	public final TreeholeSpamRedisMapper treeholeSpamRedisMapper;

	public TreeholeSpamMsg(long id, DataTransferer dataTransferer,
			ITreeholeSpamTaskRedisDao treeholeSpamTaskRedisDao,
			ITreeholeSpamRedisDao treeholeSpamRedisDao,
			TreeholeSpamRedisMapper treeholeSpamRedisMapper) {
		super(id, dataTransferer);
		this.treeholeSpamRedisDao = treeholeSpamRedisDao;
		this.treeholeSpamTaskRedisDao = treeholeSpamTaskRedisDao;
		this.treeholeSpamRedisMapper = treeholeSpamRedisMapper;
		
		
	}

}
