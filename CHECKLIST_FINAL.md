# ✅ CHECKLIST FINAL - Verificação do APOLO v2.0

## 🔧 Antes de Começar

- [ ] Arquivo `js/app.js` foi editado com token Mapbox válido?
- [ ] Token começa com `pk.eyJ`?
- [ ] Servidor HTTP está rodando na porta 8000?
- [ ] Navegador suportado? (Chrome, Firefox, Safari, Edge)
- [ ] Internet conectada (Mapbox requer conexão)

---

## 🎮 Teste de Fluxo Completo

### Splash Screen
- [ ] Logo APOLO aparece
- [ ] Animação do sol gira
- [ ] Barra de progresso preenche (4 segundos)
- [ ] Transição automática para onboarding

### Onboarding
- [ ] Slide 1: "Segurança Anônima" aparece
- [ ] Dots navegação funcionam
- [ ] Slide 2: "Mapa em Tempo Real" visível
- [ ] Slide 3: "Emergência Imediata" visível
- [ ] Botão "Começar" no último slide
- [ ] Botão "Pular" sempre disponível

### Login
- [ ] Tela com logo APOLO menor
- [ ] Campo "CPF ou E-mail"
- [ ] Campo "Senha"
- [ ] Botão "Entrar" (azul)
- [ ] Botão "Entrar Anonimamente" (outline)
- [ ] Link "Cadastre-se" funciona (vai para registro)

### HOME - MAPA (CRÍTICO)
- [ ] **SEM tela branca** ✅
- [ ] Mapa carrega em ~2 segundos ✅
- [ ] Sua localização (ponto azul) aparece
- [ ] Topbar com:
  - [ ] Botão Menu (☰) esquerda
  - [ ] Barra de busca centro
  - [ ] Botão Notificações + badge (3)
- [ ] Risk chips na horizontal (Seguro, Atenção, Moderado, Alto Risco)
- [ ] Bottom strip: "12 ocorrências próximas"
- [ ] FABs flutuantes:
  - [ ] Botão Denúncia (amarelo, bullhorn) centro-direita
  - [ ] Botão Emergência (vermelho, siren) maior, direita

### Mapa (Verificação Detalhada)
- [ ] Zonas colorizadas visíveis (3 zonas)
- [ ] Marcadores de ocorrências visíveis (6 marcadores)
- [ ] Heatmap aplicado (gradiente de cores)
- [ ] Controle zoom (+-) canto inferior direito
- [ ] Fullscreen button próximo
- [ ] Pode arrastar/pan no mapa
- [ ] Scroll zoom funciona
- [ ] Double-click zoom funciona

### Botão de Denúncia
- [ ] Clique abre nova tela
- [ ] Step indicator (1 de 3)
- [ ] Lista de tipos de ocorrência (8 tipos)
- [ ] Seleção funciona (se destaca)
- [ ] Botão "Próximo" habilitado após seleção
- [ ] Voltar botão volta para mapa

### Botão de Emergência
- [ ] Clique comum = sem ação
- [ ] **Segure por 3 segundos**:
  - [ ] Anel ao redor preenche (progresso)
  - [ ] Som/vibração (se disponível)
  - [ ] Leva para tela emergência
- [ ] Tela emergência:
  - [ ] Grande botão siren vermelho
  - [ ] Protocolo gerado (APL-XXXXXX)
  - [ ] Botão "Cancelar" disponível

### Menu Lateral (☰)
- [ ] Clique abre Menu
- [ ] Backdrop escuro aparece
- [ ] Menu desliza da esquerda
- [ ] Botão X fecha menu
- [ ] Click backdrop fecha menu
- [ ] Swipe esquerda fecha menu (mobile)
- [ ] Items visíveis:
  - [ ] Notificações
  - [ ] Lista de Ocorrências
  - [ ] Configurações
  - [ ] Ajuda
  - [ ] Logout

### Notificações
- [ ] Clique no sino (🔔) abre tela
- [ ] 4 notificações visíveis
- [ ] Ícones aparece (warning, check, bell, shield)
- [ ] Tempo relativo ("Agora", "30 min atrás", etc)
- [ ] Click marca como lida (remove "unread")
- [ ] Badge (3) some após marcar tudo

### Configurações
- [ ] Abre tela de settings
- [ ] Toggle Dark Mode:
  - [ ] Liga tema escuro
  - [ ] Mapa alterna para dark-v11
  - [ ] UI fica com fundo escuro
  - [ ] Desliga volta para claro

### Dark Mode
- [ ] Toggle em Configurações
- [ ] Aplicado em todos elementos
- [ ] Mapa muda estilo
- [ ] Persiste ao recarregar página
- [ ] Toast notifica "Modo escuro ativado"

### Ajuda (FAQ)
- [ ] 6 perguntas visíveis
- [ ] Acordeão expansível
- [ ] Click expande resposta
- [ ] Ícone seta rotaciona

### Logout
- [ ] Click em logout
- [ ] Modal confirma "Encerrar Sessão"
- [ ] Click "Confirmar" → volta para login
- [ ] Cancel → permanece na tela

---

## 🆘 Modo Console (F12)

```javascript
// Limpe cache e reload
localStorage.clear()
location.reload()

// Ativar modo 3D
enable3DMode()
// Deve inclinar mapa 45°

// Desativar modo 3D
disable3DMode()
// Deve voltar ao normal

// Toast
showToast('Teste!', 'fa-smile', 3000)
// Deve aparecer notificação

// Confirmar
showConfirm({
  title: 'Teste?',
  message: 'Confirma?',
  onConfirm: () => console.log('Confirmado'),
  onCancel: () => console.log('Cancelado')
})
// Modal deve aparecer

// Ver dados
console.log(mockReports)         // Array com 6 denúncias
console.log(mockNotifications)   // Array com 4 notificações
console.log(mapInstance)         // Objeto do mapa

// Check token
console.log(mapboxgl.accessToken)
// Deve começar com pk.eyJ (não .example)
```

---

## 🎨 Verificação Visual

### Cores
- [ ] Azul primária: `#1E88E5` (botões, texto importante)
- [ ] Amarela: `#FDD835` (destaque, botão denúncia)
- [ ] Vermelha: `#E53935` (emergência, alto risco)
- [ ] Laranja: `#FB8C00` (risco moderado)
- [ ] Verde: `#43A047` (seguro, sucesso)
- [ ] Cinza: `#9E9E9E` (desabilitado, hint)

### Typography
- [ ] Font Inter carregada
- [ ] Weights: 300, 400, 500, 600, 700
- [ ] Tamanho base: 14px
- [ ] Títulos: 18-24px

### Espaçamento
- [ ] Paddings consistentes (8px, 16px, 24px)
- [ ] Gaps entre elementos (8-12px)
- [ ] Botões: altura mínima 44px

### Responsividade
- [ ] Desktop (1920px): tudo visível
- [ ] Tablet (768px): layout adapta
- [ ] Mobile (375px): horizontal viável
- [ ] Notch/Safe Area: respeita insets

---

## 📊 Performance

- [ ] Carregamento < 3 segundos
- [ ] Interações < 100ms
- [ ] Sem lag ao arrastar mapa
- [ ] Zoom smooth
- [ ] 3D mode: 60fps

---

## 🚨 Erros Esperados (E Como Fixar)

### "Unauthorized 401"
```
❌ Token inválido ou expirado
✅ Solução: 
   1. Verifique token em https://mapbox.com/account/tokens
   2. Copie token novo
   3. Cole em js/app.js linha 285
   4. Recarregue página (Ctrl+R)
```

### "CORS Error"
```
❌ Navegador bloqueou requisição
✅ Solução:
   1. Use servidor HTTP (não file://)
   2. Mapbox suporta CORS
   3. Token configurado corretamente
```

### "Mapa em branco"
```
❌ Não aparece nada
✅ Verificar:
   1. Container <div id="map"> existe?
   2. CSS: #map { inset: 0; position: absolute; }
   3. Token configurado?
   4. Internet conectada?
```

### "Heatmap não aparece"
```
❌ Só vê marcadores
✅ Verificar:
   1. Zoom para nível 10-15
   2. Dados dos reports têm lat/lng?
   3. Layer 'waterway-label' no estilo?
```

### "GPS não funciona"
```
❌ Localização não atualiza
✅ Verificar:
   1. Permissão do navegador (allow)
   2. HTTPS em produção (HTTPS obrigatório para GPS)
   3. Recarregue página
```

### "Botão emergência não dispara"
```
❌ Nada acontece ao segurar
✅ Verificar:
   1. Segure por EXATAMENTE 3 segundos
   2. Anel ao redor deve preencher completamente
   3. Teste no console: navigate('emergency')
```

---

## 🎯 Pontos Críticos NÃO Esquecer

1. **Token Mapbox** - SEM ISSO, mapa não funciona
2. **Servidor HTTP** - `file://` não funciona
3. **Internet** - Mapbox requer conexão
4. **CSS do Mapa** - `position: fixed; inset: 0;` é essencial
5. **Permissão GPS** - Permita no banner do navegador

---

## 📱 Teste em Dispositivos Reais

### Desktop
- [ ] Chrome/Firefox/Safari/Edge
- [ ] 1920x1080, 1366x768
- [ ] Zoom 100%, 125%, 150%

### Mobile
- [ ] iOS Safari 11+
- [ ] Android Chrome 51+
- [ ] Orientação portrait
- [ ] Tamanho 375x667px

### Tablet  
- [ ] iPad Pro 12.9"
- [ ] Tab S21 10.5"
- [ ] Tanto portrait quanto landscape

---

## 🐛 Se Encontrar Erro

1. **Abra Console (F12)**
   ```
   Clique: F12 → Console
   ```

2. **Copie erro exato**
   ```
   Erro: ... in line XXX
   ```

3. **Verifique:**
   - [ ] Token configurado?
   - [ ] Internet conectada?
   - [ ] Servidor HTTP rodando?
   - [ ] Arquivos salvos?

4. **Tente:**
   - Limpar cache: Ctrl+Shift+Del
   - Hard reload: Ctrl+Shift+R
   - Mudar navegador
   - Testar em outro dispositivo

---

## ✨ Próximas Features (Bonus)

- [ ] Câmera - `navigate('camera')`
- [ ] Upload de fotos - `triggerMediaUpload()`
- [ ] Modo satélite - `mapbox://styles/mapbox/satellite-v9`
- [ ] Share denúncia - `shareReport()`
- [ ] Busca - `openOverlay('search-overlay')`

---

## 📋 Resumo Final

**Se todos itens acima estão ✅, APOLO está 100% funcional!**

### Antes vs Depois
| Antes | Depois |
|-------|--------|
| ❌ Tela branca | ✅ Mapa aparece |
| ❌ Sem 3D | ✅ Modo 3D funciona |
| ❌ Sem heatmap | ✅ Heatmap dinâmico |
| ❌ Leaflet lento | ✅ Mapbox rápido |
| ❌ Design system incompleto | ✅ Design system completo |

---

**🎉 Parabéns! APOLO está pronto para uso!**

Dúvidas? Veja os arquivos:
- `GUIA_RAPIDO.md` - Como começar
- `MAPBOX_SETUP.md` - Configurar token
- `ANALISE_COMPLETA.md` - Detalhes técnicos
- `SUMARIO_EXECUTIVO.md` - Visão geral

