package com.xtuone.base.util.akka.actor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.xtuone.base.util.StringUtils;
import com.xtuone.base.util.akka.AkkaUtil;
import com.xtuone.base.util.akka.msg.GrowthMoreMsg;
import com.xtuone.base.util.akka.msg.GrowthMsg;
import com.xtuone.base.util.akka.msg.StudentGrowthMsg;
import com.xtuone.friday.server.modules.curriculum.pushcourse.model.CourseTable;
import com.xtuone.friday.server.modules.curriculum.student.dao.IStudentDao;
import com.xtuone.friday.server.modules.curriculum.student.daoMapper.StudentRowMapper;
import com.xtuone.friday.server.modules.curriculum.student.models.Student;
import com.xtuone.friday.server.modules.curriculum.studentExt.dao.IStudentExtDao;
import com.xtuone.friday.server.modules.curriculum.studentExt.daoMapper.StudentExtRowMapper;
import com.xtuone.friday.server.modules.curriculum.studentExt.models.StudentExt;
import com.xtuone.friday.server.modules.curriculum2.growth.dao.IGrowthLocusDao;
import com.xtuone.friday.server.modules.curriculum2.growth.daoMapper.GrowthLocusRowMapper;
import com.xtuone.friday.server.modules.curriculum2.growth.model.GrowthLocus;
import com.xtuone.friday.server.modules.curriculum2.growth.redis.GrowthRedis;
import com.xtuone.friday.server.modules.curriculum2.growth.service.IGrowthStudentService;
import com.xtuone.friday.server.modules.curriculum2.growth.util.GrowthBehaviorType;

import akka.actor.UntypedActor;

@Component("studentGrowthActor")
@Scope("prototype")
public class StudentGrowthActor extends UntypedActor {

	@Resource
	IStudentDao studentDao;
	@Resource
	StudentRowMapper studentRowMapper;
	@Resource
	IGrowthStudentService growthStudentService;
	@Resource
	IStudentExtDao studentExtDao;
	@Resource
	StudentExtRowMapper studentExtRowMapper;
	
	@Resource
	IGrowthLocusDao growthLocusDao;
	
	@Resource
	GrowthRedis growthRedis;
	@Resource
	GrowthLocusRowMapper growthLocusRowMapper;
	
	@Resource
	AkkaUtil akkaUtil;
	
	
	@Override
	public void onReceive(Object msg) throws Exception {
		
		System.out.println("用户登录，处理登录值");
		
		if(msg instanceof StudentGrowthMsg){
			StringBuffer buffer = new StringBuffer();
			
			StudentGrowthMsg studentGrowthMsg = (StudentGrowthMsg) msg;
			boolean startSoftWareFlag = growthRedis.getIncrFlag(studentGrowthMsg.studentId, GrowthBehaviorType.SOFTWARE_STARTUP.value );
			if(!startSoftWareFlag){
				buffer.append(GrowthBehaviorType.SOFTWARE_STARTUP.value+"");
			}
			String oldUser = handleOldUser(studentGrowthMsg.studentId);
			if(!Strings.isNullOrEmpty(oldUser)){
				buffer.append(",").append(oldUser);
			}
			boolean PerfectionProfileFlag = growthRedis.getIncrFlag(studentGrowthMsg.studentId, GrowthBehaviorType.PERFECTION_PROFILE.value);
			//查询学生信息
			Student student = studentDao.findById(studentGrowthMsg.studentId, studentRowMapper);
			if(!PerfectionProfileFlag){
				boolean flag = false;
				if(student != null){
					if(student.getNickName() != null && student.getAvatarUrl() != null){
						//其他
						if (!StringUtils.isEmpty(student.getRealName()) && student.getLoveState() > 0 ) {
							flag = true;
						}
						if(!StringUtils.isEmpty(student.getRealName())  && !StringUtils.isEmpty(student.getSignature())){
							flag = true;
						}
						if(!StringUtils.isEmpty(student.getRealName())  && !StringUtils.isEmpty(student.getWeiboToken())){
							flag = true;
						}
						if(!StringUtils.isEmpty(student.getRealName())  && !StringUtils.isEmpty(student.getRenrenToken())){
							flag = true;
						}
						if(student.getLoveState() > 0  && !StringUtils.isEmpty(student.getSignature())){
							flag = true;
						}
						
						if(student.getLoveState() > 0  && !StringUtils.isEmpty(student.getWeiboToken())){
							flag = true;
						}
						
						if(student.getLoveState() > 0  && !StringUtils.isEmpty(student.getRenrenToken())){
							flag = true;
						}
						if(!StringUtils.isEmpty(student.getSignature())&& !StringUtils.isEmpty(student.getRenrenToken())){
							flag = true;
						}
						if(!StringUtils.isEmpty(student.getSignature())&& !StringUtils.isEmpty(student.getWeiboToken())){
							flag = true;
						}
						if(!StringUtils.isEmpty(student.getRenrenToken())&& !StringUtils.isEmpty(student.getWeiboToken())){
							flag = true;
						}
					}
					if(flag){
						//增加成长值
						buffer.append(",").append(GrowthBehaviorType.PERFECTION_PROFILE.value);
					}
				}
			}
			
			boolean addCoursesFlag = growthRedis.getIncrFlag(studentGrowthMsg.studentId, GrowthBehaviorType.ADD_COURSES.value);
			if(!addCoursesFlag){
				boolean courseFlag = false;
				//处理用户课表导入信息
				if(!StringUtils.isEmpty(student.getStudentNum())){
					growthStudentService.processCourseTable(studentGrowthMsg.studentId);
				}else{
					List<StudentExt> studentExts = studentExtDao.findByProperty("studentId", studentGrowthMsg.studentId, studentExtRowMapper);
					if(studentExts != null && studentExts.size() > 0){
						for(StudentExt studentExt:studentExts){
							String courseTable = studentExt.getCourseTable();
							if (!StringUtils.isEmpty(courseTable) && !"[]".equals(courseTable)) {
								List<CourseTable> courseTables = JSON.parseArray(courseTable,CourseTable.class);
								if(courseTables.size() > 7){
									courseFlag = true;
									break;
								}
							}
						}
					}
				}
				if(courseFlag){
					buffer.append(",").append(GrowthBehaviorType.ADD_COURSES.value);
					//处理课表信息加分
//					growthStudentService.processCourseTable(studentGrowthMsg.studentId);
				}
			}
			System.out.println("需要更新的值："+buffer.toString());
			akkaUtil.sendGrowthMoreMsg(new GrowthMoreMsg(buffer.toString(), studentGrowthMsg.studentId));
		}
		
	}
	
	/**
	 * 检查是否为旧用户
	 * @param studentId
	 * @return
	 */
	private String handleOldUser(int studentId){
		//是否为旧用户，是否加分了
		boolean incrFlag = growthRedis.getIncrFlag(studentId, GrowthBehaviorType.OLD_USER_INITIAL_SCORE.value);
		if(!incrFlag){
			boolean flag = growthRedis.getIncrFlag(studentId, GrowthBehaviorType.NEW_USER_INITIAL_SCORE.value);
			if(!flag){
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("studentId", studentId);
				map.put("behaviorId", GrowthBehaviorType.NEW_USER_INITIAL_SCORE.value);
				//先检查是不是新用户
				List<GrowthLocus> growthLocus =  growthLocusDao.findByMap(map, 1, growthLocusRowMapper);
				if(growthLocus == null || growthLocus.size() == 0){
					return GrowthBehaviorType.OLD_USER_INITIAL_SCORE.value+"";
				}
			}
		}
		
		return null;
	}
}
