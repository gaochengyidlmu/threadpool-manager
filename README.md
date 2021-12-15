# THREAD POOL MANAGER
一个分布式、高性能(目标)的动态线程池管理工具，使用 CS 架构，自提供 name-server 服务。

## Features
- [ ] 实时查询线程池的运行状态
- [ ] 动态修改线程池的核心参数
- [ ] 对业务线程池无侵入性
- [x] 对第三方线程池进行监控
- [x] 持久化
- [ ] 控制台页面
- [ ] 支持多语言客户端

## 架构
server：服务端，提供配置获取、更新、持久化相关功能  
client：客户端，在应用服务中运行，提供获取、更新本地线程池配置信息功能  
name-server：命名服务，对每个 client、server 执行注册、命名、发现服务  
console：查看控制台 Vue  
baiji-spring-boot-starter: 为 Spring Boot 提供的 starter 服务  

## 设计思路
### 监控
在 client 端，通过 ThreadPoolExecutor 提供的接口获取线程池信息，获取不到的数据通过反射获取。  
启动一个 Schedule 服务，定时向 server 端推送消息。  
推送消息时，可以做缓冲，减少对 server 端的压力。  

### 动态修改
修改完成后，需要保证即时生效，相关方案有：
1. 短轮询
2. 长轮询
3. WebSocket  
选择长轮询，简单、对服务端压力小，参考 RocketMQ 的长轮询方案。

### 服务发现与治理
可以考虑将服务接入 Euraka，也可以自己实现一个简版注册中心，毕竟要维护的内容很少。  
此处可以参考 Zookeeper，实现一个一致性算法。

### 持久化
server 与 name-server 的配置信息、监控信息会持久化，目前通过数据库进行持久化。  
可以考虑存储双份，最近半个月的数据存储在数据库，全量数据存储在 log 日志，通过 mmap 和 nio 进行文件末尾数据插入。  
此处文件序列化方式可以参考 RocketMQ 的 CommitLog 实现。
