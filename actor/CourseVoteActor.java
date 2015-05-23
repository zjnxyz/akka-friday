package com.xtuone.base.util.akka.actor;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xtuone.base.util.akka.msg.CourseVoteMsg;
import com.xtuone.friday.server.modules.curriculum2.coursesocial.dao.ICourseVoteRedisDao;
import com.xtuone.friday.server.modules.curriculum2.coursesocial.daoMappers.CourseVoteRedisMapper;
import com.xtuone.friday.server.modules.curriculum2.coursesocial.model.redis.CourseVote;
import com.xtuone.treehole.redisserver.modules.global.utils.DataTransferer;

import akka.actor.UntypedActor;

@Component("courseVoteActor")
@Scope("prototype")
public class CourseVoteActor extends UntypedActor {
	
	@Resource
	DataTransferer dataTransferer;
	@Resource
	ICourseVoteRedisDao courseVoteRedisDao;
	@Resource
	CourseVoteRedisMapper courseVoteRedisMapper;

	@Override
	public void onReceive(Object msg) throws Exception {
		if(msg instanceof CourseVoteMsg){
			CourseVoteMsg courseVoteMsg = (CourseVoteMsg) msg;
			CourseVote courseVote = courseVoteRedisDao.findById(courseVoteMsg.id, courseVoteRedisMapper);
			if(courseVote != null){
				if(dataTransferer.saveFromRedisToMysql(courseVote) > 0){
					System.out.println("课程投票同步成功");
				}
			}
		}
	}

}
