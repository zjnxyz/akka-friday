package com.xtuone.base.util.akka.actor;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xtuone.base.redisService.RedisUtil213;
import com.xtuone.base.util.akka.msg.CourseCommentMsg;
import com.xtuone.base.util.akka.msg.CourseCommentUpdateMsg;
import com.xtuone.friday.server.modules.curriculum2.coursesocial.dao.ICourseCommentRedisDao;
import com.xtuone.friday.server.modules.curriculum2.coursesocial.daoMappers.CourseCommentRedisMapper;
import com.xtuone.friday.server.modules.curriculum2.coursesocial.model.redis.CourseComment;
import com.xtuone.treehole.redisserver.modules.global.utils.DataTransferer;

import akka.actor.UntypedActor;

@Component("courseCommentInsertActor")
@Scope("prototype")
public class CourseCommentInsertActor extends UntypedActor {

	@Resource
	DataTransferer dataTransferer;
	@Resource
	ICourseCommentRedisDao courseCommentRedisDao;
	@Resource
	CourseCommentRedisMapper courseCommentRedisMapper;
	
	@Override
	public void onReceive(Object msg) throws Exception {
		if(msg instanceof CourseCommentMsg){
			CourseCommentMsg courseCommentMsg = (CourseCommentMsg)msg;
			CourseComment courseComment = courseCommentRedisDao.findById(courseCommentMsg.id, courseCommentRedisMapper);
			if(courseComment != null){
				if(dataTransferer.saveFromRedisToMysql(courseComment)> 0){
					System.out.println("课程评论同步成功："+courseCommentMsg.id);
				}else{
					//保存到redis中，延长过期时间 
					RedisUtil213 redisutil = new RedisUtil213();
					//设置成永不过期
					redisutil.expire("CourseComment:"+courseCommentMsg.id, 86400000);
				}
			}
		}else if(msg instanceof CourseCommentUpdateMsg){
			CourseCommentUpdateMsg courseCommentUpdateMsg = (CourseCommentUpdateMsg)msg;
			CourseComment courseComment = courseCommentRedisDao.findById(courseCommentUpdateMsg.id, courseCommentRedisMapper);
			if(courseComment != null){
				if(dataTransferer.updateFromRedisToMysql(courseComment)> 0){
					System.out.println("课程评论更新成功："+courseCommentUpdateMsg.id);
				}else{
					//保存到redis中，延长过期时间 
					RedisUtil213 redisutil = new RedisUtil213();
					//设置成永不过期
					redisutil.expire("CourseComment:"+courseCommentUpdateMsg.id, 86400000);
				}
			}
		}
	}

}
