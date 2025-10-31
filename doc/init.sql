-- 机器数据库，一个机器就是一个被监控的运行主体，可能是服务器、工控机或者边缘计算单元啥的
CREATE TABLE `machine`
(
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL, -- 服务器名称
    `group_id` INT NOT NULL, -- machine_group表主键，该服务器位于哪个分组中
    `type_id` INT NOT NULL, -- machine_type表主键，是哪种服务器
    `online` TINYINT NOT NULL, -- 服务器当前是否在线，0离线，1在线
    `cpu_usage` FLOAT NOT NULL, -- 服务器当前CPU占用率，0-1小数
    `ram_usage` FLOAT NOT NULL -- 服务器当前内存占用率，0-1小数

    -- 讨论下，在线和资源利用率这些，写在数据库里会不会有性能问题
    -- 我觉得还好，目前服务器没那么多，一分钟上报一个心跳，能有个屁性能问题，等多起来再优化吧
);

-- 机器硬盘情况，因为一个机器上可能挂载多块硬盘（分区），所以单独成表
CREATE TABLE `machine_disk`
(
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `machine_id` INT NOT NULL, -- 所在机器ID
    `name` VARCHAR(255) NOT NULL, -- 盘符或挂载路径
    `disk_usage` FLOAT NOT NULL -- 当前占用率，0-1小数
);

-- 机器分组，可以通过分组来区分项目
CREATE TABLE `machine_group`
(
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL
);

-- 机器类型，可区分服务器、工控机啥的，用户自行配置
CREATE TABLE `machine_type`
(
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL
);

-- 服务，机器上运行的被监控的程序
CREATE TABLE `service`
(
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `type_id` INT NOT NULL, -- service_type表主键，运行模式
    `online` TINYINT NOT NULL -- 是否正常运行，0凉了，1运行中
);

-- 运行模式，docker/命令行/systemd服务等
CREATE TABLE `service_type`
(
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL
);
