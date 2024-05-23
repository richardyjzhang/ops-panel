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
