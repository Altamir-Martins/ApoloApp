# 🗺️ Configuração do Mapbox GL JS - APOLO

## 🚀 Sobre a Mudança
O app foi atualizado para usar **Mapbox GL JS** com suporte a:
- ✅ Modo 3D (pitch e bearing)
- ✅ Heatmap dinâmico de denúncias
- ✅ Geolocalização em tempo real
- ✅ Marcadores customizados por tipo de ocorrência
- ✅ Dark Mode integrado
- ✅ Sem dependência de Google Maps API (cara)

## 🔑 Como Obter um Token Mapbox

### Opção 1: Token Gratuito (Recomendado para Desenvolvimento)

1. Acesse [mapbox.com](https://www.mapbox.com)
2. Clique em "Sign up"
3. Crie sua conta gratuitamente
4. Após login, vá para **Account → Tokens**
5. Copie o **Default token** (ou crie um novo)
6. O token começa com `pk.eyJ...`

### Opção 2: Token Público (Desenvolvimento Local)

Para testes locais SEM token válido, abra o navegador e verifique o console:

```javascript
// Adicione isto no arquivo js/app.js (linha ~285)
mapboxgl.accessToken = 'SEU_TOKEN_AQUI';
```

## 📝 Implementação Atual

No arquivo `js/app.js`, linha 284, está:

```javascript
mapboxgl.accessToken = 'pk.eyJ1IjoiZXhhbXBsZSIsImEiOiJjbGdwZGw0a2YwMDAwMG5xaHZrOHN2YW41In0.example';
```

**SUBSTITUA** pelo seu token real:

```javascript
mapboxgl.accessToken = 'pk.eyJ1IjoiSLGATUEU_TOKEN_VERDADEIROexemplo...';
```

## 🎨 Estilos Disponíveis

O app suporta alternância entre:
- `mapbox://styles/mapbox/streets-v12` (Padrão)
- `mapbox://styles/mapbox/light-v11` (Claro)
- `mapbox://styles/mapbox/dark-v11` (Escuro)
- `mapbox://styles/mapbox/satellite-v9` (Satélite)

## 🧪 Testando Localmente (SEM Token)

Para testar sem token, use o Mapbox em modo básico:

1. Remova ou deixe em branco o token
2. O mapa carregará com aviso (não funciona em produção)
3. Para desenvolvimento completo, **configure o token

## 📍 Funcionalidades Implementadas

- ✅ Geolocalização automática
- ✅ Zoom interativo (+ / -)
- ✅ Rotação & Tilt 3D
- ✅ Heatmap de denúncias (cores vermelho/laranja/amarelo/verde)
- ✅ Marcadores por tipo de ocorrência
- ✅ Zonas de risco destacadas
- ✅ Dark Mode com alternância automática
- ✅ Controles de navegação completos

## 🐛 Resolvução de Problemas

### "Erro: unauthorized"
**Solução:** Configure um token válido no arquivo `js/app.js`

### "Mapa não aparece"
**Verificar:**
1. Token está configurado?
2. Container `<div id="map">` existe no HTML?
3. CSS define altura 100% para o container?

### "Heatmap não funciona"
**Possíveis causas:**
- Dados dos reports faltam latitude/longitude
- Layer 'waterway-label' não existe no estilo

## 📚 Documentação

- [Mapbox GL JS Docs](https://docs.mapbox.com/mapbox-gl-js/)
- [Pricing (Free Tier Disponível)](https://www.mapbox.com/pricing)
- [API Reference](https://docs.mapbox.com/mapbox-gl-js/api/)

---

**Desenvolvido para APOLO - Segurança Comunitária**
