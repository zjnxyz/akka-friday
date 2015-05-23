package com.xtuone.base.util.akka.actor;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xtuone.base.util.akka.msg.TreeholeScoreInsertMsg;
import com.xtuone.treehole.redisserver.modules.curriculumV2.treeholeScore.dao.ITreeholeScoreRedisDao;
import com.xtuone.treehole.redisserver.modules.curriculumV2.treeholeScore.mapper.TreeholeScoreRedisMapper;
import com.xtuone.treehole.redisserver.modules.curriculumV2.treeholeScore.models.redis.TreeholeScore;
import com.xtuone.treehole.redisserver.modules.global.utils.DataTransferer;

import akka.actor.UntypedActor;

@Component("treeholeScoreInsertActor")
@Scope("prototype")
public class TreeholeScoreInsertActor extends UntypedActor{

	@Resource
	DataTransferer dataTransferer;
	@Resource
	ITreeholeScoreRedisDao treeholeScoreRedisDao;
	@Resource
	TreeholeScoreRedisMapper treeholeScoreRedisMapper;
	
	@Override
	public void onReceive(Object msg) throws Exception {
		if(msg instanceof TreeholeScoreInsertMsg){
			TreeholeScoreInsertMsg treeholeScoreInsertMsg = (TreeholeScoreInsertMsg) msg;
			TreeholeScore treeholeScore = treeholeScoreRedisDao.findById(treeholeScoreInsertMsg.id, treeholeScoreRedisMapper);
			if(treeholeScore != null) {
				if(dataTransferer.saveFromScoreRedisToMysql(treeholeScore)>0){
					System.out.println("打分数据同步成功："+treeholeScoreInsertMsg.id);
				}else{
					System.out.println("打分数据同步失败："+treeholeScoreInsertMsg.id);
				}
			}
		}
	}

}
