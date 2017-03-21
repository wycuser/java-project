<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>${requestScope.title}</title>
<meta name="description" content="善行创投" />
<meta name="keywords" content="善行创投" />
<meta name="author" content="shanLin" />
<meta name="copyright" content="ShanLin all Right Reserved" />
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
<meta name="apple-touch-fullscreen" content="YES" />
<meta name="apple-mobile-web-app-capable" content="yes">
<link href="../css/mobile.css" rel="stylesheet" type="text/css" media="screen">
<link rel="shortcut icon" href="favicon.ico"/>
<link rel="apple-touch-icon" href="icon.png"/>
<link rel="apple-touch-icon" sizes="72×72" href="touch-icon-ipad.png" />
<link rel="apple-touch-icon" sizes="114×114" href="touch-icon-iphone4.png" />
<script src="../js/jquery-1.11.0.min.js" type="text/javascript" ></script>
<style type="text/css">
img { height: auto; width:100%;}
</style>
</head>
<body style="background:#FFFFFF;">
<div id="sx_m_main">
   <div class="sx_app_template" id="template">
   <h3 class="sx_app_template_title">${requestScope.title}</h3>
   <p style="text-align:right;"><fmt:formatDate value="${requestScope.lastUpdateTime}" pattern="yyyy-MM-dd"/></p>
   ${requestScope.content}
   </div>
</div>
<script type="text/javascript">
var imgs = document.getElementsByTagName("img");
for (var i = 0; i < imgs.length; i++) {
	imgs[i].style.height = 'auto';
	imgs[i].style.width = '100%';
}

$(function() {
	initReady();
});
function initReady(){
	if(typeof(window.slJSBridge) == 'object')
		window.slJSBridge.share("${requestScope.title}-善行创投", "${requestScope.requestUrl}<%="?"+request.getQueryString()%>", "http://www.myshanxing.com:8888/app/images/icon.png", "${requestScope.description}",['weiXin','qq','sms','sina']);
}
</script>
</body>
</html>
