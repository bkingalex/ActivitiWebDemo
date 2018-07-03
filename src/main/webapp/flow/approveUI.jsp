<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page pageEncoding="UTF-8" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>

<html>
<head>
    <title>审批处理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script language="javascript" src="/script/jquery.js"></script>
    <script language="javascript" src="/script/pageCommon.js" charset="utf-8"></script>
    <script language="javascript" src="/script/PageUtils.js" charset="utf-8"></script>
    <script language="javascript" src="/script/DemoData.js" charset="utf-8"></script>
    <script language="javascript" src="/script/DataShowManager.js" charset="utf-8"></script>
    <link type="text/css" rel="stylesheet" href="/style/blue/pageCommon.css"/>

    <script type="text/javascript">

        // 是否需要选择下一步
        // 如果当前节点中只有一个Transition，则不需要选择，此值为false
        // 如果当前节点中有多个Transition，则需要选择，此值为true
        var needSelectTransition = false; // ${needSelectTransition}

        /**
         * 选择下一步
         */
        // 在表单的提交按钮的onclick事件上定代码：showModalDialog()，则会在提交前打开模态对话框，并在对话框关闭后再继续提交表单。
        // 在这里有一个要求，如果在"选择Transition"页面中点击了“关闭”，则不提交表单。
        function selectTransition() {
            if (needSelectTransition) {
                myShowModalDialog("selectTransitionUI.html", 500, 500);
                return false;
            }
            return true;
        }

        function selectToNode() {
            myShowModalDialog("selectToNodeUI.html", 500, 500);
        }
    </script>
</head>
<body>

<!-- 标题显示 -->
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="/style/images/title_arrow.gif"/> 审批处理
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<!--显示表单内容-->
<div id=MainArea>
    <form action="/flowAction_approve.do" method="post">
        <input type="hidden" name="taskId" value="${taskId}">
        <input type="hidden" name="applicationId" value="${applicationId}">
        <div class="ItemBlock_Title1"><!-- 信息说明 -->
            <div class="ItemBlock_Title1">
                <img border="0" width="4" height="7" src="/style/blue/images/item_point.gif"/> 申请信息
            </div>
        </div>
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table cellpadding="0" cellspacing="0" class="mainForm">
                    <tr>
                        <td><a href="javascript:void(0)" style="text-decoration: underline">[点击下载申请的文档]</a></td>
                    </tr>
                </table>
            </div>
        </div>

        <div class="ItemBlock_Title1"><!-- 信息说明 -->
            <div class="ItemBlock_Title1">
                <img border="0" width="4" height="7" src="/style/blue/images/item_point.gif"/> 审批信息
            </div>
        </div>
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table cellpadding="0" cellspacing="0" class="mainForm">
                    <tr>
                        <td>审批意见</td>
                        <td><textarea name="comment" class="TextareaStyle" style="width: 500px;"></textarea></td>
                    </tr>
                </table>
            </div>
        </div>
        <input type="hidden" id="approval" name="approval" value="true">
        <!-- 表单操作 -->
        <div id="InputDetailBar" style="float:none">
            <!--onclick事件在submit之前触发-->
            <input type="image" src="/style/blue/images/button/agree.png"/>
            <input onclick="$('#approval').val('false')" type="image" src="/style/blue/images/button/disagree.png"/>
            <!-- <img src="/style/blue/images/button/sendBack.png" onClick="selectToNode();" /> -->
            <a href="javascript:history.go(-1);"><img src="/style/images/goBack.png"/></a>
        </div>

    </form>
</div>

<div class="Description">
    说明：<br/>
    1，同意：本次审批通过，流程继续执行。如果所有的环节都通过，则表单的最终状态为：已通过。<br/>
    2，不同意：本次审批未通过，流程结束，不再继续执行。表单的最终状态为：未通过。<br/>
    <!--
 3，如果点击了同意，并且当前节点中有多个Transition，则会弹出一个窗口选择要使用的Transition。<br>
 4，退文（回退）：就是将表单退回到当前节点以前的任意一个节点，包括上一步和发起人。<br>&nbsp;&nbsp;
    接到退文的审批人对表单重新审批，审批通过后继续按原预设的流程进行流转，原先已经审批过的审批人需要重新审批。<br>&nbsp;&nbsp;
    比如，一个审批单的审批流程是：甲→乙→丙→丁。丙在审批的时候，选择了退文到甲，那么这个流程实际就变成了：甲→乙→丙→甲→乙→丙→丁。<br>
 -->
</div>


</body>
</html>

