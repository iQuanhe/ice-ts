package main.java.com.weibo.bop.pulse.query;

import com.weibo.common.utils.JedisClient;
import com.weibo.common.utils.MailService;
import com.weibo.common.utils.impl.JedisClientSingle;
import com.weibo.common.utils.impl.MailServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 系统接入服务
 *
 * @author shaowei3
 * @create 2019-01-13
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableTransactionManagement
@Configuration
@MapperScan("com.weibo.bop.pulse.query.repo.mapper")
@ComponentScan(basePackages = "com.weibo.bop.pulse")
public class BopPulseQueryApplication{

    public static void main(String[] args) {
        SpringApplication.run(BopPulseQueryApplication.class, args);
    }

    @Bean
    public JedisClient jedisClient() {
        return new JedisClientSingle();
    }

    @Bean
    public MailService mailService() {
        return new MailServiceImpl();
    }
}
