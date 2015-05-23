package com.xtuone.base.util.akka.actor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xtuone.base.util.akka.msg.TreeholeSpamMsg;
import com.xtuone.treehole.redisserver.modules.curriculum.treeholeSpam.models.TreeholeSpam;

import akka.actor.UntypedActor;

/**
 * 同步举报数据
 * @author Zz
 *
 */
@Component("treeholeSpamInsertActor")
@Scope("prototype")
public class TreeholeSpamInsertActor extends UntypedActor {

	@Override
	public void onReceive(Object msg) throws Exception {

		if(msg instanceof TreeholeSpamMsg){
			TreeholeSpamMsg treeholeSpamMsg = (TreeholeSpamMsg) msg;
			TreeholeSpam spam = treeholeSpamMsg.treeholeSpamRedisDao.findById(treeholeSpamMsg.id, 
					treeholeSpamMsg.treeholeSpamRedisMapper);
			if(spam != null){
				if (treeholeSpamMsg.dataTransferer.saveFromRedisToMysql(spam) > 0) {
					System.out.println("spam同步成功："+treeholeSpamMsg.id);
				}else{
					Map<String, String> map = new HashMap<String, String>();
					map.put(treeholeSpamMsg.id + "", 1 + "");
					treeholeSpamMsg.treeholeSpamTaskRedisDao.updateByMap(map);
					map.clear();
				}
			}
		}
	}

}
