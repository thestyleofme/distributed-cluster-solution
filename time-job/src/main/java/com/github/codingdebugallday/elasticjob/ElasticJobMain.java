package com.github.codingdebugallday.elasticjob;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

/**
 * <p>
 * 测试
 * 1）可先启动⼀个进程，然后再启动⼀个进程（两个进程模拟分布式环境下，通⼀个定时任务部署了两份在⼯作）--> idea 设置 Allow parallel run即可运行多个main方法
 * 2）两个进程逐个启动，观察现象
 * 3）关闭其中执⾏的进程，观察现象
 * </p>
 *
 * @author isaac 2020/10/12 23:38
 * @since 1.0.0
 */
public class ElasticJobMain {

    public static void main(String[] args) {
        // 配置分布式协调服务（注册中心）Zookeeper
        ZookeeperConfiguration zookeeperConfiguration =
                new ZookeeperConfiguration("localhost:2181", "data-archive-job");
        CoordinatorRegistryCenter coordinatorRegistryCenter =
                new ZookeeperRegistryCenter(zookeeperConfiguration);
        coordinatorRegistryCenter.init();

        // 配置任务（时间事件、定时任务业务逻辑、调度器）
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration
                .newBuilder("archive-job", "*/2 * * * * ?", 3)
                .shardingItemParameters("0=bachelor,1=master,2=doctor")
                .build();
        SimpleJobConfiguration simpleJobConfiguration =
                new SimpleJobConfiguration(jobCoreConfiguration, ArchiveJob.class.getName());

        JobScheduler jobScheduler = new JobScheduler(coordinatorRegistryCenter,
                LiteJobConfiguration.newBuilder(simpleJobConfiguration).overwrite(true).build());
        jobScheduler.init();
    }
}
