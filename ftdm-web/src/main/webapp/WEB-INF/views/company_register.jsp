<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
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
    <title>企业信息</title>
</head>
<body>
<div id="kaihu_page">
    <div class="bank_head">
        <img src="${url}image/js_bank_logo.png">
    </div>
    <div class="title_zu">
        企业信息
    </div>
    <div class="list_page">
        <ul>
            <li>
                <p>企业名称</p>
                <input type="text" name="org_name" value="${data.org_name}" readonly>
            </li>
            <li>
                <p>营业执照编号</p>
                <input type="text" name="business_license" value="${data.business_license}" readonly>
            </li>
            <li>
                <p>社会信用代码</p>
                <input type="text" name="" value="${data.bank_license}" readonly>
            </li>
        </ul>
    </div>
    <div class="title_zu">
        银行账户信息
    </div>
    <div class="list_page">
        <ul>
            <li>
                <p>对公账户</p>
                <input type="text" name="card_no" value="${data.card_no}" readonly>
            </li>
            <li>
                <p>开户银行</p>
                <input type="text" name="open_branch" value="${data.open_branch}" readonly>
            </li>
            <li>
                <p>角色</p>
                <input type="text" name="role" value="${data.role}" readonly>
            </li>

        </ul>
    </div>
    <div class="title_zu">
        开通授权<img src="${url}image/gt_icon.png" alt="">
    </div>
    <div class="list_page">
        <ul>
            <li>
                <p>授权金额</p>
                <input type="text" name="authed_amount" value="${data.authed_amount}" readonly>
            </li>
            <li>
                <p>授权期限</p>
                <input type="text" name="authed_limittime" value="截止${data.authed_limittime}" readonly>
            </li>
        </ul>
    </div>
    <div class="title_zu">
        信息验证
    </div>
    <div class="list_page">
        <ul>
            <li>
                <p>手机号码</p>
                <input type="text" name="" value="${data.mobile}" readonly>
            </li>
            <li>
                <p>短信验证码</p>
                <input id="auth_code" type="text" name="" maxlength="6" placeholder="请输入短信验证码">
                <div>
                    <span class="send_btn">发送验证码</span>
                    <button id="send_auth_code" style="position:absolute;right:0;width:2rem;height:100%;opacity:0">
                    </button>
                </div>
            </li>
            <li>
                <p>邮箱</p>
                <input type="text" name="email" value="${data.email}" readonly>
            </li>
        </ul>
    </div>
    <div class="title_zu">
        设置交易密码
    </div>
    <div class="list_page">
        <ul>
            <li>
                <p>设置密码</p>
                <table>
                    <tr>
                        <td height="40" id="_ocx_password_str1"></td>
                        <td>
                            <input type="text" readonly='readonly' id="kb1" name="input3" placeholder="请设置六位交易密码"
                                   class="default NoneInput90" maxlength="40" tabindex="3"/>
                        </td>
                    </tr>
                </table>
                <script>
                    if (isPhone) {
                        $("#kb1").show();
                        $("#_ocx_password_str1").hide();
                        //初始化密码卫士,绑定键盘对象。数字参数：0代表全键盘，1代表数字键盘
                        passGuard1.generate("kb1", kb, "passGuard1", 1);
                    } else {
                        $("#kb1").hide();
                        $("#_ocx_password_str1").show();
                        //绘制控件标签
                        pgeCtrl.pwdhtml("_ocx_password_str1", pgeditor1.load());
                    }
                </script>
            </li>
            <li>
                <p>确认密码</p>
                <table>
                    <tr>
                        <td height="40" id="_ocx_password_str2"></td>
                        <td>
                            <input type="text" readonly='readonly' id="kb2" name="input3" placeholder="请确认交易密码"
                                   class="default NoneInput90" maxlength="50" tabindex="3"/>
                        </td>
                    </tr>
                </table>
                <script>
                    if (isPhone) {
                        $("#kb2").show();
                        $("#_ocx_password_str2").hide();
                        //初始化密码卫士,绑定键盘对象。数字参数：0代表全键盘，1代表数字键盘
                        passGuard2.generate("kb2", kb, "passGuard2", 1);
                    } else {
                        $("#kb2").hide();
                        $("#_ocx_password_str2").show();
                        //绘制控件标签
                        pgeCtrl.pwdhtml("_ocx_password_str2", pgeditor2.load());
                    }
                </script>
            </li>
        </ul>
    </div>
    <div class="agree_xy">
        <input type="checkbox" name="" id="agree_xieyi">
        <p>本人已阅读并同意签署<span onclick="xieyi_con()">《用户服务协议》</span></p>
    </div>
    <div class="submit_btn" id="submit_btn">
        提&nbsp;&nbsp;交
    </div>
</div>
</body>
<script type="text/javascript">
    $(function () {
        var page = {
            init: function () {
                // 兼容性
                window.console = window.console || (function () {
                    var c = {};
                    c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.profile = c.clear = c.exception = c.trace = c.assert = function () {
                    };
                    return c;
                })();
                if (!window.location.origin) {
                    window.location.origin = window.location.protocol + "//" + window.location.hostname + (window.location.port ? ':' + window.location.port : '');
                }
                var RootUrl = "${url}";
                // 获取验证码
                $('#send_auth_code').click(function () {
                    page.codeFun(RootUrl)
                });
                // 提交
                $(".submit_btn").click(function () {
                    page.submitFun(RootUrl)
                });
            },
            postRequest: function (url, param, callback) {
                $.ajax({
                    url: url,
                    data: param,
                    type: 'post',
                    cache: false,
                    dataType: 'json',
                    success: function (res) {
                        callback(res);
                    },
                    error: function (res) {
                        callback(res);
                    }
                });
            },
            GetQueryString: function (name) {//取值
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                var r = window.location.search.substr(1).match(reg);
                if (r != null) {
                    return decodeURIComponent(r[2]);
                }
            },
            encodeParams: function (params) {//传值
                var r = '?',
                    p = [];
                for (var key in params) {
                    p.push(encodeURIComponent(key) + '=' + encodeURIComponent(params[key]));
                }
                return r + p.join('&');
            },
            submitFun: function (RootUrl) {
                var params = {};
                if (!$('#agree_xieyi').is(':checked')) {
                    var msg = "<div class=\"alert_box_layer\"><img src=\"${url}image/error.png\"><span>协议未同意</span></div>";
                    layer.msg(msg);
                    return;
                }
                if (isPhone) {
                    if (passGuard1.getHash() != passGuard2.getHash()) {
                        var msg = "<div class=\"alert_box_layer\"><img src=\"${url}image/error.png\"><span>两次密码不一致</span></div>";
                        layer.msg(msg);
                        passGuard1.clearpwd();
                        passGuard2.clearpwd();
                        return;
                    }
                    if (passGuard1.getLength() != 6 || passGuard2.getLength() != 6) {
                        var msg = "<div class=\"alert_box_layer\"><img src=\"${url}image/error.png\"><span>请输入六位数密码</span></div>";
                        layer.msg(msg);
                        passGuard1.clearpwd();
                        passGuard2.clearpwd();
                        return;
                    }
                    $.ajax({
                        url: "./send_randkey?token=${token}",
                        type: "GET",
                        async: false,
                        success: function (ranKey) {
                            passGuard1.setRandKey(ranKey);
                            passGuard2.setRandKey(ranKey);
                            params.random_key = ranKey;
                        }
                    });
                    params.trans_pwd = passGuard1.getOutputSM();
                } else {
                    if (pgeditor1.pwdHash() != pgeditor2.pwdHash()) {
                        var msg = "<div class=\"alert_box_layer\"><img src=\"${url}image/error.png\"><span>两次密码不一致</span></div>";
                        layer.msg(msg);
                        pgeditor1.pwdClear();
                        pgeditor2.pwdClear();
                        return;
                    }
                    if (pgeditor1.pwdLength() == undefined || pgeditor1.pwdLength() !=6
                        || pgeditor2.pwdLength() == undefined || pgeditor2.pwdLength() != 6) {
                        var msg = "<div class=\"alert_box_layer\"><img src=\"${url}image/error.png\"><span>请输入六位数密码</span></div>";
                        layer.msg(msg);
                        pgeditor1.pwdClear();
                        pgeditor2.pwdClear();
                        return;
                    }
                    Ajax.request({
                        url: "./srand_num?token=${token}",
                        type: "GET",
                        async: false,
                        success: function (xhr) {
                            var srand_num = pgeCtrl.trim(xhr.responseText);
                            srand_num = srand_num.substr(1, srand_num.length - 2);
                            pgeditor1.pwdSetSk(srand_num);
                            params.random_key = srand_num;
                        }
                    });
                    params.trans_pwd = pgeditor1.pwdResultSM();
                    pgeditor1.pwdClear();
                    pgeditor2.pwdClear();
                }
                var code = $("#auth_code").val();
                if (code.length == 0) {
                    var msg = "<div class=\"alert_box_layer\"><img src=\"${url}image/error.png\"><span>请输入验证码</span></div>";
                    layer.msg(msg);
                    return;
                }
                if(isPhone){
                    params.type = "0";
                }else{
                    params.type = "1";
                }
                params.origin_order_no = "${data.order_no}";
                params.trans_serial = "${trans_serial}";
                params.identifying_code = code;
                params.token="${token}";
                $(".submit_btn").removeAttr("onclick");
                page.postRequest(RootUrl + "account/apply_org_register", params, function (res) {
                    $(".submit_btn").click(function () {
                        page.submitFun(RootUrl)
                    });
                    if (res.recode == "10000") {
                        location.href = RootUrl + "account/com_register_success" + page.encodeParams({trans_serial: "${trans_serial}",token:"${token}"});
                    } else {
                        <%--var msg = "<div class=\"alert_box_layer\"><img src=\"${url}image/error.png\"><span>" + res.remsg + "</span></div>";--%>
                        <%--layer.msg(msg);--%>
                        <%--return;--%>
                        location.href = RootUrl + "account/error_page" + page.encodeParams({error_info: res.remsg,token:"${token}"});
                    }
                })
            },
            codeFun: function (RootUrl) {
                $('#send_auth_code').attr("disabled", true).siblings('span').text("正在发送");
                var params = {};
                params.trans_serial = "${trans_serial}";
                params.origin_order_no = "${data.order_no}";
                params.token="${token}";

                page.postRequest(RootUrl + "account/get_code_4_company", params, function (res) {
                    if (res.recode == 10000) {
                        var _no = 60;
                        var time = setInterval(function () {
                            _no--;
                            $('#send_auth_code').siblings('span').text("重新发送(" + _no + ")");
                            if (_no == 0) {
                                $('#send_auth_code').attr("disabled", false).siblings('span').text("获取验证码");
                                _no = 60;
                                clearInterval(time);
                            }
                        }, 1000);
                    } else if (res.recode == 50036) {
                        var msg = "<div class=\"alert_box_layer\"><img src=\"${url}image/error.png\"><span>验证码请求过于频繁</span></div>";
                        layer.msg(msg);
                        var _no = res.remsg;
                        var time = setInterval(function () {
                            _no--;
                            $('#send_auth_code').attr("disabled", true).siblings('span').text("重新发送(" + _no + ")");
                            if (_no == 0) {
                                $('#send_auth_code').attr("disabled", false).siblings('span').text("获取验证码");
                                _no = 60;
                                clearInterval(time);
                            }
                        }, 1000);
                    } else {
                        $('#send_auth_code').attr("disabled", false).siblings('span').text("获取验证码");
                        var msg = "<div class=\"alert_box_layer\"><img src=\"${url}image/error.png\"><span>" + res.resmsg + "</span></div>";
                        layer.msg(msg);
                    }
                    ;
                })
            },
        }
        page.init()
    });
</script>
</html>