# 使用官方的PyTorch镜像作为基础镜像
#FROM pytorch/pytorch:latest
From pytorch/pytorch:1.6.0-cuda10.1-cudnn7-runtime
# 将训练脚本、app.py和其他必要的文件复制到容器中
WORKDIR /app
# 复制文件
COPY ./app.py /app/app.py
COPY ./model.pth /app/model.pth
COPY ./vit_model_adapter.py /app/vit_model_adapter.py
# 安装Flask应用程序所需的任何依赖项
RUN pip install Flask gunicorn

# 暴露Flask应用程序将运行的端口
EXPOSE 5000

# 运行Flask应用程序的命令
CMD ["python", "app.py","gunicorn", "-c", "gunicorn_config.py"]

