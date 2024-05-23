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

    return app
