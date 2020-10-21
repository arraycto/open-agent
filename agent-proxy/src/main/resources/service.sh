#!/bin/bash

#这里可替换为你自己的执行程序，其他代码无需更改

JAR_NAME=agent-proxy-1.0-SNAPSHOT-jar-with-dependencies.jar
JAR_PATH=/opt/software/opencloud/proxy
APP_NAME=$JAR_PATH"/"$JAR_NAME

#使用说明，用来提示输入参数
usage() {
  echo "Usage: sh shell-eureka-service.sh [start|stop|restart|status]"
  exit 1
}

#检查程序是否在运行
is_exist() {
  pid=$(ps -ef | grep $APP_NAME | grep -v grep | awk '{print $2}')
  #如果不存在返回1，存在返回0
  if [ -z "${pid}" ]; then
    return 1
  else
    return 0
  fi
}

#启动方法
start() {
  is_exist
  if [ $? -eq 0 ]; then
    echo "${APP_NAME} is already running. pid=${pid}"
  else
    echo "${APP_NAME} running"
    nohup java -jar ${APP_NAME} >/dev/null &
  fi
}
debug() {
  is_exist
  if [ $? -eq 0 ]; then
    echo "${APP_NAME} is already running. pid=${pid}"
  else
    nohup java -jar -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 ${APP_NAME} --spring.profiles.active=dev --server.port=8083 >/dev/null &
  fi
}
#停止方法
stop() {
  is_exist
  if [ $? -eq "0" ]; then
    kill -9 $pid
    echo "${APP_NAME} stopping"
  else
    echo "${APP_NAME} is not running"
  fi
}

#输出运行状态
status() {
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is running. Pid is ${pid}"
  else
    echo "${APP_NAME} is NOT running."
  fi
}
copy() {
  sudo mv $APP_NAME $JAR_PATH/bak/$JAR_NAME"-"$(date '+%Y%m%d_%H.%M.%S')
  sudo mv $JAR_PATH/bak/$JAR_NAME $JAR_PATH
}
#重启
restart() {
  stop
  sleep 5
  start
}
#复制备份重启
restart_copy() {
  stop
  sleep 5
  copy
  sleep 2
  start
}

#根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
"start")
  start
  ;;
"debug")
  debug
  ;;
"stop")
  stop
  ;;
"status")
  status
  ;;
"restart")
  restart
  ;;
"copy")
  copy
  ;;
"restart_copy")
  restart_copy
  ;;
*)
  usage
  ;;
esac
