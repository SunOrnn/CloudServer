package com.ornn.elasticjob.starter;

import org.apache.shardingsphere.elasticjob.api.ElasticJob;
import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.lite.api.bootstrap.impl.ScheduleJobBootstrap;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: CANHUI.WANG * @create: 2022-10-08
 */

public class ElasticJobBeanPostProcessor implements BeanPostProcessor, DisposableBean {
    /**
     * ZooKeeper注册器
     */
    private ZookeeperRegistryCenter zookeeperRegistryCenter;


    /**
     * 已注册任务列表
     */
    private List<ScheduleJobBootstrap> scheduleList = new ArrayList<>();

    public ElasticJobBeanPostProcessor(ZookeeperRegistryCenter zookeeperRegistryCenter) {
        this.zookeeperRegistryCenter = zookeeperRegistryCenter;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 任务销毁方法
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        scheduleList.forEach(ScheduleJobBootstrap::shutdown);
    }

    /**
     * Spring IOC容器扩展接口方法，在Bean初始化后执行
     * @param o
     * @param s
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        Class<?> clazz = o.getClass();

        // 只处理自定义的ElasticTask注解
        if (!clazz.isAnnotationPresent(ElasticTask.class)) {
            return o;
        }
        if (!(o instanceof ElasticTask)) {
            return o;
        }
        ElasticJob job = (ElasticJob) o;
        // 获取注解定义
        ElasticTask annotation = clazz.getAnnotation(ElasticTask.class);
        // 定义任务名称
        String jobName = annotation.jobName();
        // 定义Cron表达式
        String cron = annotation.cron();
        // 设置任务参数
        String jobParameter = annotation.jobParameter();
        // 设置任务描述信息
        String description = annotation.description();
        // 设置数据分片数
        int shardingTotalCount = annotation.shardingTotalCount();
        // 设置数据分片参数
        String shardingItemParameters = annotation.shardingItemParameters();
        // 设置是否禁用分片项
        boolean disabled = annotation.disabled();
        // 设置重启任务定义信息是否覆盖
        boolean overwrite = annotation.overwrite();
        // 设置是否开启故障转移
        boolean failover = annotation.failover();
        // 设置是否开启错过任务重新执行
        boolean misfire = annotation.misfire();
        // 配置分片策略类
        String jobShardingStrategyClass = annotation.jobShardingStrategyClass();

        // 根据自定义注解的配置设置ElasticJob任务的配置
        JobConfiguration coreConfiguration = JobConfiguration.newBuilder(jobName, shardingTotalCount)
                .cron(cron)
                .jobParameter(jobParameter)
                .overwrite(overwrite)
                .failover(failover)
                .shardingItemParameters(shardingItemParameters)
                .disabled(disabled)
                .jobShardingStrategyType(jobShardingStrategyClass)
                .build();

        // 创建任务调度对象
        ScheduleJobBootstrap scheduleJobBootstrap = new ScheduleJobBootstrap(zookeeperRegistryCenter, job, coreConfiguration);

        // 触发任务调度
        scheduleJobBootstrap.schedule();

        // 创建的任务对象加入稽核，便于统一销毁
        scheduleList.add(scheduleJobBootstrap);

        return job;
    }
}
