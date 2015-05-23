package com.xtuone.base.util.akka.msg;

import java.io.Serializable;

import com.xtuone.treehole.redisserver.modules.curriculum.task.dao.ITreeholeCommentInsertTaskRedisDao;
import com.xtuone.treehole.redisserver.modules.curriculum.task.dao.ITreeholeCommentUpdateTaskRedisDao;
import com.xtuone.treehole.redisserver.modules.curriculum.treeholeComment.dao.ITreeholeCommentRedisDao;
import com.xtuone.treehole.redisserver.modules.curriculum.treeholeComment.mapper.TreeholeCommentRedisMapper;
import com.xtuone.treehole.redisserver.modules.global.utils.DataTransferer;

/**
 * 同步写评论的消息
 * @author Zz
 *
 */
public class TreeholeCommentUpdateMsg extends BaseMsg implements Serializable {
	private static final long serialVersionUID = 1L;

	public final TreeholeCommentRedisMapper treeholeCommentRedisMapper;
	
	public final ITreeholeCommentRedisDao treeholeCommentRedisDao;
	
	public final ITreeholeCommentUpdateTaskRedisDao treeholeCommentUpdateTaskRedisDao;
	
	public TreeholeCommentUpdateMsg(long id,DataTransferer dataTransferer,
			TreeholeCommentRedisMapper treeholeCommentRedisMapper,
			 ITreeholeCommentRedisDao treeholeCommentRedisDao,
			 ITreeholeCommentUpdateTaskRedisDao treeholeCommentUpdateTaskRedisDao ){
		
		super(id, dataTransferer);
		this.treeholeCommentRedisMapper = treeholeCommentRedisMapper;
		this.treeholeCommentRedisDao = treeholeCommentRedisDao;
		this.treeholeCommentUpdateTaskRedisDao = treeholeCommentUpdateTaskRedisDao;
	}

}
