package com.xtuone.base.util.akka.actor;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xtuone.base.util.akka.msg.TreeholeVoteOptionMsg;
import com.xtuone.treehole.redisserver.modules.curriculum.task.dao.ITreeholeVoteOptionInsertTaskRedisDao;
import com.xtuone.treehole.redisserver.modules.curriculumV2.TreeholeVoteOption.dao.ITreeholeVoteOptionRedisDao;
import com.xtuone.treehole.redisserver.modules.curriculumV2.TreeholeVoteOption.mapper.TreeholeVoteOptionRedisMapper;
import com.xtuone.treehole.redisserver.modules.curriculumV2.TreeholeVoteOption.model.redis.TreeholeVoteOption;
import com.xtuone.treehole.redisserver.modules.global.utils.DataTransferer;

import akka.actor.UntypedActor;

@Component("treeholeVoteOptionInsertActor")
@Scope("prototype")
public class TreeholeVoteOptionInsertActor extends UntypedActor{
	
	@Resource
	DataTransferer dataTransferer;
	
	@Resource
	private ITreeholeVoteOptionRedisDao treeholeVoteOptionRedisDao;
	
	@Resource
	private TreeholeVoteOptionRedisMapper treeholeVoteOptionRedisMapper;
	
	@Resource
	ITreeholeVoteOptionInsertTaskRedisDao treeholeVoteOptionInsertTaskRedisDao;
	
	@Override
	public void onReceive(Object msg) throws Exception {
		if(msg instanceof TreeholeVoteOptionMsg){
			TreeholeVoteOptionMsg treeholeVoteOptionMsg = (TreeholeVoteOptionMsg) msg;
			TreeholeVoteOption treeholeVoteOption = treeholeVoteOptionRedisDao.findById(treeholeVoteOptionMsg.getId(), 
					treeholeVoteOptionRedisMapper);
			if(treeholeVoteOption != null){
				if(dataTransferer.saveFromVoteOptionRedisToMysql(treeholeVoteOption) > 0){
					System.out.println("投票选项同步成功："+treeholeVoteOptionMsg.getId());
				}else{
					//保存失败，加入到定时保存中
					System.out.println("投票选项数据同步失败："+treeholeVoteOptionMsg.getId());
					//加入到定时任务中
					Map<String, String> map = new HashMap<String, String>();
					map.put(treeholeVoteOptionMsg.getId() + "", 1 + "");
					treeholeVoteOptionInsertTaskRedisDao.updateByMap(map);
				}
			}
		}
		
	}

}
