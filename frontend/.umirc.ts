import { defineConfig } from "umi";

export default defineConfig({
  routes: [
    {
      path: "/",
      redirect: "/dashboard",
    },
    {
      name: "登录",
      path: "/login",
      component: "./login",
      hideInMenu: true,
      layout: false,
    },
    {
      path: "/",
      component: "@/layouts/management",
      layout: false,
      routes: [
        {
          name: "状态监控",
          path: "/dashboard",
          component: "./dashboard",
        },
      ],
    },
  ],
  history: {
    type: "hash",
  },
  proxy: {
    "/api": {
      target: "http://127.0.0.1:5000/",
      changeOrigin: true,
      pathRewrite: { "^/api": "" },
    },
  },
  npmClient: "yarn",
});
