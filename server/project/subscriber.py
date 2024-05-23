import json
import datetime
import paho.mqtt.client as mqtt

from . import config, db
from .models import Machine, MachineStatus, MachineDiskStatus


# 维护数据库中的服务器状态
def update_status(app, message):
    message = json.loads(message)

    try:
        name = message['machine']
        cpu = message['cpu']
        mem = message['mem']
        disk = message['disk']
        time = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')

        with app.app_context():
            # 查找数据库中的机器记录
            machine = Machine.query.filter_by(name=name).first()
            if not machine:
                return

            # 更新CPU和内存状态
            status = MachineStatus.query.filter_by(name=name).first()
            if not status:
                status = MachineStatus(name=name, cpu=cpu, mem=mem, last=time)
                db.session.add(status)
            else:
                status.cpu = cpu
                status.mem = mem
                status.last = time
                status.verified = True

            # 更新磁盘状态
            for d in disk:
                mount = d['mount']
                usage = d['usage']

                disk_status = MachineDiskStatus.query.filter_by(
                    machine_name=name, mount=mount).first()
                if not disk_status:
                    disk_status = MachineDiskStatus(
                        machine_name=name, mount=mount, usage=usage)
                    db.session.add(disk_status)
                else:
                    disk_status.usage = usage
                    disk_status.verified = True

            db.session.commit()
    except Exception as e:
        print('心跳解析错误', message)


# MQTT 连接事件
def on_connect(client, userdata, flags, rc):
    if rc == 0:
        print("Connected to MQTT Broker!")
    else:
        print("Failed to connect, return code %d\n", rc)


# MQTT 消息事件


# 启动MQTT监听
def init_subscriber(app):
    client = mqtt.Client(client_id='OPS-PANEL-SERVER',
                         clean_session=True)

    client.username_pw_set(username=config["mqtt-username"],
                           password=config["mqtt-password"])
    client.on_connect = on_connect
    client.connect(host=config["mqtt-host"])

    # 订阅主题
    def on_message(client, userdata, msg):
        update_status(app, msg.payload.decode('utf-8'))
    client.on_message = on_message
    client.subscribe(config["mqtt-topic"])

    # 开启后台线程
    client.loop_start()
