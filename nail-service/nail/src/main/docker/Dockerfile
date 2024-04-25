FROM java:8u111-jre-alpine

# 设置工作目录
WORKDIR /app
RUN mkdir /app/server
RUN mkdir /app/server/logs
RUN mkdir /app/server/datas
# java服务
ADD nail-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]

