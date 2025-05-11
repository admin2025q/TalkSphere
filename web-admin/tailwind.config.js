module.exports = {
    content: [
      "./index.html",
      "./src/**/*.{js,ts,jsx,tsx}",  // 适配 React 文件
    ],
    theme: {
      extend: {},
    },
    plugins: [require("daisyui")],
  }
  