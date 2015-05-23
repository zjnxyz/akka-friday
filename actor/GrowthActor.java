package com.xtuone.base.util.akka.actor;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xtuone.base.util.akka.msg.GrowthMoreMsg;
import com.xtuone.base.util.akka.msg.GrowthMsg;
import com.xtuone.friday.server.modules.curriculum2.growth.service.IGrowthLocusService;

import akka.actor.UntypedActor;

@Component("growthActor")
@Scope("prototype")
public class GrowthActor extends UntypedActor{
	
	@Resource
	IGrowthLocusService growthLocusService;

	public void onReceive(Object msg) throws Exception {
		if(msg instanceof GrowthMsg){
//			System.out.println("-----*--更新成长值----");
			GrowthMsg growthMsg = (GrowthMsg) msg;
			growthLocusService.calculateGrowthValue(growthMsg.getStudentId(), growthMsg.getBehaviorId());
			
		}else if(msg instanceof GrowthMoreMsg){
			GrowthMoreMsg growthMoreMsg = (GrowthMoreMsg) msg;
			growthLocusService.calculateGrowthValueMoreBehavior(growthMoreMsg.getStudentId(), growthMoreMsg.getBehaviorIds());
		}
		
	}
	
}
