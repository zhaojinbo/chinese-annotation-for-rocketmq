package org.apache.rocketmq.example.quickstart;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 这段代码展示了如何使用DefaultMQProducer来向broker发送消息
 */
public class Producer {
    public static void main(String[] args) throws MQClientException, InterruptedException {

        /*
         * 实例化DefaultMQProducer，这里如要设置生产者组名。
         */
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        /*
         * 可以通过下面这句话来设置name server的地址，当然你也可以通过环境变量来进行设置
         * 因为我们在前面已经设置了环境变量，所以这里应该将这句话注释掉
         */
        producer.setNamesrvAddr("name-server1-ip:9876;name-server2-ip:9876");
        /*
         * 启动实例
         */
        producer.start();

        // 发送1000个消息
        for (int i = 0; i < 1000; i++) {
            try {

                /*
                 * 创建消息实例，指定topic、tag和消息主体
                 */
                Message msg = new Message("TopicTest" /* Topic */,
                        "TagA" /* Tag */,
                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                );

                /*
                 * 调用send方法来将消息发送给其中一个broker
                 */
                SendResult sendResult = producer.send(msg);

                System.out.printf("%s%n", sendResult);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }
        }

        /*
         * 最后关闭生产者producer，只需关闭一次
         */
        producer.shutdown();
    }
}