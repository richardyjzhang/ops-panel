import os
import json
import psutil
import paho.mqtt.client as mqtt

# 读取配置文件
current_dir = os.path.dirname(__file__)
config_path = os.path.join(current_dir, 'config.json')
with open(config_path, 'r', encoding='utf-8') as config_file:
    config = json.load(config_file)

# 计算CPU、内存
cpu_percent = psutil.cpu_percent()
mem_percent = psutil.virtual_memory().percent

# 依次计算各硬盘占用率
disk_percent = []
for disk in config["disks"]:
    disk_percent.append({
        "mount": disk,
        "usage": psutil.disk_usage(disk).percent
    })

# 组装为上报的JSON
message = {
    "machine": config["machine"],
    "cpu": cpu_percent,
    "mem": mem_percent,
    "disk": disk_percent
}
message = json.dumps(message, ensure_ascii=False)

# 通过MQTT上报
client = mqtt.Client(client_id=f'OPS-PANEL-{config["machine"]}',
                     clean_session=True)
client.username_pw_set(username=config["mqtt-username"],
                       password=config["mqtt-password"])
client.connect(host=config["mqtt-host"])
client.publish(config["mqtt-topic"], message)
