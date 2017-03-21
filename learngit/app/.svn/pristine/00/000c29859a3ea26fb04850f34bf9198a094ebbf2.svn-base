package com.shanlin.p2p.app.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.UnixCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.framework.communication.http.HttpUtil;
import com.shanlin.framework.mapper.JsonMapper;
import com.shanlin.framework.webservice.bbs.ClientClub;
import com.shanlin.p2p.app.constant.Constants;
import com.shanlin.p2p.app.dao.ExternalSystemLogDao;
import com.shanlin.p2p.app.model.ExternalSystemLog;
import com.shanlin.p2p.app.service.RemoteInvokeService;

@Transactional(readOnly=true)
public class RemoteInvokeServiceImpl implements RemoteInvokeService {
	
	private static final Logger log = LoggerFactory.getLogger(RemoteInvokeServiceImpl.class);
	
	private ThreadPoolExecutor threadPool;
	
	private String apiUrl;
	
	private String publicKey;
	
	private boolean invoke;
	
	@Resource
	private ExternalSystemLogDao externalSystemLogDao;
	
	@Override
	@Transactional
	public void httpInvoke(final String loginName, final int api, final String args) {
		if(!invoke)
			return;
		if(api == LOGIN && externalSystemLogDao.currentDateLogin(loginName) != null)
			return;
		threadPool.execute(new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				StringBuilder buff = new StringBuilder(getRemoteApi());
				buff.append("&user=").append(loginName).append("&arg=").append(api);
				if(args != null)
					buff.append("&").append(args);
				String uri =  buff.toString();
				Integer status = null;
				String returnVal = null;
				String errorMessage = null;
				Map<String, Object> jfMap = null;
				try{
					returnVal = HttpUtil.create().get(uri).execute();
					jfMap = JsonMapper.normalMapper().fromJson(returnVal, HashMap.class);
					if(jfMap != null){
						status = (Integer) jfMap.get("status");
						errorMessage = (String) jfMap.get("re");
					}
				}catch(Exception e){
					status = Integer.valueOf(-1);
					errorMessage = e.getMessage();
					log.error("调用PHP接口异常，arg：{}", api, e);
				}
				ExternalSystemLog esl = new ExternalSystemLog();
				esl.setLoginName(loginName);
				esl.setApi(api);
				esl.setUri(uri);
				esl.setReturnJson(returnVal);
				esl.setStatus(status == null ? 0 : status.intValue());
				esl.setErrorMessage(errorMessage);
				externalSystemLogDao.save(esl);
			}
		});
	}
	@Override
	@Transactional
	public Map<String, Object> httpInvokeReturn (String loginName, int api, String args){
		Map<String, Object> jfMap = new HashMap<>();
		if(!invoke)
			return jfMap;
		if(api == LOGIN && externalSystemLogDao.currentDateLogin(loginName) != null)
			return jfMap;
		StringBuilder buff = new StringBuilder(getRemoteApi());
		buff.append("&user=").append(loginName).append("&arg=").append(api);
		if(args != null)
			buff.append("&").append(args);
		String uri =  buff.toString();
		Integer status = null;
		String errorMessage = null;
		try{
			jfMap = HttpUtil.create().get(uri).execute2Map();
			if(jfMap != null){
				status = (Integer) jfMap.get("status");
				errorMessage = (String) jfMap.get("re");
			}
		}catch(Exception e){
			status = Integer.valueOf(-1);
			errorMessage = e.getMessage();
			log.error("调用PHP接口异常，arg：{}", api, e);
		}
//		if(api != RemoteInvokeService.QUERY){
			ExternalSystemLog esl = new ExternalSystemLog();
			esl.setLoginName(loginName);
			esl.setApi(api);
			esl.setUri(uri);
			esl.setReturnJson(jfMap == null?"":jfMap.toString());
			esl.setStatus(status == null ? 0 : status.intValue());
			esl.setErrorMessage(errorMessage);
			externalSystemLogDao.save(esl);
//		}
		return jfMap;
	}
	
	/**
	 * 
	 * @param loginName
	 * @param password
	 * @param mobilePhone
	 */
	@Override
	public void addBbsAccount(final String loginName, final String password, final String mobilePhone){
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
//				//同步php 论坛的uc
//				Client uc = new Client();
//				//生成同步注册的代码
				String jmpassword = UnixCrypt.crypt(password, DigestUtils.sha256Hex(password));
//				String $returns = uc.uc_user_register(loginName, jmpassword, "eamil@myshanxing.com", "", "", mobilePhone);
//				if($returns!=null && !"".equals($returns)){
//					int $uid = Integer.parseInt($returns);
//					log($uid, "bbs", loginName);
//				}
				//同步善林会
				ClientClub club = new ClientClub();
				String $returns = club.uc_user_register(loginName, jmpassword, "eamil@myshanxing.com", "", "", mobilePhone);
				if($returns!=null && !"".equals($returns)){
					int $uid = Integer.parseInt($returns);
					log($uid, "club", loginName);
				}
				
				httpInvoke(loginName, RemoteInvokeService.REGISTER, null);
			}
		});
	}
	
	public void updatePass(final String loginName, final String oldPass, final String newPass){
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				try{
					Map<String, String> params = new HashMap<>();
					params.put("userName", loginName);
					params.put("password", newPass);
					Map<String, Object> returnVal = HttpUtil.create()
							.post(Constants.JXGL_SYSTEM_URL + "/aPe/modifyPassword", params).execute2Map();
					if(returnVal != null){
						Boolean isSuccess = (Boolean) returnVal.get("isSuccess");
						if(!isSuccess){
							log.error("同步修改绩效管理系统密码失败, userName:" + loginName + " 原因：" + returnVal.get("messageContent"));
						}
					}
				}catch(Exception e){
					log.error("同步修改绩效管理系统密码失败, userName:"+loginName, e);
				}
				
//				Client uc = new Client();
				ClientClub club =new ClientClub();
				//同步修改php论坛
				String cryOldPass = UnixCrypt.crypt(oldPass, DigestUtils.sha256Hex(oldPass));
				String cryNewPass = UnixCrypt.crypt(newPass, DigestUtils.sha256Hex(newPass));
//				try{
//					String rs = uc.uc_user_edit(loginName, cryOldPass, cryNewPass, "", 1, "", "");
//					int prs=Integer.parseInt(rs);
//					if(prs==1 || prs==0)
//						log.info("{}同步修改成功", loginName);
//					else
//						log.error("{}同步修改不成功", loginName);
//					}catch(Exception e){
//						log.error(loginName +"同步修改不成功", e);
//					}
			       //end
				//同步修改善林汇
				  try{
					  String  rs2=club.uc_user_edit(loginName, cryOldPass, cryNewPass, "", 1, "", "");
					  int prs=Integer.parseInt(rs2);
					if(prs==1 || prs==0)
						log.info(loginName+"同步修改善林汇成功");
					else
						log.error(loginName+"同步修改善林汇不成功");
					}catch(Exception e){
						log.error(loginName +"同步修改善林汇不成功", e);
//						userManage.addexcepdisclub(loginName,"善林汇异常","修改异常");
					}
				  //end 
			}
		});
	}
	
	private void log(int uid, String prefix, String loginName){
		if(uid <= 0) {
			switch (uid) {
			case -1:
				log.error("{}:用户名不合法 loginName:{}", prefix, loginName);
				break;
			case -2:
				log.error("{}:包含要允许注册的词语 loginName:{}", prefix, loginName);
				break;
			case -3:
				log.error("{}:用户名已经存在 loginName:{}", prefix, loginName);
				break;
			case -4:
				log.error("{}:Email 格式有误", prefix);
				break;
			case -5:
				log.error("{}:Email 不允许注册", prefix);
				break;
			default:
				log.error("{}:异常未定义 返回码：{}", prefix, uid);
				break;
			}
		}
	}
	
	private String getRemoteApi(){
		long time = System.currentTimeMillis();
		return apiUrl + "?mod=shanlin&time="+time+"&token=" + DigestUtils.md5Hex(time + publicKey);
	}
	
	public ThreadPoolExecutor getThreadPool() {
		return threadPool;
	}

	public void setThreadPool(ThreadPoolExecutor threadPool) {
		this.threadPool = threadPool;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public ExternalSystemLogDao getExternalSystemLogDao() {
		return externalSystemLogDao;
	}

	public void setExternalSystemLogDao(ExternalSystemLogDao externalSystemLogDao) {
		this.externalSystemLogDao = externalSystemLogDao;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public boolean isInvoke() {
		return invoke;
	}

	public void setInvoke(boolean invoke) {
		this.invoke = invoke;
	}
	
}
