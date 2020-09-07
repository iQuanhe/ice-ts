package main.java.com.weibo.bop.pulse.query.facade.hystrix;

import com.alibaba.fastjson.JSONObject;
import main.java.com.weibo.bop.pulse.query.facade.ConfigService;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Config管理服务接口熔断处理
 *
 * @author shaowei3
 * @create 2019-03-08
 */
@Component
public class ConfigServiceHystrixImpl implements FallbackFactory<ConfigService> {

    private static final Logger logger = LoggerFactory.getLogger(ConfigServiceHystrixImpl.class);


    @Override
    public ConfigService create(final Throwable cause) {

        return new ConfigService() {

            @Override
            public JSONObject getConfigClient() {
                return null;
            }
        };
    }
}
