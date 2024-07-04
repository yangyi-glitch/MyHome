package com.springdata.springdata.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.springdata.springdata.dto.CustDTO;
import com.springdata.springdata.entity.Customer;
import com.springdata.springdata.repository.CustomerRs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.springdata.springdata.utils.ElasticsearchKey.MAPPING_TEMPLATE;

@Api(tags = "测试ES")
@RequestMapping("/elasticsearch")
@RestController
public class ElasticsearchController {
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private CustomerRs customerRs;

    @ApiOperation("创建索引库")
    @PostMapping("/createIndexLibrary")
    public Boolean createIndexLibrary() throws IOException {
        //创建request对象
        CreateIndexRequest request = new CreateIndexRequest("cust");
        //准备请求参数，DSL语句
        request.source(MAPPING_TEMPLATE, XContentType.JSON);
        //发送请求
        restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        return true;
    }

    @ApiOperation("删除索引库")
    @PostMapping("/delIndexLibrary")
    public Boolean delIndexLibrary() throws IOException {
        //创建request对象
        DeleteIndexRequest request = new DeleteIndexRequest("cust");
        //发送请求
        restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        return true;
    }

    @ApiOperation("判断索引库是否存在")
    @PostMapping("/exisIndexLibrary")
    public Boolean exisIndexLibrary() throws IOException {
        //创建request对象
        GetIndexRequest request = new GetIndexRequest("cust");
        //发送请求
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        return exists;
    }

    @ApiOperation("创建文档")
    @PostMapping("/createDocument")
    public Boolean createDocument(@ApiParam("id") @RequestParam("id") int id) throws IOException {
        Customer byId = customerRs.getById(id);
        CustDTO custDTO = JSONUtil.toBean(JSONUtil.toJsonStr(byId), CustDTO.class);
        //创建request对象
        IndexRequest request = new IndexRequest("cust").id(byId.getCustId().toString());
        request.source(JSON.toJSONString(custDTO), XContentType.JSON);
        //发送请求
        restHighLevelClient.index(request, RequestOptions.DEFAULT);
        return true;
    }

    @ApiOperation("搜索文档")
    @PostMapping("/querDocument")
    public CustDTO querDocument(@ApiParam("id") @RequestParam("id") String id) throws IOException {
        //创建request对象
        GetRequest request = new GetRequest("cust", id);
        //发送请求
        GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        String josn = response.getSourceAsString();
        CustDTO custDTO = JSON.parseObject(josn, CustDTO.class);
        return custDTO;
    }

    @ApiOperation("更新文档")
    @PostMapping("/updateDocument")
    public Boolean updateDocument(@ApiParam("id") @RequestParam("id") String id,
                                  @ApiParam("name") @RequestParam("name") String name,
                                  @ApiParam("address") @RequestParam("address") String address) throws IOException {
        //创建request对象
        UpdateRequest request = new UpdateRequest("cust", id);
        request.doc(
                "custName", name,
                "custAddress", address
        );
        //发送请求
        restHighLevelClient.update(request, RequestOptions.DEFAULT);
        return true;
    }

    @ApiOperation("删除文档")
    @PostMapping("/delDocument")
    public Boolean delDocument(@ApiParam("id") @RequestParam("id") String id) throws IOException {
        //创建request对象
        DeleteRequest request = new DeleteRequest("cust", id);
        //发送请求
        restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        return true;
    }

    @ApiOperation("批量新增")
    @PostMapping("/bulkDocument")
    public Boolean bulkDocument() throws IOException {
        //创建request对象
        BulkRequest request = new BulkRequest();
        List<Customer> all = customerRs.findAll();
        for (Customer e : all) {
            CustDTO custDTO = JSONUtil.toBean(JSONUtil.toJsonStr(e), CustDTO.class);
            request.add(new IndexRequest("cust").id(e.getCustId().toString()).source(JSON.toJSONString(custDTO), XContentType.JSON));
        }
        //发送请求
        restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        return true;
    }

    @ApiOperation("Match根据地址查询文档")
    @PostMapping("/matchDocument")
    public List<CustDTO> matchDocument(@ApiParam("address") @RequestParam("address") String address) throws IOException {
        List<CustDTO> custDTOS = new ArrayList<>();
        //创建request对象
        SearchRequest request = new SearchRequest("cust");
        request.source().query(QueryBuilders.matchQuery("custAddress", address));
        //发送请求
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        //解析
        SearchHits searchHits = response.getHits();
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit e : hits) {
            String source = e.getSourceAsString();
            CustDTO custDTO = JSON.parseObject(source, CustDTO.class);
            custDTOS.add(custDTO);
        }
        return custDTOS;
    }
}
