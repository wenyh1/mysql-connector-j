## 源码侧支持GMSSL

见 变更

### 环境部署
// TODO

### 现有环境部署
#### 打包
使用Ant工具打包

#### 环境地址：
ssh root@10.186.63.8   
root

#### 打包命令：
cd /opt/mysql-connector-j/   
执行`ant dist`

#### build.properties配置（作为Ant工具打包是用到的配置，位于mysql-connector-j目录下）：
```
com.mysql.jdbc.jdk8=/usr/local/java/jdk1.8.0_111
com.mysql.jdbc.jdk5=/usr/local/java/jdk1.5.0_22
com.mysql.jdbc.extra.libs=/opt/ant-extralibs
com.mysql.jdbc.java6.rtjar=/usr/local/java/jdk1.6.0_45/jre/lib/rt.jar
```
##### 解释：
com.mysql.jdbc.extra.libs 为需依赖的系列包