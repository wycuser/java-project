<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<meta name="renderer" content="webkit">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>APP 接口</title>
</head>
<body>
<% String ctx = "http://"+request.getServerName()+":"+ request.getServerPort() + request.getContextPath();
%>
<p><font color="blue">后台系统时间:</font><%=ctx%>/server/time.action  返回数据：1421571877853</p>
<p><font color="blue">状态sc:</font>成功:200 失败:0 未登录:1 非法请求:3 系统异常:4 (ps:如果状态是失败0，可以获取异常原因mes提示给用户)</p>
<div>
	<font color="blue">用户登录:</font><%=ctx%>/user/login.action&nbsp;&nbsp;<font color="red">(加密)</font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	loginName：登录名或手机
	password：密码
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
	userId：用户编号
	loginName：登录名
	mobilePhone：手机
	token：登录唯一标识
</div>
<p>
	<font color="blue">退出登录:</font><%=ctx%>/user/logout.action&nbsp;&nbsp;<font color="red">(加密)&nbsp;(登录)</font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId：用户编号 token：登录唯一标识
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
	userId：用户编号
</p>

<p><font color="blue">我的财富:</font><%=ctx%>/user/index.action&nbsp;&nbsp;<font color="red">(加密)&nbsp;(登录)</font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId：用户编号 token：登录唯一标识
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
	userId：用户编号
	loginName：登录名
	mobilePhone：手机号码
	accountAsset：账户资产
	balance：账户余额 
	currentDateLucre：当天收益
	lucre：总收益 
	isAuth：是否安全认证
	redPacketNum：红包个数
	unRead：未读站内信条数
</p>
<p><font color="blue">用户注册:</font><%=ctx%>/user/register.action&nbsp;&nbsp;<font color="red">(加密)</font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
		loginName：登录名
		password：密码
		mobilePhone：手机号码
		vCode：短信验证码
		pCode：推广码（可为空）
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参正则校验：</font>
	loginName: ^[a-zA-Z]([\w]{5,17})$ (用户名为6-18个字符，可使用字母、数字、下划线，需以字母开头)
	password: (密码不能小于6位，大于20位)
	mobilePhone: ^(13|15|18|14|17)[0-9]{9}$ (手机号码格式不正确)
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
	userId：用户编号
	loginName：登录名
	mobilePhone：手机
	token：登录唯一标识
</p>
<p><font color="blue">安全认证:</font><%=ctx%>/safeCertified/index.action&nbsp;&nbsp;<font color="red">(加密)&nbsp;(登录)</font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId：用户编号
	token：登录唯一标识
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
	cardStat：身份证认证状态
	mailStat：邮箱认证状态
	mail：邮箱
	phoneStat：手机认证状态
	mobilePhone：手机
	moneyPassStat：提现密码设置状态
</p>
<p><font color="blue">身份认证详细页:</font><%=ctx%>/safeCertified/idcard.action&nbsp;&nbsp;<font color="red">(加密)&nbsp;(登录)</font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId：用户编号
	token：登录唯一标识
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
	name：姓名
	idCard：身份证号码
</p>
<p><font color="blue">首页轮播图:</font><%=ctx%>/home/banner.action
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
	返回一个json集合[],每一个元素包括下面三个属性
	picturePath：轮播图路径
	title：标题
	url：点击后的url
</p>
<p><font color="blue">站内信列表:</font><%=ctx%>/user/letter/list.action<font color="red">(加密)&nbsp;(登录)</font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId：用户编号
	token：登录唯一标识
	size：每页条数
	page：第几页，从1开始
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
	hasNext：是否有下一页1存在0不存在
	content：json集合[],每一个元素包括下面三个属性
	id 站内信id，用于获取站内信明细
	title 标题
	type 站内信类型
	sendTime 时间
	status 状态0未读1已读
</p>
<p><font color="blue">站内信明细:</font><%=ctx%>/user/letter/content.action<font color="red">(加密)&nbsp;(登录)</font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId：用户编号
	token：登录唯一标识
	id：站内信id
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
	id：站内信id
	title：标题
	sendTime：时间
	status 状态0未读1已读
	content：站内信内容
</p>
<p><font color="blue">交易记录:</font><%=ctx%>/user/capital/tradingRecord.action<font color="red">(加密)&nbsp;(登录)</font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId：用户编号
	token：登录唯一标识
	size：每页条数
	page：第几页，从1开始
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
	hasNext：是否有下一页1存在0不存在
	content：json集合[],每一个元素包括下面三个属性
	id 交易记录id，用于获取交易记录明细
	createTime 创建时间
	type 类型
	amount 金额
</p>
<p><font color="blue">交易记录明细:</font><%=ctx%>/user/capital/tradingRecord/content.action<font color="red">(加密)&nbsp;(登录)</font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId：用户编号
	token：登录唯一标识
	id：交易记录id
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
	id：交易记录id
	income：收入
	expenditure：支出
	balance：余额
	createTime：创建时间
	remark：备注
	bid：标的Id
</p>
<p><font color="blue">首页五个标:</font><%=ctx%>/home/bid.action
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
	json集合[]
	id：标的id
	title：标题
	surprise：是否有惊喜奖
	loanPeriod：借款周期
	amount：金额
	mode：标还款方式
	safeType：认证类型
	status：状态
	statusButton：详情页面按钮文字
	type：是否为转让标1转让标0非转让标2体验标
</p>
<p><font color="blue">投资理财:</font><%=ctx%>/financing/bidList.action
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	type：0全部标1个人标2企业标3债权转让4善行宝
	page：第几页
	size：每页条数
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
	hasNext：是否有下一页1存在0不存在
	content：json集合[]<br/>&nbsp;&nbsp;&nbsp;&nbsp;
	type=0,1,2,3{
	id：标的id
	title：标题
	surprise：是否有惊喜奖
	loanPeriod：借款周期
	amount：金额
	mode：标还款方式
	safeType：认证类型
	status：状态
	statusButton：详情页面按钮文字
	isTransfer：是否为转让标1转让标0非转让标
	}<br/>&nbsp;&nbsp;&nbsp;&nbsp;
	type=4{id:善行宝类型id
	name:名称
	sellStatus:发售状态(S:发售F:停售)
	totalAmount:已售金额}
</p>
</p>
<p><font color="blue">注册发送短信验证码:</font><%=ctx%>/user/sendVerifyCode.action<font color="red">(加密)</font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	mobilePhone：手机号码（type=1、2）
	email：邮箱（type=3）
	type：1 注册手机号码 2 找回密码(手机验证码) 3找回密码(邮箱验证码) 
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
</p>
<p><font color="blue">修改用户登录密码:</font><%=ctx%>/user/updatePassword.action<font color="red">(加密)&nbsp;(登录)</font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId：用户编号
	token：登录唯一标识
	oldPass：旧密码
	newPass：新密码
</p>
<p><font color="blue">标的详情:</font><%=ctx%>/financing/bid.action
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	id：标id
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
	HTML页面
</p>
<p><font color="blue">关于善行创投:</font><%=ctx%>/static/about.html
</p>
<p><font color="blue">我的推广页面:</font><%=ctx%>/user/myExtendCode.action<font color="red">(登录)</font>
<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId：用户编号
	token：登录唯一标识
</p>
<p><font color="blue">添加提现密码:</font><%=ctx%>/safeCertified/moneyPass/add.action<font color="red">(加密)&nbsp;(登录)</font>
<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId：用户编号
	token：登录唯一标识
	newPass：提现密码
</p>
<p><font color="blue">修改提现密码:</font><%=ctx%>/safeCertified/moneyPass/update.action<font color="red">(加密)&nbsp;(登录)</font>
<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId：用户编号
	token：登录唯一标识
	oldPass：旧提现密码
	newPass：新提现密码
</p>
<p><font color="blue">投资详情:</font><%=ctx%>/financing/investDetails.action<font color="red">(加密)&nbsp;(登录)</font>
<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId：用户编号
	token：登录唯一标识
	id：标id
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
	id：标id
	title：标题
	loanPeriod：融资期限
	rate：年化利率
	bonus：奖励
	surprise：惊喜奖配置 1有0没有
	residueAmount：剩余额度
	balance：可用余额
</p>
<p><font color="blue">重置密码1:</font><%=ctx%>/user/resetPasswordOne.action<font color="red">(加密)</font>
<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	type：1手机号码找回 2邮箱找回
	mobilePhone：手机号码（type=1时）
	email：邮箱（type=2时）
	vCode：验证码
</p>
<p><font color="blue">重置密码2:</font><%=ctx%>/user/resetPasswordTwo.action<font color="red">(加密)</font>
<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	type：1手机号码找回 2邮箱找回
	mobilePhone：手机号码（type=1时）
	email：邮箱（type=2时）
	newPass：新密码
</p>
<p><font color="blue">投标:</font><%=ctx%>/financing/invest.action<font color="red">(加密)&nbsp;(登录)</font>
<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId：用户编号
	token：登录唯一标识
	id：标id
	money：投资金额
	hbId：红包Id
	dkId：抵扣券Id
</p>
<p><font color="blue">重置提现密码第一步:</font><%=ctx%>/safeCertified/resetMoneyPassOne.action<font color="red">(加密)&nbsp;(登录)</font>
<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId：用户编号
	token：登录唯一标识
	mobilePhone：手机号码
	vCode：验证码
</p>
<p><font color="blue">重置提现密码第二步:</font><%=ctx%>/safeCertified/resetMoneyPassTwo.action<font color="red">(加密)&nbsp;(登录)</font>
<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId：用户编号
	token：登录唯一标识
	mobilePhone：手机号码
	newPass：新密码
</p>
<p><font color="blue">已投项目列表:</font><%=ctx%>/account/creditorList.action<font color="red">(加密)&nbsp;(登录)</font>
<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId：用户编号
	token：登录唯一标识
	type：（1、投标中 2、回款中 3、已完成）
	page：第几页
	size：每页条数
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
	hasNext：是否有下一页1存在0不存在
	content：json集合[]
	id：债权id
	creditNumber：债权编号
	bid：标id
	bidNumber：标编号
	title：标题
</p>
<p><font color="blue">已投项目明细:</font><%=ctx%>/account/creditor.action<font color="red">(加密)&nbsp;(登录)</font>
<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId：用户编号
	token：登录唯一标识
	id：债权id
	type：（1、投标中 2、回款中 3、已完成）
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
	投标中：（
	bidNumber：标编号
	buyingPrice：原始投资金额
	rate：年利率
	loanPeriod：期限
	remainTime：剩余时间
	progress：进度
	title：标题
	bid：标id）
	回款中：（
	creditNumber：债权编号
	buyingPrice：原始投资金额
	rate：年利率
	waitRecyclePrice：待收本息
	nextRepayDate：下个还款日
	status：状态
	title：标题
	bid：标id
	）
	已完成：（
	creditNumber：债权编号
	buyingPrice：原始投资金额
	rate：年利率
	obtainPrice：项目收益
	closeOffTime：到期还款日
	status：状态
	title：标题
	bid：标id
	）		
</p>
<p><font color="blue">身份证认证:</font><%=ctx%>/safeCertified/idcard/add.action<font color="red">(加密)&nbsp;(登录)</font>
<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId：用户编号
	token：登录唯一标识
	name：姓名
	idCard：身份证
</p>
<p><font color="blue">账户资产:</font><%=ctx%>/account/asset.action<font color="red">(加密)&nbsp;(登录)</font>
<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId：用户编号
	token：登录唯一标识
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
	accountAsset：资产总额
	freeze：冻结金额
	balance： 账户可用余额
	lucre：累计收益
	allInvest：累计投资
	allWaitSourcePrice：待收本金
	allWaitInterestPrice：待收收益
	credits：我的积分
</p>
<p><font color="blue">体验标的详情:</font><%=ctx%>/financing/experienceBid.action
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	id：标id
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
	HTML页面
</p>
<p><font color="blue">体验标投资详情:</font><%=ctx%>/financing/experienceInvestDetails.action<font color="red">(加密)&nbsp;(登录)</font>
<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId：用户编号
	token：登录唯一标识
	id：标id
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
	id：标id
	title：标题
	loanPeriod：融资期限
	rate：年化利率
	residueAmount：剩余额度
	balance：可用余额
	investName:预约用户
</p>
<p><font color="blue">体验标投标:</font><%=ctx%>/financing/experienceInvest.action<font color="red">(加密)&nbsp;(登录)</font>
<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId：用户编号
	token：登录唯一标识
	id：标id
	money：投资金额
</p>
<p><font color="blue">体验金明细:</font><%=ctx%>/account/experienceAccount.action<font color="red">(加密)&nbsp;(登录)</font>
<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId：用户编号
	token：登录唯一标识
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
	balance：体验金余额
	investMoney：已投体验金
</p>
<p><font color="blue">体验金已投标记录:</font><%=ctx%>/account/experienceBidRecord.action<font color="red">(加密)&nbsp;(登录)</font>
<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId：用户编号
	token：登录唯一标识
	page：第几页
	size：每页条数<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
	hasNext：是否有下一页1存在0不存在
	content：json集合[]
	id：体验标id
	title：标题
	investPrice：投资金额
	interest：利息
	repalyTime：还息时间
</p>
<p><font color="blue">体验金来源:</font><%=ctx%>/account/experienceSource.action<font color="red">(加密)&nbsp;(登录)</font>
<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId：用户编号
	token：登录唯一标识
	page：第几页
	size：每页条数<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
	hasNext：是否有下一页1存在0不存在
	content：json集合[]
	source：来源
	operateTime：操作时间
	money：金额
</p>
<p><font color="blue">获取最新安卓版本:</font><%=ctx%>/server/androidVersion.action
<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
	versionCode：程序版本号
	versionName：版本名称
	title：标题
	content：内容
	url：下载连接
	isUpdate：是否强制更新1是0否
</p>
<p><font color="blue">获取最新APP版本:</font><%=ctx%>/server/appVersion.action
<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	type：类型（ios、android）
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
	versionCode：程序版本号
	versionName：版本名称
	title：标题
	content：内容
	url：下载连接
	isUpdate：是否强制更新1是0否
</p>
<p><font color="blue">获取客服电话:</font><%=ctx%>/server/servicePhone.action
<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
	客服电话
</p>
<p><font color="blue">债权转让列表:</font><%=ctx%>/account/creditorAssignmentList.action<font color="red">(加密)&nbsp;(登录)</font>
<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId：用户编号
	token：登录唯一标识
	type：类型  1.转让中的债权  2.可转让的债权 3.已转出的债权 4.已转入的债权
	page：第几页
	size：每页条数
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
	hasNext：是否有下一页1存在0不存在
	content：json集合[]
	zqId：债权标识
	creditNumber：债权ID
	title:转让名称
</p>
<p><font color="blue">债权转让详情:</font><%=ctx%>/account/creditorAssignmentDetail.action<font color="red">(加密)&nbsp;(登录)</font>
<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	zqId：债权标识
	token：登录唯一标识
	type：类型  1.转让中的债权  2.可转让的债权 3.已转出的债权 4.已转入的债权
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
	type=1{
	creditNumber：债权ID
	issueNum:剩余期数
	rate:年利率
	holdPrice:债权价值
	price:转让价格
	status:状态
	}<br/>&nbsp;&nbsp;&nbsp;&nbsp;
	type=2{
	creditNumber：债权ID
	issueNum:剩余期数
	nextRepayDate:下一还款日
	rate:年利率
	dsbx:待收本息
	holdPrice:债权价值
	fee:转让费率
	}<br/>&nbsp;&nbsp;&nbsp;&nbsp;
	type=3{
	creditNumber：债权ID
	assignmentAmount:交易金额
	buyAmount:转出时债权价值
	assignmentFees:交易费用
	outProfit:盈亏
	}<br/>&nbsp;&nbsp;&nbsp;&nbsp;
	type=4{
	creditNumber：债权ID
	issueNum:剩余期数
	rate:年利率
	buyAmount:转入时债权价值
	assignmentAmount:交易金额
	buyTime:转入时间
	}
	
</p>
<p><font color="blue">债权转让:</font><%=ctx%>/account/transfer.action<font color="red">(加密)&nbsp;(登录)</font>
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	zqId：债权Id
	token：登录唯一标识
	userId:用户Id
	price：转让价格
</p>
<p><font color="blue">取消转让:</font><%=ctx%>/account/cancel.action<font color="red">(加密)&nbsp;(登录)</font>
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	zqId：债权Id
	token：登录唯一标识
	userId:用户Id
</p>

<p><font color="blue">债权转让详情:</font><%=ctx%>/financing/creditorAssignmentDetail.action
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	zqApplyId：债权申请Id
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
	creditNumber:债权编码
	holdPrice:债权价值 
	dsbx:待收本息  
	price:转让价格 
	issueNum:剩余期数 
	repaymentWay:还款方式  
	title:债权名称
	rate:年利率
	bidType:债权类型
</p>
<p><font color="blue">债权转让投资详情:</font><%=ctx%>/financing/creditorAssignmentInvestDetail.action<font color="red">(加密)&nbsp;(登录)</font>
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	zqApplyId：债权申请Id
	token：登录唯一标识
	userId:用户Id
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
	creditNumber:债权编码
	holdPrice:债权价值 
	dsbx:待收本息  
	price:转让价格 
	issueNum:剩余期数 
	repaymentWay:还款方式  
	balance:剩余金额
	title:债权名称
	rate:年利率
	bidType:债权类型
</p>
<p><font color="blue">购买债权:</font><%=ctx%>/financing/creditorAssignmentInvest.action<font color="red">(加密)&nbsp;(登录)</font>
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	zqApplyId：债权申请Id
	token：登录唯一标识
	userId:用户Id
</p>
<p><font color="blue">善行宝详情:</font><%=ctx%>/financing/sxbaoDetails.action
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	typeId：善行宝类型id
	userId:用户Id（可选）
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
	balance:可用余额
	status:立即投资/已停售
	configs:json集合[]
	id:善行宝id
	title:项目名称
	rate:年化收益
	loanPeriod:投资期限
	mode:还款方式
	investFloor:最低投资金额
</p>
<p><font color="blue">善行宝购买:</font><%=ctx%>/financing/sxbaoInvest.action<font color="red">(加密)&nbsp;(登录)</font>
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	id：善行宝id
	token：登录唯一标识
	userId:用户Id
	money：投资金额
</p>
<p><font color="blue">极光推送:</font><%=ctx%>/push/getPushList.action<font color="red">&nbsp;</font>
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	page:页数
	size:查询长度
</p>
<p><font color="blue">极光推送查看详情:</font><%=ctx%>/push/getPushContent.action<font color="red">&nbsp;</font>
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	id:推送记录Id
</p>
<p><font color="blue">收款明细列表:</font><%=ctx%>/account/backAccountList.action<font color="red">(加密)&nbsp;(登录)</font>
 <br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
 userId:登录用户Id
 type:1:待收款,2:已收款
 page:页数
 size:查询长度
  <br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
   id:标Id
   reId:还款记录Id
   title:标题
   shouldDate:应还日期(收款日期)
   sumAmount:应还金额合计
   content:集合内容
   hasNext:是否还有下一页
</p>
<p><font color="blue">收款明细详情:</font><%=ctx%>/account/backAccountDetail.action<font color="red">(加密)&nbsp;(登录)</font>
 <br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
 userId:登录用户Id
 reId:还款记录Id
  <br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
   title:标题
   issue:期号
   jkrLoginName:借款人登录用户名
   typeMx:类型明细
   shouldDate:收款日期(应还日期)
   status:项目状态
</p>
<p><font color="blue">我的投资列表(版本2):</font><%=ctx%>/account/creditorListV2.action<font color="red">(加密)&nbsp;(登录)</font>
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId:登录用户Id
	type:1:投标中的债权,2:回收中的债权,3:已结清的债权
	page:页数
	size:查询长度
  <br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
   id:投资记录Id
   bidNumber(type为1时取)/creditNumber:标编号
   title:借款标题
   publishTime:开投时间(发布时间)
   buyingPrice:购买价格
   obtainPrice:项目收益
   content:集合内容
   hasNext:是否还有下一页
</p>
<p><font color="blue">债权转让列表(版本2):</font><%=ctx%>/account/creditorAssignmentListV2.action<font color="red">(加密)&nbsp;(登录)</font>
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	userId:登录用户Id
	type:1:转让中的债权,2:可转让的债权,3:已转出的债权,4:已转入的债权
	page:页数
	size:查询长度
   <br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
   zqId:债权
   creditNumber:债权编码
   title:项目名称
   createTime:申请转让时间(创建时间)
   holdPrice:剩余本金->(持有债权金额)
   jkPeriod:借款期限
   dsbx:待收本息
   buyTime:转让时间
   outProfit:转出盈亏
   intoProfit:转入盈亏
   content:集合内容
   hasNext:是否还有下一页
</p>
<p><font color="blue">标的状态:</font><%=ctx%>/financing/bidStatus.action
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	id:标的id
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
	状态
</p>
<p><font color="blue">体验标的状态:</font><%=ctx%>/financing/experienceBidStatus.action
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
	id:标的id
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
	状态
</p>
<p><font color="blue">红包列表:</font><%=ctx%>/account/hbList.action<font color="red">(加密)&nbsp;(登录)</font>
 <br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
 userId:登录用户Id
 state:红包状态 (1.未使用 2.已冻结 3.已使用 4.已过期)
 page:页数
 size:查询长度
  <br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
   id:Id
   userId:用户Id
   userName:用户名
   bid：标的Id
   tzid:投资Id
   state:状态 (1.未使用 2.已冻结 3.已使用 4.已过期)
   source:发放类型  (1.手动发放  2.注册 3.推广)
   type:红本类型 1.投资红包
   typeName:红本类型名
   stateName:状态名
   sourceName:发放类型名
   amount:红包金额
   startTime:开始时间
   endTime:结束时间 
   ranges:起投金额
   content:集合内容
   hasNext:是否还有下一页
</p>
<p><font color="blue">抵扣券列表:</font><%=ctx%>/account/dkList.action<font color="red">(加密)&nbsp;(登录)</font>
 <br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
 userId:登录用户Id
 state:抵扣券状态 (1.未使用 2.已冻结 3.已使用 4.已过期)
 page:页数
 size:查询长度
  <br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
   id:Id
   userId:用户Id
   userName:用户名
   bid：标的Id
   tzid:投资Id
   state:状态 (1.未使用 2.已冻结 3.已使用 4.已过期)
   source:发放类型  (1.手动发放  2.注册 3.推广4.抽奖)
   type:抵扣券类型 1.投资抵扣券
   typeName:类型名
   stateName:状态名
   sourceName:发放类型名
   amount:抵扣券金额
   startTime:开始时间
   endTime:结束时间 
   ranges:起投金额
   content:集合内容
   hasNext:是否还有下一页
</p>
<p><font color="blue">红包详情:</font><%=ctx%>/account/getHbRecord.action<font color="red">(加密)&nbsp;(登录)</font>
 <br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
 userId:登录用户Id
 id:红包Id
  <br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
   id:Id
   userId:用户Id
   userName:用户名
   bid：标的Id
   tzid:投资Id
   state:状态 (1.未使用 2.已冻结 3.已使用 4.已过期)
   source:发放类型  (1.手动发放  2.注册 3.推广)
   type:红本类型 1.投资红包
   typeName:红本类型名
   stateName:状态名
   sourceName:发放类型名
   amount:红包金额
   startTime:开始时间
   endTime:结束时间 
   ranges:起投金额
   content:红包规则内容
</p>
<p><font color="blue">抵扣券详情:</font><%=ctx%>/account/getDkRecord.action<font color="red">(加密)&nbsp;(登录)</font>
 <br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
 userId:登录用户Id
 id:红包Id
  <br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
   id:Id
   userId:用户Id
   userName:用户名
   bid：标的Id
   tzid:投资Id
   state:状态 (1.未使用 2.已冻结 3.已使用 4.已过期)
   source:发放类型  (1.手动发放  2.注册 3.推广,4抽奖)
   type:抵扣券类型 1.投资抵扣券
   typeName:类型名
   stateName:状态名
   sourceName:发放类型名
   amount:抵扣券金额
   startTime:开始时间
   endTime:结束时间 
   ranges:起投金额
   content:抵扣券规则内容
</p>
<p><font color="blue">可使用红包列表:</font><%=ctx%>/account/getHbList.action<font color="red">(加密)&nbsp;(登录)</font>
 <br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
 userId:登录用户Id
 ranges:投资金额
 bid:标的id
  <br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
   id:Id
   amount:红包金额
   startTime:开始时间
   endTime:结束时间 
   ranges:起投金额
</p>
<p><font color="blue">可使用抵扣券列表:</font><%=ctx%>/account/getDkList.action<font color="red">(加密)&nbsp;(登录)</font>
 <br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
 userId:登录用户Id
 ranges:投资金额
 bid:标的id
  <br/>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参内容：</font>
   id:Id
   amount:抵扣券金额
   startTime:开始时间
   endTime:结束时间 
   ranges:起投金额
</p>
<p><font color="blue">用户注册2:</font><%=ctx%>/user/registerV2.action&nbsp;&nbsp;<font color="red">(加密)</font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
		mobilePhone：手机号码
		password：密码
		vCode：短信验证码
		pCode：推广码（可为空）
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参正则校验：</font>
	password: (密码不能小于6位，大于20位)
	mobilePhone: ^(13|15|18|14|17)[0-9]{9}$ (手机号码格式不正确)
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
	userId：用户编号
	loginName：登录名
	mobilePhone：手机
	token：登录唯一标识
</p>
<!-- 银行卡管理及提现申请 -->
<p><font color="blue">用户银行卡列表:</font><%=ctx%>/account/bankCardList.action&nbsp;&nbsp;<font color="red">(加密)(登录) </font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
		 userId：用户编号 token：登录唯一标识
		 page:页数
		 size:查询长度
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
	id：银行卡Id
	bankName:银行名字
	number:卡号
	bank.picUrl:银行卡图片地址
	area.province:省份
	area.city:城市
	area.county:县
	content:集合内容
    hasNext:是否还有下一页
</p>
<p><font color="blue">获得银行卡信息:</font><%=ctx%>/account/getBankCard.action&nbsp;&nbsp;<font color="red">(加密)(登录) </font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
		 userId：用户编号 token：登录唯一标识
		 bankCardId:银行卡Id
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
	id：银行卡Id
	number:银行卡号
	bankId：银行Id
	bankName:银行名字
	areaId:区域Id
	province:省份
	city:城市
	county:县
	openingName:开户行
</p>
<p><font color="blue">保存银行卡信息:</font><%=ctx%>/account/opBankCard.action&nbsp;&nbsp;<font color="red">(加密)(登录) </font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
		userId：用户编号 token：登录唯一标识
		 bankId：银行Id
		areaId:区域Id
		openingName:开户行
		number:银行卡号
		id：银行卡Id（可为空）
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
	无
</p>
<p><font color="blue">删除银行卡信息:</font><%=ctx%>/account/delBankCard.action&nbsp;&nbsp;<font color="red">(加密)(登录) </font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
		userId：用户编号 token：登录唯一标识
		id：银行卡Id
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
	无
</p>
<p><font color="blue">新增银行卡信息:</font><%=ctx%>/account/addBankCard.action&nbsp;&nbsp;<font color="red">(加密)(登录) </font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
		userId：用户编号 token：登录唯一标识
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
		name：姓名 
		idCard：身份证号码
		isMoneyPwd:是否设置了提现密码  1.已设置 0.未设置
</p>

<p><font color="blue">区域列表:</font><%=ctx%>/server/areaList.action&nbsp;&nbsp;<font color="red"></font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
		无
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
</p>
<p><font color="blue">区域版本:</font><%=ctx%>/server/areaVersion.action&nbsp;&nbsp;<font color="red"></font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
		无
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
	version：版本号
</p>

<p><font color="blue">用户提现首页:</font><%=ctx%>/account/withdrawIndex.action&nbsp;&nbsp;<font color="red">(加密)(登录) </font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
		userId：用户编号 token：登录唯一标识
		bankCardId：银行卡Id
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
		count:银行卡数量(1.已绑定 0.未绑定)
		cardStat: 身份证认证状态 (1.已绑定 0.未绑定)
		isMoneyPwd: 是否设置提现密码 (1.已设置 0.未设置)
		phoneStat: 手机认证状态 (1.已绑定 0.未绑定)
		isParty: 是否注册第三方 (1.已绑定 0.未绑定)
		balance: 可提现金额
		bankCardId: 银行卡Id 
		areaId:银行卡区域Id
		number: 银行卡号
		bankName: 银行卡名称
		picUrl: 银行卡图片地址
		authName: 持卡人
		WITHDRAW_MIN_FUNDS: 提现最低金额（元）
		WITHDRAW_MAX_FUNDS: 提现最高金额（元）
		WITHDRAW_POUNDAGE_WAY:提现手续费计算方式, ED:按额度(默认方式);BL:按比例
		WITHDRAW_POUNDAGE_PROPORTION:[按比例]提现手续费比例值
		WITHDRAW_POUNDAGE_MIN:[按比例]提现手续费最低费用
		WITHDRAW_POUNDAGE_1_5:[按额度]小于提现额度金额手续费标准
		WITHDRAW_POUNDAGE_5_20:[按额度]大于等于提现额度金额手续费标准
		WITHDRAW_ED_FUNDS:提现额度金额
</p>
<p><font color="blue">提现首页计算用户提现手续费:</font><%=ctx%>/account/withdrawUserLines.action&nbsp;&nbsp;<font color="red">(加密)(登录) </font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
		userId：用户编号 token：登录唯一标识
		amount：提现金额
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
		poundage:提现手续费（元）
</p>
<p><font color="blue">提交申请提现:</font><%=ctx%>/account/withdrawApply.action&nbsp;&nbsp;<font color="red">(加密)(登录) </font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
		userId：用户编号 token：登录唯一标识
		bankCardId：银行卡Id
		amount:提现金额
		withdrawPsd：提现密码
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
	无
</p>

<p><font color="blue">银行列表:</font><%=ctx%>/account/banks.action&nbsp;&nbsp;<font color="red"></font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
		无
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
		id:银行ID
		name:银行名称
		picUrl:图片地址
</p>

<p><font color="blue">充值首页:</font><%=ctx%>/account/chargeIndex.action&nbsp;&nbsp;<font color="red">(加密)(登录)</font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
		userId：用户编号 token：登录唯一标识
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
		balance: 可用金额
		phoneStat: 手机认证状态 (1.已绑定 0.未绑定)
		cardStat: 身份证认证状态 (1.已绑定 0.未绑定)
		isParty: 是否注册第三方 (1.已绑定 0.未绑定)
		CHARGE_MIN_AMOUNT: 充值最低金额（元）
		CHARGE_MAX_AMOUNT: 充值最高金额（元）
		CHARGE_RATE:用户充值手续费率
		CHARGE_MAX_POUNDAGE:用户充值最高手续费率
</p>

<p><font color="blue">充值处理:</font><%=ctx%>/account/opChargeApply.action&nbsp;&nbsp;<font color="red">(加密)(登录)</font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
		userId：用户编号 token：登录唯一标识 amount：充值金额 retType:返回类型（可为空）
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
		linkUrl:请求地址
</p>

<p><font color="blue">用户开户:</font><%=ctx%>/account/openAccount.action&nbsp;&nbsp;<font color="red">(登录)</font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
		userId：用户编号 token：登录唯一标识 retType:返回类型（可为空）
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
		无
</p>

<p><font color="blue">绑定手机:</font><%=ctx%>/user/bindPhone.action&nbsp;&nbsp;<font color="red">(加密)(登录)</font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
		userId：用户编号 token：登录唯一标识 mobilePhone：手机号码 vCode：验证码
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
		无
</p>

<p><font color="blue">查询项目收益:</font><%=ctx%>/account/getProjectSy.action&nbsp;&nbsp;<font color="red">(加密)(登录)</font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
		bidId：标Id creditPrice：用户输入的金额
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
		obtainPrice:项目收益
</p>


<p><font color="blue">查询借款统计信息:</font><%=ctx%>/user/credit.action&nbsp;&nbsp;<font color="red">(加密)(登录)</font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
		userId：用户编号 token：登录唯一标识
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
		countMoney:借款总金额
		overdueMoney：逾期金额
		repayMoney:待还金额
		newRepayMoney:近30天应还金额
</p>

<p><font color="blue">借款列表:</font><%=ctx%>/user/creditList.action&nbsp;&nbsp;<font color="red">(加密)(登录)</font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
		userId：用户编号 token：登录唯一标识  page:页数  size:查询长度  type:类型(0.还款中 1.已结清)
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
		bid:标的Id
		title：标的名称
		key_1:第一个名称
		value_1:对应显示值
		key_2:第2个名称
		value_2:对应显示值
		key_3:第3个名称
		value_3:对应显示值
		key_4:第4个名称
		value_4:对应显示值
		content:集合内容
   		hasNext:是否还有下一页
</p>

<p><font color="blue">还款详情:</font><%=ctx%>/user/loanDetail.action&nbsp;&nbsp;<font color="red">(加密)(登录)</font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
		 userId：用户编号 token：登录唯一标识 bid:标的Id
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">返参body内容：</font>
		bid:标的Id
		num：期数
		total:还款金额
		repayDate:还款日期
		status:状态
		isPayment:控制显示还款按钮(1.显示 0.不显示)
		content:集合内容
   		hasNext:是否还有下一页
</p>

<p><font color="blue">还款操作:</font><%=ctx%>/user/payment.action&nbsp;&nbsp;<font color="red">(加密)(登录)</font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
		 userId：用户编号 token：登录唯一标识 bid:标的Id num:期数
	<br/>
</p>

<p><font color="blue">我要借款:</font><%=ctx%>/user/addLoans.action&nbsp;&nbsp;<font color="red">(加密)(登录)</font>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">入参内容：</font>
		 userId：用户编号 token：登录唯一标识 1
	<br/>
</p>

</body>
</html>
