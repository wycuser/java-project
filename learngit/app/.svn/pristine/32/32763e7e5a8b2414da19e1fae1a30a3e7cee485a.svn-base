package com.shanlin.p2p.test;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.p2p.app.dao.FundAccountDao;
import com.shanlin.p2p.app.dao.FundAccountFlowDao;
import com.shanlin.p2p.app.dao.UserAccountDao;
import com.shanlin.p2p.app.dao.UserFundStatistDao;
import com.shanlin.p2p.app.model.FundAccountFlow;
import com.shanlin.p2p.app.model.UserAccount;
import com.shanlin.p2p.app.model.UserFundStatist;
import com.shanlin.p2p.app.model.enums.FundAccountType;
import com.shanlin.p2p.app.model.enums.Judge;
import com.shanlin.p2p.app.model.enums.UserRegisterSource;
import com.shanlin.p2p.app.model.enums.UserStatus;
import com.shanlin.p2p.app.model.enums.UserType;
import com.shanlin.p2p.app.service.UserAccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/applicationContext*.xml"})
@ActiveProfiles("development")
public class UserTest {
	
	private Logger log = LoggerFactory.getLogger("userTest");
	
	@Resource
	private UserAccountDao userAccountDao;
	
	@Resource
	private UserAccountService userAccountService;
	
	@Resource
	private FundAccountFlowDao fundAccountFlowDao;
	
	@Resource
	private FundAccountDao fundAccountDao;
	
	@Resource
	private UserFundStatistDao userFundStatistDao;
	//保存用户
//    @Test
//    public void testSaveUser() {
//        User user = new User();
//        user.setLoginName("zhengxin");
//        user.setUserName("郑鑫");
//        user.setPhone("13670523577");
//        userDao.save(user);
//    }
//    
//    @Test
//    @Rollback(false)
//    @Transactional
//    public void testUpdateUser() {
//    	User user = userDao.findOne(1L);
////    	account.setEmail("983060244@qq.com");
////    	user.setUserName("郑鑫");
////    	user.setPhone("18665612354");
//    	userDao.updateUserNameByUserId("郑鑫", user.getId());
////    	user.setPhone("111");
////    	userDao.save(user);
////    	user.setPhone("18665612354");
//    	System.out.println(user.getUserName());
//    	System.out.println("aaa");
//    	User user2 = userDao.findOne(1L);
//    	System.out.println(user == user2);
////    	userDao.findUserByPhone("111");
////    	userAccountDao.saveAndFlush(account);
////    	userAccountDao.updateEmailByUserId(account.getEmail(), account.getId());
//    }
    
    @Test
    public void testGetUserAccount(){
//    	UserAccount account = userAccountDao.findOne(Long.valueOf(31));
//    	System.out.println(account);
//    	Page<UserAccount> accountPage = userAccountDao.findAll(new PageRequest(0, 10));
//    	for (UserAccount userAccount : accountPage) {
//    		System.out.println(userAccount);
//		}
//    	System.out.println(userAccountDao.findUserById(Long.valueOf(31)));
    }
    
    @Test
    @Transactional
    public void updateUserAccount(){
    	UserAccount account = userAccountDao.findOne(31L);
    	System.out.println(account.getEmail());
    	account.setEmail("983060244@qq.com");
//    	userAccountDao.saveAndFlush(account);
    	userAccountDao.updateEmailByUserId(account.getEmail(), account.getId());
    }
    
//    @Test
//    @Rollback(false)
//    @Transactional
//    public void testTwoUpdateTable() throws Exception{
//    	User user = userDao.findLockUserById(1l);
//    	System.out.println(user.getPhone());
//    	Thread.sleep(10000l);
//    	user = userDao.findLockUserById(1l);
//    	System.out.println(user.getPhone());
//    }
    
    @Test
    @Transactional
    public void testSelectAnyTable(){
    	List<FundAccountFlow> list = fundAccountFlowDao.findByFundAccountId(91L);
    	System.out.println(list.get(0).getFundAccount().getId());
    	System.out.println(fundAccountFlowDao.findCurrentDateLucreByUserId(31l));
    }
    
    @Test
    public void getTest(){
    	System.out.println(fundAccountDao.findByUserIdAndType(31l, FundAccountType.WLZH));
    	
    	Page<FundAccountFlow> flowList = fundAccountFlowDao.findByUserIdAndFundAccountType(31l, FundAccountType.WLZH, new PageRequest(0, 10, new Sort(Direction.DESC, "createTime")));
    	System.out.println(flowList.getContent());
    }
    
    @Test
    @Transactional
    public void userFundStatistTest(){
    	UserFundStatist userFundStatist = new UserFundStatist();
    	userFundStatist.setId(1000000l);
    	userFundStatistDao.save(userFundStatist);
    }
    @Test
    @Transactional
    @Rollback(false)
    public void saveUserAccount(){
    	try{
	    	UserAccount userAccount = new UserAccount();
			userAccount.setLoginName("zhengxin112");
			userAccount.setPassword("123456");
			userAccount.setMobilePhone("13670523885");
			userAccount.setUserType(UserType.ZRR);
			userAccount.setUserStatus(UserStatus.QY);
			userAccount.setRegisterTime(new Date());
			userAccount.setUserRegisterSource(UserRegisterSource.ZC);
			userAccount.setIsAssure(Judge.F);
			userAccount.setErrPassCount(Integer.valueOf(0));
			userAccount.setIsFirstLogin(Judge.S);
			userAccount.setIsDel(Judge.F);
			userAccount = userAccountService.register(userAccount, "400041");
			System.out.println(userAccount.getId());
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    		throw e;
    	}
    }
    @Transactional
    @Test
    public void testlock(){
    	fundAccountDao.lockFundAccountByUserIdAndType(25l, FundAccountType.WLZH);
    }
}
