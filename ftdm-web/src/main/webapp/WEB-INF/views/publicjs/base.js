/* 判断是pc端还是移动端  网上有很多，我只用其中一种演示*/
var sUserAgent = navigator.userAgent.toLowerCase();
var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
var bIsMidp = sUserAgent.match(/midp/i) == "midp";
var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
var bIsAndroid = sUserAgent.match(/android/i) == "android";
var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
var isPhone;
var random, datab;
var ocxPath = "https://zx.jshbank.com:663/";
var h5key = "042c0346f9ee11e4388325b35ba1c1e79a29140aad9dc663637a984f685ab88c16e350e87c29e7e82812424cc60c4d471d1e2dfdefdd11795fb5182d20af6ad4a4";
var pcx = "2c0346f9ee11e4388325b35ba1c1e79a29140aad9dc663637a984f685ab88c16";
var pcy = "e350e87c29e7e82812424cc60c4d471d1e2dfdefdd11795fb5182d20af6ad4a4";
/* 根据不同的客户端引入样以及加载页面 */
if (bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) {
    isPhone = true;
} else {
    isPhone = false;
}
if (isPhone) {
    var kb = new keyBoard({
        "chaosMode": 0, // 混乱模式,0:无混乱;1:打开时乱一次;2:每输入一个字符乱一次,默认值0
        "pressStatus": 1, // 按键状态,0:按下、抬起按键无变化;1:按下、抬起按键的颜色变化,默认值0
        "kbType": 0, // 键盘类型,0:全键盘;1:纯数字键盘,默认值0
        "odd": 51, //按键异或值
        "svg": "../svg" //svg图片的地址
    });
    var passGuard1 = new passGuard({
        "mappurl": "./send_mapping?token=9a6e4e81c1c43cca4a33ba5d4787ce7b",
        "maxLength": 6, // 最大输入长度
        "regExp1": "[\\S\\s]", // 输入过程限制的正则
        "regExp2": "[0-9]{6,12}",
        "displayMode": 0, // 显示形式,0:星号;1:明文,默认值0
        "callBack": cb1, //成功回调
        "errorCallBack": cb2, //失败回调
        "focus": inputFocus1,//H5键盘获取焦点回调
        "blur": inputBlur1,//H5键盘失去焦点回调
        "sm2KeyHex": h5key,
        "rsaPublicKey": "" // rsa公钥
    });

    var passGuard2 = new passGuard({
        "mappurl": "./send_mapping?token=9a6e4e81c1c43cca4a33ba5d4787ce7b",
        "maxLength": 6, // 最大输入长度
        "regExp1": "[\\S\\s]", // 输入过程限制的正则
        "regExp2": "[0-9]{6,12}",
        "displayMode": 0, // 显示形式,0:星号;1:明文,默认值0
        "callBack": cb3, //成功回调
        "errorCallBack": cb4, //失败回调
        "focus": inputFocus2,//H5键盘获取焦点回调
        "blur": inputBlur2,//H5键盘失去焦点回调
        "sm2KeyHex": h5key,
        "rsaPublicKey": "" // rsa公钥
    });


    function cb1() {
        // console.log("成功1");
    }

    function cb2() {
        // console.log("失败1");
    }

    function cb3() {
        // console.log("成功2");
    }

    function cb4() {
        // console.log("失败2");
    }


    function inputFocus1() {
        $("#kb1").css({border: "1px solid rgba(255,255,255,0)"})
    }

    function inputBlur1() {
        $("#kb1").css({border: "1px solid rgba(255,255,255,0)"})
    }

    function inputFocus2() {
        $("#kb2").css({border: "1px solid rgba(255,255,255,0)"})
    }

    function inputBlur2() {
        $("#kb2").css({border: "1px solid rgba(255,255,255,0)"})
    }

    window.onload = function () {
        Le = 1;//调用H5键盘的input框
        for (var i = 1; i <= 1; i++) {//PH.arrPlace-->H5输入框的placeholder数组；PH.arrId-->H5输入框的id数组
            $("#" + PH.arrId[i - 1]).val("");
        }
        setTimeout(function () {
            kb.generate();
        }, 100);
    };
} else {
    //请求通讯加密两个参数(随机数pgeRZRandNum和数据B pgeRZDataB)
    Ajax.request({
        url: "./skey_enstr?token=9a6e4e81c1c43cca4a33ba5d4787ce7b",
        type: "GET",
        async: false,
        success: function (xhr) {
            var skey_enstr = pgeCtrl.trim(xhr.responseText);
            skey_enstr = skey_enstr.substr(1, skey_enstr.length - 2);
            var o = skey_enstr.split(",");
            random = o[0];
            datab = o[1];
        }
    });
    //new 控件对象
    window.pgeditor1 = new pge({
        pgePath: ocxPath,
        pgeId: "_ocx_password1",
        pgeEditType: 0,
        pgeEreg1: "[0-9]*",
        pgeEreg2: "[0-9]{6}",
        pgeMaxLength: 6,
        pgeTabIndex: 3,
        pgeClass: "ocx_style",
        pgeInstallClass: "ocx_style",
        pgeOnKeyDown: "",
        tabCallBack: "input2",
        pgeOnFocus: "",
        pgeOnBlur: "",
        AffineX: pcx,
        AffineY: pcy,
        pgeWindowID: "password" + new Date().getTime() + 1,
        pgeRZRandNum: random,
        pgeRZDataB: datab
    });
    window.pgeditor2 = new pge({
        pgePath: ocxPath,
        pgeId: "_ocx_password2",
        pgeEditType: 0,
        pgeEreg1: "[0-9]*",
        pgeEreg2: "[0-9]{6}",
        pgeMaxLength: 6,
        pgeTabIndex: 4,
        pgeClass: "ocx_style",
        pgeInstallClass: "ocx_style",
        pgeOnKeyDown: "",
        tabCallBack: "input2",
        pgeOnFocus: "",
        pgeOnBlur: "",
        AffineX: pcx,
        AffineY: pcy,
        pgeWindowID: "password" + new Date().getTime() + 2,
        pgeRZRandNum: random,
        pgeRZDataB: datab
    });
    //定义公共对象
    window.pgeCtrl = pgeditor1;
    //初始化
    pgeInit();
}
(function (win, doc) {
// 	//自定义相对设计稿文字大小100px,方便计算
    win.dSize = 100;
    //自定义设计稿宽度
    win.dWidth = 750;
    //最大设备宽度
    win.maxWidth = 750;
    var docEl = doc.documentElement;
    //计算根文字大小,添加到html上,页面布局相采用rem单位
    var fontEl = document.createElement('style');
    var reFont = function () {
        var clientWidth = docEl.clientWidth > maxWidth ? maxWidth : docEl.clientWidth;
        var scale = clientWidth / dWidth;
        // document.title=scale;
        // scale = scale>0.6?0.6:scale;
        var size = dSize * scale;
        docEl.firstElementChild.appendChild(fontEl);
        fontEl.innerHTML = 'html{font-size:' + size + 'px!important;}body{width:' + dWidth / dSize + 'rem;margin-left:auto!important;margin-right:auto!important;}';
    }
    var rotateScreen = function () {
        setTimeout(function () {
            reFont();
        }, 300)
    }
    var loading = function () {
        reFont();
    }
    win.addEventListener("onorientationchange" in win ? "orientationchange" : "resize", rotateScreen, false);
    document.addEventListener('DOMContentLoaded', loading, false);
})(window, document)

//定义layer
layui.use('layer', function () {
    var layer = layui.layer;
});

//协议弹框
function xieyi_con() {
    layer.open({
        title: '用户服务协议'
        ,
        content: '甲方：（用户姓名）为在乙方经营个体网络借贷机构进行投资或（及）借款的个体\n' +
        '乙方：（平台名称）个体网络借贷机构\n' +
        '丙方：晋商银行股份有限公司\n' +
        '\n' +
        '甲方为乙方经营个体网络借贷机构进行投资或（及）借款的个体，乙方和丙方开展个体网络借贷机构客户资金存管业务合作。为明确三方的权利和义务，规范三方业务行为，甲乙丙三方本着自愿、平等、互惠、互利的原则，达成本协议。协议三方应予遵守。\n' +
        '\n' +
        '第一条相关定义\n' +
        '（一）个体网络借贷（下称“网络借贷”）：是指个体和个体之间通过互联网平台实现的直接借贷。\n' +
        '（二）个体网络借贷机构（下称“网贷机构”）：指乙方经营的，为投资方和融资方提供信息交互、撮合、资信评估等中介服务的机构以及其相关互联网平台、系统。\n' +
        '（三）客户资金存管业务（下称“存管业务”）：指丙方针对网络借贷交易资金存管需求开发的产品，主要功能包括账户开立、投标、放款、还款等。乙丙双方系统对接完成并对外运营后，客户于丙方单独开立账户，丙方对客户资金进行管理和监督，实现客户资金与乙方自身资金的分账管理。\n' +
        '（四）客户：指在网贷机构注册、交易，并接受丙方提供的资金存管业务的个人。根据资金的供需情况，分为投资人及借款人。\n' +
        '（五）标的：指网贷机构发布的单笔融资需求。\n' +
        '（六）投标：指投资人通过网贷机构向指定标的进行投资，丙方对投资资金进行止付处理的过程。\n' +
        '（七）放款：指满足放款条件情况下，乙方向丙方发送指令将指定标的全部已止付的投标资金进行解除止付并划转至借款人账户，同时将机构应收费用划转至机构结算账户的过程（如有）。\n' +
        '（八）还款：指借款人向投资人还款，乙方向丙方发送指令将款项从借款人账户划转至投资人账户，同时将机构应收费用划转至机构结算账户的过程（如有）。\n' +
        '\n' +
        '第二条声明\n' +
        '（一）甲方声明\n' +
        '1.甲方具有相应合法的投资或（及）借款资格，不存在法律、法规、规章、其他规范性文件和乙方交易规则禁止或限制进行网络借贷交易的情形。\n' +
        '2.甲方保证其向乙方、丙方提供的所有证件、资料均合法、真实、准确、完整和有效。甲方资料发生变化时，甲方必须按照约定的要求，及时通知乙方和丙方。\n' +
        '3.甲方保证其资金来源合法合规、且允许进行网络借贷交易，不存在洗钱等金融犯罪的情况。\n' +
        '4.乙方已向甲方清楚揭示网络借贷交易风险，甲方已明确知晓并愿意承担交易风险。\n' +
        '5.甲方已明确知晓丙方仅负责客户资金存管责任，丙方不介入乙方的商业行为，不审核标的及客户，不担保借贷行为，不承担客户因借款行为所产生的任何损失，无义务进行催收或强制扣款。\n' +
        '6.甲方同意遵守有关的法律、法规及乙方交易规则。\n' +
        '7.甲方同意丙方根据乙方平台、系统发送的电子指令和信息进行相关账务处理。\n' +
        '8.甲方同意乙丙双方根据约定，暂停或终止本业务。\n' +
        '9.甲方已详细阅读本协议所有条款，准确理解其含义，特别是其中有关乙方、丙方的责任条款，并同意本协议所有条款。甲方完全知晓本服务开通后，甲方通过乙方互联网平台进行的所有操作可能产生的任何结果。由此产生的资金、操作风险由甲方本人承担，甲方应审慎选择投资对象。\n' +
        '（二）乙方声明\n' +
        '1.乙方是依法设立的网络借贷机构，具有相应的资格开展网络借贷相关业务。\n' +
        '2.乙方承诺其服务平台注册的投资人、借款人和借款项目信息的真实性和合法性，不存在任何虚假借款人和借款项目，不存在为借款人虚构借款项目、故意隐瞒借款项目重大瑕疵等损害投资人合法权益的情形，并承担由此而可能引发的经济及法律责任。\n' +
        '3.乙方承诺其通过自身服务系统向丙方系统所提交的任何电子指令和信息由甲方自主操作或授权后自主产生，信息及指令内容完全符合甲方意愿，不得违反乙方与甲方间的约定，具有真实性、完整性、准确性、合法性，乙方认可丙方根据电子指令和信息进行的账户、账务处理。如因该类指令或数据信息不准确、不真实、不完整、不合法而可能导致甲方或丙方等相关方损失的，乙方承诺承担过错责任。\n' +
        '4.乙方有义务采取有效措施识别甲方身份，主动检测并报告可疑交易，妥善保存客户资料和交易记录。按照有关规定，建立健全有关协助查询、冻结的规章制度，协助公安机关和司法机关依法、及时查询、冻结涉案财产，配合公安机关和司法机关做好相关取证工作。\n' +
        '5.乙方对甲方资金来源、交易行为进行合法合规性审核，确保甲方及乙方本身不存在洗钱等金融犯罪情况。\n' +
        '6.乙方遵守有关的法律、法规及交易和结算规则。\n' +
        '7.乙方同意甲方根据双方约定，暂停或终止本业务。\n' +
        '8.乙方已详细阅读本协议所有条款，准确理解其含义，特别是其中有关丙方的责任条款，并同意本协议所有条款。\n' +
        '（三）丙方声明\n' +
        '1.丙方是依法设立的商业银行，仅按照本协议条款规定的内容承担职责，不负责审核甲方资金来源的合法合规性。\n' +
        '2.丙方不负责审核乙方所提供借款人和借款项目的真实性和合法性，也不保障借款人和借款项目必然还款。\n' +
        '3.丙方有义务根据有关协助查询、冻结的规章制度，协助公安机关和司法机关依法、及时查询、冻结涉案财产，配合公安机关和司法机关做好取证和执行工作。\n' +
        '4.丙方有权根据与乙方的约定，暂停或终止本业务，并进行相应的账务处理。\n' +
        '5.丙方不承担因监管机构要求停止本服务所引起的任何损失及责任。\n' +
        '6.丙方及其合作单位可向甲方发送各类业务信息。\n' +
        '\n' +
        '第三条基本规则约定\n' +
        '（一）账户\n' +
        '1.甲方用于网络借贷交易的账户应为本人于丙方开立的个人网贷专用账户。\n' +
        '2.丙方有权在账户开立以及相应的交易发生时，要求甲方进行交易校验，校验手段包括交易密码、验证码等。\n' +
        '（二）充值\n' +
        '1.充值行为由甲方主动发起，由乙方提供办理渠道。\n' +
        '2.甲方可以选择通过甲方在丙方开立的其他个人账户充值，也可以由丙方合作机构进行代扣充值。\n' +
        '（三）提现\n' +
        '1.提现行为由甲方主动发起，由乙方提供办理渠道。\n' +
        '2.甲方可以提现至甲方在丙方开立的其他个人账户。对于符合丙方要求条件的，甲方可以提现至其他个人账户。\n' +
        '（四）投标\n' +
        '1.投标行为由甲方主动发起，由乙方提供办理渠道。\n' +
        '2.投标后，账户内的投标资金将止付，已止付的资金在解除止付前不可再次用于投标或其他结算操作。\n' +
        '3.乙方提供自动投标服务且甲方确认接受该服务的，甲方授权乙方在满足投标条件时进行自动投标。授权文件由乙方保留，如丙方需要查阅时，乙方应予以支持。如因乙方未能取得还款人的充分授权而可能导致丙方或还款人等相关损失的，乙方应承担一切相关责任。\n' +
        '4.甲方投标时，如需乙方进行审核的，因乙方审核不通过而使投标不成功的，丙方不承担任何责任。\n' +
        '（五）放款\n' +
        '1.乙方审核确认达到放款条件的，乙方向丙方发送放款信息及指令；\n' +
        '2.丙方根据信息及指令，将已投标资金自动解除止付并划转到借款人指定账户。\n' +
        '3.乙方审核未通过导致无法放款的，丙方不承担任何责任。\n' +
        '（六）还款\n' +
        '1.还款行为由甲方主动发起，由乙方提供办理渠道。\n' +
        '2.还款资金从借款人指定账户划转到投资人指定账户。\n' +
        '3.乙方提供自动还款服务且甲方确认接受该服务的，甲方授权乙方根据约定进行自动还款。授权文件由乙方保留，如丙方需要查阅时，乙方应予以支持。如因乙方未能取得还款人的充分授权而可能导致丙方或还款人等相关损失的，乙方应承担一切相关责任。\n' +
        '（七）收费\n' +
        '1.乙方承诺从甲方收取的各项费用已经取得甲方的授权。授权文件由乙方保留，如丙方需要查阅时，乙方应予以支持。\n' +
        '2.丙方根据乙方的信息及指令进行划款，不对扣收款项进行进一步审查，甲乙双方由于扣收费用带来的纠纷，由甲乙双方自行处理，不得追究丙方责任。\n' +
        '（八）转出\n' +
        '乙丙双方因故暂停或终止合作的，甲方应根据乙方或丙方的安排将网贷专用账户内的资金及时提现。乙丙双方合作终止仍未提现的，由丙方将资金转出至甲方在丙方开立的其他个人账户。\n' +
        '\n' +
        '第四条反洗钱职责\n' +
        '    甲方应遵守《中华人民共和国反洗钱法》等反洗钱相关法律、法规，保证资产来源不属于违法犯罪所得，保证提供的客户信息真实准确，乙方、丙方承诺积极履行反洗钱职责。\n' +
        '\n' +
        '第五条风险提示\n' +
        '根据《网络借贷信息中介机构业务活动管理暂行办法》银监会令【2016】1号所述，丙方不承担融资项目及借贷交易信息真实性的实质审核，不对网贷资金本金及收益予以保证或承诺。\n' +
        '\n' +
        '第六条法律适用及管辖\n' +
        '本协议适用中华人民共和国法律法规。履行中如发生争议，三方协商或调解不成的，应向丙方所在地（太原市万柏林区）人民法院提起诉讼。\n' +
        '\n' +
        '第七条其他\n' +
        '（一）丙方具有对系统进行升级、改造的权利。因丙方对系统进行升级、改造而引起的服务取消、暂停或者甲方、乙方账号、服务内容、项目、方式等变化，丙方将通过适当方式提前公告，不再逐一通知甲方、乙方。\n' +
        '（二）丙方有权修改本服务条款，并通过官方网站、互联网借贷平台等渠道公布最新的约定事项，不再逐一通知甲方、乙方。如甲、乙方在本服务条款修改后继续使用本服务，则视为已了解并同意接受修改。\n' +
        '（三）因不可抗力或供电、通讯、网络等非丙方原因导致本服务不能正常进行的，丙方将视情况协助甲方、乙方解决或提供必要的帮助，但不承担责任。\n' +
        '（四）在交易过程中，因网络通讯故障或其他原因造成的错账的，丙方有权根据实际交易情况进行账务调整。\n' +
        '\n' +
        '\n' +
        '用户授权协议\n' +
        '\t\n' +
        '用户授权协议\n' +
        '一、定义及解释\n' +
        '（1）网络借贷平台（下称“平台”）由（具体公司名称）负责运营。《晋商银行个体网络借贷客户资金存管业务三方协议》（下称“三方协议”）三方为平台注册用户、（平台名称）、晋商银行，本协议对用户、（平台名称）均具有约束力的文件，具有合同上的法律效力。\n' +
        '用户同意，开通《三方协议》项下的上述所有服务及其相应的委托支付产品、存管账户，同时授权晋商银行按照平台方（安心投）的指令对用户开通的存管账户进行操作，用户已知晓上述服务可能无需用户再次输入支付密码。\n' +
        '二、声明与授权\n' +
        '（一）用户同意，在使用《三方协议》项下的服务时的意思表示均出自于用户本人的真实意愿。同时用户确保在使用服务时所填写的信息均真实有效，否则因此导致的责任由用户自行承担。\n' +
        '（二）用户在此授权，晋商银行有权根据用户在平台方的投融资活动、指令及平台方的指令，对用户的电子账户进行免密操作，以便用户在平台上正常开展投融资活动。\n' +
        '（三）用户同意，晋商银行可以根据用户在平台上设置的内容或用户事先授权给平台方的内容从用户的存管账户扣款（包括向用户收取相应的服务费用）。\n' +
        '（四）用户同意，晋商银行仅对因错误执行平台方的指令承担责任，对于因平台方发送指令错误或迟延，或平台方未取得用户的事先授权而发送指令，导致用户的电子账户或银行账户发生的资金损失，晋商银行不承担任何责任，用户应该与平台方或交易对方自行解决前述纠纷。用户理解并明白晋商银行仅在此过程中仅根据平台方扣款指令划扣相应款项，除晋商银行错误执行指令外，用户不得据此向晋商银行主张任何索赔或补偿。\n' +
        '（五）用户同意，开通《三方协议》项下的服务后，当晋商银行自用户的存管账户中划扣相应款项的情况下，若因平台方未能及时出具账单，或账单逾期、错误导致您未能及时偿付相关款项而产生违约金或者多扣、重复扣款的，因此导致的所有责任及损失，均由用户与平台方自行协商解决。\n' +
        '三、其他\n' +
        '本协议依附于《三方协议》而存在，未尽事宜均以晋商银行或平台方不时更新公布的《三方协议》及相关规则为补充；本协议与《三方协议》及相关规则不一致的地方，以本协议为准。本协议中的所有术语，除非另有说明，否则其定义与三方协议的定义相同。'
    });
}

//风险提示
function tishi_con() {
    layer.open({
        title: '风险提示'
        ,
        content: '风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示风险提示'
    });
}

function _$(id) {
    return document.getElementById(id);
}

function get_time() {
    return new Date().getTime().toString();
}
