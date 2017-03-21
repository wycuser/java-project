<!DOCTYPE HTML>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="com.shanlin.p2p.app.constant.FeeCode"%>
<html lang="zh-cn">
<head>
<title>善行创投</title>
<%@ include file="/WEB-INF/views/public/common.jspf"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="${ctx}/js/jquery.tabs.js"></script>
<c:set scope="page" value="${bid.surpriseStatus ne'WPZ'}" var="surpriseStatus"></c:set>
</head>
<body>
<div id="sx_m_main">
    <div class="sx_app_outlinebox">
        <div class="sx_app_wrap">
        <%
        Date findEndTime = (Date)request.getAttribute("findEndTime");
        long sytime = findEndTime.getTime() - System.currentTimeMillis();
        pageContext.setAttribute("sytime", sytime);
        %>
        <h1 class="sx_app_t1">${bid.title}</h1>
        <div class="sx_task_situation"><span class="${bid.status eq 'TBZ' && sytime > 0?'sx_app_blueBt':'sx_app_greyBt'}">${bid.status eq 'TBZ'? (sytime > 0?bid.status.chineseName:"已完结"):bid.status.chineseName}</span><span class="sx_app_whiteBt">${bid.isActivity eq 'S'?'活动标':(bid.type==1?'车辆周转':(bid.type==2?'房产周转':bid.type==5?'红木质押':'信用周转'))}</span><c:if test="${surpriseStatus}"><span class="sx_app_pinkBt">惊喜奖</span></c:if></div>
        </div>
        <div class="sx_task_subbox">
            <ul>
              <li class="sx_app_item_main">
                 <div>
                   <p><span class="sx_app_orange sx_app_f21"><fmt:formatNumber pattern="#0.00" value="${bid.rate * 100 - bid.bonus}"/></span><span class="sx_app_orange sx_app_f12">%</span></p>
                   <p class="sx_app_f12">年化收益</p>
                 </div>
                 <c:set var="bonus" value="${bid.bonus}"></c:set>
                 <% if(pageContext.getAttribute("bonus") != null && ((BigDecimal)pageContext.getAttribute("bonus")).compareTo(BigDecimal.ZERO) > 0){%>
                 <div class="sx_app_item_middle">
                   <p><span class="sx_app_black sx_app_f21">${bonus}</span><span class="sx_app_f12">%</span></p>
                   <p class="sx_app_f12">投资奖励</p>
                 </div>
                 <%} %>
                 <div>
                   <p><span class="sx_app_black sx_app_f21">${bid.loanPeriod != 0?bid.loanPeriod:bid.extend.loanPeriod}</span><span class="sx_app_f12">${requestScope.loanPeriodType}</span></p>
                   <p class="sx_app_f12">投资期限</p>
                 </div>
                 <p class="clear"></p>
              </li>
              <li><i>项目规模</i><span class="sx_app_item_content sx_app_f16">${bid.amount > 10000? (bid.amount / 10000):bid.amount}</span><b>&nbsp;${bid.amount > 10000?'万元':'元'}</b></li>
              <li><i>已融金额</i><span class="sx_app_item_content sx_app_orange sx_app_f18">${bid.amount - bid.residueAmount}</span><b>&nbsp;元</b></li>
              <li><i>可投金额</i><span class="sx_app_item_content sx_app_orange sx_app_f18">${bid.residueAmount}</span><b>&nbsp;元</b></li>
              <c:choose>
              <c:when test="${bid.status eq 'YFB'}">
              <li><i>发布日期</i><span class="sx_app_item_content"><fmt:formatDate value="${bid.publishTime}" pattern="yyyy-MM-dd HH点mm分"/>即将开始</span></li>
              </c:when>
              <c:otherwise>
              <li><i>发布日期</i><span class="sx_app_item_content"><fmt:formatDate value="${bid.publishTime}" pattern="yyyy-MM-dd"/></span></li>
              </c:otherwise>
              </c:choose>
              <%-- <li><i>完结日期</i><span class="sx_app_item_content"><fmt:formatDate value="${requestScope.endTime}" pattern="yyyy-MM-dd"/></span></li>--%>
              <c:if test="${bid.status eq 'TBZ' && sytime > 0}">
              <%
              	long hm=3600000*24;
				long day=sytime/hm;
				long hour=(sytime-day*hm)/3600000;
				long min=(sytime-day*hm-hour*3600000)/60000;					
			   %>
              <li><i>剩余时间</i><span class="sx_app_item_content"><%=day<0?0:day %>&nbsp;天&nbsp;<%=hour<0?0:hour %>&nbsp;时&nbsp;<%=min<0?0:min %>&nbsp;分</span></li>
              </c:if>
              <c:if test="${bid.status eq 'YJQ'}">
              <li><i>结清日期</i><span class="sx_app_item_content"><fmt:formatDate value="${bid.extend.closeOffTime}" pattern="yyyy-MM-dd"/></span></li>
              </c:if>
              <li class="noborder"><i>还款方式</i><span class="sx_app_item_content">${bid.mode.chineseName}</span></li>
            </ul>
        </div>
    </div>
    <div class="sx_app_listbox">
			<c:choose>
			<c:when test="${surpriseStatus}">
			<ul class="tab_menu">
				<li class="current"><span>标的详情</span></li>
				<li><span>还款计划</span></li>
				<li onclick="onclickInvestRecord()"><span>投资记录</span></li>
				<li><span>获奖记录</span></li>
			</ul>
			</c:when>
			<c:otherwise>
			<ul class="tab_menu">
				<li style="width:33%;" class="current"><span>标的详情</span></li>
				<li style="width:34%;"><span>还款计划</span></li>
				<li onclick="onclickInvestRecord()" style="width:33%;"><span>投资记录</span></li>
			</ul>
			</c:otherwise>
			</c:choose>
			<div class="tab_box">
				<div>
					<div class="sx_app_wrap">
                   <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="40%">融资方</td>
                      <td width="60%">${loanAccount}</td>
                    </tr>
                    <tr>
                      <td>本期融资金额</td>
                      <td>${bid.amount > 10000? (bid.amount / 10000):bid.amount}${bid.amount > 10000?'万元':'元'}</td>
                    </tr>
                    <tr>
                      <td>项目区域</td>
                      <td>${bidExtend.area.provinceName}</td>
                    </tr>
                    <tr>
                      <td>年化利率</td>
                      <td><fmt:formatNumber pattern="#0.00%" value="${bid.rate}"/></td>
                    </tr>
                    <c:if test="${not empty bidExtend.nextRepayDate}">
	                    <tr>
	                      <td>还款日期</td>
	                      <td><fmt:formatDate value="${bidExtend.nextRepayDate}" pattern="yyyy-MM-dd"/></td>
	                    </tr>
                    </c:if>
                    <tr>
                      <td>截止日期</td>
                      <td><fmt:formatDate value="${requestScope.findEndTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                    </tr>
                    <tr>
                      <td>借款用途</td>
                      <td>${bidExtend.use}</td>
                    </tr>
                    <tr valign="top">
                      <td>还款来源</td>
                      <td>${bidExtend.repaymentSource}</td>
                    </tr>
                  </table><br />
                </div>
                <div class="sx_app_wrap">
                  <h2 class="sx_app_t2">借款描述</h2>
                  <p>${bidExtend.desc}</p>
                </div>
				</div>
				<div class="hide">
                     <h2 class="sx_app_t2">还款计划</h2>
					 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="sx_app_invest_tab">
					 <c:choose>
					 <c:when test="${not empty repaymentPlanList}">
					 	<tr>
	                      <td width="25%">合约还款日期</td>
	                      <c:forEach items="${repaymentPlanList}" var="repaymentPlan">
	                      <td width="25%">${repaymentPlan[1]}</td>
	                      </c:forEach>
	                    </tr>
	                    <tr>
	                      <td>状态</td>
	                      <c:forEach items="${repaymentPlanList}" var="repaymentPlan">
	                      <td>${repaymentPlan[2].chineseName}</td>
	                      </c:forEach>
	                    </tr>
	                    <tr>
	                      <td>科目</td>
	                      <%
	                      List planList = (List)request.getAttribute("repaymentPlanList");
	                      String[] feeCodes = new String[planList.size()];
	                      for(int i = 0, len=planList.size(); i < len; i++){
	                    	  feeCodes[i] = FeeCode.getChineseName(((Integer)((Object[]) planList.get(i))[4]));
	                      }
	                      for(String feeCode : feeCodes){%>
	                      <td><%=feeCode%></td>
	                      <%}%>
	                    </tr>
	                    <tr>
	                      <td>金额（元）</td>
	                      <c:forEach items="${repaymentPlanList}" var="repaymentPlan">
	                      <td>${repaymentPlan[0]}</td>
	                      </c:forEach>
	                    </tr>
	                    <tr>
	                      <td>实际还款日期</td>
	                      <c:forEach items="${repaymentPlanList}" var="repaymentPlan">
	                      <td><fmt:formatDate value="${repaymentPlan[3]}" pattern="yyyy-MM-dd"/></td>
	                      </c:forEach>
	                    </tr>
					 </c:when>
					 <c:otherwise>
					 	<tr>
	                      <td width="30%">合约还款日期</td>
	                    </tr>
	                    <tr>
	                      <td>状态</td>
	                    </tr>
	                    <tr>
	                      <td>科目</td>
	                    </tr>
	                    <tr>
	                      <td>金额（元）</td>
	                    </tr>
	                    <tr>
	                      <td>实际还款日期</td>
	                    </tr>
					 </c:otherwise>
					 </c:choose>
                  </table>
				</div>
				<div class="hide">
                    <h2 class="sx_app_t2">投标记录</h2>
                     <p>加入人次 <span class="sx_app_orange sx_app_f21">${requestScope.countInvest}</span></p>
                     <p>投标总额 <span class="sx_app_orange sx_app_f21"><fmt:formatNumber value="${requestScope.totalInvest}" pattern="#,##,##0.00"></fmt:formatNumber></span>元</p>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="sx_app_invest_tab invest_table">
                    <!-- <tr>
                      <td><b>投标人</b></td>
                      <td><b>投标金额</b></td>
                      <td><b>投标时间</b></td>
                    </tr> -->
                  </table>
                  <a id="loadMore" onclick="loadInvestRecord()" class="sx_moreload" style="display: none;">加载更多</a>
                  <p id="loading" style="text-align: center; padding: 5px 0px; display: none;"><img src='${ctx}/images/loading.gif' /></p>
				</div>
				<c:if test="${surpriseStatus}">
					<div class="hide">
	                    <h2 class="sx_app_t2">获奖记录</h2>
	                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="sx_app_invest_tab sx_tab_bg">
	                    <tr>
	                      <td width="50%"><b>获奖人</b></td>
	                      <td width="50%"><b>奖项</b></td>
	                    </tr>
	                    <c:forEach var="surpriseLucre" items="${requestScope.surpriseLucreList}">
	                    <tr>
	                      <td>${fn:substring(surpriseLucre.accountName, 0, 4)}***</td>
	                      <td>${surpriseLucre.config.name.chineseName}</td>
	                    </tr>
	                    </c:forEach>
	                  </table><br />
						<p>惊喜奖是标的满标回款后按照您预期收益的奖励百分比直接转账到您账户，投资金额越大，返现越多。例：您投了50000元，年化收益为12%，预期一年收益则为6000元，你中了秒杀奖，2%收益返现为120元直接打到您账户，可作提现</p><br/>
	                    <c:forEach var="surpriseConfig" items="${requestScope.surpriseConfigList}">
	                    <p class="sx_app_orange sx_app_f21">${surpriseConfig.name.chineseName}</p>
	                    <p>面向对象：${surpriseConfig.name.desc}</p>
	                    <p>额外奖励：本标预期收益的<fmt:formatNumber value="${surpriseConfig.rate}" pattern="#0.00%"></fmt:formatNumber>返现</p><br/>
	                    </c:forEach>
					</div>
				</c:if>
			</div>
		</div>
</div>
<script type="text/javascript">
$(function(){
	$('.sx_app_listbox').Tabs({
		event:'click'
	});
	
});
var bidPk = ${bid.id};
var page = 1;
var hasNext = true;
var onclickStatus = false;
function onclickInvestRecord(){
	if(onclickStatus)
		return;
	if(hasNext)
		loadInvestRecord();
}
function loadInvestRecord(){
	$("#loadMore").hide();
	if(!hasNext)
		return;
	$('#loading').show();
	$.ajax({
		type : "POST",
		url : "investRecord.action",
		data : {"id":bidPk, "size":10, "page":page},
		success : function(data){
			if(data.sc != 200)
				return;
			$('#loading').hide();
			var investTab = $(".invest_table");
			page+=1;
			hasNext = data.body.hasNext;
			var str;
			$.each(data.body.content, function(i, n){
				str="<tr class=\"investrecord\">";
		         if(n[3].indexOf('手机')>-1){
		        	 str += "<td style=\" border:1px solid #f0f0f0;\">"+n[0]+" <span style=\"padding:2px 5px; background-color:#f56200; color:#ffffff; border-radius: 2px;\">"+n[3]+"</span></td>";
		         }else if(n[3].indexOf('web')>-1){
		             str += "<td style=\" border:1px solid #f0f0f0;\">"+n[0]+" <span style=\"padding:2px 5px; background-color:#5780c3; color:#ffffff; border-radius: 2px;\">"+n[3]+"</span></td>";
		         }
		        str += "<td style=\"border-bottom:1px solid #f0f0f0;\">投 &nbsp;&nbsp; 资:<span  style=\"color:#f84500;  \">"+n[1]+"</span>元</td>"
		       + "</tr>"
		       + "<tr>" 
		       +"<td  >投资时间:"+n[2]+"</td>"
		       +"<td>纯收益:<span  style=\"color:#f84500;  \">"+n[4]+"</span>元</td>"     
		        +"</tr>";
				investTab.append(str);
			});
			onclickStatus = true;
			if(hasNext)
				$("#loadMore").show();
		},
		error : function(data){onclickStatus = false;}
	});
}
</script>
</body>
</html>