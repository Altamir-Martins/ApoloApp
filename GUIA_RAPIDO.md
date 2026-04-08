# 🚀 GUIA RÁPIDO - Como Usar o APOLO App

## ⚡ Início em 5 Minutos

### Passo 1: Obter um Token Mapbox (Gratuito)

1. Acesse: https://www.mapbox.com/signup
2. Crie conta (email + senha)
3. Confirme email
4. Acesse: Account → Tokens
5. Copie o token que começa com `pk.eyJ...`

### Passo 2: Configurar o Token

1. Abra o arquivo: `js/app.js`
2. Procure pela linha ~285:
   ```javascript
   mapboxgl.accessToken = 'pk.eyJ...SEU_TOKEN_AQUI...';
   ```
3. Substitua `SEU_TOKEN_AQUI` pelo seu token do Mapbox
4. Salve o arquivo (Ctrl+S)

### Passo 3: Rodar o App

#### Opção A: Python3
```bash
cd "c:\Users\Altamir Martins\Desktop\Dev\ApoloAPP"
python -m http.server 8000
# Abra: http://localhost:8000
```

#### Opção B: Node.js
```bash
cd "c:\Users\Altamir Martins\Desktop\Dev\ApoloAPP"
npx http-server
# Abra: http://127.0.0.1:8080
```

#### Opção C: Apenas abrir arquivo
- Duplo-clique no `index.html`
- ⚠️ Alguns recursos podem não funcionar (CORS)

---

## 🎮 Como Usar o App

### Tela de Splash (Início)
- Vê a animação do logo APOLO (4 segundos)
- Clique em qualquer lugar para pular

### Onboarding (Tutorial)
- **Slide 1:** Segurança Anônima
- **Slide 2:** Mapa em Tempo Real  
- **Slide 3:** Emergência Imediata
- Botão **"Pular"** ou **"Próximo"** para navegar

### Login
- **Opção 1:** Digite CPF/Email + Senha → Entrar
- **Opção 2:** Botão **"Entrar Anonimamente"** (recomendado para teste)

### Tela Principal (Mapa)
Você verá:
- 🗺️ **Mapa interativo** com sua localização (ponto azul)
- 🔴 **Heatmap de denúncias** (cores)
- 📍 **Marcadores** por tipo de ocorrência
- 🎨 **Chips de filtro** (Seguro, Atenção, Moderado, Alto Risco)

#### Controles do Mapa
- **Zoom:** Botões +/- no canto inferior direito
- **Rotação:** Segure botão direito + arraste
- **Modo 3D:** Digite no console: `enable3DMode()`
- **Dark Mode:** Menu → Configurações

#### Botões Flutuantes
- 📢 **Bullhorn (Amarelo):** Denunciar
- 🚨 **Siren (Vermelho):** Emergência (segure 3 segundos)

### Denúncia (Formulário)
**Passo 1 - Tipo:**
- Selecione o tipo de ocorrência (Roubo, Tráfico, Acidente, etc)

**Passo 2 - Detalhe:**
- Nível de risco (Baixo → Alto)
- Descrição do fato
- Localização (usar GPS automático)
- Upload de fotos/vídeos (opcional)

**Passo 3 - Revisar:**
- Confirme os dados
- Indique se foi testemunha
- Clique em "Enviar Denúncia"

✅ **Protocolo gerado:** `APL-XXXXXX`

### Emergência
- Toque e segure o botão vermelho por 3 segundos
- Você será levado para a tela de emergência
- Confirmação automática com protocolo
- Botão de cancelamento possível

### Menu Lateral (Hamburger ☰)
- **Notificações** 🔔
- **Minhas Ocorrências** 📋
- **Configurações** ⚙️
- **Ajuda** ❓
- **Logout** 🚪

### Configurações
- 🌙 **Dark Mode:** Toggle para tema escuro
- 📍 **Localização:** Ativar/desativar GPS
- 🔔 **Notificações:** Permitir alertas
- 📊 **Dados:** Ver uso

---

## 🗺️ Compreendendo o Mapa

### Cores da Heatmap
| Cor | Significado | Densidade |
|-----|-------------|----------|
| 🟢 Verde | Seguro | Baixa (1-2 denúncias) |
| 🟡 Amarelo | Atenção | Média (3-5 denúncias) |
| 🟠 Laranja | Moderado | Alta (6-8 denúncias) |
| 🔴 Vermelho | Alto Risco | Muito Alta (9+) |

### Tipos de Marcadores
- 🎭 **Roubo/Furto** = Máscara
- 💀 **Tráfico** = Crânio
- 🚗 **Acidente** = Carro quebrado
- 💡 **Iluminação** = Lâmpada apagada
- 🛣️ **Via Perigosa** = Barreira
- 👤 **Suspeito** = Pessoa mascarada
- ⏰ **Vencida** = Relógio

### Filtragem
Clique nos chips coloridos para filtrar apenas esse tipo de risco:
- "Seguro" → Mostra apenas verde
- "Atenção" → Mostra apenas amarelo
- ...e assim por diante

---

## 🐛 Resolvendo Problemas

### "Mapa não aparece / está branco"
**Solução:**
1. Verifique o console (F12 → Console)
2. Veja se há erro "unauthorized"
3. Confirme se o token foi configurado corretamente
4. Teste o token em: https://mapbox.com/account/tokens

### "Heatmap não está visível"
**Solução:**
1. Zoom out (use -) para ver melhor
2. Verifique se o mapa carregou completamente
3. Tente mudar de estilo (Menu → Configurações)

### "Localização não funciona"
**Solução:**
1. Permita acesso ao GPS no navegador
2. Recarregue a página (F5)
3. Tente novamente

### "Botão de emergência não dispara"
**Solução:**
1. Segure por **exatamente 3 segundos**
2. O anel ao redor deve preencher completamente
3. Depois navegará para tela de emergência

### "App muito lento"
**Solução:**
1. Desative modo 3D (se ligado)
2. Zoom para um nível mais alto
3. Limpe cache (Ctrl+Shift+Del)
4. Tente em outro navegador

---

## 🔧 Modo Desenvolvedor

### Comandos no Console (F12)

```javascript
// Ativar modo 3D
enable3DMode()

// Desativar modo 3D
disable3DMode()

// Mostrar notificação
showToast('Olá!', 'fa-smile', 3000)

// Criar modal
showConfirm({
  title: 'Teste',
  message: 'Funciona?',
  onConfirm: () => console.log('OK'),
  onCancel: () => console.log('Cancelado')
})

// Ver dados dos reports
console.log(mockReports)

// Navegar entre telas
navigate('home')
navigate('reports-list')
navigate('report')
```

### Telas Disponíveis
- `splash` - Logo APOLO
- `onboarding` - Tutorial
- `login` - Autenticação
- `register` - Cadastro
- `home` - Mapa principal
- `reports-list` - Lista de ocorrências
- `report-detail` - Detalhe de 1 ocorrência
- `report` - Formulário denúncia
- `emergency` - Tela emergência
- `notifications` - Notificações
- `profile` - Meu perfil
- `settings` - Configurações
- `help` - FAQ
- `success` - Sucesso

---

## 📱 Limites & Throttling

### Mapbox Free Tier
- **Visões:** 50.000/mês (aprox. 1.600 por dia)
- **API:** Até 600 requisições/minuto
- **Dados:** Até 1GB assets
- **Suporte:** Comunidade

### Performance
- **Carregamento mapa:** ~1-2 segundos
- **Zoom interaction:** Sub-100ms
- **Heatmap update:** ~200-500ms

---

## 📚 Referências

- 📖 Mapbox Docs: https://docs.mapbox.com/mapbox-gl-js/
- 🔗 Mapbox Playground: https://studio.mapbox.com/
- 💬 Issues: Abra no diretório `/issues`

---

## ✅ Checklist de Teste

- [ ] App carrega sem erros
- [ ] Mapa aparececom sua localização
- [ ] Zoom funciona (+/-)
- [ ] Heatmap visível
- [ ] Dark mode alterna estilos
- [ ] Botão denúncia abre formulário
- [ ] Formulário completa em 3 etapas
- [ ] Botão emergência dispara após 3s
- [ ] Menu lateral abre/fecha
- [ ] Notificações aparecem
- [ ] Dark mode persiste

---

## 💡 Dicas

1. **Use modo anônimo** para testes rápidos
2. **Ative modo 3D** no console para impressionar! 😎
3. **Teste no mobile** abrindo DevTools com F12 → Device Toggle
4. **GPS funciona melhor** se tiver local real ou Google Maps aberto
5. **Modo 3D é lindo** - Experimente! `enable3DMode()`

---

**Pronto? Vá em frente! 🚀**

Dúvidas? Veja `ANALISE_COMPLETA.md` ou `MAPBOX_SETUP.md`
