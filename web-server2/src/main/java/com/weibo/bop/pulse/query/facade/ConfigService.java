package main.java.com.weibo.bop.pulse.query.facade;

import com.alibaba.fastjson.JSONObject;
import com.weibo.bop.pulse.query.facade.hystrix.ConfigServiceHystrixImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author shaowei3
 * @create 2019-03-08
 */
@FeignClient(name = "configService", url = "${config.url}", fallbackFactory = ConfigServiceHystrixImpl.class)
public interface ConfigService {

    @GetMapping("/")
    JSONObject getConfigClient();
}
