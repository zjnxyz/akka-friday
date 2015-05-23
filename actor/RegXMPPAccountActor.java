package com.xtuone.base.util.akka.actor;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xtuone.base.util.akka.msg.XMPPAccountMsg;
import com.xtuone.chat.server.AccountManager;
import com.xtuone.friday.server.modules.curriculum2.studentEjabberd.dao.IStudentEjabberdDao;
import com.xtuone.friday.server.modules.global.utils.DateUtil;

import akka.actor.UntypedActor;

@Component("regXMPPAccountActor")
@Scope("prototype")
public class RegXMPPAccountActor extends UntypedActor{
	
	@Resource
	IStudentEjabberdDao studentEjabberdDao;

	@Override
	public void onReceive(Object msg) throws Exception {
		
		if(msg instanceof XMPPAccountMsg){
			XMPPAccountMsg xmppAccountMsg = (XMPPAccountMsg) msg;
			creatXMPPAccountBySuperId(xmppAccountMsg.getStudentId(), xmppAccountMsg.getSuperId());
		}
	}
	
	/**
	 * 用superid作为密码创建XMPP帐号
	 * 
	 * @param studentId
	 * @param superId
	 * @param sourceOfPasswordType
	 */
	private void creatXMPPAccountBySuperId(int studentId, String superId) {
		if ( !studentEjabberdDao.exist(studentId) && AccountManager.createAccount(studentId + "", superId)) {
			Map<String, Object> ejabberdMap = new HashMap<String, Object>();
			ejabberdMap.put("studentId", studentId);
			ejabberdMap.put("xmppDomain", AccountManager.DOMAIN);
			ejabberdMap.put("addTime", DateUtil.getCurrentTime());
			studentEjabberdDao.saveByMap(ejabberdMap);
		}
	}

}
