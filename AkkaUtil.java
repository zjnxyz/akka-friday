package com.xtuone.base.util.akka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.xtuone.base.util.akka.config.SpringExtension;
import com.xtuone.base.util.akka.msg.BatchTreeholeMessageUpdateMsg;
import com.xtuone.base.util.akka.msg.CourseCommentMsg;
import com.xtuone.base.util.akka.msg.CourseCommentUpdateMsg;
import com.xtuone.base.util.akka.msg.CourseVoteMsg;
import com.xtuone.base.util.akka.msg.GrowthMoreMsg;
import com.xtuone.base.util.akka.msg.GrowthMsg;
import com.xtuone.base.util.akka.msg.StudentGrowthMsg;
import com.xtuone.base.util.akka.msg.TreeholeCommentMsg;
import com.xtuone.base.util.akka.msg.TreeholeCommentUpdateMsg;
import com.xtuone.base.util.akka.msg.TreeholeEvaluateMsg;
import com.xtuone.base.util.akka.msg.TreeholeLikeMsg;
import com.xtuone.base.util.akka.msg.TreeholeMessageMsg;
import com.xtuone.base.util.akka.msg.TreeholeMessageUpdateMsg;
import com.xtuone.base.util.akka.msg.TreeholeScoreInsertMsg;
import com.xtuone.base.util.akka.msg.TreeholeSpamMsg;
import com.xtuone.base.util.akka.msg.TreeholeVoteMsg;
import com.xtuone.base.util.akka.msg.TreeholeVoteOptionMsg;
import com.xtuone.base.util.akka.msg.UpdateGenderMsg;
import com.xtuone.base.util.akka.msg.UpdateLoginTimeMsg;
import com.xtuone.base.util.akka.msg.XMPPAccountMsg;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
/**
 * 
 * @author Zz
 * akka帮助类
 *
 */
@Component("akkaUtil")
@Scope("prototype")
public class AkkaUtil {
	
	/**
	 * 初始化一个actorSystem
	 */
	@Autowired
	private ActorSystem actorSystem;
	
	private static ActorRef messageActor = null;
	
	private static ActorRef commentActor = null;
	
	private static ActorRef likeActor = null;
	
	private static ActorRef evaluateActor = null;
	
	private static ActorRef spamActor = null;
	
	private static ActorRef messageUpdateActor = null;
	
	private static ActorRef commentUpdateActor = null;
	
	private static ActorRef voteInsertActor = null;
	
	private static ActorRef voteOptionInsertActor = null;
	
	private static ActorRef scoreInsertActor = null;
	
	private static ActorRef studentUpdateActor = null;
	
	private static ActorRef growthActor = null;
	
	private static ActorRef studentGrowthActor = null;
	
	private static ActorRef courseCommentInsertActor = null;
	
	private static ActorRef courseVoteActor = null;
	
	private static ActorRef regXMPPAccountActor = null;
	
	private static ActorRef gopushActor = null;
	
	public ActorRef getMessageActor(){
		
		if(messageActor == null){
			messageActor =  actorSystem.actorOf(
					SpringExtension.SpringExtProvider.get(actorSystem).props("treeholeMessageInsertActor"),
					"treeholeMessageInsertActor");
		}
		return messageActor;
	}
	
	public boolean sendInsertMessageMsg(TreeholeMessageMsg treeholeMessageMsg){
		getMessageActor().tell(treeholeMessageMsg, ActorRef.noSender());
		return true;
	}
	
	public  ActorRef getCommentActor(){
		if(commentActor == null){
			commentActor = actorSystem.actorOf(
					SpringExtension.SpringExtProvider.get(actorSystem).props("treeholeCommentInsertActor"),
					"commentInsert");
		}
		return commentActor;
	}
	
	public boolean sendInsertCommentMsg(TreeholeCommentMsg treeholeCommentMsg ){
		getCommentActor().tell(treeholeCommentMsg, ActorRef.noSender());
		return true;
	}
	
	public  ActorRef getLikeActor(){
		if(likeActor == null){
			likeActor = actorSystem.actorOf(
					SpringExtension.SpringExtProvider.get(actorSystem).props("treeholeLikeInsertActor"),
					"likeInsert");
		}
		return likeActor;
	}
	
	public boolean sendInsertLikeMsg(TreeholeLikeMsg treeholeLikeMsg){
		getLikeActor().tell(treeholeLikeMsg, ActorRef.noSender());
		return true;
	}
	
	public  ActorRef getEvaluateActor(){
		if(evaluateActor == null){
			evaluateActor = actorSystem.actorOf(
					SpringExtension.SpringExtProvider.get(actorSystem).props("treeholeEvaluateInsertActor"),
					"evaluateInsert");
		}
		return evaluateActor;
	}
	
	public boolean sendInsertEvaluateMsg(TreeholeEvaluateMsg treeholeEvaluateMsg){
		getEvaluateActor().tell(treeholeEvaluateMsg, ActorRef.noSender());
		return true;
	}
	
	public ActorRef getSpamActor(){
		if(spamActor == null){
			 spamActor  = actorSystem.actorOf(
						SpringExtension.SpringExtProvider.get(actorSystem).props("treeholeSpamInsertActor"),
						"spamInsert");
		}
		return spamActor;
	}
	
	public boolean sendInsertSpamMsg(TreeholeSpamMsg treeholeSpamMsg){
		getSpamActor().tell(treeholeSpamMsg, ActorRef.noSender());
		return true;
	}
	
	public ActorRef messageUpdateActor(){
		if(messageUpdateActor == null){
			messageUpdateActor = actorSystem.actorOf(
					SpringExtension.SpringExtProvider.get(actorSystem).props("treeholeMessageUpdateActor"),
					"messageUpdate");
		}
		return messageUpdateActor;
	}
	
	public boolean sendUpdateMssageMsg(TreeholeMessageUpdateMsg treeholeMessageUpdateMsg){
		messageUpdateActor().tell(treeholeMessageUpdateMsg, ActorRef.noSender());
		return true;
	}
	
	/**
	 * 批量更新主题
	 * @param msgs
	 * @return
	 */
	public boolean sendBatchUpdateMessageMsg(BatchTreeholeMessageUpdateMsg msgs){
		messageUpdateActor().tell(msgs, ActorRef.noSender());
		return true;
	}
	
	public ActorRef commentUpdateActor(){
		if(commentUpdateActor == null){
			commentUpdateActor = actorSystem.actorOf(
					SpringExtension.SpringExtProvider.get(actorSystem).props("treeholeCommentUpdateActor"),
					"commentUpdate");
		}
		
		return commentUpdateActor;
	}
	
	public boolean sendUpdateCommentMsg(TreeholeCommentUpdateMsg treeholeCommentUpdateMsg){
		commentUpdateActor().tell(treeholeCommentUpdateMsg, ActorRef.noSender());
		return true;
	}
	
	public ActorRef voteInsertActor(){
		if(voteInsertActor == null){
			voteInsertActor = actorSystem.actorOf(
					SpringExtension.SpringExtProvider.get(actorSystem).props("treeholeVoteInsertActor"),
					"voteInsert");
		}
		
		return voteInsertActor;
	}
	
	public boolean sendInsertVoteMsg(TreeholeVoteMsg treeholeVoteMsg){
		voteInsertActor().tell(treeholeVoteMsg, ActorRef.noSender());
		return true;
	}
	
	public ActorRef voteOptionInsertActor(){
		if(voteOptionInsertActor == null){
			voteOptionInsertActor = actorSystem.actorOf(
					SpringExtension.SpringExtProvider.get(actorSystem).props("treeholeVoteOptionInsertActor"),
					"voteOptionInsert");
		}
		return voteOptionInsertActor;
	}
	
	public boolean sendInsertVoteOptionMsg(TreeholeVoteOptionMsg treeholeVoteOptionMsg){
		voteOptionInsertActor().tell(treeholeVoteOptionMsg, ActorRef.noSender());
		return true;
	}
	
	public ActorRef scoreInsertActor(){
		
		if(scoreInsertActor == null){
			scoreInsertActor = actorSystem.actorOf(
					SpringExtension.SpringExtProvider.get(actorSystem).props("treeholeScoreInsertActor"),
					"scoreInsert");
		}
		return scoreInsertActor;
	}
	
	public boolean sendInserScoreMsg(TreeholeScoreInsertMsg treeholeScoreInsertMsg){
		scoreInsertActor().tell(treeholeScoreInsertMsg, ActorRef.noSender());
		return true;
	}
	
	public ActorRef studentUpdateActor(){
		if(studentUpdateActor == null){
			studentUpdateActor = actorSystem.actorOf(
					SpringExtension.SpringExtProvider.get(actorSystem).props("studentUpdateActor"),
					"studentUpdate");
		}
		return studentUpdateActor;
	}
	
	public boolean sendUpdateLastLoginTime(UpdateLoginTimeMsg updateLoginTimeMsg){
		studentUpdateActor().tell(updateLoginTimeMsg, ActorRef.noSender());
		return true;
	}
	
	public boolean sendUpdateGender(UpdateGenderMsg updateGender){
		studentUpdateActor().tell(updateGender, ActorRef.noSender());
		return true;
	}
	
	public ActorRef growthActor(){
		if(growthActor == null){
			growthActor = actorSystem.actorOf(SpringExtension.SpringExtProvider.get(actorSystem).props("growthActor"),
					"growth");
		}
		return growthActor;
	}

	public boolean sendGrowthMsg(GrowthMsg growthMsg){
		growthActor().tell(growthMsg, ActorRef.noSender());
		return true;
	}
	
	/**
	 * 多个加分操作
	 * @param growthMoreMsg
	 * @return
	 */
	public boolean sendGrowthMoreMsg(GrowthMoreMsg growthMoreMsg){
		growthActor().tell(growthMoreMsg, ActorRef.noSender());
		return true;
	}
	
	public ActorRef studentGrowthActor(){
		if(studentGrowthActor == null){
			studentGrowthActor = actorSystem.actorOf(SpringExtension.SpringExtProvider.get(actorSystem).props("studentGrowthActor"),
					"studentGrowth");
		}
		return studentGrowthActor;
	}
	
	public boolean sendStudentGrowthMsg(StudentGrowthMsg studentGrowthMsg){
		studentGrowthActor().tell(studentGrowthMsg, ActorRef.noSender());
		return true;
	}
	
	public ActorRef courseCommentInsertActor(){
		if(courseCommentInsertActor == null){
			courseCommentInsertActor = actorSystem.actorOf(SpringExtension.SpringExtProvider.get(actorSystem).props("courseCommentInsertActor"),
					"courseCommentInsert");
		}
		return courseCommentInsertActor;
	}
	
	public boolean sendCourseCommentInsertMsg(CourseCommentMsg courseCommentMsg){
		courseCommentInsertActor().tell(courseCommentMsg, ActorRef.noSender());
		return true;
	}
	
	public boolean sendCourseCommentUpdateMsg(CourseCommentUpdateMsg courseCommentUpdateMsg){
		courseCommentInsertActor().tell(courseCommentUpdateMsg, ActorRef.noSender());
		return true;
	}
	
	public ActorRef courseVoteActor(){
		if(courseVoteActor == null){
			courseVoteActor = actorSystem.actorOf(SpringExtension.SpringExtProvider.get(actorSystem).props("courseVoteActor"),
					"courseVote");
		}
		return courseVoteActor;
	}
	
	public boolean sendCourseVoteInsertMsg(CourseVoteMsg courseVoteMsg){
		courseVoteActor().tell(courseVoteMsg, ActorRef.noSender());
		return true;
	}
	
	public ActorRef regXMPPAccountActor(){
		if(regXMPPAccountActor == null){
			regXMPPAccountActor = actorSystem.actorOf(SpringExtension.SpringExtProvider.get(actorSystem).props("regXMPPAccountActor"),
					"regXMPPAccount");
		}
		return regXMPPAccountActor;
	}
	
	public boolean sendRegXMPPMsg(XMPPAccountMsg xmppAccountMsg){
		regXMPPAccountActor().tell(xmppAccountMsg, ActorRef.noSender());
		return true;
	}
	
	public ActorRef gopushActor(){
		if(gopushActor == null){
			gopushActor = actorSystem.actorOf(SpringExtension.SpringExtProvider.get(actorSystem).props("gopushActor"),
					"gopushRegist");
		}
		return gopushActor;
	}
	
	public boolean sendGopushMsg(String md5){
		gopushActor().tell(md5, ActorRef.noSender());
		return true;
	}
}
