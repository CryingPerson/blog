// vite.config.ts
import { fileURLToPath, URL } from "node:url";
import { defineConfig } from "file:///mnt/c/dev/workspace/blog/front/node_modules/vite/dist/node/index.js";
import vue from "file:///mnt/c/dev/workspace/blog/front/node_modules/@vitejs/plugin-vue/dist/index.mjs";
import vueJsx from "file:///mnt/c/dev/workspace/blog/front/node_modules/@vitejs/plugin-vue-jsx/dist/index.mjs";
import vueDevTools from "file:///mnt/c/dev/workspace/blog/front/node_modules/vite-plugin-vue-devtools/dist/vite.mjs";
var __vite_injected_original_import_meta_url = "file:///mnt/c/dev/workspace/blog/front/vite.config.ts";
var vite_config_default = defineConfig({
  plugins: [vue(), vueJsx(), vueDevTools()],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", __vite_injected_original_import_meta_url))
    }
  },
  server: {
    hmr: true,
    // HMR 설정 추가
    watch: {
      // 파일 변경 감지 설정 추가
      usePolling: true,
      // 폴링 방식을 사용하여 변경 사항 감지
      interval: 1e3
      // 폴링 간격 (1초)
    }
  }
});
export {
  vite_config_default as default
};
//# sourceMappingURL=data:application/json;base64,ewogICJ2ZXJzaW9uIjogMywKICAic291cmNlcyI6IFsidml0ZS5jb25maWcudHMiXSwKICAic291cmNlc0NvbnRlbnQiOiBbImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCIvbW50L2MvZGV2L3dvcmtzcGFjZS9ibG9nL2Zyb250XCI7Y29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2ZpbGVuYW1lID0gXCIvbW50L2MvZGV2L3dvcmtzcGFjZS9ibG9nL2Zyb250L3ZpdGUuY29uZmlnLnRzXCI7Y29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2ltcG9ydF9tZXRhX3VybCA9IFwiZmlsZTovLy9tbnQvYy9kZXYvd29ya3NwYWNlL2Jsb2cvZnJvbnQvdml0ZS5jb25maWcudHNcIjtpbXBvcnQgeyBmaWxlVVJMVG9QYXRoLCBVUkwgfSBmcm9tICdub2RlOnVybCdcblxuaW1wb3J0IHsgZGVmaW5lQ29uZmlnIH0gZnJvbSAndml0ZSdcbmltcG9ydCB2dWUgZnJvbSAnQHZpdGVqcy9wbHVnaW4tdnVlJ1xuaW1wb3J0IHZ1ZUpzeCBmcm9tICdAdml0ZWpzL3BsdWdpbi12dWUtanN4J1xuaW1wb3J0IHZ1ZURldlRvb2xzIGZyb20gJ3ZpdGUtcGx1Z2luLXZ1ZS1kZXZ0b29scydcblxuLy8gaHR0cHM6Ly92aXRlanMuZGV2L2NvbmZpZy9cbmV4cG9ydCBkZWZhdWx0IGRlZmluZUNvbmZpZyh7XG4gIHBsdWdpbnM6IFt2dWUoKSwgdnVlSnN4KCksIHZ1ZURldlRvb2xzKCldLFxuICByZXNvbHZlOiB7XG4gICAgYWxpYXM6IHtcbiAgICAgICdAJzogZmlsZVVSTFRvUGF0aChuZXcgVVJMKCcuL3NyYycsIGltcG9ydC5tZXRhLnVybCkpXG4gICAgfVxuICB9LFxuICBzZXJ2ZXI6IHtcbiAgICBobXI6IHRydWUsIC8vIEhNUiBcdUMxMjRcdUM4MTUgXHVDRDk0XHVBQzAwXG4gICAgd2F0Y2g6IHtcbiAgICAgIC8vIFx1RDMwQ1x1Qzc3QyBcdUJDQzBcdUFDQkQgXHVBQzEwXHVDOUMwIFx1QzEyNFx1QzgxNSBcdUNEOTRcdUFDMDBcbiAgICAgIHVzZVBvbGxpbmc6IHRydWUsIC8vIFx1RDNGNFx1QjlDMSBcdUJDMjlcdUMyRERcdUM3NDQgXHVDMEFDXHVDNkE5XHVENTU4XHVDNUVDIFx1QkNDMFx1QUNCRCBcdUMwQUNcdUQ1NkQgXHVBQzEwXHVDOUMwXG4gICAgICBpbnRlcnZhbDogMTAwMCAvLyBcdUQzRjRcdUI5QzEgXHVBQzA0XHVBQ0E5ICgxXHVDRDA4KVxuICAgIH1cbiAgfVxufSlcbiJdLAogICJtYXBwaW5ncyI6ICI7QUFBK1EsU0FBUyxlQUFlLFdBQVc7QUFFbFQsU0FBUyxvQkFBb0I7QUFDN0IsT0FBTyxTQUFTO0FBQ2hCLE9BQU8sWUFBWTtBQUNuQixPQUFPLGlCQUFpQjtBQUw4SSxJQUFNLDJDQUEyQztBQVF2TixJQUFPLHNCQUFRLGFBQWE7QUFBQSxFQUMxQixTQUFTLENBQUMsSUFBSSxHQUFHLE9BQU8sR0FBRyxZQUFZLENBQUM7QUFBQSxFQUN4QyxTQUFTO0FBQUEsSUFDUCxPQUFPO0FBQUEsTUFDTCxLQUFLLGNBQWMsSUFBSSxJQUFJLFNBQVMsd0NBQWUsQ0FBQztBQUFBLElBQ3REO0FBQUEsRUFDRjtBQUFBLEVBQ0EsUUFBUTtBQUFBLElBQ04sS0FBSztBQUFBO0FBQUEsSUFDTCxPQUFPO0FBQUE7QUFBQSxNQUVMLFlBQVk7QUFBQTtBQUFBLE1BQ1osVUFBVTtBQUFBO0FBQUEsSUFDWjtBQUFBLEVBQ0Y7QUFDRixDQUFDOyIsCiAgIm5hbWVzIjogW10KfQo=
