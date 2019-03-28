@Echo Off
TITLE  存管2.0打包配置文件校验
:start
:: 测试环境打包根目录，请设置
set test_rootpath=D:\My Project\ftdm_plus_c
:: 准生产环境打包根目录，请设置
set wait_online_rootpath=D:\My Project\ftdm_plus_publish_b
:: 生产测试环境打包根目录，请设置
set online_test_rootpath=D:\My Project\ftdm_plus_publish_a
:: 生产环境打包根目录，请设置
set online_rootpath=D:\My Project\ftdm_plus_publish_a
set rootpath=0
set copyfilepath=0
set path_obj_length=12;
set path_obj[0].Res=\ftdm-common\src\main\resources
set path_obj[0].Des=\ftdm-common
set path_obj[0].DIR1=\config
set path_obj[0].File1=\system
set path_obj[1].Res=\ftdm-service-billcheck\src\main\resources
set path_obj[1].Des=\ftdm-billcheck
set path_obj[1].DIR1=\config
set path_obj[1].File1=\jdbc
set path_obj[1].DIR2=\spring
set path_obj[1].File2=\spring-context
set path_obj[2].Res=\ftdm-service-outside\src\main\resources
set path_obj[2].Des=\ftdm-outside
set path_obj[2].DIR1=\config
set path_obj[2].File1=\jdbc
set path_obj[2].DIR2=\spring
set path_obj[2].File2=\spring-context
set path_obj[3].Res=\ftdm-service-platform\src\main\resources
set path_obj[3].Des=\ftdm-platform
set path_obj[3].DIR1=\config
set path_obj[3].File1=\jdbc
set path_obj[3].DIR2=\spring
set path_obj[3].File2=\spring-context
set path_obj[4].Res=\ftdm-service-prod\src\main\resources
set path_obj[4].Des=\ftdm-prod
set path_obj[4].DIR1=\config
set path_obj[4].File1=\jdbc
set path_obj[4].DIR2=\spring
set path_obj[4].File2=\spring-context
set path_obj[4].DIR3=\spring
set path_obj[4].File3=\active-mq
set path_obj[5].Res=\ftdm-service-public\src\main\resources
set path_obj[5].Des=\ftdm-public
set path_obj[5].DIR1=\config
set path_obj[5].File1=\jdbc
set path_obj[5].DIR2=\spring
set path_obj[5].File2=\spring-context
set path_obj[5].DIR3=\spring
set path_obj[5].File3=\active-mq
set path_obj[6].Res=\ftdm-service-search\src\main\resources
set path_obj[6].Des=\ftdm-search
set path_obj[6].DIR1=\config
set path_obj[6].File1=\jdbc
set path_obj[6].DIR2=\spring
set path_obj[6].File2=\spring-context
set path_obj[7].Res=\ftdm-service-thirdparty\src\main\resources
set path_obj[7].Des=\ftdm-thirdparty
set path_obj[7].DIR1=\config
set path_obj[7].File1=\jdbc
set path_obj[7].DIR2=\spring
set path_obj[7].File2=\spring-context
set path_obj[7].DIR3=\spring
set path_obj[7].File3=\active-mq
set path_obj[8].Res=\ftdm-service-user\src\main\resources
set path_obj[8].Des=\ftdm-user
set path_obj[8].DIR1=\config
set path_obj[8].File1=\jdbc
set path_obj[8].DIR2=\spring
set path_obj[8].File2=\spring-context
set path_obj[9].Res=\ftdm-service-account\src\main\resources
set path_obj[9].Des=\ftdm-account
set path_obj[9].DIR1=\config
set path_obj[9].File1=\jdbc
set path_obj[9].DIR2=\spring
set path_obj[9].File2=\spring-context
set path_obj[9].DIR3=\spring
set path_obj[9].File3=\active-mq
set path_obj[10].Res=\ftdm-web\src\main\resources
set path_obj[10].Des=\ftdm-web
set path_obj[10].DIR1=\config
set path_obj[10].File1=\cfg
set path_obj[10].DIR2=\spring
set path_obj[10].File2=\spring-context
set path_obj[11].Res=\ftdm-service-thirdparty\src\main\resources
set path_obj[11].Des=\ftdm-thirdparty
set path_obj[11].DIR1=\config
set path_obj[11].File1=\thirdparty

set date0=%date:~0,4%_%date:~5,2%_%date:~8,2%
set time0=%time:~0,2%_%time:~3,2%_%time:~6,2%
set dttm=%date0%_%time0%


CLS
:: COLOR 90
:: 使用COLOR命令对控制台输出颜色进行更改
:: mode con cols=56 lines=20
:: MODE语句为设定窗体的宽和高
:sc_main
echo       ----------------------------------------------
echo.&echo.
echo             		0.测试 & echo.
echo             		1.准生产 & echo.
echo             		2.生产测试 & echo.
echo             		3.生产 & echo.
echo             		Q.退出 & echo.&echo.&echo.&echo.&echo.&echo.
set "select="
set/p select= 输入数字，按回车继续 :
if "%select%"=="0" (goto sc_test) 
if "%select%"=="1" (goto sc_wait_online) 
if "%select%"=="2" (goto sc_online_test) 
if "%select%"=="3" (goto sc_online) 
if "%select%"=="Q" (goto sc_exit)
:sc_exit
exit
goto :eof

:sc_test
set copyfilepath=%cd%\packageFile\test
set rootpath=%test_rootpath%
goto :start_copy_to_bak

:sc_wait_online
set copyfilepath=%cd%\packageFile\wait_online
set rootpath=%wait_online_rootpath%
goto :start_copy_to_bak

:sc_online_test
set copyfilepath=%cd%\packageFile\online_test
set rootpath=%online_test_rootpath%
goto :start_copy_to_bak

:sc_online
set copyfilepath=%cd%\packageFile\online
set rootpath=%online_rootpath%
goto :start_copy_to_bak



:start_copy_to_bak
	echo 正在备份文件，请稍候！
	SET path_obj_index=0
	:BakLoopStart
	IF %path_obj_index% EQU %path_obj_length% GOTO :start_copy_to_res
	 
	SET path_obj_current.Res=0
	SET path_obj_current.Des=0
	SET path_obj_current.File1=0
	SET path_obj_current.File2=0
	SET path_obj_current.File3=0
	SET path_obj_current.DIR1=0
	SET path_obj_current.DIR2=0
	SET path_obj_current.DIR3=0
	 
	FOR /F "usebackq delims==. tokens=1-3" %%I IN (`SET path_obj[%path_obj_index%]`) DO (
	  SET path_obj_current.%%J=%%K
	)
	
	set res=%rootpath%%path_obj_current.Res%
	set dec=%cd%\bak%dttm%%path_obj_current.Des%
	set dir1=%path_obj_current.DIR1%
	set dir2=%path_obj_current.DIR2%
	set dir3=%path_obj_current.DIR3%
	set file1=%dir1%%path_obj_current.File1%.properties
	set file2=%dir2%%path_obj_current.File2%.xml
	set file3=%dir3%%path_obj_current.File3%.xml
	
	
	:: md %dec%
	IF %path_obj_current.File1% NEQ 0 (
		echo 正在备份文件：%path_obj_current.File1%.properties
		:: xcopy "%res%%dir1%" "%dec%%dir1%\" /s /y /q
		if exist "%dec%%dir1%" (
			echo dir is exist.
		) else (
			md "%dec%%dir1%"
		)
		copy "%res%%file1%" "%dec%%file1%"
	)
	
	IF %path_obj_current.File2% NEQ 0 (
		echo 正在备份文件：%path_obj_current.File2%.xml
		:: xcopy "%res%%dir2%" "%dec%%dir2%\" /s /y /q
		if exist "%dec%%dir2%" (
			echo dir is exist.
		) else (
			md "%dec%%dir2%"
		)
		copy "%res%%file2%" "%dec%%file2%"
	)
	
	IF %path_obj_current.File3% NEQ 0 (
		echo 正在备份文件：%path_obj_current.File3%.xml
		:: xcopy "%res%%dir3%" "%dec%%dir3%\" /s /y /q
		if exist "%dec%%dir3%" (
			echo dir is exist.
		) else (
			md "%dec%%dir3%"
		)
		copy "%res%%file3%" "%dec%%file3%"
	)
	
	 
	SET /A path_obj_index=%path_obj_index% + 1
	 
	GOTO :BakLoopStart
	
:start_copy_to_res
	echo 备份完成。
	echo 正在拷贝文件，请稍后。
	SET path_obj_index=0
	:CopyLoopStart
	IF %path_obj_index% EQU %path_obj_length% GOTO :end_process
	 
	SET path_obj_current.Res=0
	SET path_obj_current.Des=0
	SET path_obj_current.File1=0
	SET path_obj_current.File2=0
	SET path_obj_current.File3=0
	SET path_obj_current.DIR1=0
	SET path_obj_current.DIR2=0
	SET path_obj_current.DIR3=0
	 
	FOR /F "usebackq delims==. tokens=1-3" %%I IN (`SET path_obj[%path_obj_index%]`) DO (
	  SET path_obj_current.%%J=%%K
	)
	
	set res=%rootpath%%path_obj_current.Res%
	set dec=%copyfilepath%%path_obj_current.Des%
	set dir1=%path_obj_current.DIR1%
	set dir2=%path_obj_current.DIR2%
	set dir3=%path_obj_current.DIR3%
	set file1=%dir1%%path_obj_current.File1%.properties
	set file2=%dir2%%path_obj_current.File2%.xml
	set file3=%dir3%%path_obj_current.File3%.xml
	
	:: md %dec%
	IF %path_obj_current.File1% NEQ 0 (
		echo 正在拷贝文件：%path_obj_current.File1%.properties
		copy "%dec%%file1%" "%res%%file1%"
	)
	
	IF %path_obj_current.File2% NEQ 0 (
		echo 正在拷贝文件：%path_obj_current.File2%.xml
		copy "%dec%%file2%" "%res%%file2%"
	)
	
	IF %path_obj_current.File3% NEQ 0 (
		echo 正在拷贝文件：%path_obj_current.File3%.xml
		copy "%dec%%file3%" "%res%%file3%"
	)
	
	SET /A path_obj_index=%path_obj_index% + 1
	 
	GOTO :CopyLoopStart
	:end_process
	echo 拷贝完成
	pause
	cls
	goto :sc_main