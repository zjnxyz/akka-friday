package com.xtuone.base.util.akka.actor;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xtuone.base.util.akka.msg.TreeholeVoteMsg;
import com.xtuone.treehole.redisserver.modules.curriculum.task.dao.ITreeholeVoteInsertTaskRedisDao;
import com.xtuone.treehole.redisserver.modules.curriculumV2.treeholeVote.dao.ITreeholeVoteRedisDao;
import com.xtuone.treehole.redisserver.modules.curriculumV2.treeholeVote.mapper.TreeholeVoteRedisMapper;
import com.xtuone.treehole.redisserver.modules.curriculumV2.treeholeVote.models.redis.TreeholeVote;
import com.xtuone.treehole.redisserver.modules.global.utils.DataTransferer;

import akka.actor.UntypedActor;
@Component("treeholeVoteInsertActor")
@Scope("prototype")
public class TreeholeVoteInsertActor extends UntypedActor {

	@Resource
	DataTransferer dataTransferer;
	
	@Resource
	ITreeholeVoteRedisDao treeholeVoteRedisDao;
	
	@Resource
	TreeholeVoteRedisMapper treeholeVoteRedisMapper;
	
	@Resource
	ITreeholeVoteInsertTaskRedisDao treeholeVoteInsertTaskRedisDao;
	
	@Override
	public void onReceive(Object msg) throws Exception {
		if( msg instanceof TreeholeVoteMsg){
			TreeholeVoteMsg treeholeVoteMsg = (TreeholeVoteMsg) msg;
			TreeholeVote treeholeVote = treeholeVoteRedisDao.findById(treeholeVoteMsg.getId(), treeholeVoteRedisMapper);
			if(treeholeVote != null){
				if(dataTransferer.saveFromVoteRedisToMySql(treeholeVote) > 0){
					System.out.println("投票数据同步成功："+treeholeVoteMsg.getId());
				}else{
					
					System.out.println("投票数据同步失败："+treeholeVoteMsg.getId());
					//加入到定时任务中
					Map<String, String> map = new HashMap<String, String>();
					map.put(treeholeVoteMsg.getId() + "", 1 + "");
					treeholeVoteInsertTaskRedisDao.updateByMap(map);
				}
			}
		}
	}

}
