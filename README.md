# APOLO — Segurança Comunitária

Protótipo mobile (HTML/CSS/JS) do aplicativo APOLO, plataforma de segurança comunitária baseada em denúncias anônimas e navegação segura.

## ✨ Atualizações Recentes (v2.0)

### 🗺️ Migração para Mapbox GL JS
- ✅ **Substituído Leaflet por Mapbox GL JS** com suporte a modo 3D
- ✅ **Heatmap de denúncias** com gradiente de cores (azul → verde → amarelo → laranja → vermelho)
- ✅ **Modo 3D** com controle de pitch (inclinação) e bearing (rotação)
- ✅ **Dark Mode** integrado com alternância automática de estilos
- ✅ **Geolocalização** em tempo real com animação de voo
- ✅ **Marcadores customizados** por tipo de ocorrência

### 🐛 Correções
- ✅ Corrigido problema de **tela branca após login**
- ✅ CSS da tela de mapa agora utiliza `position: fixed` + `inset: 0`
- ✅ Removido Leaflet e suas dependências
- ✅ Implementadas funções auxiliares `showToast()` e `showConfirm()`

## ✅ Funcionalidades Implementadas

### Navegação
- Sistema de navegação entre telas totalmente funcional e sem travamentos
- Botão X do menu lateral **corrigido e funcional**
- Backdrop clicável fecha qualquer overlay aberto
- Tecla ESC fecha overlays (modo desktop)
- Swipe para a esquerda fecha o menu lateral (touch)
- Histórico de navegação (botões "voltar")
- Zero travamentos de tela

### Telas
| Tela | Status | Notas |
|------|--------|-------|
| Splash com animação do sol | ✅ | Animação de 4s |
| Onboarding (3 slides) | ✅ | Navegação fluida |
| Login | ✅ | Anônimo + Credenciais |
| Cadastro + força de senha | ✅ | Máscaras de CPF |
| **Home com mapa Mapbox + GPS** | ✅ | **NOVO: 3D, Heatmap** |
| Lista de ocorrências | ✅ | Filtros por risco |
| Detalhe da ocorrência | ✅ | Confirmação de testemunha |
| Formulário de denúncia (3 etapas) | ✅ | Upload de mídia |
| Emergência com hold 3s | ✅ | Protocolo automático |
| Notificações | ✅ | Marcação de lidas |
| Perfil | ✅ | Informações do usuário |
| Configurações + Dark Mode | ✅ | Theme switcher automático |
| Ajuda/FAQ | ✅ | Accordion expansível |
| Tela de sucesso | ✅ | Feedback visual |

### Overlays / Modais
| Componente | Fecha com X | Fecha com Backdrop | Status |
|------------|-------------|-------------------|--------|
| Menu lateral | ✅ | ✅ | Funcional |
| Busca (bottom sheet) | ✅ | ✅ | Funcional |
| Filtro (bottom sheet) | ✅ | ✅ | Funcional |
| Localização (bottom sheet) | ✅ | ✅ | Funcional |
| Suporte (bottom sheet) | ✅ | ✅ | Funcional |
| Zona de risco (bottom sheet) | ✅ | ✅ | Funcional |
| Modal de confirmação | ✅ | ✅ | Funcional |

### Mapa (NOVO - Mapbox GL JS)
- ✅ **Localização em tempo real** via GPS com animação de voo
- ✅ **Heatmap dinâmico** (densidade de denúncias)
  - Verde (seguro) → Amarelo (atenção) → Laranja (moderado) → Vermelho (alto risco)
- ✅ **Marcadores de ocorrências** com ícones por tipo
- ✅ **Zonas de risco colorizadas** por intensidade
- ✅ **Click em zona**: abre bottom sheet com ocorrências da área
- ✅ **Dark mode**: alterna estilos Mapbox automáticamente
- ✅ **Modo 3D**: pitch + bearing controlável
- ✅ **Controles**: Zoom, Fullscreen, Navigation
- ✅ **Geolocalização automática** com fallback
- ✅ **Popups customizados** ao passar hover nos marcadores

## ⚙️ Requisitos de Configuração

### Mapbox GL JS (OBRIGATÓRIO)

Para usar o mapa com todas as funcionalidades, você precisa de um **token Mapbox gratuito**:

1. Acesse [mapbox.com/signup](https://www.mapbox.com/signup)
2. Crie uma conta gratuita (até 50.000 visões/mês)
3. Copie seu **token de acesso público**
4. Edite `js/app.js` linha ~285:

```javascript
// ANTES (não funciona):
mapboxgl.accessToken = 'pk.eyJ1IjoiZXhhbXBsZSIsImEiOiJjbGdwZGw0a2YwMDAwMG5xaHZrOHN2YW41In0.example';

// DEPOIS (com seu token):
mapboxgl.accessToken = 'pk.eyJ1IjoiSEVVTOMSEUTOKENVERDADEIRO...';
```

Mais detalhes: Ver arquivo **MAPBOX_SETUP.md**

## 🚀 Como Usar

### Localmente
```bash
# Abrir em Python 3
python -m http.server 8000

# Ou com Node.js
npx http-server

# Visitar em qualquer navegador
http://localhost:8000
```

### Estrutura do Projeto
```
ApoloAPP/
├── index.html           # Shell principal da aplicação
├── css/
│   ├── design-system.css # Variáveis CSS & componentes base
│   └── app.css          # Estilos específicos do app
├── js/
│   ├── navigation.js    # Sistema de navegação & overlays
│   └── app.js           # Lógica da aplicação + Mapbox
├── MAPBOX_SETUP.md      # Documentação do Mapbox
└── README.md            # Este arquivo
```

## 🎨 Design System

### Cores
- **Primária**: `#1E88E5` (Azul)
- **Amarela**: `#FDD835` (Destaque)
- **Vermelho**: `#E53935` (Emergência/Alto Risco)
- **Laranja**: `#FB8C00` (Risco Moderado)
- **Verde**: `#43A047` (Seguro)

### Espaçamento
Escala: 4px, 8px, 12px, 16px, 20px, 24px...

### Tipografia
- **Font**: Inter
- **Weights**: 300, 400, 500, 600, 700, 800, 900

## 📱 Viewport & Responsividade

- **Mobile First**: Otimizado para 375px+
- **Aspect Ratio**: 9:16 (portrait)
- **Safe Areas**: Suporta notch/Dynamic Island
- **Touch Optimized**: Botões mínimo 44x44px

## 🔐 Segurança

- Todas as denúncias são **100% anônimas**
- Nenhum dado pessoal é armazenado localmente
- Comunicação será criptografada (implementação futura)

## � Otimizações Mobile/Android

### ✅ Completamente Otimizado para Mobile!

O app APOLO foi 100% otimizado para dispositivos mobile e Android:

- ✅ **Responsive Design** para telas 375px - 1200px+
- ✅ **Touch-Friendly** - Botões mínimo 44x44px
- ✅ **Safe Areas** - Suporta notches e Dynamic Island
- ✅ **Dark Mode** automático baseado no sistema
- ✅ **Teclado Suave** - Não quebra layout em Android
- ✅ **Orientação** - Suporte completo portrait/landscape
- ✅ **Mapa Responsivo** - Redimensiona automaticamente
- ✅ **Haptic Feedback** - Vibração no Android

### Media Queries Implementadas

- `< 375px` - Telas ultra-compactas (antigos)
- `375px - 600px` - **Telefones padrão PRINCIPAL**
- `600px - 768px` - Tablets pequenos
- `> 768px` - Tablets e desktops

### Como Testar em Mobile

**No Chrome/Firefox Desktop:**
1. Abrir DevTools (F12)
2. Device Mode (Ctrl+Shift+M)
3. Escolher: iPhone SE (375px), Pixel 5 (393px), ou iPad

**Em Dispositivo Real:**
```bash
# Encontre o IP do computador
# No Android: abra Chrome e vá para http://SEU_IP:8000
# No iPhone: abra Safari e vá para http://SEU_IP:8000
```

### Recursos Móveis
- Geolocalização GPS
- Vibração (haptic feedback)
- Detecção de orientação
- Status bar customizável (Android)
- Full-screen API

### Documentação Completa
- `MOBILE_OPTIMIZATION.md` - 350+ linhas de documentação
- `MOBILE_QUICKSTART.md` - Guia rápido de teste (30s)
- `MOBILE_SUMMARY.md` - Sumário de mudanças

---

## 🐛 Troubleshooting

### "Tela branca após login"
✅ **CORRIGIDO** - CSS da tela home agora utiliza `position: fixed; inset: 0;`

### "Mapa não aparece"
1. Verifique se o token Mapbox está configurado em `js/app.js`
2. Abra o console (F12) para ver erros
3. Se erro 401: token inválido ou expirado

### "Heatmap não funciona"
- Verifique se os dados dos reports têm `lat` e `lng`
- Layer 'waterway-label' é necessário no estilo

### "Dark mode é estranho"
- Limpe cache do navegador (Ctrl+Shift+Del)
- Verifique atributo `data-theme` no `<html>`

### "Layout não muda no mobile"
- Verificar Device Mode está ATIVO (ícone de celular clicado)
- Fazer hard refresh: Ctrl+Shift+R
- Tentar em nova aba

## 📈 Próximos Passos

- [ ] Integração com backend real
- [ ] Autenticação com Firebase/Auth0
- [ ] Upload de mídia (AWS S3/Cloudinary)
- [ ] Push Notifications
- [ ] Integração com APIs de emergência (190, 911, etc)
- [ ] Analytics e Dashboard de admin
- [ ] PWA (Service Workers)
- [ ] Offline Mode
- [ ] Share de denúncias por protocolo

## 📄 Licença

Projeto APOLO © 2026 - Todos os direitos reservados para fins de segurança comunitária.

---

**Desenvolvido com ❤️ para segurança urbana**

## 📱 Estrutura de Arquivos

```
index.html              — Estrutura completa do app (todas as telas e overlays)
css/
  design-system.css     — Tokens, variáveis CSS, reset, componentes base
  app.css               — Estilos de todas as telas e componentes
js/
  navigation.js         — Sistema de navegação (telas, overlays, modais, backdrop)
  app.js                — Lógica do app (mapa, formulários, dados mock, emergência)
```

## 🔗 URLs de Entrada

- `/` — index.html (splash → onboarding → login → home)

## 🗺️ Fluxo Principal

1. Splash (3s) → Onboarding (3 slides) → Login → Home/Mapa
2. Home: abrir menu, pesquisar, ver zonas de risco, denunciar, emergência
3. Botão Megafone → Formulário de Denúncia (3 etapas) → Sucesso
4. FAB Emergência (hold 3s) → Tela Emergência → Acionamento

## 🎨 Design System

- Fonte: Inter
- Grid: 8pt
- Cores: Preto/Branco + Amarelo (#FDD835) + Azul (#1E88E5)
- Estados de risco: Verde → Amarelo → Laranja → Vermelho
- Dark Mode suportado

## 🚀 Próximas Etapas (Para Kotlin/Android Studio)

- [ ] Implementar autenticação real (JWT/Firebase)
- [ ] API backend para denúncias
- [ ] Notificações push (FCM)
- [ ] Criptografia ponta-a-ponta
- [ ] Upload de mídia
- [ ] Anonimização de IP
- [ ] Dashboard para órgãos parceiros
