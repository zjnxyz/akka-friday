package com.xtuone.base.util.akka.msg;

import java.io.Serializable;

import com.xtuone.treehole.redisserver.modules.curriculum.task.dao.ITreeholeEvaluateTaskRedisDao;
import com.xtuone.treehole.redisserver.modules.curriculum.treeholeEvaluate.dao.ITreeholeEvaluateRedisDao;
import com.xtuone.treehole.redisserver.modules.curriculum.treeholeEvaluate.mapper.TreeholeEvaluateRedisMapper;
import com.xtuone.treehole.redisserver.modules.global.utils.DataTransferer;

public class TreeholeEvaluateMsg extends BaseMsg implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public final ITreeholeEvaluateTaskRedisDao treeholeEvaluateTaskRedisDao;
	public final ITreeholeEvaluateRedisDao treeholeEvaluateRedisDao;
	public final TreeholeEvaluateRedisMapper treeholeEvaluateRedisMapper;

	public TreeholeEvaluateMsg(long id, DataTransferer dataTransferer,
			ITreeholeEvaluateTaskRedisDao treeholeEvaluateTaskRedisDao,
			ITreeholeEvaluateRedisDao treeholeEvaluateRedisDao,
			TreeholeEvaluateRedisMapper treeholeEvaluateRedisMapper) {
		super(id, dataTransferer);
		this.treeholeEvaluateTaskRedisDao = treeholeEvaluateTaskRedisDao;
		this.treeholeEvaluateRedisDao = treeholeEvaluateRedisDao;
		this.treeholeEvaluateRedisMapper = treeholeEvaluateRedisMapper;
	}

}
