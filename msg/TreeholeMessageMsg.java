package com.xtuone.base.util.akka.msg;

import java.io.Serializable;

import com.xtuone.treehole.redisserver.modules.curriculum.task.dao.ITreeholeMessageInsertTaskRedisDao;
import com.xtuone.treehole.redisserver.modules.curriculum.treeholeMessage.dao.ITreeholeMessageRedisDao;
import com.xtuone.treehole.redisserver.modules.curriculum.treeholeMessage.mapper.TreeholeMessageRedisMapper;
import com.xtuone.treehole.redisserver.modules.global.utils.DataTransferer;

public class TreeholeMessageMsg extends BaseMsg implements Serializable {

	private static final long serialVersionUID = -8728770265230800806L;
		
	public final ITreeholeMessageRedisDao treeholeMessageRedisDao;
	
	public final TreeholeMessageRedisMapper treeholeMessageRedisMapper;
	
	public final ITreeholeMessageInsertTaskRedisDao treeholeMessageInsertTaskRedisDao;
	
	public TreeholeMessageMsg(long id,DataTransferer dataTransferer,
			ITreeholeMessageRedisDao treeholeMessageRedisDao,
			TreeholeMessageRedisMapper treeholeMessageRedisMapper,
			ITreeholeMessageInsertTaskRedisDao treeholeMessageInsertTaskRedisDao){
		super(id, dataTransferer);
		this.treeholeMessageRedisDao = treeholeMessageRedisDao;
		this.treeholeMessageRedisMapper = treeholeMessageRedisMapper;
		this.treeholeMessageInsertTaskRedisDao = treeholeMessageInsertTaskRedisDao;
	}
	

}
