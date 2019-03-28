
(function(a){
	//Windows IE32
	var PGEdit_IE32_EXE = "JSHBANKALL.exe";
	var PGEdit_IE32_VERSION = "1.0.0.1";
	//Windows IE64
	var PGEdit_IE64_EXE = "JSHBANKALL.exe";
	var PGEdit_IE64_VERSION = "1.0.0.1";
	//Windows IE ClassId & ProgId
	var PGEdit_IE_CLASSID = "8DCB0150-471C-4304-8F4A-C7FE5DE899A3";
	var PGEdit_IE_ProgId = "JSHBANKPassGuardX.PassGuard.1";
	//Windows 非IE插件(NPAPI,用于Chrome42-,FireFox50-)
	var PGEdit_FF = "JSHBANKALL.exe";
	var PGEdit_FF_VERSION = "1.0.0.1"; 
	//Windows 非IE插件 Mime类型和PluginName
	var PGEdit_FF_MIMETYPE = "jshbankpass-guard-x";
	var PGEdit_FF_PluginName = "JSHBANKPassGuardX";
	//Mac OS 插件版(NPAPI,用于Safari,FireFox50-,Chrome42-)
	var PGEdit_MacOs = "JSHBANK.pkg";
	var PGEdit_MacOs_VERSION = "1.0.0.1";
	//Mac OS 插件版 Mime类型和PluginName
	var PGEdit_MacOs_MIMETYPE = "jshbankpass-guard-x";
	var PGEdit_MacOs_PluginName = "JSHBANKPassGuardX";
	//Windows & Mac OS 非插件(用于Chrome42+,FireFox50+,MircroSoft Edge)
	var PGEdit_Edge = "JSHBANKALL.exe";
	var PGEdit_Edge_Mac = "JSHBANKmac.pkg";
	var PGEdit_Edge_VERSION = "1.0.0.1";
	//license授权
	var license = "Z1RSZWMveXljTjdzdkU5OHVJRHVCNUZ3ZGdaZGl3M2NRaHIrMEhod2hMcVk0MkxVZjM1RmdtSWFuTTNBQy9jNkNzSFFaRHBZTGdCem5zcUdlUTlzeFVJUndRY2wwRVdQMnMxb05tR2ZwZ0ZhQXNTeTg5em5oVDkycW0yS1hzMTY3YlRxRVU2YkNzU1BOQUxDMlRxSU1TVThaSlBERnI0V2pOVGF4K2ZzQmdVPXsiaWQiOjAsInR5cGUiOiJwcm9kdWN0IiwicGFja2FnZSI6WyIiXSwiYXBwbHluYW1lIjpbIipqc2hiYW5rLmNvbSJdLCJwbGF0Zm9ybSI6NH0=";
	var licenseMac = "Vjc3TjlKdVNrTk5iemRlaS9HSzhlSWVTYktyTXA1bTUzakZRYjQwWkVIWTZ5UEY0WWowMldpRzZRSlhFWUdMZzFwOXNydUhqTGhPTHd4U1c0NEh5OGR2aTdxWm5QYVZhVWpoV0FQdFF2Uk9lcHlRQWJxbWJyUnNwVTQ0bnppM3UvVUo3cVBvOHRyQVRqL1NXdGRDVnZkcS90SXRnQVU2b05NbG5RYVhRUHpjPXsiaWQiOjAsInR5cGUiOiJwcm9kdWN0IiwicGFja2FnZSI6WyIiXSwiYXBwbHluYW1lIjpbIipqc2hiYW5rLmNvbSJdLCJwbGF0Zm9ybSI6OH0=";
	var urlPar = "aHR0cDovL3p4LmpzaGJhbmsuY29tLw==";
	//设置是否升级 0:可选升级;1:强制升级
	var PGEdit_Update = "0",installText = "请点此安装控件",upText = "请点此升级控件",logFlag = true;//日志开关
	//Windows & Mac OS 非插件下用到的一些变量
	var PGEdit_Edge_URL = "https://windows10.microdone.cn",port = 5841,lastPort = port + 4;
	var CIJSON = {"interfacetype":0,"data":{"switch":3}};//检查控件是否安装
	var ICJSON = {"interfacetype":0,"data":{"switch":2}};//实例化控件窗口
	var INCJSON = {"interfacetype":1,"data":{}};//初始化控件参数
	var OPJSON = {"interfacetype":0,"data":{"switch":0}};//开启控件保护
	var XTJSON = {"interfacetype":0,"data":{"switch":5}};//心跳监测
	var CPJSON = {"interfacetype":0,"data":{"switch":1}};//关闭控件保护
	var OUTJSON = {"interfacetype":2,"data":{}};//获取值类json串
	var CLPJSON = {"interfacetype":0,"data":{"switch":4}};//清空密码
	//心跳监听变量、集合、本地服务地址、日志开关、全局检测安装变量
	var isInstalled = -1,objVersion = "",interv,urls,onceInterv={},iterArray=[];
	var inFlag = {},datac,RZCIJSON,pges = [];//控制是否能输入
	if(navigator.userAgent.indexOf("MSIE")<0){navigator.plugins.refresh()}//非IE需要刷新plugins数组
	function pge(option){
		this.settings = {};
		if(option){
			this.settings = option;
		}
		this.pgeDownText = installText;
		this.osBrowser = this.checkOsBrowser();
		if(this.osBrowser != 10 && this.osBrowser != 11){
			this.pgeVersion = this.getVersion();
		}
		isInstalled = this.checkInstall();
		if(this.settings.pgeId){
			for(var i = 0;i < pges.length;i++){
				if(this.settings.pgeId == pges[i].settings.pgeId){
					pges.splice(i,1);
				}
			}
			pges.push(this);
			inFlag[this.settings.pgeWindowID] = {"flag":false};
		}
	}
	//脚本版本号
	pge.apiVersion = "2.0";
	//设置授权license	
	pge.prototype.setLicense = function(){
		if(isInstalled){
			var control = document.getElementById(this.settings.pgeId);
			if(this.osBrowser == 1 || this.osBrowser == 3){
				control.license = license;
			}
		}
	}
	//给控件设置随机因子
	pge.prototype.pwdSetSk = function(s){
		if(isInstalled){
			try{
				var control = this.pwdGetEById(this.settings.pgeId);
				if (this.osBrowser==1 || this.osBrowser==3 || this.osBrowser==6 || this.osBrowser==8) {
					control.input1 = s;
				} else if (this.osBrowser==2) {
					control.input(1,s);
				}else if (this.osBrowser == 10 || this.osBrowser == 11){
					var id = this.settings.pgeWindowID;
					INCJSON = {"interfacetype" : 1,"data" : {}};
					INCJSON.id = id , INCJSON.data.aeskey = s;
					this.pwdGetData(INCJSON,"2000");
				}
			}catch(err){}
		}
	}
	//获得密码长度*/
	pge.prototype.pwdLength = function(){
		var code = 0;
		if(!isInstalled){
			code = 0;
		}else{
			try{
				var control = this.pwdGetEById(this.settings.pgeId);
				if (this.osBrowser==1 || this.osBrowser==3) {
					code = control.output3;
				} else if (this.osBrowser==2) {
					code = control.output(3);
				}else if (this.osBrowser==6 || this.osBrowser==8) {
					code = control.get_output3();
				}else if (this.osBrowser == 10 || this.osBrowser == 11){
					var id =this.settings.pgeWindowID;
				    OUTJSON.id = id,OUTJSON.data.datatype = 3,OUTJSON.data.encrypt = 0;
					code = this.pwdGetData(OUTJSON,"2001");
				}
			}catch(err){code = 0;}
		}
		return code;
	}
	//获得密码的Hash值
	pge.prototype.pwdHash = function(){
		var code = 0;
		if(!isInstalled){
			code = 0;
		}else{
			try{
				var control = this.pwdGetEById(this.settings.pgeId);
				if(this.osBrowser ==1||this.osBrowser ==3){
					code = control.output2;
				}else if(this.osBrowser ==2){
					code = control.output(2);
				}else if(this.osBrowser ==6||this.osBrowser ==8){
					code = control.get_output2();
				}else if(this.osBrowser ==10||this.osBrowser ==11){
					var id = this.settings.pgeWindowID;
					OUTJSON.id = id,OUTJSON.data.datatype = 2,OUTJSON.data.encrypttype = 1;
					code = this.pwdGetData(OUTJSON,"2002");
				}
			}catch(err){code = 0;}
		}
		return code;
	}
	//判断是否是简单密码
	pge.prototype.pwdSimple =function(){
		var code = '';
		if(!isInstalled){
			code = '';
		}else{
			try{
				var control = this.pwdGetEById(this.settings.pgeId);
				if(this.osBrowser ==1||this.osBrowser ==3){
					code =control.output44;
				}else if(this.osBrowser ==2){
					code =control.output(13);
				}else if(this.osBrowser ==6||this.osBrowser ==8){
					code =control.get_output10();
				}else if(this.osBrowser ==10||this.osBrowser ==11){
					var id = this.settings.pgeWindowID;
					OUTJSON.id = id,OUTJSON.data.datatype = 13,OUTJSON.data.encrypttype = 1;
					code = this.pwdGetData(OUTJSON,"2003");
				}
			}catch(err){code = ''}
		}
		return code;
	}
	//获得密码的强度(强度的规则可调整)
	pge.prototype.pwdStrength = function(){
		var code = 0;
		if(!isInstalled){
			code = 0;
		}else{
			try{
				var control = this.pwdGetEById(this.settings.pgeId);
				if(this.osBrowser ==1||this.osBrowser ==3){
					var len = control.output3;
					var num = control.output4;
					var zoom = control.output54;
				}else if(this.osBrowser ==2||this.osBrowser ==4||this.osBrowser ==5){
					var len = control.output(3);
					var num = control.output(4);
					var zoom = control.output(4,1);
				}else if(this.osBrowser ==6||this.osBrowser ==8){
					var len = control.get_output3();
					var num = control.get_output4();
					var zoom = control.get_output16();
				}else if(this.osBrowser ==10||this.osBrowser ==11){
					var id = this.settings.pgeWindowID;
					OUTJSON.id = id,OUTJSON.data.datatype = 3, OUTJSON.data.encrypttype = 0;
					var len = this.pwdGetData(OUTJSON,"2001");
					OUTJSON.data.datatype = 4, OUTJSON.data.encrypttype = 2;
					var num = this.pwdGetData(OUTJSON,"2004");
					OUTJSON.data.datatype = 4,OUTJSON.data.encrypttype = 1;
					var zoom = this.pwdGetData(OUTJSON,"2005");
				}
				if(len < 6){
					code = 0;
				}else if(num == 1){
					code = 1; // 弱
				}else if(num == 2){
					code = 2; // 中
				}else if(num == 3){
					code = 3; // 强
				}
			}catch(err){code = 0;}
		}
		return code;
	}
	//判断是否匹配正则2
	pge.prototype.pwdValid = function(){
		var code =1;
		if(!isInstalled){
			code =1;			
		}else{
			try{
				var control = this.pwdGetEById(this.settings.pgeId);
				if(this.osBrowser ==1||this.osBrowser ==3){
					if (control.output1) code = control.output5;
				}else if(this.osBrowser ==2){
					code = control.output(5);
				}else if(this.osBrowser ==6||this.osBrowser ==8){
					code = control.get_output5();
				}else if ( this.osBrowser ==10||this.osBrowser == 11 ){
					var id = this.settings.pgeWindowID;
					OUTJSON.id = id,OUTJSON.data.datatype = 5,OUTJSON.data.encrypttype = 0;
					code = this.pwdGetData(OUTJSON,"2006");
				}
			}catch(err){code =1;}
		}
		return code;
	}
	//获得密码字符类型个数
	pge.prototype.pwdNum =function(){
		var code =0;
		if(!isInstalled){
			code =0;
		}else{
			try{
				var control =this.pwdGetEById(this.settings.pgeId);
				if(this.osBrowser ==1||this.osBrowser ==3){
					code =control.output4;
				}else if(this.osBrowser ==2){
					code =control.output(4);
				}else if(this.osBrowser ==6||this.osBrowser ==8){
					code =control.output4();
				}else if(this.osBrowser ==10||this.osBrowser ==11){
					var id = this.settings.pgeWindowID;
					OUTJSON.id = id,OUTJSON.data.datatype = 4,OUTJSON.data.encrypttype = 0;
					code = this.pwdGetData(OUTJSON,"2004");			
				}
			}catch(err){code =0;}
		}
		return code;
	}
	//清空密码
	pge.prototype.pwdClear =function(){
		if(isInstalled){
			if(this.osBrowser ==10 ||this.osBrowser ==11){
				var id =this.settings.pgeWindowID;
				var inputID =this.settings.pgeId
				this.pwdGetEById(inputID).value = "",CLPJSON.id = id;
				
				this.pwdGetData(CLPJSON,"2007");		
			}else{
				var control =document.getElementById(this.settings.pgeId);
				control.ClearSeCtrl();
			} 			
		}
	},
	//获得密码AES密文
	pge.prototype.pwdResult =function(){
		var code ='';
		if(!isInstalled){
			code = '';
		}else{
			try{
				var control = this.pwdGetEById(this.settings.pgeId);
				if(this.osBrowser ==1||this.osBrowser ==3){
					code =control.output1;
				}else if(this.osBrowser ==2){
					code = control.output(7);
				}else if(this.osBrowser ==6||this.osBrowser ==8){
					code = control.get_output1();
				}else if(this.osBrowser ==10 || this.osBrowser == 11){
					OUTJSON.id = this.settings.pgeWindowID,OUTJSON.data.datatype = 7,OUTJSON.data.encrypttype = 0;
					code = this.pwdGetData(OUTJSON,"2008");
				}
			}catch(err){code ='';}
		}
		return code;
	},
	//获得密码SM4+SM2密文
	pge.prototype.pwdResultSM =function(){
		var code ='';
		if(!isInstalled){
			code = '';
		}else{
			try{
				var sm=this.settings.AffineX +"|" +this.settings.AffineY;
				var control = this.pwdGetEById(this.settings.pgeId);
				if(this.osBrowser ==1||this.osBrowser ==3){
					control.input10="1";
					control.input7=sm;
					code = control.output104;
				}else if(this.osBrowser ==2){
					control.input(200,sm);
					code = control.output(7,12);
				}else if(this.osBrowser ==6||this.osBrowser ==8){
					control.input13=sm;
					code = control.get_output22();
				}else if(this.osBrowser ==10 || this.osBrowser == 11){
					OUTJSON.id = this.settings.pgeWindowID,OUTJSON.data.datatype = 7,OUTJSON.data.encrypttype = 12;
					code = this.pwdGetData(OUTJSON,"2008");
				}
			}catch(err){code ='';}
		}
		return code;
	},
	//获得计算机Mac信息密文
	pge.prototype.machineNetwork =function(){
		var code ='';
		if(!isInstalled){
			code = '';
		}else{
			try{
				var control = this.pwdGetEById(this.settings.pgeId);
				if(this.osBrowser ==1||this.osBrowser ==3){
					code = control.GetIPMacList();
				}else if(this.osBrowser ==2){
					code =control.output(9);
				}else if (this.osBrowser==6 || this.osBrowser==8) {
					code = control.get_output7(0);
				}else if(this.osBrowser ==10||this.osBrowser ==11){
					var id = this.settings.pgeWindowID;
					OUTJSON.id = id,OUTJSON.data.datatype = 9,OUTJSON.data.encrypttype = 0;
					code = this.pwdGetData(OUTJSON,"2009");
				}
			}catch(err){code ='';}
		}
		return code;
	}
	//获得计算机硬盘信息密文
	pge.prototype.machineDisk =function(){
		var code ='';
		if(!isInstalled){
			code = '';
		}else{
			try{
				var control = this.pwdGetEById(this.settings.pgeId);
				if(this.osBrowser ==1||this.osBrowser ==3){
					code = control.GetNicPhAddr(1);
				}else if(this.osBrowser ==2){
					code = control.output(11);
				}else if(this.osBrowser ==6||this.osBrowser ==8){
					code = control.get_output7(2);
				}else if(this.osBrowser ==10||this.osBrowser ==11){
					var id =this.settings.pgeWindowID;
					OUTJSON.id = id,OUTJSON.data.datatype = 11,OUTJSON.data.encrypttype = 0;
					code = this.pwdGetData(OUTJSON,"2010");
				}
			}catch(err){code ='';}
		}
		return code;
	},
	//获得计算机cpu信息密文
	pge.prototype.machineCPU = function(){
		var code ='';
		if(!isInstalled){
			code = '';
		}else{
			try{
				var control = this.pwdGetEById(this.settings.pgeId);
				if(this.osBrowser ==1||this.osBrowser ==3){
					code =control.GetNicPhAddr(2);
				}else if(this.osBrowser ==2){
					code =control.output(10);
				}else if(this.osBrowser ==6||this.osBrowser ==8){
					code = control.get_output7(1);
				}else if(this.osBrowser ==10||this.osBrowser ==11){
					var id = this.settings.pgeWindowID;
					OUTJSON.id = id,OUTJSON.data.datatype = 10,OUTJSON.data.encrypttype = 0;
					code = this.pwdGetData(OUTJSON,"2011");
				}
			}catch(err){code ='';}
		}
		return code;
	}
	//获得控件版本号
	pge.prototype.getVersion =function(){
		try{
			if(this.osBrowser ==1||this.osBrowser ==3){
				var comActiveX =new ActiveXObject(PGEdit_IE_ProgId);
				return comActiveX.output35;
			}else if(this.osBrowser ==2||this.osBrowser ==6||this.osBrowser ==8){
				var arr =new Array(),pge_version;
				var pge_info =navigator.plugins[PGEdit_FF_PluginName].description;
				if(pge_info.indexOf(":")>0){
					arr = pge_info.split(":");
					pge_version =arr[1];	
				}else {
					pge_version ='';
				}
			}else if(this.osBrowser ==10||this.osBrowser ==11){
				if(isInstalled ==-1||!isInstalled)return;
				var id =this.settings.pgeWindowID
				OUTJSON.id = id,OUTJSON.data.datatype = 12,OUTJSON.data.encrypttype = 0;
				this.pwdJsonp(OUTJSON, "1006", id, inputID)
			}
			return  pge_version;
		}catch(err){}
	}
	//检测操作系统和浏览器信息	
	pge.prototype.checkOsBrowser =function(){
		var userosbrowser,usera = navigator.userAgent,regStr_chrome = /chrome\/[\d.]+/gi,regStr_firefox =/Firefox\/[\d.]+/gi;
		if((navigator.platform == "Win32") || (navigator.platform == "Win")){
			if((usera.indexOf("MSIE") > 0) || (usera.indexOf("msie") > 0) ||(usera.indexOf("Trident") > 0) || (usera.indexOf("trident") > 0)){
				if(usera.indexOf("ARM")>0){
					userosbrowser = 9;
					this.pgeditIEExe ="";
				}else{
					userosbrowser = 1;
					this.pgeditIEExe = PGEdit_IE32_EXE;
					this.pgeditIEClassid = PGEdit_IE_CLASSID;
				}
			}else if(usera.indexOf("Edge") > 0){
				userosbrowser = 10;
				this.pgeditFFExe = PGEdit_Edge;
			}else if(usera.indexOf("Chrome") > 0){
				var chromeVersion = usera.match(regStr_chrome).toString();
				chromeVersion =parseInt(chromeVersion.replace(/[^0-9.]/gi,""));
				if(chromeVersion >42){
					userosbrowser =10;
					this.pgeditFFExe = PGEdit_Edge;
				}else{
					userosbrowser =2;
					this.pgeditFFExe = PGEdit_FF;
				}
			}else if(usera.indexOf("Firefox") > 0){
				var firefoxVersion = usera.match(regStr_firefox).toString();
				firefoxVersion = parseInt(firefoxVersion.replace(/[^0-9.]/gi,""));
				if(firefoxVersion >=50){
					userosbrowser =10;
					this.pgeditFFExe = PGEdit_Edge
				}else{
					userosbrowser = 2;
					this.pgeditFFExe = PGEdit_FF;
				}
			}else{
				userosbrowser = 2;
				this.pgeditFFExe = PGEdit_FF;
			}
		}else if(navigator.platform == "Win64"){
				if((usera.indexOf("Windows NT 6.2") > 0 || usera.indexOf("window nt 6.2") > 0) && usera.indexOf("Firefox") == -1){
					userosbrowser =1;
					this.pgeditIEClassid = PGEdit_IE_CLASSID;
					this.pgeditIEExe =PGEdit_IE32_EXE;
				}else if(usera.indexOf("MSIE") > 0 || usera.indexOf("msie") > 0 || usera.indexOf("Trident") > 0 || usera.indexOf("trident") > 0){
					userosbrowser = 3;
					this.pgeditIEClassid = PGEdit_IE_CLASSID;
					this.pgeditIEExe =PGEdit_IE64_EXE;
				}else if(usera.indexOf("Edge") || usera.indexOf("Firefox") > 0){
					userosbrowser = 10;
					this.pgeditFFExe = PGEdit_Edge;
				}else if(usera.indexOf("Chrome")){
					var chromeVersion = usera.match(regStr_chrome).toString();
					chromeVersion = parseInt(chromeVersion.replace(/[^0-9.]/gi,""));
					if(chromeVersion >= 42){
						userosbrowser = 10;
						this.pgeditFFExe = PGEdit_Edge;
					}else{
						userosbrowser =2;
						this.pgeditFFExe = PGEdit_FF;
					}
				}else{
					userosbrowser = 2;
					this.pgeditFFExe =PGEdit_FF
				}
		}else if(navigator.userAgent.indexOf("Macintosh")>0 ){
			if(usera.indexOf("Safari")> 0 && (usera.indexOf("Version/5.1")>0 || usera.indexOf("Version/5.2")>0 || usera.indexOf("Version/6")>0)){
				/*try{
					var regStr_mac =  /Mac OS X [\d]+_[\d]+/gi;
					var numStr = navigator.userAgent.match(regStr_mac).toString();
					var num = parseInt(numStr.replace(/[^0-9]/gi, ""));
					if(num >= 1013){
						userosbrowser = 11;
						this.pgeditFFExe = PGEdit_Edge_Mac;
					}else{
						userosbrowser=6;//macos
						this.pgeditFFExe=PGEdit_MacOs;		
					}
				}catch(err){*/
					userosbrowser=6;//macos
					this.pgeditFFExe=PGEdit_MacOs;		
				//}
			}else if(usera.indexOf("Chrome") > 0 || usera.indexOf("Firefox") > 0 ){
				var chromeVersion = usera.match(regStr_chrome);
				var firefoxVersion = usera.match(regStr_firefox);
				if(chromeVersion != null){
					chromeVersion = chromeVersion.toString();
					chromeVersion = parseInt(chromeVersion.replace(/[^0-9.]/gi,""));
					if(chromeVersion >= 42){
						userosbrowser =11;
						this.pgeditFFExe =PGEdit_Edge_Mac;
					}else{
						userosbrowser = 9;
						this.pgeditFFExe =PGEdit_MacOs;
					}
				}
				if(firefoxVersion != null){
					firefoxVersion = firefoxVersion.toString();
					firefoxVersion = parseInt(firefoxVersion.replace(/[^0-9.]/gi,""));
					if(firefoxVersion >= 50){
						userosbrowser = 11;
						this.pgeditFFExe = PGEdit_Edge_Mac;
					}else{
						userosbrowser = 8;
						this.pgeditFFExe =PGEdit_MacOs;
					}
				}
			}else if(usera.indexOf("Opera")>0 && (navigator.userAgent.indexOf("Version/11.6")>0 || navigator.userAgent.indexOf("Version/11.7")>0) ){
				userosbrowser = 8;
				this.pgeditFFExe = PGEdit_MacOs;
			}else if(navigator.userAgent.indexOf("Safari")>=0){
				/*try{
					var regStr_mac =  /Mac OS X [\d]+_[\d]+/gi;
					var numStr = navigator.userAgent.match(regStr_mac).toString();
					var num = parseInt(numStr.replace(/[^0-9]/gi, ""));
					if(num >= 1013){
						userosbrowser = 11;
						this.pgeditFFExe = PGEdit_Edge_Mac;
					}else{
						userosbrowser=6;//macos
						this.pgeditFFExe=PGEdit_MacOs;		
					}
				}catch(err){*/
					userosbrowser=6;//macos
					this.pgeditFFExe=PGEdit_MacOs;		
				//}		
			}else{
				userosbrowser = 0;
				this.pgeditFFExe = "";
			}
		}
		return userosbrowser;
	}
	//获得控件绘制标签代码
	pge.prototype.getPgeHtml = function(){
		if(this.osBrowser ==1||this.osBrowser ==3){
			return '<OBJECT ID="' + this.settings.pgeId + '" CLASSID="CLSID:' + this.pgeditIEClassid + '" codebase="'
				+this.settings.pgePath+ this.pgeditIECab + '" onkeydown="if(13==event.keyCode || 27==event.keyCode)'+this.settings.pgeOnKeyDown+';" tabindex="'+this.settings.pgeTabIndex+'" class="' + this.settings.pgeClass + '">' 
				+ '<param name="edittype" value="'+ this.settings.pgeEditType + '"><param name="maxlength" value="' + this.settings.pgeMaxLength +'"><param name="input58" value="'+this.settings.pgeOnFocus+'"><param name="input59" value="'+this.settings.pgeOnBlur+'">' 
				+ '<param name="input2" value="'+ this.settings.pgeEreg1 + '"><param name="input3" value="'+ this.settings.pgeEreg2 + '"></OBJECT>';
		}else if(this.osBrowser == 2){
			var ff = "";
			if(navigator.userAgent.indexOf("SE 2.X") > -1 || navigator.userAgent.indexOf("OPR") > -1){
			ff = "this.focus();";
			}
			var pgeOcx='<embed onmouseover="'+ff+'" onfocus="'+this.settings.pgeOnFocus+'" input_0 ="' + license + '" onblur="'+this.settings.pgeOnBlur+'" ID="' + this.settings.pgeId + '"  maxlength="'+this.settings.pgeMaxLength+'" input_2="'+this.settings.pgeEreg1+'" input_3="'+this.settings.pgeEreg2+'" edittype="'+this.settings.pgeEditType+'" type="application/' + PGEdit_FF_MIMETYPE + '" tabindex="'+this.settings.pgeTabIndex+'" class="' + this.settings.pgeClass + '" ';
			if(this.settings.pgeOnKeyDown!=undefined && this.settings.pgeOnKeyDown!="") pgeOcx+=' input_1013="'+this.settings.pgeOnKeyDown+'"';
			if(this.settings.tabCallBack!=undefined && this.settings.tabCallBack!="") pgeOcx+=' input_1009="setTimeout(function(){document.getElementById(\''+this.settings.tabCallBack+'\').focus();},20);"';
			if(navigator.userAgent.indexOf("Chrome") > -1 || navigator.userAgent.indexOf("Safari") > -1){
			if(this.settings.pgeOnFocus!=undefined && this.settings.pgeOnFocus!="") pgeOcx+=' input_1010="'+this.settings.pgeOnFocus+'"';
			if(this.settings.pgeOnBlur!=undefined && this.settings.pgeOnBlur!="") pgeOcx+=' input_1011="'+this.settings.pgeOnBlur+'"';
			}
			if(this.settings.pgeFontName!=undefined && this.settings.pgeFontName!="") pgeOcx+=' FontName="'+this.settings.pgeFontName+'"';
			if(this.settings.pgeFontSize!=undefined && this.settings.pgeFontSize!="") pgeOcx+=' FontSize='+Number(this.settings.pgeFontSize)+'';					
			pgeOcx+='/>';
			return pgeOcx;
		}else if(this.osBrowser == 6 || this.osBrowser == 8){
			return '<embed ID="' + this.settings.pgeId + '" tabCallBack = "'+this.settings.pgeMacTabCB+'" setlic ="'+licenseMac+'" losefocuscallback="'+this.settings.pgeOnBlur+'" havefocuscallback="'+this.settings.pgeOnFocus+'" input2="'+ this.settings.pgeEreg1 + '"  input3="'+ this.settings.pgeEreg2 + '" input4="'+Number(this.settings.pgeMaxLength)+'" input0="'+Number(this.settings.pgeEditType)+'" type="application/' + PGEdit_MacOs_MIMETYPE + '" version="'+PGEdit_MacOs_VERSION+'" tabindex="'+this.settings.pgeTabIndex+'" class="' + this.settings.pgeClass + '"/>';
		}else if(this.osBrowser == 10 || this.osBrowser == 11){
			/*if((this.getConvertVersion(objVersion)<this.getConvertVersion(PGEdit_Edge_VERSION))&&PGEdit_Update=="1"){
				var winPath = this.settings.pgePath+this.pgeditFFExe;
				return '<div id="'+this.settings.pgeId+'_down" class="'+this.settings.pgeInstallClass+'" style="text-align:center;line-height:25px;"><a href="'+this.settings.pgePath+this.pgeditFFExe+'">'+this.pgeDownText+'</a></div>';
			}*/
		//else{
				return '<div id="'+this.settings.pgeId+'_down" class="'+this.settings.pgeInstallClass+'" style="text-align:center;line-height:25px;"><a href="'+this.settings.pgePath+this.pgeditFFExe+'">'+this.pgeDownText+'</a></div>';
			//}
		}else{
			return '<div id="'+this.settings.pgeId+'_down" class="'+this.settings.pgeInstallClass+'" style="text-align:center;">暂不支持此浏览器</div>';
		}
	},
	pge.prototype.getJsVersion = function(){
		var jsVersion = "";
		if(this.osBrowser == 1) jsVersion = PGEdit_IE32_VERSION;
		if(this.osBrowser == 2) jsVersion = PGEdit_FF_VERSION;
		if(this.osBrowser == 3) jsVersion = PGEdit_IE64_VERSION;
		if(this.osBrowser == 6 || this.osBrowser ==8 ) jsVersion = PGEdit_MacOs_VERSION;
		return jsVersion;
	},
	//用document.write()绘制控件标签代码
	pge.prototype.generate = function(){
		if(this.osBrowser == 10 || this.osBrowser == 11){ return document.write(this.getPgeHtml()); }
		if( !isInstalled){
			return document.write(this.getPgeHtml());
		}else{
			var jsVersion = this.getJsVersion();
			if((this.getConvertVersion(this.pgeVersion) <this.getConvertVersion(jsVersion))&& PGEdit_Update ==1){
				this.setDownText();
				return document.write(this.getPgeHtml()); 	
			}
			
		}
		return document.write(this.getPgeHtml());
	},
	//返回控件绘制标签代码
	pge.prototype.load = function() {
		if(this.osBrowser == 10 || this.osBrowser == 11){ return this.getPgeHtml(); }
		if(!isInstalled){
			return this.getDownHtml();
		}else{
			var jsVersion = this.getJsVersion();
			if(this.getConvertVersion(this.pgeVersion) < this.getConvertVersion(jsVersion) && PGEdit_Update ==1){
				this.setDownText();
				return this.getDownHtml();
			}
		}
		return this.getPgeHtml();
	},
	//返回提示下载的标签代码
	pge.prototype.getDownHtml =function(){
		if(this.osBrowser ==1 || this.osBrowser ==3){
			return '<div id="'+this.settings.pgeId+'_down" class="'+this.settings.pgeInstallClass+'" style="text-align:center;line-height:28px;"><a href="'+this.settings.pgePath+this.pgeditIEExe+'">'+this.pgeDownText+'</a></div>'  ;
		}else if(this.osBrowser ==2 || this.osBrowser ==6 ||this.osBrowser ==8 ||this.osBrowser ==10 ||this.osBrowser ==11){
			return '<div id="'+this.settings.pgeId+'_down" class="'+this.settings.pgeInstallClass+'" style ="text-align:center;line-height:25px;"><a href="'+this.settings.pgePath+this.pgeditFFExe+'">'+this.pgeDownText+'</a></div>'
		}else{
			return '<div id="'+this.settings.pgeId+'_down" class="'+this.settings.pgeInstallClass+'"style ="text-align:center";>暂不支持此浏览器</div>'
		}
	},
	//检测控件是否已安装
	pge.prototype.checkInstall = function(s,callf){
		try{
			if(this.osBrowser ==1 || this.osBrowser ==3){
				var comActiveX = new ActiveXObject(PGEdit_IE_ProgId);
				return true;
			}else if(this.osBrowser ==2 || this.osBrowser ==6 || this .osBrowser ==8){
				var arr =new Array(),pge_version;
				var pge_info = navigator.plugins[PGEdit_FF_PluginName].description
					if(pge_info.indexOf(":") > 0){
						arr = pge_info.split(":");
						pge_version = arr[1];
						if(pge_version !="")return true;
					}else{
						 pge_version = "";
						 return false;
					}
			}else if((this.osBrowser == 10 ||this.osBrowser ==11) && s == 1 ){
				var id = CIJSON.id = this.settings.pgeWindowID;
				urls = PGEdit_Edge_URL + ":" + port;
				this.pwdJsonp(CIJSON,"1000",id,"",callf);
			}
		}catch(err){
			return false
		}
		return true;
	}
	//发同步ajax请求
	pge.prototype.pwdGetData = function(datas,type){
		 var datac = this.getEnStr(this.settings.pgeRZRandNum,datas);
		 var RZCIJSON = {"rankey":this.settings.pgeRZRandNum,"datab":this.settings.pgeRZDataB,"datac":datac};
		 var d = 0,typeStr = "";
		 if(type == "2000") typeStr = "set random";
		 if(type == "2001") typeStr = "get length";
		 if(type == "2002") typeStr = "get hash";
		 if(type == "2003") typeStr = "get simple";
		 if(type == "2004") typeStr = "get pwdnums";
		 if(type == "2005") typeStr = "get pwdnumstwo";
		 if(type == "2006") typeStr = "pwd valid";
		 if(type == "2007") typeStr = "clear pwd";
		 if(type == "2008") typeStr = "get pwdresult";
		 if(type == "2009") typeStr = "get mac";
		 if(type == "2010") typeStr = "get harddisk";
		 if(type == "2011") typeStr = "get cpu";
		 datas = JSON.stringify(RZCIJSON).replace(/\+/g,"%2B");
		 Ajax.request({
			 url : urls,
			 timeout :2000,
			 type : "GET",
			 async : false,
			 data :{
				 jsoncallback :"jsoncallback",
				 "str" : datas,
				 "type" : typeStr
			 },
			 success : function(x){
				 x = x.responseText;
				 x = x.substring(13,x.length-1);
				 x = JSON.parse(x);
				 d = x.data;
			 },
			 error : function(x){
				 d = -1;}
		 });
		 return d;
	}
	//发异步ajax请求
	pge.prototype.pwdJsonp = function(datas,type,id,inputID,callf){
		var datac = this.getEnStr(this.settings.pgeRZRandNum,datas),obj = this;
		var RZCIJSON = {"rankey":this.settings.pgeRZRandNum,"datab":this.settings.pgeRZDataB,"datac":datac};
		var typeStr = "",timenums = 5000;
		if(type != "1000") timenums = 20000;
		if(type == "1000") typeStr = "checkinstall";
		if(type == "1004") typeStr = "initControl";
		if(type == "1001") typeStr = "openProt";
		if(type == "1002") typeStr = "intervlOut";
		if(type == "1003") typeStr = "instControl";
		if(type == "1005") typeStr = "closeProt";
		if(type == "1006") typeStr = "get version";
		if(type == "1007") typeStr = "check password type num";
		if(type == "1008") typeStr = "check password type num";
		jsonp({
			url : urls,
			time : timenums,
			data : { "str" : JSON.stringify(RZCIJSON),"jsoncallback" : "cb","type" : typeStr},
			callback : "jsoncallback",
			success : function(x){
				if(type == "1000"){
					objVersion = x.data;
					if(x.code == 0){
						if(logFlag) console.log("control is installed");
					}else{
						if(logFlag) console.log("code:" + x.code);
					}
					if(!!callf) callf(obj);
				}else if(type == "1001"){
					if(x.code == 0){
						if(logFlag){
							console.log("pgeWindowsID:" + id);
							console.log("openProt success");
						}
						inFlag[id].flag = true;
					}
					if(logFlag) console.log("x.data:" + x.data + ",x.code:" + x.code);
				}else if(type == "1002"){
					var code = parseInt(x.data);
					if(logFlag){
						console.log("pgeWindowsID:" + id);
						console.log("x.data.length:" + x.data +",x.code:" +x.code);
					}
					var va = pgeCtrl.pwdGetEById(inputID),len = va.value.len,y ="",i =0;
					for(; i < code;i++) y +="*";
					va.value = y;
				}else if(type == "1003"){
					if(x.code == 0){
						if(logFlag){
							console.log("pgeWindowsID:" + id);
							console.log("inst success");
						}
						obj.initControl(id);
					}
					if(logFlag) console.log("x.data:" + x.data + ",x.code:" + x.code);
				}else if(type =="1004"){
					if(x.code == 0){
						if(logFlag){
							console.log("pgeWindowsID:" + id);
							console.log("init success");
						}
						inFlag[id] = {"flag":false};
					}
					if(logFlag) console.log("x.data:" + x.data + ",x.code:" + x.code);
				}else if(type =="1005"){
					if(x.code == 0){
						if(logFlag){
							console.log("pgeWindowsID:" + id);
							console.log("closeProt success");
						}
						inFlag[id].flag = false;
					}
					if(logFlag) console.log("x.data:" + x.data + ",x.code:" + x.code);
					inFlag[id].id = false; 
				}else if(type =="1006"){
					if(!!callf) callf(x.data);
				}else if(type =="1007"){
					if(!!callf) callf(x.data);
				}else if(type =="1008"){
					if(!!callf) callf(x.data);
				}
			},
			fail : function(jqXHR,textStatus,errorThrown){
				if(type == "1000"){
					if(port < lastPort){
						 port++;
						 obj.checkInstall(1,callf);
						 
					}else{checkInstall = false;return;}
				}
			 }
		})
	}
	//转换版本号
	//将控件版本号转换成整形值，用于比较
	pge.prototype.getConvertVersion = function(version){
		try{
			if( version ==undefined || version =="" ){
				return 0;
			}else{
				var flag = false;m = "";
				if(this.osBrowser == 1 || this.osBrowser == 3){
					if(version.indexOf(",") > -1 )flag =true;
				}
				if(flag)m = version.split(",");
				else m = version.split(".");
				var v = parseInt(m[0]*1000) + parseInt(m[1]*100) + parseInt(m[2]*10) + parseInt(m[3]);
				 return v;
			}
		
		}catch(err){return 0;}
	}
	//毫秒，主要用于防止缓存
	pge.prototype.pwdGetTime = function(){
		return new Date().getTime();
	}
	//字符串两端去空格
	pge.prototype.trim = function(x){
	    return x.replace(/(^\s*)|(\s*$)/g, ''); 
	}
	//通过id获取页面元素
	pge.prototype.pwdGetEById = function(id){
		return	document.getElementById(id);
	},
	pge.prototype.pwdhtml = function(id,htmlstr){
		this.pwdGetEById(id).innerHTML = htmlstr
	}
	//加密通信数据
	pge.prototype.getEnStr = function( sKey,jsonStr){
		var neiKey = [0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77, 0x1A, 0x2A, 0x2B,
			0x2C, 0x2D, 0x2E, 0x2F, 0x3A, 0x3B, 0x11, 0x22, 0x33, 0x44, 0x55,
			0x66, 0x77, 0x1A, 0x2A, 0x2B, 0x2C, 0x2D, 0x2E, 0x2F, 0x3A, 0x3B];
		var fkey = "",lx = "";
		for( var i = 0; i < sKey.length;i++ ){
			lx = String.fromCharCode(sKey[i].charCodeAt(0) ^ neiKey[i]);
			fkey += lx;
		}
		var hexKey = CryptoJS.enc.Utf8.parse(fkey);
		var enStr = CryptoJS.AES.encrypt(JSON.stringify(jsonStr),hexKey,{
			 mode : CryptoJS.mode.ECB,
			 padding : CryptoJS.pad.Pkcs7
		});
		return enStr.toString();
	}
	//设置升级提示文字
	pge.prototype.setDownText = function(){
		if(isInstalled != -1){
			 this.pgeDownText = upText;
		}
	},
	//当按enter键时，提交表单
	pge.prototype.setSX =function(e,m,o){
			var keynum,va =this.pwdGetEById(o.id),len =va.value.length,maxlen =this.settings.pgeMaxLength+1;
			if(window.event){
				keynum =e.keyCode;
			}else if(e.which){
				keynum =e.which;
			}
			if(keynum ==13){
				o.blur();
				eval("("+m+")");
			}
	}, 		
	//控制不能从密码框中间编辑
	pge.prototype.setCX = function(ctrl){
		var caretPos =0,len =ctrl.value.length;
		if(document.selection){
			var sel =document.selection.createRange();
			sel.moveStart('character',-ctrl.value.length);
			caretPos =sel.text.length;
		}else if(ctrl.selectionStart || ctrl.selectionStart =='0'){
			caretPos = ctrl.selectionStart;
		}
		if(caretPos <= len){
			if(ctrl.setSelectionRange){//设置光标位置函数 FF
				setTimeout(function(){
					ctrl.setSelectionRange(len,len);
				},1);	
			}else if(ctrl.createTextRange){//ie
			  var range =ctrl.createTextRange();
			  range.collapse(true);
			  range.moveEnd('character',len);
			  range.moveStart('character',len);
			  range.select();
			}
		}
	},
	//实例化密码控件窗口
	pge.prototype.instControl = function(id,obj){
		ICJSON.id = id;
		this.pwdJsonp(ICJSON,"1003",id);
	}
	//初始化密码控件窗口参数
	pge.prototype.initControl = function(id,inputID){
		var my =this.settings.AffineX +"|" +this.settings.AffineY;//公钥
		INCJSON.id = id,INCJSON.data.edittype = this.settings.pgeEditType,INCJSON.data.maxlength = this.settings.pgeMaxLength;
		INCJSON.data.reg1 = this.settings.pgeEreg1,INCJSON.data.reg2 = this.settings.pgeEreg2,INCJSON.data.sm2xyhexkey=my;
		INCJSON.data.lic = {"liccode":license,"url": urlPar};
		if(this.osBrowser == 11) INCJSON.data.lic.liccode = licenseMac;
		this.pwdJsonp(INCJSON,"1004",id);
		//初始化对应id心跳值
		onceInterv[id]="";
	}
	//开启密码控件保护
	pge.prototype.openProt = function(id,inputID){
		inFlag[id].flag = false,OPJSON.id = id;
		this.pwdJsonp(OPJSON, "1001",id);
		if(typeof onceInterv[id] =="string" ){//监听焦点切出
			for( var i =0; i < iterArray.length;i++){
				window.clearInterval(iterArray[i]);
			}
			onceInterv[id] =window.setInterval("pgeCtrl.intervlOut(\""+id+"\",\""+inputID+"\")",800);
			iterArray.push(onceInterv[id]);
		}
	},
	//检测心跳
	pge.prototype.intervlOut = function(id,inputID){
		XTJSON.id =id;
		this.pwdJsonp(XTJSON, "1002",id,inputID);
	},
	//关闭密码控件保护
	pge.prototype.closeProt = function(id,inputID){
		CPJSON.id = id;
		this.pwdJsonp(CPJSON,"1005",id)
		if(typeof onceInterv[id]=="number" ){
			 for(var i = 0; i <= iterArray.length; i++){
				  window.clearInterval(iterArray[i]);
			 }
			 onceInterv[id] ="";
		}
	}
	//同步版ajax
	Ajax = function() {  
	    function request(opt) {  
	        function fn() {  
	        }  
	        var url = opt.url || "";  
	        var async = opt.async !== false, method = opt.method || 'GET', data = opt.data  
	                || null, success = opt.success || fn, error = opt.failure  
	                || fn;  
	        method = method.toUpperCase();  
	        if (method == 'GET' && data) {  
	            var args = "";  
	            if(typeof data == 'string'){  
	                //alert("string")  
	            args = data;  
	            }else if(typeof data == 'object'){  
	                //alert("object")  
	                var arr = new Array();  
	                for(var k in data){  
	                    var v = data[k];  
	                    arr.push(k + "=" + v);  
	                }  
	                args = arr.join("&");  
	            }  
	        url += (url.indexOf('?') == -1 ? '?' : '&') + args;  
	            data = null;  
	        }  
	        var xhr = window.XMLHttpRequest ? new XMLHttpRequest()  
	                : new ActiveXObject('Microsoft.XMLHTTP');  
	        xhr.onreadystatechange = function() {  
	            _onStateChange(xhr, success, error);  
	        };  
	        xhr.open(method, url, async);  
	        if (method == 'POST') {  
	            xhr.setRequestHeader('Content-type',  
	                    'application/x-www-form-urlencoded;');  
	        }  
	        xhr.send(data);  
	        return xhr;  
	    }  
	    function _onStateChange(xhr, success, failure) {  
	        if (xhr.readyState == 4) {  
	            var s = xhr.status;  
	            if (s >= 200 && s < 300) {  
	                success(xhr);  
	            } else {  
	                failure(xhr);  
	            }  
	        } else {  
	        }  
	    }  
	    return {  
	        request : request  
	    };  
	}();
	//异步版ajax
	function jsonp(options) {
	    options = options || {};
	    if (!options.url || !options.callback) {
	        throw new Error("参数不合法");
	    }
	    var callbackName = ('jsonp_' + Math.random()).replace(".", "");
	    var oHead = document.getElementsByTagName('head')[0];
	    options.data[options.callback] = callbackName;
	    var params = formatParams(options.data);
	    var oS = document.createElement('script');
	    oHead.appendChild(oS);
	
	    //创建jsonp回调函数
	    window[callbackName] = function (json) {
	        oHead.removeChild(oS);
	        clearTimeout(oS.timer);
	        window[callbackName] = null;
	        options.success && options.success(json);
	    };
	    //发送请求
	    oS.src = options.url + '?' + params;
	    //超时处理
	    if (options.time) {
	        oS.timer = setTimeout(function () {
	            window[callbackName] = null;
	            oHead.removeChild(oS);
	            options.fail && options.fail({ message: "超时" });
	        }, options.time);
	    }
	};
	//格式化参数
	function formatParams(data) {
	    var arr = [];
	    for (var name in data) {
	        arr.push(name + '=' + encodeURIComponent(data[name]));
	    }
	    return arr.join('&');
	}
	a.pge = pge;
	a.pges = pges;
	a.inFlag = inFlag;
})(window);
window.pgeCtrl = new pge();
function sleep(n){
	var start = new Date().getTime();
	while(true) if(new Date()-start >n )break;
}
function pgeInit(callf){
	if(pgeCtrl.osBrowser !=10 && pgeCtrl.osBrowser != 11){
		for(var i = 0;i < pges.length;i++ ){
			pges[i].setLicense();
		}
		if(!!callf) callf(window.isInstalled);
	}else{
		 pgeCtrl.checkInstall(1,function(isIns){
			 if(isIns) window.isInstalled =true;else window.isInstalled =false;
			 if(!!callf) callf(window.isInstalled);
			 if(window.isInstalled){
				 var o;
				 for(var i = 0;i < pges.length;i++ ){
					 sleep(100);
					 var obj = pges[i];
					 var id =obj.settings.pgeId;
					 var winId =obj.settings.pgeWindowID;
					 var strr = "";
					if(obj.osBrowser == 10){
						strr = "this.type=\'password\';";
					}
						document.querySelector("#" + id + "_down").parentNode.innerHTML = '<input type="text" onfocus="'+ strr +'pgeCtrl.openProt(\''+winId+'\',this.id);pgeCtrl.setCX(this);'+obj.settings.pgeOnFocus+'"  onkeydown="pgeCtrl.setSX(event,\''+obj.settings.pgeOnKeyDown+'\',this);" onclick = "pgeCtrl.setCX(this)" onblur = "pgeCtrl.closeProt(\''+winId+'\',this.id);'+obj.settings.pgeOnBlur+'" id = "'+id+'" style="ime-mode:disabled" tabindex="2" class="' + obj.settings.pgeClass + '"/>';
					o = obj.pwdGetEById(id);
					o.pid = winId;
					if(obj.osBrowser == 10 || obj.osBrowser == 11){
						if(o != null){
							if(obj.osBrowser == 11){
								o.onkeypress = function(e){
									var objtemp;
									for(var xo in pges){
										if(pges[xo].settings.pgeId == this.id){
											objtemp = pges[xo];
										}
									}
									var chrTyped, chrCode=0, evt = e ? e : event,chrCode = evt.which;
									var x = String.fromCharCode(chrCode);
									var maxlength = parseInt(objtemp.settings.pgeMaxLength);
									if(chrCode>=32 && chrCode<=126){
										if(this.value.length > (maxlength-1)) return false;
										var reg1 = objtemp.settings.pgeEreg1.replace("*","");
										reg1 = new RegExp(reg1);
										if(reg1.test(x)) this.value += '*';
										return false;
									}else return true;
								}
								o.onkeydown = function(e){
									var chrTyped, chrCode=0, evt=e?e:event;
									chrCode = evt.which;
									var x = String.fromCharCode(chrCode);
									if(chrCode == 13){
										this.blur();
										eval("("+obj.settings.pgeOnKeyDown+")");
									}else if(chrCode>=37 && chrCode<=40) return false;
									 else return true;
								}
							}
							if(obj.osBrowser == 10){
								o.onkeypress = function(){ 
									return inFlag[this.pid].flag;
								}
							}
						}
						obj.instControl(winId,obj);
					}
				 }
			 }
		 })
	}
}