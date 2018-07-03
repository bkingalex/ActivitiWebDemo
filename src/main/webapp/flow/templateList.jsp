<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page pageEncoding="UTF-8" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<html>
<head>

    <title>审批流程列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script language="javascript" src="../script/jquery.js"></script>
    <script language="javascript" src="../script/pageCommon.js" charset="utf-8"></script>
    <script language="javascript" src="../script/PageUtils.js" charset="utf-8"></script>
    <link type="text/css" rel="stylesheet" href="../style/blue/pageCommon.css" />
    <link type="text/css" rel="stylesheet" href="../style/blue/select.css" />
</head>
<body>
<div id="Title_bar" style="margin-bottom: 20px;">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="../style/images/title_arrow.gif"/> 申请模板选择
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<table width="85%" cellspacing="0" cellpadding="1" border="0" class="TableBorder" style="margin-left: 15px;">
    <tbody>
    <tr>
        <td>
            <table width="100%" cellspacing="0" cellpadding="0" border="0">
                <tr>
                    <td class="HeadRight">
                        <table height="27" cellspacing="0" cellpadding="0" border="0">
                            <tr>
                                <td class="HeadLeft">请选择申请模板</td>
                                <td width="35" class="HeadMiddle"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td class="Detail dataContainer" datakey="formTemplateList">
            <!-- 显示表单模板列表 -->
            <c:if test="${templateList!=null||fn:length(templateList)>0}">
                <c:forEach items="${templateList}" var="item" varStatus="status">
                    <div id="DetailBlock" class="template">
                        <img width="16" height="16" src="../style/images/FileType/doc.gif"/>
                            <a href="/flowAction_submitUI.do?templateId=${item.id}">${item.name}</a>
                    </div>
                </c:forEach>
            </c:if>
        </td>
    </tr>
    </tbody>
</table>

<div class="Description">
    说明：此页面是列出所有的表单模板记录<br/>
</div>

</body>
</html>
