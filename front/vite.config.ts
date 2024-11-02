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
    proxy: {
      '/api': {
        target: 'http://localhost:8888',
        // rewrite: (path) => path.replace(/^\/api/, ''), // 필요에 따라 주석 해제// 필요에 따라 추가
      },
      '/logout': {
        target: 'http://localhost:8888',
      },
    },
  },
})
