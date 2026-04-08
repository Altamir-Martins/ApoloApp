#!/bin/bash
# 🚀 APOLO - Script de Inicialização Rápida
# Execute este script para rodar o app automaticamente

echo "╔════════════════════════════════════════════════════════════╗"
echo "║              🚀 APOLO - Inicialização v2.0                ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

# Verifica se Python está instalado
if ! command -v python &> /dev/null && ! command -v python3 &> /dev/null
then
    echo "❌ Python não encontrado!"
    echo "Instale Python 3 em: https://www.python.org/downloads/"
    exit 1
fi

# Define comando Python
if command -v python3 &> /dev/null
then
    PYTHON="python3"
else
    PYTHON="python"
fi

echo "✅ Python detectado: $PYTHON"
echo ""

# Navega para o diretório do app
cd "$(dirname "$0")" || exit

echo "📁 Diretório: $(pwd)"
echo ""

# Verifica Token Mapbox
echo "🔑 Verificando configuração do Token Mapbox..."
if grep -q "pk.eyJ" js/app.js && ! grep -q ".example" js/app.js
then
    echo "✅ Token Mapbox configurado!"
else
    echo "⚠️  Token Mapbox não configurado ou é o padrão"
    echo ""
    echo "Para configurar:"
    echo "  1. Acesse: https://www.mapbox.com/signup"
    echo "  2. Crie conta (gratuita)"
    echo "  3. Copie token (pk.eyJ...)"
    echo "  4. Edite: js/app.js linha 285"
    echo "  5. Cole: mapboxgl.accessToken = 'SEU_TOKEN'"
    echo ""
    read -p "Continuar mesmo assim? (s/n) " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Ss]$ ]]
    then
        echo "❌ Abortado."
        exit 1
    fi
fi

echo ""
echo "🌐 Iniciando servidor HTTP..."
echo "   Porta: 8000"
echo "   URL: http://localhost:8000"
echo ""
echo "⏹️  Para parar: pressione Ctrl+C"
echo ""

# Inicia servidor
$PYTHON -m http.server 8000 --bind 127.0.0.1

# Se chegou aqui, usuário parou o servidor
echo ""
echo "✅ Servidor encerrado."
