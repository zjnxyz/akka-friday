package com.xtuone.base.util.akka.actor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xtuone.base.util.akka.msg.TreeholeEvaluateMsg;
import com.xtuone.treehole.redisserver.modules.curriculum.treeholeEvaluate.models.TreeholeEvaluate;

import akka.actor.UntypedActor;
/**
 * 呵呵数量同步
 * @author Zz
 *
 */
@Component("treeholeEvaluateInsertActor")
@Scope("prototype")
public class TreeholeEvaluateInsertActor extends UntypedActor {

	@Override
	public void onReceive(Object msg) throws Exception {
		if(msg instanceof TreeholeEvaluateMsg){
			TreeholeEvaluateMsg treeholeEvaluateMsg = (TreeholeEvaluateMsg) msg;
			TreeholeEvaluate evaluate = treeholeEvaluateMsg.treeholeEvaluateRedisDao.findById(treeholeEvaluateMsg.id, 
					treeholeEvaluateMsg.treeholeEvaluateRedisMapper);
			if(evaluate != null){
				if (treeholeEvaluateMsg.dataTransferer.saveFromRedisToMysql(evaluate) > 0) {
					System.out.println("呵呵数量同步："+treeholeEvaluateMsg.id);
				}else{
					Map<String, String> map = new HashMap<String, String>();
					map.put(treeholeEvaluateMsg.id + "", 1 + "");
					treeholeEvaluateMsg.treeholeEvaluateTaskRedisDao.updateByMap(map);
					map.clear();
					
				}
			}
		}

	}

}
