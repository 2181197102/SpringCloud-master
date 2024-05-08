FROM debian



RUN apt-get -y update && apt-get -y upgrade

#
## 安装 Python 3 和相关依赖
RUN apt-get install -y python3
RUN apt-get install -y python3-pip
# 安装java11
RUN apt-get install -y default-jdk

# 验证 Python 和 Java 安装
RUN java -version && \
    python3 --version

#安装依赖包
RUN pip3 install thrift pyradiomics scikit-learn joblib numpy pandas SimpleITK openpyxl

# 设置工作目录docker
WORKDIR /app
RUN mkdir /app/server
RUN mkdir /app/server/logs
RUN mkdir /app/server/datas
# java服务
ADD nsclc-0.0.1-SNAPSHOT.jar app.jar

# python 模型服务
COPY ./server /app/server

#执行命令，因为多个CMD只会认准最后一个，所以需要组合两个启动程序
COPY start.sh /app/start.sh
RUN chmod +x start.sh

# 正式启动服务
CMD ["/app/start.sh"]
