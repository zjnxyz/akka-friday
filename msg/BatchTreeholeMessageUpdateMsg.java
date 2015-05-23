package com.xtuone.base.util.akka.msg;

import java.io.Serializable;
import java.util.Set;

import com.xtuone.treehole.redisserver.modules.curriculum.task.dao.ITreeholeMessageUpdateTaskRedisDao;
import com.xtuone.treehole.redisserver.modules.curriculum.task.dao.ITreeholeMessageUpdateTaskTimelineDao;
import com.xtuone.treehole.redisserver.modules.curriculum.treeholeMessage.dao.ITreeholeMessageRedisDao;
import com.xtuone.treehole.redisserver.modules.curriculum.treeholeMessage.mapper.TreeholeMessageRedisMapper;
import com.xtuone.treehole.redisserver.modules.global.utils.DataTransferer;

public class BatchTreeholeMessageUpdateMsg implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public final Set<Integer> ids;
	
	public final DataTransferer dataTransferer;
	
	public final ITreeholeMessageRedisDao treeholeMessageRedisDao;
	
	public final TreeholeMessageRedisMapper treeholeMessageRedisMapper;
	
	public final ITreeholeMessageUpdateTaskRedisDao treeholeMessageUpdateTaskRedisDao;
	

	public BatchTreeholeMessageUpdateMsg(Set<Integer> ids, DataTransferer dataTransferer,
			ITreeholeMessageRedisDao treeholeMessageRedisDao,
			TreeholeMessageRedisMapper treeholeMessageRedisMapper,
			ITreeholeMessageUpdateTaskRedisDao treeholeMessageUpdateTaskRedisDao) {
		
		this.ids = ids;
		this.dataTransferer = dataTransferer;
		this.treeholeMessageRedisDao = treeholeMessageRedisDao;
		this.treeholeMessageRedisMapper = treeholeMessageRedisMapper;
		this.treeholeMessageUpdateTaskRedisDao = treeholeMessageUpdateTaskRedisDao;
		
	}

}
