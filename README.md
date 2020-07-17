# AccountingService
Accounting Service

## Spring Boot 开发四大步
- 添加响应依赖
- 添加相应注解
- 编写代码
- 添加响应配置

## 异常处理


## 加密
- 密码必须要加密生成，数据库存储加密后的密码
- 加密方式：MD5 ，SHA5

### 基本加密
password 
MD5(password) -> s -> database
SHA(password) -> s -> database

### 加盐加密 Salt
随机生成一个盐 salt = UUID() 

MD5(password + salt)-> database


### 多次加盐加密

Round1：MD5(password + salt) -> s_1

Round2：MD5(s1 + salt) -> s_2

Round3：MD5(s2 + salt) -> s_3
......
Round N：MD5(s_n-1 + salt) -> final s -> database





