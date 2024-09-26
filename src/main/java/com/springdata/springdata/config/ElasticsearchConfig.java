package com.springdata.springdata.config;

import cn.hutool.json.JSONArray;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.Cache;
import java.util.List;
import java.util.Map;

@Configuration
public class ElasticsearchConfig {
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("192.168.218.131", 9200, "http")));
        return client;
    }

    public static void main(String[] args) {
        String x = "[{\"a\":1,\"b\":2},{\"a\":3,\"b\":4}]";
        JSONArray jsonArray = new JSONArray(x);
        for (int i=0;i<jsonArray.size();i++) {
            Map<String,Integer> map = (Map<String, Integer>) jsonArray.get(i);
            Integer a = map.get("a");
            Integer b = map.get("b");
            System.out.println(a+"+"+b+"="+(a+b));
        }

        String u = "我爱你";
        String y = "我爱你";
        System.out.println(u.equals(y));
    }
}
