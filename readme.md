# Easy Transaction 分布式事务 Demo

## 项目背景

基于 Spring Cloud 和 Easy Transaction 框架的分布式事务 Demo。

## 安装

1、使用 [db](./db) 文件夹下的 SQL 脚本初始化 MySQL 数据库。

  - 执行 schema.sql
  - 执行 schema-easytrans.sql
  - 执行 data.sql

2、启动应用程序

  - 启动 [eureka](./eureka) 模块的 `EurekaServerApplication`
  - 启动 [account-service](./account-service) 模板的 `AccountServiceApplication`
  - 启动 [order-service](./order-service) 模块的 `OrderServiceApplication`

3、完成。

## 使用

调用 POST 请求：

```
http://localhost:7010/v1/orders?userId=2&price=80
```

其中，`userId` 使用 1-3 范围内数值。
