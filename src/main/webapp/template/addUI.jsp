<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page pageEncoding="UTF-8" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>


<html>
<head>
    <title>文档模板信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script language="javascript" src="/script/jquery.js"></script>
    <script language="javascript" src="/script/pageCommon.js" charset="utf-8"></script>
    <script language="javascript" src="/script/PageUtils.js" charset="utf-8"></script>
    <link type="text/css" rel="stylesheet" href="../style/blue/pageCommon.css" />
    <script type="text/javascript">
    </script>
</head>
<body>

<!-- 标题显示 -->
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="/style/images/title_arrow.gif"/> 文档模板信息
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<!--显示表单内容-->
<div id=MainArea>
    <form action="/templateAction_add.do" method="post" enctype="multipart/form-data">
        <div class="ItemBlock_Title1"><!-- 信息说明 --><div class="ItemBlock_Title1">
            <img border="0" width="4" height="7" src="/style/blue/images/item_point.gif" /> 模板基本信息 </div>
        </div>

        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table cellpadding="0" cellspacing="0" class="mainForm">
                    <tr>
                        <td>模板名称</td>
                        <td><input type="TEXT" name="name" class="InputStyle" />  *</td>
                    </tr>
                    <tr>
                        <td>所用流程</td>
                        <td><select class="SelectStyle" name="pdkey">
                            <c:if test="${pdList!=null || fn:length(pdList)>0}">
                                <c:forEach items="${pdList}" var="item">
                            <option value="${item.key}">${item.name}</option>
                                </c:forEach>
                            </c:if>
                        </select> *
                        </td>
                    </tr>
                </table>
            </div>
        </div>

        <div class="ItemBlock_Title1"><!-- 信息说明 --><div class="ItemBlock_Title1">
            <img border="0" width="4" height="7" src="/style/blue/images/item_point.gif" /> 模板文件 </div>
        </div>

        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table cellpadding="0" cellspacing="0" class="mainForm">
                    <tr>
                        <td width="120px;">请选择文件(doc格式)</td>
                        <td><input type="file" name="source" class="InputStyle" style="width:450px;" /> </td>
                    </tr>
                </table>
            </div>
        </div>

        <!-- 表单操作 -->
        <div id="InputDetailBar">
            <input type="image" src="/style/images/save.png"/>
            <a href="javascript:history.go(-1);"><img src="/style/images/goBack.png"/></a>
        </div>
    </form>
</div>

<div class="Description">
    说明：<br />
    1，模板文件是doc扩展名的文件（Word文档）。<br />
    2，如果是添加：必须要选择模板文件。<br />
    3，如果是修改：只是在 需要更新模板文件时 才选择一个文件。
</div>

</body>
</html>


