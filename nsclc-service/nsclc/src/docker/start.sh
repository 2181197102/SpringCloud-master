#!/bin/bash

# 必须先启动第一个python服务再启动第二个java服务
nohup python3 /app/server/py_server.py > /app/server/logs/nsclc.log 2>&1 &

java -jar /app/app.jar
