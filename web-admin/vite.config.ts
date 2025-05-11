import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-swc'
import { visualizer } from 'rollup-plugin-visualizer';
import tailwindcss from "@tailwindcss/vite";
import autoprefixer from 'autoprefixer';
import path from 'path'
// https://vite.dev/config/
export default defineConfig({
  base: './', // 设置为相对路径
  plugins: [
    tailwindcss(),
    react(),
    visualizer({
      open: true, // 打包完成后自动打开分析报告
    }),
  ],
  server: {
    port: 3000,
    proxy: {
      '/admin': {
        target: 'http://localhost:8080', // 后端服务器地址
        changeOrigin: true, // 修改请求头中的 Origin
        // rewrite: (path) => path.replace(/^\/admin/, ''), // 去掉 /api 前缀
      },
    },
  },
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src'), // 配置路径别名
    },
  },
  css: {
    postcss: {
      plugins: [
        autoprefixer, // 使用 Autoprefixer 插件
      ],
    },
  },
})
