/* ============================================================
   APOLO — APP LOGIC
   ============================================================ */

'use strict';

// ============================================================
// DADOS MOCK
// ============================================================
const mockReports = [
  {
    id: 'APL-001',
    type: 'Roubo/Furto',
    icon: 'fa-masks-theater',
    risk: 'high',
    riskLabel: 'Alto Risco',
    riskColor: '#E53935',
    riskBg: 'rgba(229,57,53,0.1)',
    desc: 'Assalto a mão armada registrado próximo ao ponto de ônibus. Dois suspeitos em moto, capacete preto.',
    location: 'Av. Paulista, próx. metrô Consolação',
    time: 'Há 15 min',
    witnesses: 3,
    lat: -23.5630,
    lng: -46.6543,
  },
  {
    id: 'APL-002',
    type: 'Iluminação',
    icon: 'fa-lightbulb-slash',
    risk: 'moderate',
    riskLabel: 'Risco Moderado',
    riskColor: '#FB8C00',
    riskBg: 'rgba(251,140,0,0.1)',
    desc: 'Rua completamente sem iluminação desde segunda-feira. Trecho de 3 quarteirões no escuro.',
    location: 'R. Augusta, entre Bela Cintra e Consolação',
    time: 'Há 2 horas',
    witnesses: 7,
    lat: -23.5558,
    lng: -46.6567,
  },
  {
    id: 'APL-003',
    type: 'Via Perigosa',
    icon: 'fa-road-barrier',
    risk: 'attention',
    riskLabel: 'Atenção',
    riskColor: '#F9A825',
    riskBg: 'rgba(253,216,53,0.12)',
    desc: 'Buraco grande no meio da via, sem sinalização. Já causou acidente com motociclista.',
    location: 'Rua Haddock Lobo, 480',
    time: 'Há 1 dia',
    witnesses: 12,
    lat: -23.5570,
    lng: -46.6620,
  },
  {
    id: 'APL-004',
    type: 'Suspeito',
    icon: 'fa-user-secret',
    risk: 'attention',
    riskLabel: 'Atenção',
    riskColor: '#F9A825',
    riskBg: 'rgba(253,216,53,0.12)',
    desc: 'Pessoa com comportamento suspeito rondando o estacionamento há mais de 1 hora.',
    location: 'Shopping Paulista, Estacionamento B3',
    time: 'Há 30 min',
    witnesses: 2,
    lat: -23.5618,
    lng: -46.6552,
  },
  {
    id: 'APL-005',
    type: 'Acidente',
    icon: 'fa-car-burst',
    risk: 'high',
    riskLabel: 'Alto Risco',
    riskColor: '#E53935',
    riskBg: 'rgba(229,57,53,0.1)',
    desc: 'Colisão entre carro e moto. SAMU já foi acionado. Via parcialmente bloqueada.',
    location: 'Av. Rebouças x R. Groenlândia',
    time: 'Há 5 min',
    witnesses: 8,
    lat: -23.5652,
    lng: -46.6698,
  },
  {
    id: 'APL-006',
    type: 'Tráfico',
    icon: 'fa-skull-crossbones',
    risk: 'high',
    riskLabel: 'Alto Risco',
    riskColor: '#E53935',
    riskBg: 'rgba(229,57,53,0.1)',
    desc: 'Movimentação suspeita de tráfico no beco lateral. Pessoas entrando e saindo com frequência.',
    location: 'R. Peixoto Gomide, beco lateral',
    time: 'Há 45 min',
    witnesses: 1,
    lat: -23.5643,
    lng: -46.6512,
  },
];

const mockNotifications = [
  {
    id: 1,
    icon: 'fa-triangle-exclamation',
    iconClass: 'notif-icon--yellow',
    title: 'Novo alerta na sua área',
    desc: 'Roubo reportado a 300m da sua localização atual.',
    time: 'Agora',
    unread: true,
  },
  {
    id: 2,
    icon: 'fa-circle-check',
    iconClass: 'notif-icon--blue',
    title: 'Sua denúncia foi verificada',
    desc: 'A denúncia #APL-001 foi confirmada por 3 testemunhas.',
    time: '30 min atrás',
    unread: true,
  },
  {
    id: 3,
    icon: 'fa-bell',
    iconClass: 'notif-icon--blue',
    title: 'Atualização de status',
    desc: 'A ocorrência #APL-003 foi encaminhada à Prefeitura.',
    time: '2 horas atrás',
    unread: true,
  },
  {
    id: 4,
    icon: 'fa-shield',
    iconClass: 'notif-icon--blue',
    title: 'Área liberada',
    desc: 'A rua bloqueada na Av. Rebouças foi normalizada.',
    time: '1 dia atrás',
    unread: false,
  },
];

const faqData = [
  {
    q: 'Como faço uma denúncia anônima?',
    a: 'Toque no ícone de megafone na tela principal ou acesse "Nova Denúncia" pelo menu. Sua identidade nunca é revelada — usamos criptografia ponta a ponta.',
  },
  {
    q: 'Como fico sabendo que minha denúncia foi recebida?',
    a: 'Após o envio, você recebe um número de protocolo. Com ele, acompanhe o status em "Minhas Denúncias" no menu de perfil.',
  },
  {
    q: 'O que acontece ao acionar o modo emergência?',
    a: 'Os serviços de emergência da sua cidade são notificados com sua localização em tempo real. Mantenha o app aberto até o socorro chegar.',
  },
  {
    q: 'Minhas fotos ficam salvas no servidor?',
    a: 'Imagens são criptografadas antes do envio e armazenadas temporariamente. Após análise, são excluídas dos nossos servidores.',
  },
  {
    q: 'Posso usar sem criar conta?',
    a: 'Sim! O modo anônimo permite usar o mapa e ver ocorrências sem cadastro. Para denunciar, recomendamos ao menos um e-mail anônimo.',
  },
  {
    q: 'Quem tem acesso às denúncias?',
    a: 'Apenas órgãos de segurança pública parceiros. Nunca compartilhamos com terceiros ou empresas privadas.',
  },
];

const zoneData = {
  zone1: {
    name: 'Zona de Alta Atenção — Centro',
    risk: 'Alto Risco',
    riskClass: 'chip--red',
    incidents: [mockReports[0], mockReports[5]],
  },
  zone2: {
    name: 'Área de Atenção — Consolação',
    risk: 'Atenção',
    riskClass: 'chip--yellow',
    incidents: [mockReports[1], mockReports[2]],
  },
  zone3: {
    name: 'Área Segura — Jardins',
    risk: 'Seguro',
    riskClass: 'chip--green',
    incidents: [],
  },
};

// ============================================================
// ESTADO DO FORMULÁRIO
// ============================================================
const reportState = {
  incidentType: null,
  risk: 'Baixo',
  desc: '',
  location: 'Localização atual',
  witness: null,
  currentStep: 1,
};

// ============================================================
// SPLASH → ONBOARDING
// ============================================================
window.addEventListener('DOMContentLoaded', () => {
  // Mostra splash
  const splashScreen = document.getElementById('screen-splash');
  splashScreen.style.display = 'flex';
  splashScreen.classList.add('active');
  NAV.currentScreen = 'splash';

  // Inicializa onboarding (primeira slide ativa)
  const slides = document.querySelectorAll('.onboarding-slide');
  slides.forEach((s, i) => {
    s.classList.toggle('active', i === 0);
  });

  // Barra de progresso do splash
  const progressFill = document.getElementById('splashProgress');
  let progress = 0;
  const interval = setInterval(() => {
    progress += 2;
    if (progressFill) progressFill.style.width = progress + '%';
    if (progress >= 100) {
      clearInterval(interval);
      setTimeout(() => navigate('onboarding'), 300);
    }
  }, 40);
});

// ============================================================
// ONBOARDING
// ============================================================
let obSlide = 0;

document.addEventListener('DOMContentLoaded', () => {
  const btnNext = document.getElementById('btnObNext');
  const btnSkip = document.getElementById('btnObSkip');

  if (btnNext) btnNext.addEventListener('click', onboardingNext);
  if (btnSkip) btnSkip.addEventListener('click', () => navigate('login'));
});

function onboardingNext() {
  const slides = document.querySelectorAll('.onboarding-slide');
  const dots   = document.querySelectorAll('.ob-dot');
  const btn    = document.getElementById('btnObNext');

  obSlide++;
  if (obSlide >= slides.length) {
    navigate('login');
    return;
  }

  slides.forEach((s, i) => s.classList.toggle('active', i === obSlide));
  dots.forEach((d, i)   => d.classList.toggle('active', i === obSlide));

  if (btn) btn.textContent = obSlide === slides.length - 1 ? 'Começar' : 'Próximo';
}

// ============================================================
// MAPA COM MAPBOX GL
// ============================================================
let mapInstance = null;
let mapInitialized = false;
let userMarker = null;
let reportMarkers = [];
let heatmapLayer = null;

// ⚠️ CONFIGURAÇÃO IMPORTANTE: Configure seu token do Mapbox
// Obtenha gratuitamente em: https://www.mapbox.com
// Token é carregado daqui ou de config.js (não versionado)
// Aguarda que window.MAPBOX_TOKEN seja definido
if (!window.MAPBOX_TOKEN) {
  window.MAPBOX_TOKEN = 'pk.eyJ1IjoiYWx0YW1pcm1hcnRpbnMiLCJhIjoiY21ucWlyZmJjMDdxcDJvcHB2ZnBmamthNCJ9.81XYl4HuboCcBEefv1MIsQ';
}
const token = window.MAPBOX_TOKEN;
if (token && typeof mapboxgl !== 'undefined') {
  mapboxgl.accessToken = token;
}
if (!token || token === '') {
  console.error('Erro: Token Mapbox não configurado!');
}

function initMap() {
  if (mapInitialized) return;
  mapInitialized = true;

  const defaultCenter = [-46.6560, -23.5608]; // [lng, lat] para Mapbox
  const mapContainer = document.getElementById('map');

  // Se não tem token válido, mostra placeholder com instruções de desenvolvimento
  if (!mapboxgl.accessToken || mapboxgl.accessToken.includes('.example')) {
    mapContainer.innerHTML = `
      <div style="
        width: 100%;
        height: 100%;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        color: white;
        text-align: center;
        padding: 20px;
        box-sizing: border-box;
        font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
      ">
        <i class="fa-solid fa-map" style="font-size: 64px; margin-bottom: 20px; opacity: 0.8;"></i>
        <h2 style="margin: 0 0 12px; font-size: 24px;">Mapbox Não Configurado</h2>
        <p style="margin: 0 0 20px; font-size: 14px; opacity: 0.9; max-width: 300px;">
          Para usar o mapa em 3D com heatmap, configure seu token Mapbox gratuitamente.
        </p>
        <div style="
          background: rgba(255,255,255,0.1);
          padding: 16px;
          border-radius: 8px;
          max-width: 90%;
          margin-bottom: 20px;
          font-size: 12px;
          font-family: 'Courier New', monospace;
          border: 1px solid rgba(255,255,255,0.2);
        ">
          <strong>Arquivo:</strong> js/app.js<br>
          <strong>Linha:</strong> 285<br>
          <strong>Variável:</strong> mapboxgl.accessToken
        </div>
        <a href="https://www.mapbox.com" target="_blank" style="
          background: white;
          color: #667eea;
          padding: 10px 20px;
          border-radius: 4px;
          text-decoration: none;
          font-weight: 600;
          font-size: 14px;
          display: inline-block;
          margin-top: 10px;
        ">Obter Token Gratuito</a>
      </div>
    `;
    showToast('ℹ️ Mapa precisa de Token Mapbox. Veja MAPBOX_SETUP.md', 'fa-info-circle', 5000);
    return;
  }

  try {
    mapInstance = new mapboxgl.Map({
      container: 'map',
      style: 'mapbox://styles/mapbox/streets-v12',
      center: defaultCenter,
      zoom: 15,
      pitch: 0,
      bearing: 0,
    });

    // Remove controle de atribuição padrão
    mapInstance.setMaxPitch(60);

    // Aguarda mapa carregar
    mapInstance.on('load', function() {
      // Geolocalização
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          (pos) => {
            const lat = pos.coords.latitude;
            const lng = pos.coords.longitude;
            mapInstance.flyTo({
              center: [lng, lat],
              zoom: 15,
              duration: 1500,
            });
            addUserMarker(lat, lng);
          },
          () => {
            addUserMarker(defaultCenter[1], defaultCenter[0]);
          }
        );
      } else {
        addUserMarker(defaultCenter[1], defaultCenter[0]);
      }

      // Adiciona zonas de risco
      addRiskZones();

      // Adiciona marcadores de ocorrências
      addReportMarkers();

      // Adiciona heatmap
      addHeatmapLayer();

      // Controles de navegação
      mapInstance.addControl(new mapboxgl.NavigationControl(), 'bottom-right');
      mapInstance.addControl(new mapboxgl.FullscreenControl(), 'bottom-right');

      // ✨ NOVO: Iniciar rastreamento de localização em tempo real
      updateRealTimeLocation();
    });

    mapInstance.on('error', (error) => {
      console.error('Erro ao carregar mapa:', error);
      if (error.error && error.error.includes('401')) {
        showToast('⚠️ Token inválido. Configure em js/app.js', 'fa-circle-exclamation', 5000);
      }
    });
  } catch (e) {
    console.error('Erro ao inicializar Mapbox:', e);
    showToast('❌ Erro ao carregar mapa. Verifique o token.', 'fa-exclamation-triangle', 5000);
  }
}

function addUserMarker(lat, lng) {
  if (userMarker) userMarker.remove();

  const el = document.createElement('div');
  el.className = 'user-location-marker';
  el.innerHTML = '<i class="fa-solid fa-location-arrow"></i>';

  userMarker = new mapboxgl.Marker({ element: el })
    .setLngLat([lng, lat])
    .addTo(mapInstance)
    .setPopup(new mapboxgl.Popup({ offset: 20 }).setHTML('<div style="padding: 10px; min-width: 150px;"><strong>Você está aqui</strong><br><small>Rastreando sua posição em tempo real.</small></div>'));
}

function addRiskZones() {
  const zones = [
    { lat: -23.5630, lng: -46.6543, color: '#E53935', radius: 0.003, id: 'zone1' },
    { lat: -23.5570, lng: -46.6620, color: '#FB8C00', radius: 0.0025, id: 'zone2' },
    { lat: -23.5558, lng: -46.6480, color: '#43A047', radius: 0.002, id: 'zone3' },
  ];

  zones.forEach((zone, idx) => {
    // Cria polígono circular (aproximado)
    const circleFeature = {
      type: 'Feature',
      geometry: {
        type: 'Point',
        coordinates: [zone.lng, zone.lat],
      },
      properties: {
        id: zone.id,
        color: zone.color,
      },
    };

    // Layer de círculo com fill
    mapInstance.addSource('zone-' + idx, {
      type: 'geojson',
      data: circleFeature,
    });

    mapInstance.addLayer({
      id: 'zone-circle-' + idx,
      type: 'circle',
      source: 'zone-' + idx,
      paint: {
        'circle-radius': 35,
        'circle-color': zone.color,
        'circle-opacity': 0.15,
        'circle-stroke-color': zone.color,
        'circle-stroke-width': 2,
        'circle-stroke-opacity': 0.4,
      },
    });

    // Hover efeito
    mapInstance.on('mouseenter', 'zone-circle-' + idx, function() {
      mapInstance.getCanvas().style.cursor = 'pointer';
    });

    mapInstance.on('mouseleave', 'zone-circle-' + idx, function() {
      mapInstance.getCanvas().style.cursor = '';
    });

    // Click para abrir detalhe
    mapInstance.on('click', 'zone-circle-' + idx, function() {
      openZoneDetail(zone.id);
    });
  });
}

function addReportMarkers() {
  const colors = {
    high: '#E53935',
    moderate: '#FB8C00',
    attention: '#FDD835',
    safe: '#43A047',
  };

  mockReports.forEach((report) => {
    const color = colors[report.risk] || '#9E9E9E';

    // Cria elemento customizado do marcador
    const el = document.createElement('div');
    el.style.cssText = `
      width: 32px;
      height: 32px;
      background: ${color};
      border-radius: 50% 50% 50% 0;
      border: 2px solid white;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.25);
      display: flex;
      align-items: center;
      justify-content: center;
      transform: rotate(-45deg);
      font-size: 14px;
      color: white;
      cursor: pointer;
    `;

    // Adiciona popup
    const popup = new mapboxgl.Popup({ offset: 25 })
      .setHTML(`
        <div style="padding: 8px;">
          <strong>${report.type}</strong><br>
          <small>${report.desc.substring(0, 60)}...</small>
        </div>
      `);

    const marker = new mapboxgl.Marker({ element: el })
      .setLngLat([report.lng, report.lat])
      .setPopup(popup)
      .addTo(mapInstance);

    // Evento de click: mostra descrição da denúncia
    el.addEventListener('click', () => {
      marker.togglePopup();
    });

    reportMarkers.push(marker);
  });
}

function addHeatmapLayer() {
  // Prepare heatmap data from reports
  const heatmapData = {
    type: 'FeatureCollection',
    features: mockReports.map(report => ({
      type: 'Feature',
      geometry: {
        type: 'Point',
        coordinates: [report.lng, report.lat],
      },
      properties: {
        intensity: report.risk === 'high' ? 1 : report.risk === 'moderate' ? 0.7 : 0.4,
      },
    })),
  };

  // Adiciona fonte de dados
  if (!mapInstance.getSource('heatmap-source')) {
    mapInstance.addSource('heatmap-source', {
      type: 'geojson',
      data: heatmapData,
    });

    // Adiciona heatmap layer
    mapInstance.addLayer(
      {
        id: 'heatmap-layer',
        type: 'heatmap',
        source: 'heatmap-source',
        paint: {
          'heatmap-weight': [
            'interpolate',
            ['linear'],
            ['get', 'intensity'],
            0,
            0,
            1,
            1,
          ],
          'heatmap-intensity': [
            'interpolate',
            ['linear'],
            ['zoom'],
            0,
            1,
            9,
            3,
          ],
          'heatmap-color': [
            'interpolate',
            ['linear'],
            ['heatmap-density'],
            0,
            'rgba(0, 0, 255, 0)',
            0.1,
            'rgb(65, 105, 225)',
            0.3,
            'rgb(34, 139, 34)',
            0.5,
            'rgb(255, 215, 0)',
            0.7,
            'rgb(255, 165, 0)',
            1,
            'rgb(255, 0, 0)',
          ],
          'heatmap-radius': [
            'interpolate',
            ['linear'],
            ['zoom'],
            0,
            2,
            9,
            20,
          ],
          'heatmap-opacity': [
            'interpolate',
            ['linear'],
            ['zoom'],
            7,
            1,
            9,
            0.3,
          ],
        },
      },
      'waterway-label'
    );
  }
}

function enable3DMode() {
  if (mapInstance) {
    mapInstance.setPitch(45);
    mapInstance.setBearing(25);
    showToast('Modo 3D ativado', 'fa-cube');
  }
}

function disable3DMode() {
  if (mapInstance) {
    mapInstance.setPitch(0);
    mapInstance.setBearing(0);
    showToast('Modo 2D', 'fa-map');
  }
}

/**
 * Toggle 3D Mode - Um único botão com duas funções
 */
function toggle3DMode() {
  if (!mapInstance) return;

  const currentPitch = mapInstance.getPitch();
  const is3DOn = currentPitch > 0;

  if (is3DOn) {
    disable3DMode();
    document.getElementById('btn3DToggle').style.opacity = '0.6';
  } else {
    enable3DMode();
    document.getElementById('btn3DToggle').style.opacity = '1';
  }
}

/**
 * Acessar câmera do dispositivo
 * Funcional em Android/iOS com suporte a HTML5 FileAPI
 */
async function openCamera() {
  try {
    // Verificar suporte
    const hasGetUserMedia = !!(
      navigator.getUserMedia ||
      navigator.webkitGetUserMedia ||
      navigator.mozGetUserMedia ||
      (navigator.mediaDevices && navigator.mediaDevices.getUserMedia)
    );

    if (!hasGetUserMedia) {
      showToast('Câmera não suportada neste dispositivo', 'fa-camera-slash');
      return;
    }

    // Usar HTML5 input file com accept="image/*;capture=environment"
    const input = document.createElement('input');
    input.type = 'file';
    input.accept = 'image/*;capture=environment'; // Abre câmera no Android
    
    input.onchange = (e) => {
      const file = e.target.files[0];
      if (file) {
        // Armazenar arquivo capturado
        window.capturedImage = file;
        const reader = new FileReader();
        reader.onload = (event) => {
          // Pode ser usado para preview
          console.log('📸 Foto capturada:', file.name);
          showToast('Foto capturada com sucesso! 📸', 'fa-camera');
        };
        reader.readAsDataURL(file);
      }
    };
    
    input.click();
  } catch (err) {
    console.error('Erro ao acessar câmera:', err);
    showToast('Erro ao acessar câmera', 'fa-exclamation');
  }
}

function centerCameraOnUser() {
  if (!mapInstance) {
    return;
  }

  if (!userMarker) {
    showToast('Localização do usuário ainda não disponível', 'fa-location-slash');
    return;
  }

  const coords = userMarker.getLngLat();
  mapInstance.flyTo({
    center: [coords.lng, coords.lat],
    zoom: Math.max(mapInstance.getZoom(), 16),
    speed: 0.8,
    curve: 1.2
  });
  showToast('Centralizando no usuário', 'fa-location-dot');
}

/**
 * Atualizar localização em tempo real no mapa
 */
function updateRealTimeLocation() {
  if (!navigator.geolocation) {
    console.warn('Geolocalização não disponível');
    return;
  }

  const watchId = navigator.geolocation.watchPosition(
    (position) => {
      const { latitude, longitude, accuracy } = position.coords;
      
      // Atualizar marcador de usuário
      if (userMarkerInstance) {
        userMarkerInstance.setLngLat([longitude, latitude]);
      }

      // Atualizar display de localização
      const locationDisplay = document.getElementById('locationDisplay');
      const locationText = document.getElementById('locationText');
      
      if (locationDisplay && locationText) {
        locationDisplay.style.display = 'block';
        // Usar API de reverse geocoding para nome da rua/bairro
        locationText.textContent = `${latitude.toFixed(4)}, ${longitude.toFixed(4)}`;
        
        // Tentar obter nome da localidade (requer API externa)
        // Por enquanto, mostrar coordenadas
      }

      // Atualizar marcador de usuário
      if (userMarker) {
        userMarker.setLngLat([longitude, latitude]);
      } else {
        addUserMarker(latitude, longitude);
      }

      // Animar câmera para seguir usuário automaticamente se zoom estiver baixo
      if (mapInstance && mapInstance.getZoom() < 15) {
        mapInstance.flyTo({
          center: [longitude, latitude],
          zoom: 15,
          speed: 0.5
        });
      }
    },
    (error) => {
      console.warn('Erro ao obter localização:', error);
      showToast('Erro ao obter localização', 'fa-location-slash');
    },
    {
      enableHighAccuracy: true,
      timeout: 5000,
      maximumAge: 0,
      watchPosition: true
    }
  );

  // Armazenar watchId para limpeza posterior
  window.geolocationWatchId = watchId;
}

function openZoneDetail(zoneId) {
  const zone = zoneData[zoneId];
  if (!zone) return;

  document.getElementById('zoneTitle').textContent = zone.name;

  let html = `<span class="chip ${zone.riskClass} zone-risk-label"><span class="chip-dot"></span>${zone.risk}</span>`;

  if (zone.incidents.length === 0) {
    html += '<p style="color:var(--text-hint);font-size:13px">Nenhuma ocorrência registrada.</p>';
  } else {
    html += '<div class="zone-incidents">';
    zone.incidents.forEach(inc => {
      html += `
        <div class="zone-incident-item" onclick="openReportDetail('${inc.id}');closeOverlay('zone-overlay')">
          <div class="zii-icon" style="background:${inc.riskBg};color:${inc.riskColor}">
            <i class="fa-solid ${inc.icon}"></i>
          </div>
          <div class="zii-body">
            <div class="zii-type">${inc.type}</div>
            <div class="zii-desc">${inc.desc.substring(0, 70)}...</div>
            <div class="zii-time">${inc.time} · ${inc.location}</div>
          </div>
        </div>`;
    });
    html += '</div>';
  }

  html += `<button class="btn btn--primary btn--full" style="margin-top:16px" onclick="closeOverlay('zone-overlay');navigate('report')">
    <i class="fa-solid fa-bullhorn"></i> Denunciar nessa área
  </button>`;

  document.getElementById('zoneContent').innerHTML = html;
  openOverlay('zone-overlay');
}

// ============================================================
// LISTA DE OCORRÊNCIAS
// ============================================================
function renderReportsList() {
  const container = document.getElementById('reportsList');
  if (!container) return;

  container.innerHTML = mockReports.map(r => `
    <div class="report-item" onclick="openReportDetail('${r.id}')">
      <div class="ri-header">
        <span class="ri-type">${r.type}</span>
        <span class="chip ${riskToChipClass(r.risk)}">${r.riskLabel}</span>
      </div>
      <p class="ri-desc">${r.desc}</p>
      <div class="ri-footer">
        <i class="fa-solid fa-location-dot"></i>
        <span>${r.location}</span>
        <span style="margin-left:auto">${r.time}</span>
        <i class="fa-solid fa-users"></i>
        <span>${r.witnesses}</span>
      </div>
    </div>
  `).join('');
}

function riskToChipClass(risk) {
  const map = { high: 'chip--red', moderate: 'chip--orange', attention: 'chip--yellow', safe: 'chip--green' };
  return map[risk] || '';
}

function openReportDetail(reportId) {
  const report = mockReports.find(r => r.id === reportId);
  if (!report) return;

  window._currentReportDetail = report;

  const container = document.getElementById('detailContainer');
  if (!container) return;

  container.innerHTML = `
    <div class="detail-header">
      <div class="detail-meta">
        <span class="chip ${riskToChipClass(report.risk)}">${report.riskLabel}</span>
        <i class="fa-solid fa-clock"></i> ${report.time}
      </div>
      <h2 class="detail-title">${report.type}</h2>
      <div class="detail-meta">
        <i class="fa-solid fa-location-dot" style="color:var(--blue)"></i>
        ${report.location}
      </div>
    </div>
    <p class="detail-desc">${report.desc}</p>
    <div class="detail-section">
      <div class="detail-section-title">Protocolo</div>
      <div class="protocol-box" style="width:100%;display:flex;flex-direction:column;align-items:center;padding:12px 16px">
        <span>#${report.id}</span>
      </div>
    </div>
    <div class="detail-section">
      <div class="detail-section-title">Testemunhas</div>
      <div class="witness-confirm">
        <p>Você também testemunhou esta ocorrência?</p>
        <div class="btns">
          <button class="btn btn--primary" onclick="confirmWitness('${report.id}', true)" style="flex:1">
            <i class="fa-solid fa-check"></i> Sim
          </button>
          <button class="btn btn--ghost" onclick="confirmWitness('${report.id}', false)" style="flex:1">
            <i class="fa-solid fa-xmark"></i> Não
          </button>
        </div>
      </div>
    </div>
    <div class="detail-section">
      <div class="detail-section-title">Dados</div>
      <div class="report-summary">
        <div class="rs-row">
          <span class="rs-label">ID</span>
          <span class="rs-value">#${report.id}</span>
        </div>
        <div class="rs-row">
          <span class="rs-label">Tipo</span>
          <span class="rs-value">${report.type}</span>
        </div>
        <div class="rs-row">
          <span class="rs-label">Testemunhas</span>
          <span class="rs-value">${report.witnesses} confirmações</span>
        </div>
        <div class="rs-row">
          <span class="rs-label">Registrado</span>
          <span class="rs-value">${report.time}</span>
        </div>
      </div>
    </div>
  `;

  navigate('report-detail');
}

function confirmWitness(reportId, confirmed) {
  if (confirmed) {
    showToast('Sua confirmação foi registrada!', 'fa-check-circle');
    const report = mockReports.find(r => r.id === reportId);
    if (report) report.witnesses++;
  } else {
    showToast('Entendido. Obrigado pelo retorno.');
  }
}

function shareReport() {
  showToast('Link copiado!', 'fa-copy');
}

// ============================================================
// FORMULÁRIO DE DENÚNCIA
// ============================================================
function resetReportForm() {
  reportState.incidentType = null;
  reportState.risk = 'Baixo';
  reportState.currentStep = 1;
  reportState.witness = null;

  // Reseta cards selecionados
  document.querySelectorAll('.incident-card').forEach(c => c.classList.remove('selected'));

  // Reseta botões de risco
  document.querySelectorAll('.risk-btn').forEach(b => b.classList.remove('active'));
  const firstRisk = document.querySelector('.risk-btn--green');
  if (firstRisk) firstRisk.classList.add('active');

  // Reseta testemunha
  document.querySelectorAll('.witness-btn').forEach(b => b.classList.remove('selected'));

  // Desativa botão próximo
  const btnNext = document.getElementById('btnStep1Next');
  if (btnNext) btnNext.disabled = true;

  // Mostra step 1
  goToStep(1);

  // Atualiza steps bar
  updateStepsBar(1);
}

function selectIncident(el, type) {
  document.querySelectorAll('.incident-card').forEach(c => c.classList.remove('selected'));
  el.classList.add('selected');
  reportState.incidentType = type;

  const btn = document.getElementById('btnStep1Next');
  if (btn) btn.disabled = false;
}

function selectRisk(el) {
  document.querySelectorAll('.risk-btn').forEach(b => b.classList.remove('active'));
  el.classList.add('active');
  reportState.risk = el.dataset.risk;
}

function goToStep(step) {
  // Esconde todos os steps
  document.querySelectorAll('.report-step').forEach(s => s.classList.remove('active'));

  // Mostra step atual
  const targetStep = document.getElementById('reportStep' + step);
  if (targetStep) targetStep.classList.add('active');

  reportState.currentStep = step;
  updateStepsBar(step);

  // Step 3: preenche resumo
  if (step === 3) fillReportSummary();
}

function updateStepsBar(activeStep) {
  document.querySelectorAll('.step').forEach(s => {
    const n = parseInt(s.dataset.step);
    s.classList.remove('active', 'done');
    if (n < activeStep) s.classList.add('done');
    if (n === activeStep) s.classList.add('active');
  });
}

function fillReportSummary() {
  const container = document.getElementById('reportSummary');
  if (!container) return;

  container.innerHTML = `
    <div class="rs-row">
      <span class="rs-label">Tipo de ocorrência</span>
      <span class="rs-value">${reportState.incidentType || '—'}</span>
    </div>
    <div class="rs-row">
      <span class="rs-label">Nível de risco</span>
      <span class="rs-value">${reportState.risk}</span>
    </div>
    <div class="rs-row">
      <span class="rs-label">Localização</span>
      <span class="rs-value">${reportState.location}</span>
    </div>
    <div class="rs-row">
      <span class="rs-label">Enviado como</span>
      <span class="rs-value"><i class="fa-solid fa-user-secret"></i> Anônimo</span>
    </div>
  `;
}

function setWitness(value) {
  reportState.witness = value;
  document.getElementById('witnessYes').classList.toggle('selected', value === true);
  document.getElementById('witnessNo').classList.toggle('selected', value === false);
}

function submitReport() {
  if (reportState.incidentType === null) {
    showToast('Selecione o tipo de ocorrência', 'fa-circle-exclamation');
    return;
  }

  const protocol = 'APL-' + Math.floor(100000 + Math.random() * 900000);
  document.getElementById('successProtocol').textContent = '#' + protocol;
  document.getElementById('successTitle').textContent = 'Denúncia Enviada!';
  document.getElementById('successMessage').textContent = 'Sua denúncia foi recebida e está em análise. Obrigado por contribuir com a segurança da comunidade.';

  navigate('success');
  showToast('Denúncia enviada com sucesso!', 'fa-check-circle');
}

function triggerMediaUpload() {
  showToast('Upload de mídia ativo', 'fa-camera');
}

function setLocation(type) {
  const labels = {
    current: 'Localização atual (GPS)',
    manual: 'Endereço manual',
    map: 'Marcado no mapa',
  };
  reportState.location = labels[type] || 'Localização atual';
  const el = document.getElementById('reportLocation');
  if (el) el.textContent = reportState.location;
  closeOverlay('location-overlay');
  showToast('Localização definida');
}

// ============================================================
// EMERGÊNCIA (TELA)
// ============================================================
let emergencyTimer = null;
let emergencyProgress = 0;
const EMERGENCY_HOLD_MS = 3000;
const EMERGENCY_INTERVAL_MS = 50;

function resetEmergencyScreen() {
  cancelEmergencyScreen();
  const idle   = document.getElementById('emergencyIdle');
  const active = document.getElementById('emergencyActive');
  if (idle)   idle.style.display   = 'flex';
  if (active) active.style.display = 'none';

  const ring = document.getElementById('eRingProg');
  if (ring) ring.style.strokeDashoffset = '327';
}

function startEmergencyScreen(e) {
  e.preventDefault();
  emergencyProgress = 0;

  const ring = document.getElementById('eRingProg');
  const circumference = 327;

  emergencyTimer = setInterval(() => {
    emergencyProgress += EMERGENCY_INTERVAL_MS;
    const offset = circumference - (circumference * emergencyProgress / EMERGENCY_HOLD_MS);
    if (ring) ring.style.strokeDashoffset = Math.max(0, offset).toString();

    if (emergencyProgress >= EMERGENCY_HOLD_MS) {
      clearInterval(emergencyTimer);
      triggerEmergencyActive();
    }
  }, EMERGENCY_INTERVAL_MS);
}

function cancelEmergencyScreen() {
  if (emergencyTimer) {
    clearInterval(emergencyTimer);
    emergencyTimer = null;
  }
  emergencyProgress = 0;
  const ring = document.getElementById('eRingProg');
  if (ring) ring.style.strokeDashoffset = '327';
}

function triggerEmergencyActive() {
  const idle   = document.getElementById('emergencyIdle');
  const active = document.getElementById('emergencyActive');
  if (idle)   idle.style.display   = 'none';
  if (active) active.style.display = 'flex';

  const protocol = 'APL-' + Math.floor(100000 + Math.random() * 900000);
  const el = document.getElementById('eaProtocol');
  if (el) el.textContent = '#' + protocol;

  showToast('Emergência acionada! Protocolo: #' + protocol, 'fa-siren-on', 5000);
}

function cancelEmergencyActive() {
  showConfirm({
    title: 'Cancelar emergência?',
    message: 'Os serviços de emergência serão avisados do cancelamento.',
    onConfirm: () => {
      resetEmergencyScreen();
      showToast('Emergência cancelada', 'fa-circle-check');
    }
  });
}

// ============================================================
// FAB EMERGÊNCIA (HOME)
// ============================================================
let fabEmergencyTimer = null;
let fabEmergencyProgress = 0;

function startEmergencyHold(e) {
  e.preventDefault();
  fabEmergencyProgress = 0;
  const ring = document.getElementById('emergencyRing');
  const circumference = 157;

  fabEmergencyTimer = setInterval(() => {
    fabEmergencyProgress += EMERGENCY_INTERVAL_MS;
    const offset = circumference - (circumference * fabEmergencyProgress / EMERGENCY_HOLD_MS);
    if (ring) ring.style.strokeDashoffset = Math.max(0, offset).toString();

    if (fabEmergencyProgress >= EMERGENCY_HOLD_MS) {
      clearInterval(fabEmergencyTimer);
      navigate('emergency');
      setTimeout(triggerEmergencyActive, 300);
    }
  }, EMERGENCY_INTERVAL_MS);
}

function cancelEmergencyHold() {
  if (fabEmergencyTimer) {
    clearInterval(fabEmergencyTimer);
    fabEmergencyTimer = null;
  }
  fabEmergencyProgress = 0;
  const ring = document.getElementById('emergencyRing');
  if (ring) ring.style.strokeDashoffset = '157';
}

// ============================================================
// NOTIFICAÇÕES
// ============================================================
function renderNotifications() {
  const container = document.getElementById('notificationsList');
  if (!container) return;

  if (mockNotifications.length === 0) {
    container.innerHTML = `<div style="text-align:center;padding:40px 20px;color:var(--text-hint)">
      <i class="fa-regular fa-bell-slash" style="font-size:48px;margin-bottom:12px;display:block"></i>
      Nenhuma notificação
    </div>`;
    return;
  }

  container.innerHTML = mockNotifications.map(n => `
    <div class="notif-item ${n.unread ? 'unread' : ''}" onclick="markRead(${n.id})">
      <div class="notif-icon ${n.iconClass}">
        <i class="fa-solid ${n.icon}"></i>
      </div>
      <div class="notif-body">
        <div class="notif-title">${n.title}</div>
        <div class="notif-desc">${n.desc}</div>
        <div class="notif-time">${n.time}</div>
      </div>
    </div>
  `).join('');
}

function markRead(id) {
  const notif = mockNotifications.find(n => n.id === id);
  if (notif) notif.unread = false;
  renderNotifications();
}

function markAllRead() {
  mockNotifications.forEach(n => n.unread = false);
  renderNotifications();
  showToast('Todas marcadas como lidas');
}

// ============================================================
// FAQ
// ============================================================
function renderFAQ() {
  const container = document.getElementById('faqList');
  if (!container) return;

  container.innerHTML = faqData.map((item, i) => `
    <div class="faq-item" id="faq-${i}">
      <div class="faq-question" onclick="toggleFAQ(${i})">
        <span>${item.q}</span>
        <i class="fa-solid fa-chevron-down"></i>
      </div>
      <div class="faq-answer">${item.a}</div>
    </div>
  `).join('');
}

function toggleFAQ(index) {
  const item = document.getElementById('faq-' + index);
  if (!item) return;
  item.classList.toggle('open');
}

// ============================================================
// AUTENTICAÇÃO
// ============================================================
function togglePassword(inputId, btn) {
  const input = document.getElementById(inputId);
  if (!input) return;
  const isPass = input.type === 'password';
  input.type = isPass ? 'text' : 'password';
  const icon = btn.querySelector('i');
  if (icon) {
    icon.className = isPass ? 'fa-regular fa-eye-slash' : 'fa-regular fa-eye';
  }
}

function maskCPF(input) {
  let v = input.value.replace(/\D/g, '');
  v = v.replace(/(\d{3})(\d)/, '$1.$2');
  v = v.replace(/(\d{3})(\d)/, '$1.$2');
  v = v.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
  input.value = v;
}

function checkPasswordStrength(input) {
  const val = input.value;
  const fill  = document.getElementById('strengthFill');
  const label = document.getElementById('strengthLabel');
  if (!fill || !label) return;

  let strength = 0;
  if (val.length >= 8) strength++;
  if (/[A-Z]/.test(val)) strength++;
  if (/[0-9]/.test(val)) strength++;
  if (/[^A-Za-z0-9]/.test(val)) strength++;

  const levels = [
    { pct: '0%',   color: 'var(--gray-300)', text: 'Força da senha' },
    { pct: '25%',  color: 'var(--red)',       text: 'Fraca' },
    { pct: '50%',  color: 'var(--orange)',    text: 'Regular' },
    { pct: '75%',  color: 'var(--yellow-dark)', text: 'Boa' },
    { pct: '100%', color: 'var(--green)',     text: 'Forte' },
  ];

  const level = levels[strength];
  fill.style.width = level.pct;
  fill.style.background = level.color;
  label.textContent = level.text;
  label.style.color = level.color;
}

// ============================================================
// LOGOUT
// ============================================================
function confirmLogout() {
  closeAllOverlays();
  setTimeout(() => {
    showConfirm({
      title: 'Encerrar Sessão',
      message: 'Você será desconectado do APOLO. Deseja continuar?',
      onConfirm: () => navigate('login'),
    });
  }, 250);
}

// ============================================================
// DARK MODE
// ============================================================
function toggleDarkMode(checkbox) {
  document.documentElement.setAttribute('data-theme', checkbox.checked ? 'dark' : '');

  // Troca estilo do mapa se inicializado
  if (mapInstance) {
    const newStyle = checkbox.checked
      ? 'mapbox://styles/mapbox/dark-v11'
      : 'mapbox://styles/mapbox/light-v11';
    mapInstance.setStyle(newStyle);
  }

  showToast(checkbox.checked ? 'Modo escuro ativado' : 'Modo claro ativado');
}

// ============================================================
// FILTER / TABS
// ============================================================
function filterRisk(type) {
  showToast('Filtro aplicado: ' + type);
}

function applyFilter() {
  closeOverlay('filter-overlay');
  showToast('Filtros aplicados');
}

function switchTab(btn, tab) {
  document.querySelectorAll('#reportsTabs .tab').forEach(t => t.classList.remove('active'));
  btn.classList.add('active');
  renderReportsList();
}

// ============================================================
// TOAST NOTIFICATIONS
// ============================================================
function showToast(message, icon = 'fa-check-circle', duration = 3000) {
  const toast = document.getElementById('toast');
  const toastMsg = document.getElementById('toastMsg');
  const toastIcon = document.querySelector('.toast-icon');

  if (!toast || !toastMsg) return;

  toastMsg.textContent = message;
  if (toastIcon) {
    toastIcon.className = 'toast-icon fa-solid ' + icon;
  }

  toast.classList.add('visible');
  toast.style.opacity = '1';
  toast.style.transform = 'translateY(0)';

  setTimeout(() => {
    toast.style.opacity = '0';
    toast.style.transform = 'translateY(20px)';
    setTimeout(() => {
      toast.classList.remove('visible');
    }, 300);
  }, duration);
}

// ============================================================
// CONFIRM MODALS
// ============================================================
function showConfirm(options = {}) {
  const { title = 'Confirmar', message = '', onConfirm = () => {}, onCancel = () => {} } = options;

  // Cria overlay de confirmação
  let confirmOverlay = document.getElementById('confirm-overlay');
  if (!confirmOverlay) {
    confirmOverlay = document.createElement('div');
    confirmOverlay.id = 'confirm-overlay';
    confirmOverlay.style.cssText = `
      position: fixed;
      inset: 0;
      background: rgba(0, 0, 0, 0.6);
      display: flex;
      align-items: center;
      justify-content: center;
      z-index: 1000;
      animation: fadeIn 0.2s ease-out;
    `;
    document.body.appendChild(confirmOverlay);
  }

  const modal = document.createElement('div');
  modal.style.cssText = `
    background: white;
    border-radius: 12px;
    padding: 24px;
    max-width: 90%;
    width: 320px;
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
    animation: slideUp 0.3s ease-out;
  `;

  modal.innerHTML = `
    <h3 style="margin: 0 0 12px; font-size: 18px; font-weight: 600;">${title}</h3>
    <p style="margin: 0 0 20px; color: var(--text-secondary); font-size: 14px;">${message}</p>
    <div style="display: flex; gap: 12px;">
      <button class="btn btn--ghost" style="flex: 1;" onclick="document.getElementById('confirm-overlay').remove()">Cancelar</button>
      <button class="btn btn--primary" style="flex: 1;" onclick="document.getElementById('confirm-overlay').remove()">Confirmar</button>
    </div>
  `;

  // Encontra os botões e adiciona eventos
  setTimeout(() => {
    const buttons = modal.querySelectorAll('button');
    if (buttons[0]) buttons[0].addEventListener('click', onCancel);
    if (buttons[1]) buttons[1].addEventListener('click', onConfirm);
  }, 10);

  confirmOverlay.innerHTML = '';
  confirmOverlay.appendChild(modal);
  confirmOverlay.style.display = 'flex';

  setTimeout(() => {
    confirmOverlay.style.opacity = '1';
  }, 10);
}

// ============================================================
// OVERLAY & MODAL MANAGEMENT
// ============================================================
function closeAllOverlays() {
  const backdrops = document.querySelectorAll('.overlay-backdrop.active');
  backdrops.forEach(b => b.classList.remove('active'));

  ['menu-overlay', 'search-overlay', 'filter-overlay', 'location-overlay',
   'supp-overlay', 'zone-overlay', 'confirm-overlay'].forEach(id => {
    const el = document.getElementById(id);
    if (el) {
      el.classList.remove('active');
      el.style.display = 'none';
    }
  });
}
