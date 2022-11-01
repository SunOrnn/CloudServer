package com.ornn.schedule.job;

import com.ornn.elasticjob.starter.ElasticTask;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;

import java.util.Date;

/**
 * @author: CANHUI.WANG * @create: 2022-10-08
 */
@ElasticTask(jobName = "testJob", cron = "*/5 * * * * ?", description = "自定义Task", overwrite = true)
public class TestJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("跑任务->" + new Date());
    }
}
