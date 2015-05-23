package com.xtuone.base.util.akka.actor;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xtuone.base.redisService.RedisUtil4Gopush;
import com.xtuone.friday.server.modules.curriculum.student.util.MD5;
import com.xtuone.friday.server.modules.curriculum2.student.util.EncrpytionKey;

import akka.actor.UntypedActor;

@Component("gopushActor")
@Scope("prototype")
public class GopushActor extends UntypedActor {
	
	@Resource
	RedisUtil4Gopush redisUtil4Gopush;
	
	public String key = "UserStatus:";

	@Override
	public void onReceive(Object msg) throws Exception {
		if(msg instanceof String){
			String studentId = (String) msg;
			String md5 = MD5.getMD5(studentId +EncrpytionKey.gopushKey);
			redisUtil4Gopush.setString(key+md5, "1:"+studentId);
		}
	}

}
