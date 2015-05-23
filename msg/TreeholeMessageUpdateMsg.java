package com.xtuone.base.util.akka.msg;

import java.io.Serializable;

import com.xtuone.treehole.redisserver.modules.curriculum.task.dao.ITreeholeMessageUpdateTaskRedisDao;
import com.xtuone.treehole.redisserver.modules.curriculum.task.dao.ITreeholeMessageUpdateTaskTimelineDao;
import com.xtuone.treehole.redisserver.modules.curriculum.treeholeMessage.dao.ITreeholeMessageRedisDao;
import com.xtuone.treehole.redisserver.modules.curriculum.treeholeMessage.mapper.TreeholeMessageRedisMapper;
import com.xtuone.treehole.redisserver.modules.global.utils.DataTransferer;

public class TreeholeMessageUpdateMsg extends BaseMsg implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public final ITreeholeMessageRedisDao treeholeMessageRedisDao;
	
	public final TreeholeMessageRedisMapper treeholeMessageRedisMapper;
	
	public final ITreeholeMessageUpdateTaskRedisDao treeholeMessageUpdateTaskRedisDao;
	

	public TreeholeMessageUpdateMsg(long id, DataTransferer dataTransferer,
			ITreeholeMessageRedisDao treeholeMessageRedisDao,
			TreeholeMessageRedisMapper treeholeMessageRedisMapper,
			ITreeholeMessageUpdateTaskRedisDao treeholeMessageUpdateTaskRedisDao) {
		super(id, dataTransferer);
		this.treeholeMessageRedisDao = treeholeMessageRedisDao;
		this.treeholeMessageRedisMapper = treeholeMessageRedisMapper;
		this.treeholeMessageUpdateTaskRedisDao = treeholeMessageUpdateTaskRedisDao;
		
	}

}
