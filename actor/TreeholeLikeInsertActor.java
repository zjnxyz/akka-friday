package com.xtuone.base.util.akka.actor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xtuone.base.util.akka.msg.TreeholeLikeMsg;
import com.xtuone.treehole.redisserver.modules.curriculumV2.treeholeLike.models.redis.TreeholeLike;

import akka.actor.UntypedActor;

/**
 * 同步插入喜欢数据
 * @author Zz
 */
@Component("treeholeLikeInsertActor")
@Scope("prototype")
public class TreeholeLikeInsertActor extends UntypedActor {

	@Override
	public void onReceive(Object msg) throws Exception {

		if(msg instanceof TreeholeLikeMsg){
			TreeholeLikeMsg treeholeLikeMsg = (TreeholeLikeMsg) msg;
			TreeholeLike treeholeLike = treeholeLikeMsg.treeholeLikeRedisDao.findById(treeholeLikeMsg.id, treeholeLikeMsg.treeholeLikeRedisMapper2);
			if(treeholeLike != null){
				if(treeholeLikeMsg.dataTransferer.saveFromLikeRedisToMysql(treeholeLike) > 0){
					System.out.println("like同步成功:"+treeholeLikeMsg.id);
				}else{
					Map<String, String> map = new HashMap<String, String>();
					map.put(treeholeLikeMsg.id + "", 1 + "");
					treeholeLikeMsg.treeholeLikeTaskRedisDao.updateByMap(map);
					map.clear();
				}
			}
		}
	}

}
