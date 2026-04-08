# Configuração Mapbox para Produção

## Local Development

1. Copiar `config.example.js` → `config.js`
2. Adicionar seu token Mapbox:

```javascript
// config.js
window.MAPBOX_TOKEN = 'seu_token_mapbox_aqui';
```

## Vercel Deployment

1. Ir a: https://vercel.com/dashboard
2. Selecionar o projeto ApoloApp
3. Settings → Environment Variables
4. Adicionar variável: `MAPBOX_TOKEN` = seu_token_aqui
5. Redeploy

## GitHub Pages

Para usar com GitHub Pages:

1. Gerar um novo token Mapbox público em https://account.mapbox.com/tokens/
2. Seguir o passo "Vercel Deployment" acima
3. Ou adicionar manualmente em `index.html`:

```html
<script>
  window.MAPBOX_TOKEN = 'seu_token_aqui';
</script>
```

## Ordem de Carregamento do Token

O app tenta carregar o token nesta ordem:

1. `window.MAPBOX_TOKEN` (já définido em script anterior)
2. `config.js` (desenvolvimento local, não versionado)
3. `localStorage.getItem('mapbox_token')` (testes em produção)
4. Variável de ambiente do Vercel (não implementada ainda)

Se nenhum token for encontrado, o mapa mostra um placeholder instructivo.
