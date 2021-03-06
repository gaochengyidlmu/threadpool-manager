# THREAD POOL MANAGER
#### 吹嘘的
一个分布式、高性能的动态线程池管理工具 Baiji，能够无侵入的对第三方线程池进行统计、监控、动态修改。
采用 CS 架构，提供 spring boot starter，极简配置快速插入已有项目。
极少外部依赖，内部实现 CP 模式的分布式 KV 内存数据库，能够以此提供命名服务、配置管理、分布式锁等。  

#### 实际
这是一个学习性质的项目，是为了检测最近一年对 Java 的学习情况，也是受周志明老师 [fenix 项目](https://github.com/fenixsoft/awesome-fenix)的影响，
希望能够通过一个项目将自己所学融汇贯通。因此这个项目中的功能，能自己实现就自己实现，尽管可能会存在性能、通用性问题o(╯□╰)o。  
这个项目会尝试融入分布式的核心知识，比如 Raft 算法的实现、服务发现与治理、配置中心、负载均衡、RPC、持久化、缓存。对于常见的算法，也会尽量自行实现后融入其中，比如 LRU、快排等。
同时也会尽量应用各种设计模式，并讲解清楚何时应该应用设计模式及相关好处。  

## Features List
- [ ] 实时查询线程池的运行状态
- [ ] 动态修改线程池的核心参数
- [x] 对业务线程池无侵入性
- [x] 对第三方线程池进行监控
- [x] 持久化
- [ ] 控制台页面
- [ ] 支持多语言客户端
- [ ] 命名服务
- [ ] 配置中心
- [x] spring-boot-starter

## 架构
![架构图](./doc/线程池管理器架构.svg)  
server：服务端，提供更新、持久化线程池配置信息的相关功能  
client：客户端，在应用服务中运行，提供获取、更新本地线程池配置信息功能  
name server：服务端向命名服务注册自身信息，并通过心跳保活；客户端通过命名服务获取服务端地址  
配置中心：提供动态的配置信息  

## 设计思路
### 监控
在 client 端，通过 ThreadPoolExecutor 提供的接口获取线程池信息，获取不到的数据通过反射获取。  
启动一个 Schedule 服务，定时向 server 端推送消息。  
推送消息时，可以做缓冲，减少对 server 端的压力。    
也可以通过长连接、连接池的方式，使用 rpc 进行消息的上报。  

### 动态修改
修改完成后，需要保证即时生效，相关方案有：
1. 短轮询
2. 长轮询
3. 长连接
  
短轮询频率不好把控，间隔长会导致更新不及时，间隔短会导致服务器压力大，大量 cpu 时间耗费在请求解析上。  
长轮询，实现简单、对服务端压力小，参考 RocketMQ 的长轮询方案。  
长连接，需要池化，整体实现会复杂一些，但是可以复用连接，比如监控信息的上报

### 分布式 KV 内存数据库
CAP 中选择 CP 模式，对 CAP 理论 P 是必选的，剩下 C 和 A，因为希望能够提供一个类似 zookeeper 的配置中心，所以选择 CP 模式。
基于这个 KV 内存数据库，可以实现配置中心功能。依托于配置功能，可以提供服务注册与发现。  
应用基于该数据库的分布式一致性，可以实现分布式锁。

### 持久化
server 与 name-server 的配置信息、监控信息会持久化，目前通过数据库进行持久化。  
可以考虑存储双份，最近半个月的数据存储在数据库，全量数据存储在 log 日志，通过 mmap 和 nio 进行文件末尾数据插入。  
也可以考虑将数据全存在 log 日志，参考 rocketmq 的 commitlog 与 indexfile 实现，对数据进行搜索。

### RPC
涉及到序列化、反序列化，请求的方式等。

### 负载均衡
1. 顺序
2. 随机
3. 比例
4. 公平

### 缓存
自定义缓存注解 Cached EvictCache，通过 CacheAspect 织入缓存读、写、无效的操作，保证对业务无侵入性，并实现 SpEL 解析。
```java
public interface ThreadPoolConfigMapper {
  @Cached(namespace = "#applicationName", key = "#threadPoolName")
  @Select("SELECT * FROM baiji_tp_config WHERE application_name = #{applicationName} AND thread_pool_name = #{threadPoolName} LIMIT 0, 1")
  ThreadPoolConfigEntity selectOneByApplicationNameAndThreadPoolName(
      @Param("applicationName") String applicationName,
      @Param("threadPoolName") String threadPoolName);

  void insert(ThreadPoolConfigEntity entity);

  @EvictCache(namespace = "#entity.applicationName", key = "#entity.threadPoolName")
  void update(ThreadPoolConfigEntity entity);
}
```

## 设计模式
### 单例模式

### 模板模式

### 代理模式

### 工厂模式

### 适配器模式

### 建造者模式

### 责任链模式

