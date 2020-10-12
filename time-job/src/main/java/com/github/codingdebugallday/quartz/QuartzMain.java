package com.github.codingdebugallday.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/10/12 1:43
 * @since 1.0.0
 */
public class QuartzMain {

    /**
     * 创建任务调度器
     */
    public static Scheduler createScheduler() throws SchedulerException {
        SchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
        return stdSchedulerFactory.getScheduler();
    }

    /**
     * 创建一个任务
     */
    public static JobDetail createJob(Class<? extends Job> jobClass) {
        JobBuilder jobBuilder = JobBuilder.newJob(jobClass);
        jobBuilder.withIdentity("jobName", "myJob");
        return jobBuilder.build();
    }

    /**
     * 创建任务的时间触发器
     * cron表达式由七个位置组成，空格分隔
     * 1、Seconds（秒）  0~59
     * 2、Minutes（分）  0~59
     * 3、Hours（小时）  0~23
     * 4、Day of Month（天）1~31,注意有的月份不足31天
     * 5、Month（月） 0~11,或者 JAN,FEB,MAR,APR,MAY,JUN,JUL,AUG,SEP,OCT,NOV,DEC
     * 6、Day of Week(周)  1~7,1=SUN或者  SUN,MON,TUE,WEB,THU,FRI,SAT
     * 7、Year（年）1970~2099  可选项
     * 示例：
     * 0 0 11 * * ? 每天的11点触发执行一次
     * 0 30 10 1 * ? 每月1号上午10点半触发执行一次
     */
    public static Trigger createTrigger(String cronExpression) {
        return TriggerBuilder.newTrigger()
                .withIdentity("triggerName", "myTrigger")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
    }

    public static void main(String[] args) throws SchedulerException {
        Scheduler scheduler = createScheduler();
        JobDetail jobDetail = createJob(DemoJob.class);
        // 每隔两秒执行一次
        String cron = "*/2 * * * * ?";
        Trigger trigger = createTrigger(cron);
        // 开始调度
        scheduler.scheduleJob(jobDetail,trigger);
        scheduler.start();
    }
}
