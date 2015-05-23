package com.xtuone.base.util.akka.actor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.xtuone.base.util.akka.msg.BatchTreeholeMessageUpdateMsg;
import com.xtuone.base.util.akka.msg.TreeholeMessageUpdateMsg;
import com.xtuone.treehole.redisserver.modules.curriculum.treeholeMessage.models.TreeholeMessage;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

@Component("treeholeMessageUpdateActor")
@Scope("prototype")
public class TreeholeMessageUpdateActor extends UntypedActor {
	
	@Override
	public void preStart() throws Exception {
		super.preStart();
		System.out.println("启动TreeholeMessageUpdateActor...");
	}

	@Override
	public void onReceive(Object msg) throws Exception {

		if(msg instanceof TreeholeMessageUpdateMsg){
			TreeholeMessageUpdateMsg treeholeMessageUpdateMsg = (TreeholeMessageUpdateMsg) msg;
			TreeholeMessage message = treeholeMessageUpdateMsg.treeholeMessageRedisDao.findById(treeholeMessageUpdateMsg.id, treeholeMessageUpdateMsg.treeholeMessageRedisMapper);
			if(message != null){
				if (treeholeMessageUpdateMsg.dataTransferer.updateFromRedisToMysql(message) > 0) {
					System.out.println("message更新成功："+treeholeMessageUpdateMsg.id);
				}
			}else{
				String readCountStr = treeholeMessageUpdateMsg.treeholeMessageRedisDao.findByFeild(treeholeMessageUpdateMsg.id, "readCount");
				if(!Strings.isNullOrEmpty(readCountStr)){
					message = new TreeholeMessage();
					message.setId((int)treeholeMessageUpdateMsg.id);
					message.setReadCount(Integer.parseInt(readCountStr));
					String isProhibitedStr =  treeholeMessageUpdateMsg.treeholeMessageRedisDao.findByFeild(treeholeMessageUpdateMsg.id, "isProhibited");
					int isProhibited = 0;
					if(!Strings.isNullOrEmpty(isProhibitedStr)){
						isProhibited = Integer.parseInt(isProhibitedStr);
					}
					message.setIsProhibited(isProhibited);
					int hide = 0;
					String hideStr = treeholeMessageUpdateMsg.treeholeMessageRedisDao.findByFeild(treeholeMessageUpdateMsg.id, "hide");
					if(!Strings.isNullOrEmpty(hideStr)){
						hide = Integer.parseInt(hideStr);
					}
					message.setHide(hide);
					treeholeMessageUpdateMsg.dataTransferer.updateTreeholeMessage(message);
					treeholeMessageUpdateMsg.treeholeMessageRedisDao.deleteById(treeholeMessageUpdateMsg.id);
				}else{
					//保存到同步列表中
					System.out.println("message同步失败，保存到同步列表中："+treeholeMessageUpdateMsg.id);
					//加入到另一个同步列表中，里面存很多同步不成功的
					Map<String, String> map = new HashMap<String, String>();
					map.put(treeholeMessageUpdateMsg.id + "", 1 + "");
					treeholeMessageUpdateMsg.treeholeMessageUpdateTaskRedisDao.updateByMap(map);
					map.clear();
				}
//				treeholeMessageUpdateMsg.treeholeMessageUpdateTaskTimelineDao.addMember(1, treeholeMessageUpdateMsg.id, System.currentTimeMillis());
			}
		}else if(msg instanceof BatchTreeholeMessageUpdateMsg){
			System.out.println("--->处理批量--");
			BatchTreeholeMessageUpdateMsg bMsg = (BatchTreeholeMessageUpdateMsg)msg;
			TreeholeMessageUpdateMsg treeholeMessageUpdateMsg;
			for(Integer id:bMsg.ids){
				treeholeMessageUpdateMsg = new TreeholeMessageUpdateMsg(id, bMsg.dataTransferer,
						bMsg.treeholeMessageRedisDao,
						bMsg.treeholeMessageRedisMapper,
						bMsg.treeholeMessageUpdateTaskRedisDao);
				//发送给自己处理
				self().tell(treeholeMessageUpdateMsg, ActorRef.noSender());
				
			}
		}
		
	}

}
