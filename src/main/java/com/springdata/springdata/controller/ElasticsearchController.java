package com.springdata.springdata.controller;

import com.springdata.springdata.dto.CustDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Api(tags = "测试ES")
@RequestMapping("/elasticsearch")
@RestController
public class ElasticsearchController {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @ApiOperation("保存es")
    @PostMapping("/one")
    public void one() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("yangyi");
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }
}
