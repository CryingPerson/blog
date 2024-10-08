import { fileURLToPath, URL } from 'url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'

// Vite config (vite.config.js)
export default defineConfig({
  plugins: [vue(), vueJsx()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  server: {
    port: 3000,
    // proxy: {  // 프록시 설정 제거
    //   '/auth': {
    //     target: 'http://localhost:8888',
    //     changeOrigin: true,
    //   },
    // },
  },
})
