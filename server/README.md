# 服务器运维监控 - 服务端程序

## 总体说明

服务端程序通过 Flask 框架监听 HTTP 请求，为 Web 端服务；同时监听 MQTT 消息，接收各环境的心跳，维护数据库。

## 配置文件

程序启动时读取`config.json`配置文件，其中包含程序密钥及各类中间件的连接信息。

为避免上传敏感信息，该文件不在仓库中上传，以下给出示例。

```json
{
  "app-key": "用于数据库敏感信息加密",
  "mqtt-host": "MQTT Broker地址",
  "mqtt-username": "MQTT登录用户名",
  "mqtt-password": "MQTT登录密码"
}
```
