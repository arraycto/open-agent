
[![Build Status](https://travis-ci.org/zhoutaoo/SpringCloud.svg?branch=master)](https://travis-ci.org/zhoutaoo/SpringCloud)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![codecov](https://codecov.io/gh/zhoutaoo/SpringCloud/branch/master/graph/badge.svg)](https://codecov.io/gh/zhoutaoo/SpringCloud)

## 快速开始
功能：自动化运维服务，自己写的玩的，没有在生产部署、无测试数据
RPC：[参考](https://github.com/luxiaoxun/NettyRpc)
### 先决条件

首先本机先要安装以下环境。

- [git](https://git-scm.com/)
- [java8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) 
- [maven](http://maven.apache.org/) 

### 开发环境搭建

linux和mac下可在项目根目录下执行 `./install.sh` 快速搭建开发环境。如要了解具体的步骤，请看如下文档。


**具体步骤如下：**

 
### 编译 & 启动

* 1.启动基础服务 

|  服务           |   服务名         |  端口      | 备注                                            |
|----------------|-----------------|-----------|-------------------------------------------------|
|  agent-channel |   管道服务        |  18866    |  转发命令、连接探针的服务，rpc的服务端点，配置文件server.properties  |
|  agent-proxy   |   代理服务        |  8080     |  代理服务，提供前端接口 ，springboot服务  |
|  agent-star    |   探针服务        |           |  探针服务，执行具体命令，配置文件client.properties |
```text
Agent安装在每台机器上，与channel建立长连接，然后channel定期把连接自己的agent信息上报到中心，
中心会维护完整的agent与channel关系数据。分享两个流程：

1.Agent注册

Agent有一个默认配置文件，启动后首先连接ZooKeeper，
连接时会上报本机的IP、SN等必要信息，ZooKeeper计算出应该连哪个channel集群，
返回给channel列表。

2.下发命令

外部系统都是调用proxy来下发命令，proxy收到请求后会根据目标机器查出对应channel，
然后把任务下发给channel，channel再把命令转发到agent去执行。
```

架构图：
<p align="left">
  <img src="https://github.com/767248371/open-agent/blob/master/picture/frame.png">
</p>

理想场景：
<p align="left">
  <img src="https://github.com/767248371/open-agent/blob/master/picture/scence.png">
</p>

* 2.创建数据库及表

定时任务数据库：参见scheduler-*.sql

**子项目脚本**

路径一般为：子项目/db

如：`auth/db` 下的脚本，请先执行ddl建立表结构后再执行dml数据初使化

**应用脚本**
参见：service.sh，请自行更改

* 3.案例示意图

<p align="left">
  <img src="https://github.com/767248371/open-agent/blob/master/picture/cmd_1.png">
</p>
<p align="left">
  <img  src="https://github.com/767248371/open-agent/blob/master/picture/cmd_2.png">
</p>
<p align="left">
  <img src="https://github.com/767248371/open-agent/blob/master/picture/日志.png">
</p>
<p align="left">
  <img  src="https://github.com/767248371/open-agent/blob/master/picture/详情.png">
</p>
* 6.前端项目
vue写的
待完善

* 7.我的目标
```text
资产核对：服务器上架后会设置为网络启动，然后会加载一个mini的OS在内存中运行，这个OS中就已经包含了我们的Agent，OS启动后就可以下发指令来采集服务器的硬件信息做资产核对，如CPU、内存、磁盘的厂商及大小信息等。
OS安装：交付业务前会先安装OS，安装什么样的OS也是向这个内存中的Agent下发命令实现的。
环境配置：OS安装完成后像机器上的账号、通用运维脚本、定时任务等基础环境的初始化。
应用发布：应用配置与软件包的上线发布。
运行监控：上线后应用与业务的监控脚本、监控Agent的安装。
日常运维：登录服务器、单机、批量等日常运维操作，包括业务下线前的清理工作等。

```

 
## 待完成功能 
```text
❌ 集群压测
❌ 多环境部署
❌ 文档
❌ 线程分配不合理，需要根据具体问题具体分析
❌ 定时任务暂停ping的功能
👌 watchDog无法停止
👌 Channel重启，Proxy CommandFactory会阻塞
👌 心跳更新redis客户端数据
👌 redis客户端数据需要加入过期时间
👌 zookeeper认证

```