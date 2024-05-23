from flask import Blueprint
from flask_login import login_required

from ..models import MachineStatus, MachineDiskStatus

status = Blueprint('status', __name__)


# 查询所有环境的运行情况
@status.route('/status', methods=['GET'])
@login_required
def list_status():

    # 分别获取运行状态和硬盘信息
    machine_status = MachineStatus.query.all()
    disk_status = MachineDiskStatus.query.all()

    # 将磁盘信息补充到对应的环境信息中
    name2status = {}
    for m in machine_status:
        name2status[m.name] = m.to_dict()
        name2status[m.name]['disk'] = []
    for d in disk_status:
        if d.machine_name in name2status:
            name2status[d.machine_name]['disk'].append(d.to_dict())

    # 形成JSON列表返回
    return list(name2status.values()), 200
