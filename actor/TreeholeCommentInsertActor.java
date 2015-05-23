package com.xtuone.base.util.akka.actor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xtuone.base.util.akka.msg.TreeholeCommentMsg;
import com.xtuone.treehole.redisserver.modules.curriculum.treeholeComment.models.TreeholeComment;

import akka.actor.UntypedActor;

/**
 * 异步写入评论
 * @author Zz
 *
 */
@Component("treeholeCommentInsertActor")
@Scope("prototype")
public class TreeholeCommentInsertActor extends UntypedActor {

	@Override
	public void onReceive(Object msg) throws Exception {
		if(msg instanceof TreeholeCommentMsg){
			TreeholeCommentMsg treeholeCommentMsg = (TreeholeCommentMsg) msg;
			TreeholeComment comment = treeholeCommentMsg.treeholeCommentRedisDao.findById(treeholeCommentMsg.id, 
					treeholeCommentMsg.treeholeCommentRedisMapper);
			if(comment != null){
				if ( treeholeCommentMsg.dataTransferer.saveFromRedisToMysql(comment) > 0) {
					System.out.println("评论同步成功："+treeholeCommentMsg.id);
				}else{
					Map<String, String> map = new HashMap<String, String>();
					map.put(treeholeCommentMsg.id + "", 1 + "");
					treeholeCommentMsg.treeholeCommentInsertTaskRedisDao.updateByMap(map);
					map.clear();
				}
			}
		}

	}

}
