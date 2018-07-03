<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page pageEncoding="UTF-8" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>


<html>
<head>
    <title>待我审批</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script language="javascript" src="/script/jquery.js"></script>
    <script language="javascript" src="/script/pageCommon.js" charset="utf-8"></script>
    <script language="javascript" src="/script/PageUtils.js" charset="utf-8"></script>
    <link type="text/css" rel="stylesheet" href="/style/blue/pageCommon.css"/>
    <script type="text/javascript">
    </script>
</head>
<body>

<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="/style/images/title_arrow.gif"/> 待我审批
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<form>
    <input type="hidden" name="pageNum" value="1"/>
</form>

<div id="MainArea">
    <table cellspacing="0" cellpadding="0" class="TableStyle">
        <!-- 表头-->
        <thead>
        <tr align="CENTER" valign="MIDDLE" id="TableTitle">
            <td width="250">标题</td>
            <td width="115">申请人</td>
            <td width="115">申请日期</td>
            <td>相关操作</td>
        </tr>
        </thead>
        <!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="formList">
        <c:if test="${list != null || fn:length(list)>0}">
            <c:forEach items="${list}" var="item">
                <tr class="TableDetail1 template">
                    <td><a href="approveUI.html">${item.application.title}</a></td>
                    <td>${item.application.applicant.name}&nbsp;</td>
                    <td>${item.application.applyDate}&nbsp;</td>
                    <td><a href="/flowAction_approveUI.do?taskId=${item.task.id}&applicationId=${item.application.id}">审批处理</a>
                        <!-- <a href="showForm.html">查看申请信息</a> -->
                        <a href="/flowAction_approveInfoList.do?applicationId=${item.application.id}">查看流转记录</a>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>

    <!-- 其他功能超链接 -->
    <div id="TableTail">
        <div id="TableTail_inside"></div>
    </div>
</div>

</body>
</html>


