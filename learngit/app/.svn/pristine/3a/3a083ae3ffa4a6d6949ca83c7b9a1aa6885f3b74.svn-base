<!DOCTYPE HTML>
<%@page import="java.util.Date"%>
<html lang="zh-cn">
<head>
<title>善行创投</title>
<%@ include file="/WEB-INF/views/public/common.jspf"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="${ctx}/js/jquery.tabs.js"></script>
</head>
<body>
<div id="sx_m_main">
    <div class="sx_app_outlinebox">
        <div class="sx_app_wrap">
        <h1 class="sx_app_t1">${experienceBid.title}</h1>
        <%
        Date findEndTime = (Date)request.getAttribute("findEndTime");
        long sytime = findEndTime.getTime() - System.currentTimeMillis();
        pageContext.setAttribute("sytime", sytime);
        %>
        <div class="sx_task_situation"><span class="${experienceBid.status eq 'TBZ' && sytime > 0?'sx_app_blueBt':'sx_app_greyBt'}">${experienceBid.status eq 'TBZ'? (sytime > 0?experienceBid.status.chineseName:"已完结"):experienceBid.status.chineseName}</span></div>
        </div>
        <div class="sx_task_subbox">
            <ul>
              <li class="sx_app_item_main">
                 <div>
                   <p><span class="sx_app_orange sx_app_f21"><fmt:formatNumber pattern="#0.00" value="${experienceBid.rate}"/></span><span class="sx_app_orange sx_app_f12">%</span></p>
                   <p class="sx_app_f12">年化收益</p>
                 </div>
                 <div>
                   <p><span class="sx_app_black sx_app_f21">${experienceBid.loanPeriod}</span><span class="sx_app_f12">天</span></p>
                   <p class="sx_app_f12">体验期限</p>
                 </div>
                 <div>
                   <p><span class="sx_app_black sx_app_f21">${experienceBid.amount > 10000? (experienceBid.amount / 10000):experienceBid.amount}</span><span class="sx_app_f12">${experienceBid.amount > 10000?'万元':'元'}</span></p>
                   <p class="sx_app_f12">项目规模</p>
                 </div>
                 <p class="clear"></p>
              </li>
              <li><i>已融金额</i><span class="sx_app_item_content sx_app_orange sx_app_f18">${experienceBid.amount - experienceBid.residueAmount}</span><b>&nbsp;元</b></li>
              <li><i>可投金额</i><span class="sx_app_item_content sx_app_orange sx_app_f18">${experienceBid.residueAmount}</span><b>&nbsp;元</b></li>
              <c:choose>
              <c:when test="${experienceBid.status eq 'YFB'}">
              <li><i>发布日期</i><span class="sx_app_item_content"><fmt:formatDate value="${experienceBid.publishTime}" pattern="yyyy-MM-dd HH点mm分"/>即将开始</span></li>
              </c:when>
              <c:otherwise>
              <li><i>发布日期</i><span class="sx_app_item_content"><fmt:formatDate value="${experienceBid.publishTime}" pattern="yyyy-MM-dd"/></span></li>
              </c:otherwise>
              </c:choose>
              <li><i>完结日期</i><span class="sx_app_item_content"><fmt:formatDate value="${requestScope.endTime}" pattern="yyyy-MM-dd"/></span></li>
              <li><i>还款方式</i><span class="sx_app_item_content">利息到期一次付清</span></li>
              <li><i>费率说明</i><span class="sx_app_item_content">体验活动不收取任何费用</span></li>
              <c:if test="${experienceBid.status eq 'HKZ' || experienceBid.status eq 'YJQ'}">
	              <li><i>还息日期</i><span class="sx_app_item_content"><fmt:formatDate value="${experienceBid.repayDate}" pattern="yyyy-MM-dd"/></span></li>
              </c:if>
              <c:if test="${experienceBid.status eq 'TBZ' && sytime > 0}">
              <%
              	long hm=3600000*24;
				long day=sytime/hm;
				long hour=(sytime-day*hm)/3600000;
				long min=(sytime-day*hm-hour*3600000)/60000;					
			   %>
              <li class="noborder"><i>剩余时间</i><span class="sx_app_item_content"><%=day<0?0:day %>&nbsp;天&nbsp;<%=hour<0?0:hour %>&nbsp;时&nbsp;<%=min<0?0:min %>&nbsp;分</span></li>
              </c:if>
            </ul>
        </div>
    </div>
    <div class="sx_app_listbox">
			<ul class="tab_menu">
				<li class="current" style="width:50%;"><span>体验信息</span></li>
				<li style="width:50%;"><span>体验纪录</span></li>
			</ul>
			<div class="tab_box">
				<div>
                    <p class="sx_app_orange sx_app_f16">标的名称</>
                    <p>善行创投体验标</p><br />
                    <p class="sx_app_orange sx_app_f16">活动介绍</>
                    <p>活动期间，用户能通过多种活动形式获取不同金额的体验金，获得的体验金，虽不是真实货币，却可以用于平台的体验标进行理财体验，且所得收益可以以真实货币形式直接提现到账。目的在于让用户充分熟悉投标流程并享受投资回报。</p><br />
                    <p class="sx_app_orange sx_app_f16">获取条件</>
                    <p>1.新用户注册获取200元体验金，实名认证再获取800元体验金<br />
          2.活动前已注册的老用户可以收到由善行创投发送的体验金口令激活短信，用户按短信提示操作即可领取1000元体验金（体验金活动前注册的为老用户）<br />
          3.新、老用户可以在善林荟活动专区通过不定时发布的体验金口令贴获取体验金兑换口令，抢到的口令能兑换不等额的体验金。 <br />
          4.新、老用户通过扫描游戏二维码或关注微信公众平台（myshanxing），参与趣味互动小游戏赢取不等额的体验金。</p><br/>
                    <p class="sx_app_orange sx_app_f16">体验流程</>
                    <p><img src="${ctx}/images/process.png" alt="" width="100%" /></p><br/>
                    <p class="sx_app_orange sx_app_f16">使用规则：</>
                    <p>1.该体验金仅限用于活动有效期内体验专区体验标的的投资，无法对真实标的进行投资，体验标也不接受现金投资。</p>
                    <p>2.该体验金不显示在用户平台账户的可用资金中，只显示在体验金项目资金中，不可提现且只能使用一次。</p>
                    <p>3.体验金投资操作流程和正常投资一样，且体验标无利息管理费，无积分发放。</p>
                    <p>4.投体验标的用户必须经过注册和实名认证（身份认证、邮箱绑定和提现密码设置）后才能体验。</p>
                    <p>5.成功投体验标获得站内信通知和提示框提醒</p>
                    <p>6.在体验期内，用户获得新的体验金的情况下可以追加资金进入理财体验活动</p>
                    <p>7.体验金虚拟投资产生收益后收益以真实货币形式直接到账平台账户，不算入在体验金额度，放款通过时系统自动收回体验本金。</p>
                    <p>8.收益方式和体验期限以发行的当期理财体验产品描述为准</p>
                    <p>9.体验金自获取即日到活动结束内有效，到期后未进行投资系统将自动回收体验金。</p>
                    <p>10.本期理财体验活动设有可参与的总额度，一旦达到该总额度，体验金活动提前结束。</p>
                    <p>11.对于恶意刷奖者，善行创投有权终止其所得奖励。</p>
                    <p class="sx_app_orange">*本规则最终解析权归善行创投所有</p>
				</div>
				<div class="hide">
                    <h2 class="sx_app_t2">投标记录</h2>
                     <p>加入人次 <span class="sx_app_orange sx_app_f21">${bidRecordList.totalElements}</span></p>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="sx_app_invest_tab invest_table">
                    <tr>
                      <td><b>投标人</b></td>
                      <td><b>投标金额</b></td>
                      <td><b>投标时间</b></td>
                    </tr>
                    <c:forEach items="${bidRecordList.content}" var="bidRecord">
                    <tr class="investrecord">
                      <td>${fn:substring(bidRecord[0], 0, 4)}***</td>
                      <td><fmt:formatNumber value="${bidRecord[1]}" pattern="#,##,##0.00"></fmt:formatNumber></td>
                      <td><fmt:formatDate value="${bidRecord[2]}" pattern="yyyy-MM-dd HH:mm"/></td>
                    </tr>
                    </c:forEach>
                  </table>
                   <c:if test="${bidRecordList.number + 1 < bidRecordList.totalPages}">
                  	<a id="loadMore" onclick="loadInvestRecord()" class="sx_moreload">加载更多</a>
                  </c:if>
				</div>
			</div>
		</div>
</div>
<script type="text/javascript">
$(function(){
	$('.sx_app_listbox').Tabs({
		event:'click'
	});
	
});
var bidPk = ${experienceBid.id};
var size = ${bidRecordList.size};
var page = 1;
var hasNext = ${bidRecordList.number + 1 < bidRecordList.totalPages};
function loadInvestRecord(){
	if(!hasNext)
		return;
	$.ajax({
		type : "POST",
		async : false,
		url : "experienceInvestRecord.action",
		data : {"id":bidPk, "size":size, "page":page+1},
		success : function(data){
			if(data.sc != 200)
				return;
			//$(".investrecord").remove();
			var investTab = $(".invest_table");
			page+=1;
			hasNext = data.body.hasNext;
			$.each(data.body.content, function(i, n){
				investTab.append(
				"<tr class=\"investrecord\">"
				+"<td>"+n[0]+"</td>"
				+"<td>"+n[1]+"</td>"
				+"<td>"+n[2]+"</td>"
				+"</tr>");
			});
			if(!hasNext)
				$("#loadMore").remove();
		}
	});
}
</script>
</body>
</html>
