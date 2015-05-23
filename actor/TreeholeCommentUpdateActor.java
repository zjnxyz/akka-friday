package com.xtuone.base.util.akka.actor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xtuone.base.util.akka.msg.TreeholeCommentUpdateMsg;
import com.xtuone.treehole.redisserver.modules.curriculum.treeholeComment.models.TreeholeComment;

import akka.actor.UntypedActor;

/**
 * 异步写入评论
 * @author Zz
 *
 */
@Component("treeholeCommentUpdateActor")
@Scope("prototype")
public class TreeholeCommentUpdateActor extends UntypedActor {

	@Override
	public void onReceive(Object msg) throws Exception {
		if(msg instanceof TreeholeCommentUpdateMsg){
			TreeholeCommentUpdateMsg treeholeCommentUpdateMsg = (TreeholeCommentUpdateMsg) msg;
			TreeholeComment comment = treeholeCommentUpdateMsg.treeholeCommentRedisDao.findById(treeholeCommentUpdateMsg.id, 
					treeholeCommentUpdateMsg.treeholeCommentRedisMapper);
			if(comment != null){
				if ( treeholeCommentUpdateMsg.dataTransferer.updateFromRedisToMysql(comment) > 0) {
					System.out.println("评论更新成功："+treeholeCommentUpdateMsg.id);
				}else{
					Map<String, String> map = new HashMap<String, String>();
					map.put(treeholeCommentUpdateMsg.id + "", 1 + "");
					treeholeCommentUpdateMsg.treeholeCommentUpdateTaskRedisDao.updateByMap(map);
					map.clear();
				}
			}
		}

	}

}
