<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="java.util.List" %>
<%@ page import="cn.sitcat.bean.ApplicationQueryVo" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page pageEncoding="UTF-8" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<html>
<head>
    <title>我的申请查询</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script language="javascript" src="/script/jquery.js"></script>
    <script language="javascript" src="/script/pageCommon.js" charset="utf-8"></script>
    <script language="javascript" src="/script/PageUtils.js" charset="utf-8"></script>
    <link type="text/css" rel="stylesheet" href="/style/blue/pageCommon.css" />
    <script type="text/javascript">
            $(function () {
                $("#status").val('${status}');
                console.log('${status}');
            });
    </script>
</head>
<body>

<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="/style/images/title_arrow.gif"/> 我的申请查询
        </div>
        <div id="Title_End"></div>
    </div>
</div>
<div id="QueryArea">
    <div style="height: 30px">
        <form action="#">
            <table border=0 cellspacing=3 cellpadding=5>
                <tr>
                    <td><select id="status" name="status" class="SelectStyle">
                        <option value="">查看全部状态</option>
                        <option value="审批中">审批中</option>
                        <option value="未通过">未通过</option>
                        <option value="已通过">已通过</option>
                    </select>
                    </td>
                    <td><a href=""><input type="IMAGE" src="/style/blue/images/button/query.PNG"/></a></td>
                </tr>
            </table>
        </form>
    </div>
</div>

<div id="MainArea">
    <table cellspacing="0" cellpadding="0" class="TableStyle">
        <!-- 表头-->
        <thead>
        <tr align="CENTER" valign="MIDDLE" id="TableTitle">
            <td width="250px">标题</td>
            <td width="115px">申请人</td>
            <td width="115px">申请日期</td>
            <td width="115px">当前状态</td>
            <td>相关操作</td>
        </tr>
        </thead>
        <tbody id="TableData" class="dataContainer" datakey="formList">
<c:if test="${applicationList != null || fn:length(applicationList)>0}">
    <c:forEach items="${applicationList}" var="item" varStatus="status">
        <tr align="CENTER" class="TableDetail1 template">
            <td><a href="../Flow_Formflow/showForm.html">${item.title}</a></td>
            <td>${item.applicant.name}&nbsp;</td>
            <td>${item.applyDate}&nbsp;</td>
            <c:if test="${item.status eq '审批中'}">
               <td><a href="javascript: window.open('<%=basePath%>/flowAction_showPng.do?applicationId=${item.id}','newwindow','height=900, width=600, top=50%, left=50%, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');">${item.status}</a>&nbsp;</td>
            </c:if>
            <c:if test="${item.status != '审批中'}">
                <td>${item.status}&nbsp;</td>
            </c:if>

            <td><a href="../Flow_Formflow/showForm.html">查看申请信息</a>
                <a href="/flowAction_approveInfoList.do?applicationId=${item.id}">查看流转记录</a>
                <a href="../Flow_Formflow/editAndResubmitUI.html">修改后再次提交</a>
                <a href="#" onClick="return delConfirm()">删除</a>
            </td>
        </tr>
    </c:forEach>
</c:if>
		</tbody>
    </table>

    <!-- 其他功能超链接 -->
    <div id="TableTail"><div id="TableTail_inside"></div></div>
</div>
</body>
</html>

