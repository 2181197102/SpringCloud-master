import multiprocessing

# 默认'127.0.0.1:8000'，防火墙需开启对应端口
bind = '0.0.0.0:5000'

# 并发进程数，默认1
workers = multiprocessing.cpu_count() * 2 + 1

# 允许挂起的连接数最大值，默认2048
backlog = 2048

# 进程的工作方式，默认'sync'
worker_class = 'gevent'

# 最大客户客户端并发数量,对使用线程和协程的worker的工作有影响
#worker_connections = 1200

# 调试模式，默认False
#Debugging = True

# 进程名
proc_name = 'gunicorn.proc'


# pid文件，如果不设置将不会创建
pidfile = '/tmp/gunicorn.pid'

# 访问记录
accesslog = '-'
# 访问记录格式
# access_log_format = '%(h)s %(t)s %(U)s %(q)s'

# 日志文件
errorlog = '-'

# 错误日志输出等级,默认'info'
#loglevel = 'debug'
