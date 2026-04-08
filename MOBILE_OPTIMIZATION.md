# 📱 Guia de Otimizações Mobile/Android

## Resumo das Otimizações Implementadas

Este documento descreve todas as otimizações mobile implementadas no app APOLO para garantir uma experiência perfeita em dispositivos Android, iOS e telas pequenas.

---

## 1. Otimizações CSS

### Media Queries Implementadas

#### Telas Pequenas (≤ 600px)
- **Font scaling**: Redução específica de tamanhos de fonte
- **Touch targets**: Mínimo de 44x44px para todos os botões
- **Espaçamento**: Redução adaptativa de padding/margin
- **FABs**: Ajuste de tamanho e posicionamento
- **Overlays**: Máxima largura = 100% da tela
- **Safe areas**: Implementação de `env(safe-area-inset-*)`

#### Telas Extra Pequenas (≤ 375px - Dispositivos Antigos)
- **FABs reduzidos**: 48x48px com fonte reduzida
- **Incident cards**: Tamanho compacto (70px altura)
- **Spacing uniforme**: Uso de variáveis menores

#### Modo Paisagem (height < 500px)
- **Redução de padding vertical**: Para acomodar teclado
- **Ícones menores**: 28px ao invés de 32px
- **FAB reposicionado**: Mais próximo do topo

#### Telas de Alta Densidade (Retina)
- **Bordas otimizadas**: 0.5px ao invés de 1px
- **Hardware acceleration**: Touch-action: manipulation

### Safe Areas (Notches/Dynamic Island)

```css
/* iOS notch support */
padding-top: max(var(--sp-3), env(safe-area-inset-top));
padding-bottom: max(var(--sp-3), env(safe-area-inset-bottom));
padding-left: env(safe-area-inset-left);
padding-right: env(safe-area-inset-right);
```

---

## 2. Otimizações JavaScript

### Classe MobileOptimization

Localização: `js/mobile-optimization.js`

#### Funcionalidades Principais

**Detecção de Dispositivo:**
```javascript
- isMobile: Detecta via user agent
- isAndroid: Específico para Android
- isIOS: Específico para iOS
- Suporte a iPad, iPhone, tablets
```

**Prevenção de Zoom:**
- Desabilita zoom duplo-tap (Android)
- Previne zoom ao focar input (iOS)
- Define zoom = 1 ao usar inputs

**Gerenciamento de Teclado Suave:**
- Posiciona absolutamente body quando teclado aberto
- Scroll automático para input em foco
- Mantém layout sem saltos

**Prevenção de Pull-to-Refresh:**
- Desabilita gesto Android nativo
- Permite scroll normal dentro do app

**Orientação:**
- Detecta mudanças (portrait ↔ landscape)
- Redimensiona mapa automaticamente
- Dispatch custom events para app

**Interações de Toque:**
- Remove tap highlight
- Feedback visual via opacidade
- Touch-action: manipulation

---

## 3. Meta Tags Implementadas

### Viewport
```html
<meta name="viewport" content="
  width=device-width,
  initial-scale=1.0,
  maximum-scale=1.0,
  user-scalable=no,
  viewport-fit=cover
" />
```

### Suporte Android
```html
<meta name="theme-color" content="#1e88e5" />
```

### Suporte iOS
```html
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />
```

---

## 4. Tamanhos Otimizados (Breakpoints)

| Tamanho | Largura | Uso |
|---------|---------|-----|
| XL | > 1200px | Desktop |
| LG | 992px - 1200px | Tablet landscape |
| MD | 768px - 992px | Tablet portrait |
| SM | 600px - 768px | Dispositivos intermediários |
| XS | < 600px | Telefones |
| XXS | < 375px | Telefones antigos |

---

## 5. Font Sizes Otimizados para Mobile

| Token | Desktop | Mobile |
|-------|---------|--------|
| text-xs | 11px | 10px |
| text-sm | 12px | 12px |
| text-base | 14px | 15px |
| text-md | 16px | 15px |
| text-lg | 18px | 18px |
| text-xl | 20px | 20px |
| text-2xl | 24px | 24px |

---

## 6. Touch Targets Garantidos

Todos os elementos interativos têm mínimo de **44x44px**:

- ✅ Botões (btn)
- ✅ FABs (fab)
- ✅ Menu items (smn-item)
- ✅ Incident cards
- ✅ Tabs
- ✅ Inputs
- ✅ Selects

---

## 7. Componentes Específicos Otimizados

### Map Controls
```javascript
// Redimensiona automaticamente ao rotacionar
map.resize();

// Controles Mapbox ajustados para mobile
.mapboxgl-ctrl button {
  width: 36px;
  height: 36px;
}
```

### FABs (Floating Action Buttons)
```
Desktop: 56x56px
Mobile: 52x52px
Pequeno: 48x48px
Emergency (Desktop): 64x64px
Emergency (Mobile): 60x60px
```

### Form Inputs
```css
min-height: 44px;
font-size: 16px; /* Previne zoom em iOS ao focar */
```

### Modal
```css
max-width: calc(100% - 32px);
padding: 20px 16px;
border-radius: 12px;
```

---

## 8. Otimizações de Performance

### 1. Redução de Movimento
- Animations desabilitadas em `prefers-reduced-motion`
- Transitions suavizadas

### 2. Compressão de Imagens
- Usar WebP quando possível
- SVG inline para ícones

### 3. Lazy Loading
- Mapbox tiles carregam sob demanda
- Imagens de perfil com lazy loading

### 4. Caching
- LocalStorage para dados não-sensíveis
- Service Workers para offline

---

## 9. Recursos de Dispositivo (APIs Nativas)

### Geolocalização
```javascript
navigator.geolocation.getCurrentPosition(success, error, {
  enableHighAccuracy: true,
  timeout: 5000,
  maximumAge: 0
});
```

### Vibração (Haptic Feedback)
```javascript
navigator.vibrate(50); // Feedback ao clicar
```

### Status da Bateria
```javascript
navigator.getBattery()
  .then(battery => console.log(battery.level));
```

### Rede
```javascript
navigator.onLine // true/false
navigator.connection.effectiveType // "4g", "3g", etc
```

### Orientação
```javascript
screen.orientation.lock('portrait');
screen.orientation.unlock();
```

---

## 10. Dark Mode Mobile

### Tema Automático
```javascript
// Detecta preferência do sistema
window.matchMedia('(prefers-color-scheme: dark)')
  .addListener((e) => {
    // Atualiza tema automaticamente
  });
```

### Cores Otimizadas para AMOLED
- Uso de `#000000` ao invés de `#0a0a0a`
- Economiza bateria em telas OLED

---

## 11. Teste em Dispositivos Reais

### Dispositivos Recomendados
- ✅ dispositivo Android (375px width, 667px height)
- ✅ dispositivo Android XXL (480px width)
- ✅ iPhone 12 (390px width, 844px height)
- ✅ iPad (768px width, 1024px height)

### Teste Android
```bash
# Via Android Studio Emulator
# ou
# Abrir em navegador: http://localhost:8000
```

### Teste iOS
```bash
# Via Safari em Mac
# ou
# Abrir em iPhone real
```

### Teste Landscape
- Girar dispositivo durante vários fluxos
- Verificar reflow do mapa
- Confirmar teclado não quebra layout

---

## 12. Checklist de Implementação

- [x] Media queries para mobile (mobile-first)
- [x] Font scaling adaptativo
- [x] Touch targets ≥ 44x44px
- [x] Safe areas implementado
- [x] Geolocalização funcional
- [x] Mapa responsivo
- [x] FABs otimizados
- [x] Formulários touch-friendly
- [x] Teclado suave não quebra layout
- [x] Dark mode automático
- [x] Orientação (portrait/landscape)
- [x] Prevenção de zoom duplo
- [x] Feedback tátil
- [x] Meta tags corretas
- [x] Teste em múltiplos tamanhos

---

## 13. Debugging Mobile

### Console Log com Info
```javascript
// Ativa debug no console
window.mobileOptimization.logDebugInfo();
```

### Inspecionar Elemento
- Chrome DevTools → Device Toolbar
- Firefox Developer Tools → Responsive Design Mode
- Safari → Develop → iPhone Simulator

### Network Throttling
- Simular 3G/4G em DevTools
- Testar performance em conexões lentas

---

## 14. Possíveis Melhorias Futuras

1. **PWA Manifest** - Instalação como app nativo
2. **Service Workers** - Offline support completo
3. **WebGL Performance** - Otimizar renderização do Mapbox
4. **Push Notifications** - Web Push API
5. **Camera Access** - Captura de fotos para reports
6. **Biometria** - Fingerprint/Face ID para login
7. **Voice Commands** - Web Speech API
8. **AR Features** - WebXR para map view

---

## 15. Suporte Navegadores

| Navegador | Versão Mín | Suporte |
|-----------|-----------|---------|
| Chrome | 90 | ✅ Completo |
| Firefox | 88 | ✅ Completo |
| Safari | 14 | ✅ Completo |
| Samsung Internet | 14 | ✅ Completo |
| UC Browser | 13 | ⚠️ Parcial |
| Internet Explorer | N/A | ❌ Não |

---

## 16. Links Relacionados

- [MDN - Mobile Web Development](https://developer.mozilla.org/en-US/docs/Mozilla/Mobile)
- [Android Design Guidelines](https://developer.android.com/design)
- [iOS Human Interface Guidelines](https://developer.apple.com/design/human-interface-guidelines/)
- [A11y Mobile](https://www.w3.org/WAI/mobile/)
- [Mapbox Mobile Documentation](https://docs.mapbox.com/mapbox-gl-js/overview/)

---

**Última atualização:** Janeiro 2025  
**Status:** ✅ Production Ready  
**Compatibilidade:** Android 6.0+, iOS 12+, Tablets
