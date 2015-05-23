package com.xtuone.base.util.akka.actor;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xtuone.base.util.akka.msg.UpdateGenderMsg;
import com.xtuone.base.util.akka.msg.UpdateLoginTimeMsg;
import com.xtuone.friday.server.modules.curriculum2.location.service.INearStudentService;

import akka.actor.UntypedActor;

@Component("studentUpdateActor")
@Scope("prototype")
public class StudentUpdateActor extends UntypedActor {
	@Resource
	INearStudentService nearStudentService;

	@Override
	public void onReceive(Object msg) throws Exception {
		if(msg instanceof UpdateGenderMsg){
			UpdateGenderMsg updateGender = (UpdateGenderMsg) msg;
			nearStudentService.updateGender(updateGender.studentId, updateGender.gender);
		}else if(msg instanceof UpdateLoginTimeMsg){
			UpdateLoginTimeMsg updateLoginTimeMsg = (UpdateLoginTimeMsg) msg;
			nearStudentService.updateLastLoginTime(updateLoginTimeMsg.studentId, updateLoginTimeMsg.lastLoginTime);
		}
	}

}
