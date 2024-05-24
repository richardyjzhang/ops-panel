import React, { useEffect } from "react";
import { useRequest } from "ahooks";
import { Card, Row, Col, Progress } from "antd";
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

  return (
    <div className={styles.root}>
      <Row gutter={[12, 12]} justify="start">
        {states?.map((p: API.MachineState) => {
          return (
            <Col span={6} className={styles.col}>
              <Card
                className={styles.card}
                key={p.name}
                title={p.name}
                bordered={false}
              >
                <div className={styles.cardContent}>
                  <Row className={styles.cardRow}>
                    <Col span={8}>最近心跳💓</Col>
                    <Col span={16}>{p.last}</Col>
                  </Row>
                  <Row className={styles.cardRow}>
                    <Col span={8}>CPU占用率</Col>
                    <Col span={16} className={styles.progress}>
                      <Progress
                        strokeColor={strokeColor}
                        percent={p.cpu}
                        showInfo={false}
                      />
                      <div className={styles.progressNum}>
                        {formatNumber(p.cpu)}
                      </div>
                    </Col>
                  </Row>
                  <Row className={styles.cardRow}>
                    <Col span={8}>内存占用率</Col>
                    <Col span={16} className={styles.progress}>
                      <Progress
                        strokeColor={strokeColor}
                        percent={p.mem}
                        showInfo={false}
                      />
                      <div className={styles.progressNum}>
                        {formatNumber(p.mem)}
                      </div>
                    </Col>
                  </Row>
                  {p.disk.map((d) => (
                    <Row className={styles.cardRow}>
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
            </Col>
          );
        })}
      </Row>
    </div>
  );
};

export default DashboardPage;
