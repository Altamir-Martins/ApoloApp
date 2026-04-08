@echo off
REM 🚀 APOLO - Script de Inicialização Rápida (Windows)
REM Execute este arquivo para rodar o app automaticamente

setlocal enabledelayedexpansion

cls
echo.
echo ╔════════════════════════════════════════════════════════════╗
echo ║              🚀 APOLO - Inicialização v2.0                ║
echo ╚════════════════════════════════════════════════════════════╝
echo.

REM Verifica se Python está instalado
python --version >nul 2>&1
if errorlevel 1 (
    echo ❌ Python não encontrado!
    echo.
    echo Instale Python 3 em: https://www.python.org/downloads/
    echo Durante a instalação, marque a opção "Add Python to PATH"
    echo.
    pause
    exit /b 1
)

for /f "tokens=*" %%i in ('python --version') do set PYTHON_VERSION=%%i
echo ✅ %PYTHON_VERSION% detectado
echo.

REM Verifica Token Mapbox
echo 🔑 Verificando configuração do Token Mapbox...
findstr /I "pk.eyJ" js\app.js >nul
if errorlevel 1 (
    echo ⚠️  Token Mapbox não configurado!
    echo.
    echo Para configurar:
    echo   1. Acesse: https://www.mapbox.com/signup
    echo   2. Crie conta (gratuita^)
    echo   3. Copie token (pk.eyJ...^)
    echo   4. Edite: js\app.js linha 285
    echo   5. Cole: mapboxgl.accessToken = 'SEU_TOKEN'
    echo.
    set /p CONTINUE="Continuar mesmo assim? (s/n): "
    if /i not "!CONTINUE!"=="s" (
        echo ❌ Abortado.
        pause
        exit /b 1
    )
) else (
    echo ✅ Token Mapbox detectado!
)

echo.
echo 🌐 Iniciando servidor HTTP...
echo    Porta: 8000
echo    URL: http://localhost:8000
echo.
echo ⏹️  Para parar: pressione Ctrl+C
echo.
echo Abrindo navegador em 2 segundos...
timeout /t 2 /nobreak

REM Tenta abrir navegador automaticamente
start http://localhost:8000 2>nul || (
    echo.
    echo ℹ️  Abra manualmente: http://localhost:8000
)

echo.
REM Inicia servidor
python -m http.server 8000

echo.
echo ✅ Servidor encerrado.
pause
