<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="${url}css/style.css"/>
    <link rel="stylesheet" type="text/css" href="${url}css/egret.css">
    <link rel="stylesheet" type="text/css" href="${url}css/microdone-h5.css">

    <script type="text/javascript" src="${url}js/jquery-3.2.0.min.js"></script>
    <script type="text/javascript" src="${url}js/microdoneH5.js"></script>
    <script language="javascript" src="${url}js/crypto-js.js"></script>
    <script language="javascript" src="${url}js/PassGuardCtrl.js"></script>
    <script type="text/javascript" src="${url}js/layui/layui.all.js"></script>
    <script type="text/javascript" src="${url}publicjs/base.js"></script>
	<title>申请成功</title>
</head>
<body>
    <div id="kaihu_page" >
        <div class="bank_head">
            <img src="${url}image/js_bank_logo.png">
        </div>
        <div class="result_message">
            <img src="${url}image/duihao.png">
            <span>申请成功</span>
            <p style="padding: 0 0.8rem;">您的申请已提交，请耐心等待银行审核...</p>
        </div>

        <div class="list_page list_page1">
            <ul>
                <li>
                    <p>企业名称 </p>
                    <p>${data.org_name}</p>
                </li>
                <li>
                    <p>卡号</p>
                    <p>${data.card_no}</p>
                </li>

            </ul>
        </div>

        <%--<div class="list_page list_page1">--%>
            <%--<ul>--%>
                <%--<li>--%>
                    <%--<p>收款账户名 </p>--%>
                    <%--<p>xx理财平台清算户</p>--%>
                <%--</li>--%>
                <%--<li>--%>
                    <%--<p>收款账户</p>--%>
                    <%--<p>1234 1234 1234 1234 </p>--%>
                <%--</li>--%>
            <%----%>
                 <%----%>
            <%--</ul>--%>
        <%--</div>--%>
        <div class="sure_btn"  id="submit">
            完&nbsp;&nbsp;成
        </div>
    </div>
</body>
<script type="text/javascript">
    $('#submit').click(function () {
        location.href = "${data.return_url}"
    });
</script>
<script type="text/javascript">

</script>
</html>