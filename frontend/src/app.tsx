import { defineApp } from "umi";
import { ConfigProvider, theme } from "antd";

export default defineApp({
  rootContainer: (container) => {
    return (
      <ConfigProvider
        theme={{
          algorithm: theme.darkAlgorithm,
          token: {
            colorPrimary: "orange",
          },
          components: {
            Card: {
              headerBg: "orange",
            },
            Progress: {
              defaultColor: "orange",
            },
          },
        }}
      >
        {container}
      </ConfigProvider>
    );
  },
});
