@echo off

set newVer=""
if "%1"=="" (
	set /p newVer=Please enter the new version number: 
) else (
	set newVer=%1
)
if %newVer%=="" (
	echo Invalid new version number
	goto ended
)

set sure="n"
set /p sure=The new number is %newVer%, are you sure? (y/n): 
if not "%sure%"=="y" (
	echo Not confirmed to modify version number
	goto ended
)

call mvn versions:set -DnewVersion=%newVer%
call mvn versions:commit

:ended