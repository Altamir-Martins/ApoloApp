/**
 * Mobile Optimization Module
 * Enhances mobile/Android experience with touch handling and adaptive behaviors
 */

class MobileOptimization {
  constructor() {
    this.isMobile = this.detectMobile();
    this.isAndroid = this.detectAndroid();
    this.isIOS = this.detectIOS();
    this.viewportHeight = window.innerHeight;
    this.viewportWidth = window.innerWidth;
    this.isLandscape = window.innerWidth > window.innerHeight;
    
    this.init();
  }

  /**
   * Initialize mobile optimizations
   */
  init() {
    if (!this.isMobile) return;

    // Prevent viewport zoom on iOS
    this.preventIOSZoom();

    // Handle viewport changes on Android
    this.handleViewportChanges();

    // Optimize touch interactions
    this.optimizeTouchInteractions();

    // Handle soft keyboard show/hide
    this.handleSoftKeyboard();

    // Prevent pull-to-refresh on Android
    this.preventPullToRefresh();

    // Add viewport meta tag updates
    this.updateViewportMetaTags();

    // Log detection for debugging
    console.log('📱 Mobile Optimization Initialized', {
      isMobile: this.isMobile,
      isAndroid: this.isAndroid,
      isIOS: this.isIOS,
      screenSize: `${this.viewportWidth}x${this.viewportHeight}`,
      isLandscape: this.isLandscape,
    });
  }

  /**
   * Detect if device is mobile
   */
  detectMobile() {
    const userAgent = navigator.userAgent.toLowerCase();
    const mobileKeywords = [
      'mobile', 'android', 'iphone', 'ipad', 'ipod', 'windows phone',
      'blackberry', 'nokia', 'opera mini', 'op_mini', 'touch',
    ];
    
    return mobileKeywords.some(keyword => userAgent.includes(keyword)) ||
           window.innerWidth < 768;
  }

  /**
   * Detect Android device
   */
  detectAndroid() {
    return navigator.userAgent.toLowerCase().includes('android');
  }

  /**
   * Detect iOS device
   */
  detectIOS() {
    return /iPad|iPhone|iPod/.test(navigator.userAgent);
  }

  /**
   * Prevent zoom on iOS when input is focused
   */
  preventIOSZoom() {
    if (!this.isIOS) return;

    const inputs = document.querySelectorAll('input, textarea, select');
    inputs.forEach(input => {
      input.addEventListener('focus', () => {
        document.body.style.zoom = 1;
      });

      input.addEventListener('blur', () => {
        document.body.style.zoom = 1;
      });
    });
  }

  /**
   * Handle viewport size changes
   */
  handleViewportChanges() {
    let resizeTimeout;
    
    window.addEventListener('resize', () => {
      clearTimeout(resizeTimeout);
      resizeTimeout = setTimeout(() => {
        this.viewportHeight = window.innerHeight;
        this.viewportWidth = window.innerWidth;
        const newIsLandscape = window.innerWidth > window.innerHeight;

        if (this.isLandscape !== newIsLandscape) {
          this.isLandscape = newIsLandscape;
          this.handleOrientationChange();
        }

        // Reposition FAB and overlays on resize
        this.adjustLayoutOnResize();
      }, 300);
    });

    window.addEventListener('orientationchange', () => {
      this.handleOrientationChange();
    });
  }

  /**
   * Handle orientation changes
   */
  handleOrientationChange() {
    console.log('🔄 Orientation changed:', this.isLandscape ? 'landscape' : 'portrait');
    
    // Dispatch custom event for app to listen
    window.dispatchEvent(new CustomEvent('orientationChanged', {
      detail: { isLandscape: this.isLandscape }
    }));

    // Adjust map height if visible
    if (map && map.resize) {
      setTimeout(() => map.resize(), 100);
    }
  }

  /**
   * Adjust layout on screen resize
   */
  adjustLayoutOnResize() {
    const fabContainer = document.querySelector('.fab-container');
    const mapBottomStrip = document.querySelector('.map-bottom-strip');
    const mapTopbar = document.querySelector('.map-topbar');

    if (fabContainer && this.viewportHeight < 500) {
      // Adjust FAB position for small screens
      fabContainer.style.bottom = `${70 + (this.isLandscape ? 20 : 0)}px`;
    }

    if (mapBottomStrip && this.viewportHeight < 600 && this.isLandscape) {
      mapBottomStrip.style.padding = '6px 12px';
    }
  }

  /**
   * Optimize touch interactions
   */
  optimizeTouchInteractions() {
    // Improve touch feedback for buttons
    const buttons = document.querySelectorAll('button, .btn, .fab, .incident-card, .smn-item');
    
    buttons.forEach(button => {
      // Remove tap highlight on Android
      button.style.webkitTapHighlightColor = 'transparent';

      // Add active state on touch start for faster feedback
      button.addEventListener('touchstart', () => {
        button.style.opacity = '0.7';
      });

      button.addEventListener('touchend', () => {
        button.style.opacity = '1';
      });

      button.addEventListener('touchcancel', () => {
        button.style.opacity = '1';
      });
    });

    // Prevent double-tap zoom
    let lastTap = 0;
    document.addEventListener('touchend', (e) => {
      const now = Date.now();
      if (now - lastTap < 300 && e.touches.length === 0) {
        e.preventDefault();
      }
      lastTap = now;
    }, false);
  }

  /**
   * Handle soft keyboard appearance
   */
  handleSoftKeyboard() {
    if (!this.isAndroid) return;

    const inputs = document.querySelectorAll('input, textarea, select');
    let keyboardOpen = false;

    inputs.forEach(input => {
      input.addEventListener('focus', () => {
        keyboardOpen = true;
        document.body.style.position = 'fixed';
        document.body.style.width = '100%';
        
        // Scroll input into view
        setTimeout(() => {
          input.scrollIntoView({ behavior: 'smooth', block: 'center' });
        }, 100);
      });

      input.addEventListener('blur', () => {
        keyboardOpen = false;
        document.body.style.position = 'static';
        document.body.style.width = '100%';
      });
    });

    window.addEventListener('resize', () => {
      if (keyboardOpen && document.activeElement) {
        setTimeout(() => {
          document.activeElement.scrollIntoView({ behavior: 'smooth', block: 'center' });
        }, 100);
      }
    });
  }

  /**
   * Prevent pull-to-refresh on Android devices
   */
  preventPullToRefresh() {
    if (!this.isAndroid) return;

    let startY = 0;

    document.addEventListener('touchstart', (e) => {
      startY = e.touches[0].clientY;
    });

    document.addEventListener('touchmove', (e) => {
      const currentY = e.touches[0].clientY;
      
      // If scrolled more than 100px from top and moving down, prevent default
      if (window.scrollY === 0 && currentY > startY + 100) {
        e.preventDefault();
      }
    }, { passive: false });
  }

  /**
   * Update viewport meta tags for better mobile experience
   */
  updateViewportMetaTags() {
    const viewportTag = document.querySelector('meta[name="viewport"]');
    
    if (!viewportTag) {
      const newTag = document.createElement('meta');
      newTag.name = 'viewport';
      newTag.content = 'width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no, viewport-fit=cover';
      document.head.appendChild(newTag);
    }

    // Add theme-color for Android
    if (this.isAndroid) {
      let themeColor = document.querySelector('meta[name="theme-color"]');
      if (!themeColor) {
        themeColor = document.createElement('meta');
        themeColor.name = 'theme-color';
        themeColor.content = '#1e88e5';
        document.head.appendChild(themeColor);
      }
    }

    // Add status-bar-style for iOS
    if (this.isIOS) {
      let statusBar = document.querySelector('meta[name="apple-mobile-web-app-status-bar-style"]');
      if (!statusBar) {
        statusBar = document.createElement('meta');
        statusBar.name = 'apple-mobile-web-app-status-bar-style';
        statusBar.content = 'black-translucent';
        document.head.appendChild(statusBar);
      }
    }
  }

  /**
   * Get safe area insets (for notched devices)
   */
  getSafeAreaInsets() {
    const style = getComputedStyle(document.documentElement);
    return {
      top: parseInt(style.getPropertyValue('env(safe-area-inset-top)')) || 0,
      right: parseInt(style.getPropertyValue('env(safe-area-inset-right)')) || 0,
      bottom: parseInt(style.getPropertyValue('env(safe-area-inset-bottom)')) || 0,
      left: parseInt(style.getPropertyValue('env(safe-area-inset-left)')) || 0,
    };
  }

  /**
   * Optimize button sizes for touch
   */
  optimizeButtonSizes() {
    const buttons = document.querySelectorAll('button, .btn');
    buttons.forEach(btn => {
      const width = btn.offsetWidth;
      const height = btn.offsetHeight;

      // Ensure minimum touch target size of 44x44px
      if (width < 44 || height < 44) {
        const padding = Math.max(12, Math.ceil((44 - height) / 2));
        btn.style.minHeight = '44px';
        btn.style.minWidth = '44px';
        btn.style.padding = `${padding}px 16px`;
      }
    });
  }

  /**
   * Handle status bar color for Android
   */
  setStatusBarColor(color) {
    if (!this.isAndroid) return;

    const metaTag = document.querySelector('meta[name="theme-color"]');
    if (metaTag) {
      metaTag.content = color;
    }
  }

  /**
   * Lock orientation for specific screens
   */
  lockOrientation(newOrientation) {
    if (screen.orientation && screen.orientation.lock) {
      try {
        const orientation = newOrientation || 'portrait';
        screen.orientation.lock(orientation).catch(err => {
          console.warn('Could not lock orientation:', err);
        });
      } catch (err) {
        console.warn('Orientation lock not supported:', err);
      }
    }
  }

  /**
   * Unlock orientation
   */
  unlockOrientation() {
    if (screen.orientation && screen.orientation.unlock) {
      try {
        screen.orientation.unlock();
      } catch (err) {
        console.warn('Orientation unlock not supported:', err);
      }
    }
  }

  /**
   * Enable full-screen immersive mode (Android)
   */
  enableFullscreen() {
    const elem = document.documentElement;
    
    if (elem.requestFullscreen) {
      elem.requestFullscreen().catch(err => {
        console.warn('Fullscreen request failed:', err);
      });
    } else if (elem.webkitRequestFullscreen) {
      elem.webkitRequestFullscreen();
    }
  }

  /**
   * Disable fullscreen
   */
  exitFullscreen() {
    if (document.exitFullscreen) {
      document.exitFullscreen();
    } else if (document.webkitExitFullscreen) {
      document.webkitExitFullscreen();
    }
  }

  /**
   * Vibrate device (haptic feedback)
   */
  vibrate(duration) {
    if (navigator.vibrate) {
      navigator.vibrate(duration || 50);
    }
  }

  /**
   * Get device battery level
   */
  async getBatteryLevel() {
    if (!navigator.getBattery) return null;

    try {
      const battery = await navigator.getBattery();
      return {
        level: battery.level,
        charging: battery.charging,
        chargingTime: battery.chargingTime,
        dischargingTime: battery.dischargingTime,
      };
    } catch (err) {
      console.warn('Battery API not supported:', err);
      return null;
    }
  }

  /**
   * Check network connection
   */
  getNetworkStatus() {
    return {
      online: navigator.onLine,
      type: navigator.connection?.effectiveType || 'unknown',
      downlink: navigator.connection?.downlink || 'unknown',
    };
  }

  /**
   * Automatically switch theme based on system preference
   */
  observeSystemTheme() {
    if (window.matchMedia) {
      const darkMode = window.matchMedia('(prefers-color-scheme: dark)');
      
      darkMode.addListener((e) => {
        const isDark = e.matches;
        // Dispatch event for app to listen
        window.dispatchEvent(new CustomEvent('systemThemeChanged', {
          detail: { isDark }
        }));
      });

      // Initial check
      const isDark = darkMode.matches;
      if (isDark) {
        window.dispatchEvent(new CustomEvent('systemThemeChanged', {
          detail: { isDark: true }
        }));
      }
    }
  }

  /**
   * Debug info
   */
  logDebugInfo() {
    console.group('📱 Mobile Device Info');
    console.log('User Agent:', navigator.userAgent);
    console.log('Viewport:', `${this.viewportWidth}x${this.viewportHeight}`);
    console.log('Device:', {
      isMobile: this.isMobile,
      isAndroid: this.isAndroid,
      isIOS: this.isIOS,
      isLandscape: this.isLandscape,
    });
    console.log('Network:', this.getNetworkStatus());
    console.log('Safe Areas:', this.getSafeAreaInsets());
    console.groupEnd();
  }
}

// Initialize on page load
document.addEventListener('DOMContentLoaded', () => {
  window.mobileOptimization = new MobileOptimization();
  
  // Observe system theme changes
  window.mobileOptimization.observeSystemTheme();
});
