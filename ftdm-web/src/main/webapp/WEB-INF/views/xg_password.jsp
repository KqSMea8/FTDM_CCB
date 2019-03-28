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
	<title>${data.typeStr}成功</title>
</head>
<body>
    <div id="kaihu_page" >
        <div class="bank_head">
            <img src="${url}image/js_bank_logo.png">
        </div>
        <div class="result_message">
            <img src="${url}image/duihao.png">
            <span>${data.typeStr}成功</span>
            <p>您的交易密码已设置成功</p>
        </div>
        <div class="sure_btn" id="submit">
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