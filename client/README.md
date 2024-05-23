# 服务器运维监控 - 客户端程序

## 总体说明

脚本`main.py`运行时，动态获取 CPU、内存、硬盘状况，并给服务端发送心跳。

## 配置文件

程序启动时读取`config.json`配置文件，其中包含硬盘挂载点配置和远程 MQTT Broker 连接信息。

为避免上传敏感信息，该文件不在仓库中上传，以下给出示例。

其中，`disks`字段为实体硬盘的挂载点，用于处理一个服务器多个硬盘的情况。

```json
{
  "machine": "服务器名字，用于区分多服务器",
  "disks": ["/", "/data"],
  "mqtt-host": "MQTT Broker地址",
  "mqtt-username": "MQTT登录用户名",
  "mqtt-password": "MQTT登录密码",
  "mqtt-topic": "MQTT主题"
}
```

## 部署运行

首先需要按照`requirements.txt`安装 Python 依赖，然后推荐使用 Linux 系统的`Crontab`设置定时任务

```crontab
* * * * * /path/to/python /path/to/main.py
```
