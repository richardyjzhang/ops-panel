import os
import json
from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from flask_login import LoginManager

current_dir = os.path.dirname(__file__)
with open(os.path.join(current_dir, 'config.json'), 'r') as config_file:
    config = json.load(config_file)

db = SQLAlchemy()


def create_app():
    app = Flask(__name__)
    app.config["JSON_AS_ASCII"] = False
    app.config["SECRET_KEY"] = 'my-intelligence'
    app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///db.sqlite'

    db.init_app(app)

    login_manager = LoginManager()
    login_manager.init_app(app)

    from .models import User

    @login_manager.user_loader
    def load_user(user_id):
        return User.query.get(int(user_id))

    from .blueprints.auth import auth as auth_blueprint
    app.register_blueprint(auth_blueprint)

    from .blueprints.status import status as status_blueprint
    app.register_blueprint(status_blueprint)

    # 仅用于初始添加用户，后续应注释以下两行
    # from .blueprints.user import user as user_blueprint
    # app.register_blueprint(user_blueprint)

    from .subscriber import init_subscriber
    init_subscriber(app)

    return app
