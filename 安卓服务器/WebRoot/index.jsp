<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>310图书馆占座app-首页</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">首页</a></li>
			<li><a href="<%=basePath %>ClassInfo/ClassInfo_FrontQueryClassInfo.action" target="OfficeMain">班级</a></li> 
			<!-- 
			<li><a href="<%=basePath %>UserInfo/UserInfo_FrontQueryUserInfo.action" target="OfficeMain">用户</a></li> 
			<li><a href="<%=basePath %>UserType/UserType_FrontQueryUserType.action" target="OfficeMain">用户类型</a></li>
			<li><a href="<%=basePath %>SeatState/SeatState_FrontQuerySeatState.action" target="OfficeMain">座位状态</a></li>   -->
			<li><a href="<%=basePath %>Room/Room_FrontQueryRoom.action" target="OfficeMain">阅览室</a></li> 
			<li><a href="<%=basePath %>Seat/Seat_FrontQuerySeat.action" target="OfficeMain">座位查询</a></li> 
			<li><a href="<%=basePath %>SeatOrder/SeatOrder_FrontQuerySeatOrder.action" target="OfficeMain">座位预约</a></li> 
			<li><a href="<%=basePath %>SelectSeat/SelectSeat_FrontQuerySelectSeat.action" target="OfficeMain">选座查询</a></li> 
			<li><a href="<%=basePath %>Jubao/Jubao_FrontQueryJubao.action" target="OfficeMain">举报查询</a></li> 
			<li><a href="<%=basePath %>Jc/Jc_FrontQueryJc.action" target="OfficeMain">奖惩查询</a></li> 
		</ul>
		<br />
	</div> 
	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>desk.jsp" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p> <a href="<%=basePath%>login/login_view.action"><font color=red>后台登陆</font></a></p>
	</div>
</div>
</body>
</html>
