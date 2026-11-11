@echo off
setlocal
cd /d "%~dp0"
set "V=8.7"
if "%GRADLE_USER_HOME%"=="" set "GRADLE_USER_HOME=%USERPROFILE%\.gradle"
set "D=%GRADLE_USER_HOME%\wrapper\dists\gradle-%V%-bin"
set "G=%D%\gradle-%V%\bin\gradle.bat"
if not exist "%G%" (
  powershell -NoProfile -ExecutionPolicy Bypass -Command "$d='%D%'; New-Item -ItemType Directory -Force $d | Out-Null; $z=Join-Path $d 'g.zip'; Invoke-WebRequest -UseBasicParsing 'https://services.gradle.org/distributions/gradle-%V%-bin.zip' -OutFile $z; Expand-Archive -Force $z $d; Remove-Item $z"
)
call "%G%" %*
