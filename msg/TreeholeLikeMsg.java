package com.xtuone.base.util.akka.msg;

import java.io.Serializable;

import com.xtuone.treehole.redisserver.modules.curriculum.task.dao.ITreeholeLikeTaskRedisDao;
import com.xtuone.treehole.redisserver.modules.curriculumV2.treeholeLike.dao.ITreeholeLikeRedisDao;
import com.xtuone.treehole.redisserver.modules.curriculumV2.treeholeLike.mapper.TreeholeLikeRedisMapper2;
import com.xtuone.treehole.redisserver.modules.global.utils.DataTransferer;

public class TreeholeLikeMsg extends BaseMsg implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public final ITreeholeLikeRedisDao treeholeLikeRedisDao;
	
	public final ITreeholeLikeTaskRedisDao treeholeLikeTaskRedisDao;
	
	public final TreeholeLikeRedisMapper2 treeholeLikeRedisMapper2;

	public TreeholeLikeMsg(long id, DataTransferer dataTransferer,
			ITreeholeLikeRedisDao treeholeLikeRedisDao,
			ITreeholeLikeTaskRedisDao treeholeLikeTaskRedisDao,
			TreeholeLikeRedisMapper2 treeholeLikeRedisMapper2) {
		
		super(id, dataTransferer);
		this.treeholeLikeRedisDao =  treeholeLikeRedisDao;
		this.treeholeLikeTaskRedisDao = treeholeLikeTaskRedisDao;
		
		this.treeholeLikeRedisMapper2 = treeholeLikeRedisMapper2;
	
	}

}
