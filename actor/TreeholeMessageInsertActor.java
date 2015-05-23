package com.xtuone.base.util.akka.actor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xtuone.base.util.akka.msg.TreeholeMessageMsg;
import com.xtuone.treehole.redisserver.modules.curriculum.treeholeMessage.models.TreeholeMessage;

import akka.actor.UntypedActor;
@Component("treeholeMessageInsertActor")
@Scope("prototype")
public class TreeholeMessageInsertActor extends UntypedActor {
	
	@Override
	public void preStart() throws Exception {
		super.preStart();
		System.out.println("启动TreeholeMessageInsertActor...");
	}

	@Override
	public void onReceive(Object msg) throws Exception {

		if(msg instanceof TreeholeMessageMsg){
			TreeholeMessageMsg treeholeMessageMsg = (TreeholeMessageMsg) msg;
			TreeholeMessage message = treeholeMessageMsg.treeholeMessageRedisDao.findById(treeholeMessageMsg.id, treeholeMessageMsg.treeholeMessageRedisMapper);
			if(message != null){
				if (treeholeMessageMsg.dataTransferer.saveFromRedisToMysql(message) > 0) {
					System.out.println("message同步成功："+treeholeMessageMsg.id);
				}else{
					//保存到同步列表中
					System.out.println("message同步失败，保存到同步列表中："+treeholeMessageMsg.id);
					Map<String, String> map = new HashMap<String, String>();
					map.put(treeholeMessageMsg.id + "", 1 + "");
					treeholeMessageMsg.treeholeMessageInsertTaskRedisDao.updateByMap(map);
					map.clear();
				}
			}
		}
	}

}
