package com.intsmaze.flink.client;

import com.intsmaze.flink.apollo.ApolloFlatMap;
import com.intsmaze.flink.base.env.BaseFlink;
import com.intsmaze.flink.client.task.source.SimpleDataSource;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * github地址: https://github.com/intsmaze
 * 博客地址：https://www.cnblogs.com/intsmaze/
 * 出版书籍《深入理解Flink核心设计与实践原理》
 *
 * @auther: intsmaze(刘洋)
 * @date: 2020/10/15 18:33
 */
public class ApolloClient extends BaseFlink {

    /**
     * 本地启动参数  -isLocal local
     * 集群启动参数  -isIncremental isIncremental
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        ApolloClient topo = new ApolloClient();
        topo.run(ParameterTool.fromArgs(args));
    }

    @Override
    public String getJobName() {
        return "ApolloClient";
    }

    @Override
    public String getConfigName() {
        return "topology-apollo.xml";
    }

    @Override
    public String getPropertiesName() {
        return "config.properties";
    }

    @Override
    public void createTopology(StreamExecutionEnvironment builder) {
        builder.setParallelism(1);
        DataStream<String> inputDataStrem = env.addSource(new SimpleDataSource());

        DataStream<String> processDataStream = inputDataStrem.flatMap(new ApolloFlatMap());

        processDataStream.print("集成apollo配置中心，订阅参数");

    }

}

