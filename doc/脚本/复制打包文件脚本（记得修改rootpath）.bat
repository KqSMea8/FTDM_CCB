@echo off
set rootpath="D:\My Project\ftdm_plus_publish_b"

echo ....................................................
echo start copy account jars!
xcopy %rootpath%"\ftdm-service-account\target\lib" "D:\ftdm_jar\account\lib\" /s /e /y /q
copy %rootpath%"\ftdm-service-account\target\ftdm-service-account.jar" "D:\ftdm_jar\account\ftdm-service-account.jar"
echo finished!

echo .
echo ....................................................
echo start copy outside jars!
xcopy %rootpath%"\ftdm-service-outside\target\lib" "D:\ftdm_jar\outside\lib\" /s /e /y /q
copy %rootpath%"\ftdm-service-outside\target\ftdm-service-outside.jar" "D:\ftdm_jar\outside\ftdm-service-outside.jar"
echo finished!

echo .
echo ....................................................
echo start copy platform jars!
xcopy %rootpath%"\ftdm-service-platform\target\lib" "D:\ftdm_jar\platform\lib\" /s /e /y /q
copy %rootpath%"\ftdm-service-platform\target\ftdm-service-platform.jar" "D:\ftdm_jar\platform\ftdm-service-platform.jar"
echo finished!

echo .
echo ....................................................
echo start copy prod jars!
xcopy %rootpath%"\ftdm-service-prod\target\lib" "D:\ftdm_jar\prod\lib\" /s /e /y /q
copy %rootpath%"\ftdm-service-prod\target\ftdm-service-prod.jar" "D:\ftdm_jar\prod\ftdm-service-prod.jar"
echo finished!

echo .
echo ....................................................
echo start copy public jars!
xcopy %rootpath%"\ftdm-service-public\target\lib" "D:\ftdm_jar\public\lib\" /s /e /y /q
copy %rootpath%"\ftdm-service-public\target\ftdm-service-public.jar" "D:\ftdm_jar\public\ftdm-service-public.jar"
echo finished!

echo .
echo ....................................................
echo start copy search jars!
xcopy %rootpath%"\ftdm-service-search\target\lib" "D:\ftdm_jar\search\lib\" /s /e /y /q
copy %rootpath%"\ftdm-service-search\target\ftdm-service-search.jar" "D:\ftdm_jar\search\ftdm-service-search.jar"
echo finished!

echo .
echo ....................................................
echo start copy thirdparty jars!
xcopy %rootpath%"\ftdm-service-thirdparty\target\lib" "D:\ftdm_jar\thirdparty\lib\" /s /e /y /q
copy %rootpath%"\ftdm-service-thirdparty\target\ftdm-service-thirdparty.jar" "D:\ftdm_jar\thirdparty\ftdm-service-thirdparty.jar"
echo finished!

echo .
echo ....................................................
echo start copy ccbThirdparty jars!
xcopy %rootpath%"\ftdm-service-ccbThirdparty\target\lib" "D:\ftdm_jar\ccbThirdparty\lib\" /s /e /y /q
copy %rootpath%"\ftdm-service-ccbThirdparty\target\ftdm-service-thirdpart.jar" "D:\ftdm_jar\ccbThirdparty\ftdm-service-cbbThirdparty.jar"
echo finished!

echo .
echo ....................................................
echo start copy user jars!
xcopy %rootpath%"\ftdm-service-user\target\lib" "D:\ftdm_jar\user\lib\" /s /e /y /q
copy %rootpath%"\ftdm-service-user\target\ftdm-service-user.jar" "D:\ftdm_jar\user\ftdm-service-user.jar"
echo finished!

echo .
echo ....................................................
echo start copy billcheck jars!
xcopy %rootpath%"\ftdm-service-billcheck\target\lib" "D:\ftdm_jar\billcheck\lib\" /s /e /y /q
copy %rootpath%"\ftdm-service-billcheck\target\ftdm-service-billcheck.jar" "D:\ftdm_jar\billcheck\ftdm-service-billcheck.jar"
echo finished!

echo .
echo ....................................................
echo start copy web.war!
copy %rootpath%"\ftdm-web\target\ftdm-web.war" "D:\ftdm_jar\ftdm-web.war"
echo finished!

echo .
echo ............................................................
echo ............................................................
echo .....          请检查是否所有文件都复制成功！          .....
echo .....            按任意键打开 "D:\ftdm_jar"            .....
echo ............................................................
echo ............................................................

pause

start D:\ftdm_jar