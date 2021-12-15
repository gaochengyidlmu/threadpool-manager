-- 创建数据库
CREATE DATABASE IF NOT EXISTS `baiji_tp_manager` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
USE `baiji_tp_manager`;

-- 线程池配置表，并不与具体的线程池对应
-- 线程池实例信息 = 线程池配置 + 服务 ip:port
DROP TABLE IF EXISTS `baiji_tp_config`;
CREATE TABLE `baiji_tp_config`
(
    `id`              int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `app_name`        varchar(256) DEFAULT NULL COMMENT '应用名称',
    `tp_name`         varchar(56)  DEFAULT NULL COMMENT '线程池名称',
    `core_size`       int(11)      DEFAULT NULL COMMENT '核心线程数',
    `max_size`        int(11)      DEFAULT NULL COMMENT '最大线程数',
    `queue_type`      varchar(256) DEFAULT NULL COMMENT '队列类型',
    `queue_capacity`  int(11)      DEFAULT NULL COMMENT '队列大小',
    `rejected_type`   varchar(256) DEFAULT NULL COMMENT '拒绝策略',
    `keep_alive_time` int(11)      DEFAULT NULL COMMENT '线程存活时间',
    `created_at`      datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`      datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    INDEX `uk_app_name` (`app_name`, `tp_name`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='线程池配置表';

-- 线程池快照表
DROP TABLE IF EXISTS `baiji_tp_snapshot`;
CREATE TABLE `baiji_tp_snapshot`
(
    `id`                 int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tp_id`              int(11) unsigned NOT NULL COMMENT '线程池配置 id',
    `ip`                 varchar(256) DEFAULT NULL COMMENT '线程池实例服务 ip',
    `port`               int(11)      DEFAULT NULL COMMENT '线程池实例服务 port',
    `active_thread_num`  int(11)      DEFAULT NULL COMMENT '活动线程数',
    `idle_thread_num`    int(11)      DEFAULT NULL COMMENT '空闲线程数',
    `current_task_count` int(11)      DEFAULT NULL COMMENT '当前剩余任务数',
    `total_task_count`   int(11)      DEFAULT NULL COMMENT '总任务数',
    `created_at`         datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`         datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    INDEX `uk_tp` (`tp_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='线程池配置表';
