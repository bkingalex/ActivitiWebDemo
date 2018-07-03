<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page pageEncoding="UTF-8" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>

<html>
<head>
    <title>表单模板列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script language="javascript" src="/script/jquery.js"></script>
    <script language="javascript" src="/script/pageCommon.js" charset="utf-8"></script>
    <script language="javascript" src="/script/PageUtils.js" charset="utf-8"></script>
    <link type="text/css" rel="stylesheet" href="/style/blue/pageCommon.css" />
    <script type="text/javascript">
    </script>
</head>
<body>

<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="/style/images/title_arrow.gif"/> 模板管理
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<div id="MainArea">
    <table cellspacing="0" cellpadding="0" class="TableStyle">

        <!-- 表头-->
        <thead>
        <tr align=center valign=middle id=TableTitle>
            <td width="250px">模板名称</td>
            <td width="250px">所用流程</td>
            <td>相关操作</td>
        </tr>
        </thead>

        <!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="documentTemplateList">
        <c:if test="${templateList!=null || fn:length(templateList)>0}">
            <c:forEach items="${templateList}" var="item" varStatus="status">
        <tr class="TableDetail1 template">
            <td><a href="javascript:void('下载')">${item.name}</a>&nbsp;</td>
            <td>${item.pdkey}&nbsp;</td>
            <td><a onClick="return delConfirm()" href="/templateAction_del.do?id=${item.id}">删除</a>
                <a href="/templateAction_edit.do?id=${item.id}">修改</a>
                <a href="/templateAction_download.do?id=${item.id}">下载</a>
            </td>
        </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>

    <!-- 其他功能超链接 -->
    <div id="TableTail">
        <div id="TableTail_inside">
            <a href="/templateAction_addUI.do"><img src="<%=basePath%>/style/blue/images/button/addNew.png" /></a>
        </div>
    </div>

    <div class="description">
        说明：<br />
        1，删除时，相应的文件也被删除。<br />
        2，下载时，文件名默认为：{表单模板名称}.doc<br />
    </div>

</div>
</body>
</html>

