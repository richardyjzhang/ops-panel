import React, { useEffect } from "react";
import { useRequest } from "ahooks";
import dayjs from "dayjs";
import { Card, Row, Col, Progress, ConfigProvider, theme } from "antd";
import { WifiOutlined, DisconnectOutlined } from "@ant-design/icons";
import { fetchStatusRequest } from "@/service";
import styles from "./index.less";

const DashboardPage: React.FC = () => {
  const { runAsync: fetchStatus, data: states } = useRequest(
    fetchStatusRequest,
    {
      manual: true,
    }
  );

  useEffect(() => {
    fetchStatus();
    const id = setInterval(() => {
      fetchStatus();
    }, 5000);
    return () => {
      clearInterval(id);
    };
  }, []);

  // 进度条配色
  const strokeColor = {
    "0%": "#FFA500",
    "20%": "#FFA500",
    "50%": "#FF7F50",
    "100%": "#FF0000",
  };

  // 格式化数字
  const formatNumber = (num: number) => {
    if (num < 10) {
      return `${num.toFixed(2)} %`;
    } else if (num < 100) {
      return `${num.toFixed(1)} %`;
    } else {
      return "100 %";
    }
  };

  // 判断是否离线
  const isOffline = (machineState: API.MachineState) => {
    const last = dayjs(machineState.last, "YYYY-MM-DD HH:mm:ss");
    const now = dayjs();
    const delta = now.diff(last);
    return delta > 1000 * 60 * 5;
  };

  // 判断是否值得注意
  const isAlarming = (machineState: API.MachineState) => {
    if (isOffline(machineState)) {
      return true;
    }

    if (machineState.cpu > 90 || machineState.mem > 90) {
      return true;
    }

    for (let i = 0; i < machineState.disk.length; ++i) {
      const disk = machineState.disk[i];
      if (disk.usage > 90) {
        return true;
      }
    }

    return false;
  };

  return (
    <div className={styles.root}>
      <Row gutter={[12, 12]} justify="start">
        {states?.map((m: API.MachineState) => {
          return (
            <Col span={6} className={styles.col}>
              <ConfigProvider
                theme={{
                  components: {
                    Card: {
                      headerBg: isAlarming(m) ? "#FF0000" : "orange",
                    },
                  },
                }}
              >
                <Card
                  className={
                    isAlarming(m) ? styles.cardAlarming : styles.cardNormal
                  }
                  key={m.name}
                  title={m.name}
                  bordered={false}
                  extra={
                    isOffline(m) ? (
                      <DisconnectOutlined className={styles.cardIconError} />
                    ) : (
                      <WifiOutlined className={styles.cardIconOK} />
                    )
                  }
                >
                  <div className={styles.cardContent}>
                    <Row className={styles.cardRow}>
                      <Col span={8}>最近心跳</Col>
                      <Col span={16}>{m.last}</Col>
                    </Row>
                    <Row className={styles.cardRow}>
                      <Col span={8}>CPU占用率</Col>
                      <Col span={16} className={styles.progress}>
                        <Progress
                          strokeColor={strokeColor}
                          percent={m.cpu}
                          showInfo={false}
                        />
                        <div className={styles.progressNum}>
                          {formatNumber(m.cpu)}
                        </div>
                      </Col>
                    </Row>
                    <Row className={styles.cardRow}>
                      <Col span={8}>内存占用率</Col>
                      <Col span={16} className={styles.progress}>
                        <Progress
                          strokeColor={strokeColor}
                          percent={m.mem}
                          showInfo={false}
                        />
                        <div className={styles.progressNum}>
                          {formatNumber(m.mem)}
                        </div>
                      </Col>
                    </Row>
                    {m.disk.map((d) => (
                      <Row key={d.mount} className={styles.cardRow}>
                        <Col span={8}>{`${d.mount} 占用率`}</Col>
                        <Col span={16} className={styles.progress}>
                          <Progress
                            strokeColor={strokeColor}
                            percent={d.usage}
                            showInfo={false}
                          />
                          <div className={styles.progressNum}>
                            {formatNumber(d.usage)}
                          </div>
                        </Col>
                      </Row>
                    ))}
                  </div>
                </Card>
              </ConfigProvider>
            </Col>
          );
        })}
      </Row>
    </div>
  );
};

export default DashboardPage;
