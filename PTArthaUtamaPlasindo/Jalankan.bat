@echo off
chcp 65001 >nul
cd /d "%~dp0"
where java >nul 2>nul
if errorlevel 1 (
    echo Java tidak ditemukan. Instal JDK atau JRE 8 atau lebih baru.
    pause
    exit /b 1
)
java -jar "dist\PTArthaUtamaPlasindo.jar"
if errorlevel 1 (
    echo.
    echo Aplikasi tidak dapat dijalankan. Lihat pesan kesalahan di atas.
    pause
)
