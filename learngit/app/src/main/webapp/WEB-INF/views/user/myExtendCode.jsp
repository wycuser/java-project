<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
<title>善行创投</title>
<%@ include file="/WEB-INF/views/public/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/qrcode.min.js"></script>
</head>
<body class="sx_aboutbg" style="background:#e6eef1;">
<div id="sx_m_main">
   <div class="sx_app_biz_box">
      <h1>成功邀请好友投资，可获返利奖励！</h1>
      <h2>1、这是您的专属推广码，请您邀请的好友在注册时输入：</h2>
      <%-- c:set scope="page" var="code" value="${not empty interiorCode?(interiorCode[1]== null?interiorCode[0]:interiorCode[1]):userExtendCode.myCode}"></c:set>--%>
      <c:set scope="page" var="code" value="${userExtendCode.myCode}"></c:set>
      <p class="sx_app_biz_ma sx_app_f18">${not empty interiorCode?(interiorCode[1]== null?interiorCode[0]:interiorCode[1]):userExtendCode.myCode}</p>
      <div id="qrcode" class="sx_app_biz_pic"></div><br />
      <h2>2、这是您的专用邀请链接，请通过 QQ或邮箱 发送给好友：</h2>
      <div class="sx_app_biz_textlink"><p id="content" >开启您的财富之旅，从注册善行创投开始！http://www.myshanxing.com/pay/slaward.html?code=${code}</p></div>
   </div>
   <div>
		<h1 style="text-align: center;color: #f44711;font-size: 18px;line-height: 150%;margin-bottom: 10px;">我的好友</h1>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="sx_app_invest_tab invest_table">
			<tr>
			  <td><b>好友用户名</b></td>
			  <td><b>实名认证</b></td>
			  <td><b>累计投资金额</b></td>
			</tr>
		</table>
		<a id="loadMore" onclick="loadInvestRecord()" class="sx_moreload">加载更多</a>
		<p id="loading" style="text-align: center; padding: 5px 0px; display: none;"><img src='${ctx}/images/loading.gif' /></p>
	</div>
</div>
<script type="text/javascript">
var linkUrl = "http://www.myshanxing.com/pay/slaward.html?code=${code}";
new QRCode("qrcode", {text: linkUrl, width: 150, height: 150});
$(function() {
	loadInvestRecord();
	initReady();
});
function initReady(){
	if(typeof(window.slJSBridge) == 'object')
		window.slJSBridge.share("推荐善行创投给您-享受家庭理财设计师般服务", linkUrl, "http://www.myshanxing.com:8888/app/images/icon.png", "开启您的财富之旅，从注册善行创投开始！",['weiXin','qq','sms','sina']);
}
var uid = ${userExtendCode.id};
var page = 0;
var hasNext = true;
function formatNum(num){
	if(num == null || num == 0)
		return "0";
	var newStr = "", str = num + "";
	var count = 0;
	if(str.indexOf(".") == -1){
		for(var i=str.length-1;i>=0;i--){
			if(count % 3 == 0 && count != 0)
				newStr = str.charAt(i) + "," + newStr;
			else
				newStr = str.charAt(i) + newStr;
 			count++;
		}
   		str = newStr + ".00";
	}else{
		for(var i = str.indexOf(".")-1;i>=0;i--){
			if(count % 3 == 0 && count != 0)
		   		newStr = str.charAt(i) + "," + newStr;
			else
				newStr = str.charAt(i) + newStr;
	 		count++;
	   	}
	    str = newStr + (str + "00").substr((str + "00").indexOf("."),3);
	}
	return str;
}
function loadInvestRecord(){
	$("#loadMore").hide();
	if(!hasNext)
		return;
	$('#loading').show();
	$.ajax({
		type : "POST",
		url : "findExtendData.action",
		data : {"userId":uid, "size":10, "page":page},
		success : function(data){
			if(data.sc != 200)
				return;
			$('#loading').hide();
			var investTab = $(".invest_table");
			page+=1;
			hasNext = data.body.hasNext;
			var str;
			$.each(data.body.content, function(i, n){
				str="<tr class=\"investrecord\">"
				  +"<td>"+ n[0] +"</td>"
				  +"<td>"+ (null == n[1]?"":n[1]) +"</td>"
				  +"<td>"+ formatNum(n[2]) +"</td>"
				  +"</tr>";
				investTab.append(str);
			});
			onclickStatus = true;
			if(hasNext)
				$("#loadMore").show();
		}
	});
}
</script>
</body>
</html>
