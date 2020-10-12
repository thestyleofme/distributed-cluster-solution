## 分布式集群场景下潜在问题以及Web服务综合解决方案

### 一致性hash问题及解决方案

* 普通Hash算法实现
* 一致性Hash不含虚拟节点
* 一致性Hash含虚拟节点

### 分布式id

* UUID
* 独⽴数据库的⾃增ID（不推荐，麻烦）

```
⽐如A表分表为A1表和A2表，那么肯定不能让A1表和A2表的ID⾃增，那么ID怎么获取呢？我们可
以单独的创建⼀个Mysql数据库，在这个数据库中创建⼀张表，这张表的ID设置为⾃增，其他地⽅
需要全局唯⼀ID的时候，就模拟向这个Mysql数据库的这张表中模拟插⼊⼀条记录，此时ID会⾃
增，然后我们可以通过Mysql的select last_insert_id() 获取到刚刚这张表中⾃增⽣成的ID.

DROP TABLE IF EXISTS `DISTRIBUTE_ID`;
CREATE TABLE `DISTRIBUTE_ID` (
`id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
`createtime` datetime DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into DISTRIBUTE_ID(createtime) values(NOW());
select LAST_INSERT_ID()；
```
* SnowFlake 雪花算法（可以⽤，推荐）
* 借助Redis的Incr命令获取全局唯⼀ID（推荐）

### 分布式调度

* Quartz
* elastic-job
```
表数据使用time-job/src/main/resources/elastic-job-test.sql，可模拟任务分片以及高可用
```