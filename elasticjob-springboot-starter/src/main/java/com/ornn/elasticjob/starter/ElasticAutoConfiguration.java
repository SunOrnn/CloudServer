package com.ornn.elasticjob.starter;

import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperConfiguration;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: CANHUI.WANG * @create: 2022-10-08
 */
@Configuration
@ConditionalOnProperty({"elasticjob.zk.serverLists", "elasticjob.zk.namespace"})
public class ElasticAutoConfiguration {
    /**
     * ZooKeeper注册中心的配置
     */
    @Bean
    @ConfigurationProperties("elasticjob.zk")
    public ZookeeperConfiguration getConfiguration(@Value("${elasticjob.zk.serverLists}") String serverLists, @Value("${elasticjob.zk.namespace}") String nameapace) {
        return new ZookeeperConfiguration(serverLists, nameapace);
    }

    /**
     * 初始化注册信息
     */
    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter zookeeperRegistryCenter(ZookeeperConfiguration configuration) {
        return new ZookeeperRegistryCenter(configuration);
    }

    /**
     * 配置处理自定义ElasticJob任务的逻辑类
     */
    @Bean
    public ElasticJobBeanPostProcessor elasticJobBeanPostProcessor(ZookeeperRegistryCenter center) {
        return new ElasticJobBeanPostProcessor(center);
    }
}
