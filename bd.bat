@echo off
chcp 65001 >nul
cd /d "D:\XY55\prog\mcplug\t1m"
set "JAVA_HOME=C:\Users\afang\.jdks\ms-21.0.9"

echo.
echo === 开始构建过程 ===
echo.
echo 执行命令: gradle build --info
echo.

:: 执行 Gradle 构建，并过滤输出（仅保留关键信息，取前30行）
D:\XY55\gradle-9.2.1\bin\gradle.bat build --info -Dorg.gradle.console=rich 2>&1 | findstr /i "Task BUILD Starting Executing :compile :process :jar"
