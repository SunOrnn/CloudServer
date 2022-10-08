package com.ornn.elasticjob.starter;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: CANHUI.WANG * @create: 2022-10-08
 */
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ElasticTask {
    /**
     * 定义任务名称
     * @return
     */
    String jobName();

    /**
     * 定义Core时间表达式
     */
    String cron();

    /**
     * 定义任务参数信息
     * @return
     */
    String jobParameter() default "";

    /**
     * 定义任务描述信息
     * @return
     */
    String description() default "";

    /**
     * 定义任务分片数
     * @return
     */
    int shardingTotalCount() default 1;

    /**
     * 定义任务分片参数
     * @return
     */
    String shardingItemParameters() default "";

    /**
     * 设置是否禁用数据分片功能
     * @return
     */
    boolean disabled() default false;

    /**
     * 配置分片策略
     * @return
     */
    String jobShardingStrategyClass() default "";

    /**
     * 配置是否故障转移功能
     * @return
     */
    boolean failover() default false;

    /**
     * 配置是否在运行重启时重置任务定义信息（包括Corn时间片设置）
     * @return
     */
    boolean overwrite() default false;

    /**
     * 配置是否开启错误任务重新执行
     * @return
     */
    boolean misfire() default false;
}
