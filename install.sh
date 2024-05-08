#!/usr/bin/env bash
echo '==================1.开发环境准备================================'
echo '1.1请确保安装并java8, git, maven并设置好环境变量'
echo '1.2请确保安装并docker,docker-compose并设置好环境变量'

#确认环境信息准备就绪
read -r -p "开发环境准备好了吗? [Y/n] " envConfirm
case $envConfirm in
    [yY][eE][sS]|[yY])
		echo "Yes 继续执行"
		;;
    [nN][oO]|[nN])
		echo "No 终止执行"
		exit 1
       	;;
    *)
		echo "Invalid input... 终止执行"
		exit 1
		;;
esac

echo '==================1.3清理当前脚本启动的容器和产生的镜像(可选的)=============='
#清理当前脚本启动的容器和产生的镜像(可选的)
#docker stop sc-rabbitmq sc-redis sc-mysql
#docker rm sc-rabbitmq sc-redis sc-mysql
#docker image rm rabbitmq:alpine redis:alpine mysql:9.6-alpine

docker stop sc-monitor-admin sc-authorization-server sc-authentication-server sc-organization sc-gateway-admin sc-gateway-web sc-nsclc-server
docker rm sc-monitor-admin sc-authorization-server sc-authentication-server sc-organization sc-gateway-admin sc-gateway-web
#docker image rm cike/admin cike/authorization-server:latest cike/authentication-server:latest cike/organization:latest cike/gateway-admin:latest cike/gateway-web:latest
mvn install -Dmaven.test.skip=true
mvn -pl ./common/core,./common/web,./facade,./auth/authentication-client install -Dmaven.test.skip=true

echo '==================2.安装认证公共包到本地maven仓库=================='
#安装认证公共包到本地maven仓库
cd common && mvn install -D maven.test.skip=true
echo '当前目录:' && pwd

#回到根目录
cd -

echo '==================3.安装认证客户端到本地maven仓库=================='
#安装认证客户端到本地maven仓库
cd auth/authentication-client && mvn install -U -D maven.test.skip=true
echo '当前目录:' && pwd

#回到根目录
cd -

echo '==================4.docker-compose启动公共服务==================='
#去docker-compose目录
cd docker-compose
echo '==================4.1显示环境变量: docker-compose/.env =========='
#显示环境变量
cat ./.env
echo ''

#按需要开启公共服务
echo '==================4.2启动 mysql or redis or rabbitmq ========'
docker-compose -f docker-compose.yml up -d mysql
docker-compose -f docker-compose.yml up -d redis
docker-compose -f docker-compose.yml up -d rabbitmq

echo '当前目录:' && pwd

#回到根目录
cd -

echo '==================4.3.构建镜像: 配置中心, 消息中心========'

#构建镜像:消息中心
cd ./center/bus
mvn package -U -D maven.test.skip=true && mvn docker:build -U -D maven.test.skip=true

#回到根目录
cd -

echo '==================4.4.启动注册中心, 配置中心, 消息中心============'
#去docker-compose目录
cd docker-compose

#启动注册中心
docker-compose -f docker-compose.yml -f docker-compose.nacos.yml up -d nacos

#回到根目录
cd -

echo '==================5.构建镜像并启动网关(gateway)相关服务==============='
#构建镜像:网关服务
echo '====构建镜像:网关服务===='
cd ./gateway/gateway-web
mvn package -U -D maven.test.skip=true && mvn docker:build -U -D maven.test.skip=true

#回到根目录
cd -

#构建镜像:网关管理服务
echo '====构建镜像:网关管理服务===='
cd ./gateway/gateway-admin
mvn package -U -D maven.test.skip=true && mvn docker:build -U -D maven.test.skip=true

#确认初始化网关服务的DB:./gateway/gateway-admin/src/main/db
#echo '你可以立即去部署网关服务的DB(脚本路径:./gateway/gateway-admin/src/main/db),然后回来继续...'
#read -r -p "确认网关服务的DB部署好了吗? [Y/n] " gwDbConfirm
#case $gwDbConfirm in
#    [yY][eE][sS]|[yY])
#		echo "Yes 继续执行"
#		;;
#    [nN][oO]|[nN])
#		echo "No 终止执行"
#		exit 1
#       	;;
#    *)
#		echo "Invalid input... 终止执行"
#		exit 1
#		;;
#esac

#回到根目录
cd -

#去docker-compose目录
cd docker-compose

#启动网关服务
docker-compose -f docker-compose.yml -f docker-compose.spring-gateway.yml up -d gateway-web

#启动网关管理服务
docker-compose -f docker-compose.yml -f docker-compose.spring-gateway.yml up -d gateway-admin

#回到根目录
cd -

echo '==================6.构建镜像并启动组织(organization)相关服务=================='
#构建镜像:组织服务
cd ./sysadmin/organization
mvn package -U -D maven.test.skip=true && mvn docker:build -U -D maven.test.skip=true

#确认初始化授权/认证服务的DB:./sysadmin/db
#echo '你可以立即去部署组织服务的DB(脚本路径:./sysadmin/db),然后回来继续...'
#read -r -p "确认部署组织服务的DB部署好了吗? [Y/n] " orgDbConfirm
#case $orgDbConfirm in
#    [yY][eE][sS]|[yY])
#		echo "Yes 继续执行"
#		;;
#    [nN][oO]|[nN])
#		echo "No 终止执行"
#		exit 1
#       	;;
#    *)
#		echo "Invalid input... 终止执行"
#		exit 1
#		;;
#esac

#回到根目录
cd -

#去docker-compose目录
cd docker-compose

#启动组织服务
docker-compose -f docker-compose.yml -f docker-compose.auth.yml up -d organization

#回到根目录
cd -

echo '==================7.构建镜像并启动认证(auth)相关服务=================='
#构建镜像:认证服务
cd ./auth/authentication-server
mvn package -U -D maven.test.skip=true && mvn docker:build -U -D maven.test.skip=true

#回到根目录
cd -

#构建镜像:授权服务
cd ./auth/authorization-server
mvn package -U -D maven.test.skip=true && mvn docker:build -U -D maven.test.skip=true

#确认初始化授权/认证服务的DB:./auth/db
#echo '你可以立即去部署授权/认证服务的DB(脚本路径:./auth/db),然后回来继续...'
#read -r -p "确认部署授权/认证服务的DB部署好了吗? [Y/n] " authDbConfirm
#case $authDbConfirm in
#    [yY][eE][sS]|[yY])
#		echo "Yes 继续执行"
#		;;
#    [nN][oO]|[nN])
#		echo "No 终止执行"
#		exit 1
#       	;;
#    *)
#		echo "Invalid input... 终止执行"
#		exit 1
#		;;
#esac

#回到根目录
cd -

#去docker-compose目录
cd docker-compose

#启动网关服务
docker-compose -f docker-compose.yml -f docker-compose.auth.yml up -d authorization-server

#启动网关管理服务
docker-compose -f docker-compose.yml -f docker-compose.auth.yml up -d authentication-server

#回到根目录
cd -

echo '==================8.构建镜像并启动监控(monitor)相关服务==============='
#构建镜像:管理台服务
cd ./monitor/admin
mvn package -U -D maven.test.skip=true && mvn docker:build -U -D maven.test.skip=true

#回到根目录
cd -

#去docker-compose目录
cd docker-compose

#启动网关服务
docker-compose -f docker-compose.yml -f docker-compose.monitor.yml up -d monitor-admin

#回到根目录
cd -

echo '==================9.构建镜像并启动非小细胞肺癌应用(nsclc)相关服务==============='

cd ./nsclc-service/nsclc

mvn package -U -D maven.test.skip=true && mvn docker:build -U -D maven.test.skip=true

cd -

cd docker-compose/application

docker-compose -f ../docker-compose.yml -f docker-compose.app.nsclc.yml up -d nsclc

cd -
echo '代码发布上线了，你不想亲自进行发布验证吗，你不会是以为没有隐藏的bug吧？'


echo '==================10.构建镜像并启动甲病诊断应用(nail)相关服务==============='

cd ./nail-service/nail

mvn package -U -D maven.test.skip=true && mvn docker:build -U -D maven.test.skip=true

cd -

cd docker-compose/application

docker-compose -f ../docker-compose.yml -f docker-compose.app.nail.yml up -d nail

cd -

echo '==================10.构建镜像并启动甲病诊断接口(NailApp)相关服务==============='

cd ..

cd NailApp/AppTest

# docker-compose -f docker-compose.yml up -d app
docker-compose up -d --build

cd -

cd SpringCloud-master