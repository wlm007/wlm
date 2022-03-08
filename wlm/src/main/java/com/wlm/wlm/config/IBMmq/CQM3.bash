#! /bin/bash
QmgrName=CQM3
#创建队列管理器
crtmqm $QmgrName
#启动队列管理器
strmqm $QmgrName
#定义一个服务器连接通道
echo "DEFINE CHANNEL(TESTCHANNEL) CHLTYPE(SVRCONN) TRPTYPE(TCP) MCAUSER('mqm')" | runmqsc $QmgrName
#修改默认监听端口为1415（本人1414被占，不是必须修改，注：若不修改请注意自行修改后续脚本中的端口）
echo "DEFINE LISTENER(LISTENER.TCP) TRPTYPE(TCP) PORT(1415)" | runmqsc $QmgrName
#队列管理器权限，禁用认证
echo "SET CHLAUTH(*) TYPE(BLOCKUSER) USERLIST(*MQADMIN) ACTION(REMOVE)" | runmqsc $QmgrName
echo "SET CHLAUTH(SYSTEM.ADMIN.SVRCONN) TYPE(ADDRESSMAP) ADDRESS(*) ACTION(REMOVE)" | runmqsc $QmgrName
echo "SET CHLAUTH(SYSTEM.*) TYPE(ADDRESSMAP) ADDRESS(*) ACTION(REMOVE)" | runmqsc $QmgrName
echo "ALTER AUTHINFO(SYSTEM.DEFAULT.AUTHINFO.IDPWOS) AUTHTYPE(IDPWOS) CHCKCLNT(OPTIONAL)" | runmqsc $QmgrName
#启动监听器
echo "START LISTENER(LISTENER.TCP)" | runmqsc $QmgrName
#禁用通道验证
echo "alter qmgr chlauth(disabled)" | runmqsc $QmgrName
#刷新安全策略
echo "REFRESH SECURITY TYPE(CONNAUTH)" | runmqsc $QmgrName
#定义集群（MYClUSTER）接收通道
echo "DEFINE CHANNEL(TO.CQM3) CHLTYPE(CLUSRCVR) TRPTYPE(TCP) CONNAME('192.168.137.13(1415)') CLUSTER(MYClUSTER)" | runmqsc $QmgrName
#定义集群（MYClUSTER）发送通道
echo "DEFINE CHANNEL(TO.CQM1) CHLTYPE(CLUSSDR) TRPTYPE(TCP) CONNAME('192.168.137.11(1415)') CLUSTER(MYClUSTER)" | runmqsc $QmgrName
#定义集群共享队列
echo "DEFINE QLOCAL(CQ1) CLUSTER(MYClUSTER)" | runmqsc $QmgrName