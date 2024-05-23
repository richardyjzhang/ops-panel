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
                    <Col span={16}>
                      <Progress percent={p.cpu} />
                    </Col>
                  </Row>
                  <Row className={styles.cardRow}>
                    <Col span={8}>内存占用率</Col>
                    <Col span={16}>
                      <Progress percent={p.mem} />
                    </Col>
                  </Row>
                  {p.disk.map((d) => (
                    <Row className={styles.cardRow}>
                      <Col span={8}>{`硬盘 ${d.mount} 使用率`}</Col>
                      <Col span={16}>
                        <Progress percent={d.usage} />
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
