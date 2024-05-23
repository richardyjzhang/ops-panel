from flask_login import UserMixin
from sqlalchemy.orm import Mapped, mapped_column

from . import db


# 封装SQLAlchemy的Model
class Base:

    def to_dict(self):
        model_dict = dict(self.__dict__)
        del model_dict['_sa_instance_state']
        return model_dict


# 用户类，用于登录
class User(UserMixin, db.Model, Base):
    id: Mapped[int] = mapped_column(primary_key=True)
    username: Mapped[str] = mapped_column()
    password: Mapped[str] = mapped_column()


# 机器类，表示一个服务器的环境
class Machine(db.Model, Base):
    name: Mapped[str] = mapped_column(primary_key=True)


# 机器状态
class MachineStatus(db.Model, Base):
    name: Mapped[str] = mapped_column(primary_key=True)
    cpu: Mapped[float] = mapped_column()
    mem: Mapped[float] = mapped_column()
    last: Mapped[str] = mapped_column()


# 机器磁盘状态，和机器形成一对多关系
class MachineDiskStatus(db.Model, Base):
    machine_name: Mapped[str] = mapped_column(primary_key=True)
    mount: Mapped[str] = mapped_column(primary_key=True)
    usage: Mapped[float] = mapped_column()
