package com.springdata.springdata.controller;

import com.springdata.springdata.dto.CotsDTO;
import com.springdata.springdata.entity.Customer;
import com.springdata.springdata.service.CustomerService;
import com.springdata.springdata.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "顾客管理接口")
@RequestMapping("/users")
@RestController
public class CotsController {
    @Autowired
    private CustomerService customerService;

    @ApiOperation("保存顾客")
    @PostMapping("/saveCots")
    public String saveCots(@RequestBody CotsDTO cotsDTO){
        String res = customerService.saveCots(cotsDTO);
        return res;
    }

    @ApiOperation("根据姓名删除顾客")
    @PostMapping("/delCotsId")
    public String delCots(@ApiParam("顾客姓名") @RequestParam(value = "name",required = false) String name){
        String res = customerService.delCots(name);
        return res;
    }

    @ApiOperation("查询所有顾客接口")
    @PostMapping("/getCotsAll")
    public Result<List<Customer>> getCotsAll(@ApiParam("查询页数") @RequestParam(value = "page",required = false,defaultValue = "0" ) int page,
                                             @ApiParam("查询条数") @RequestParam(value = "size",required = false,defaultValue = "10") int size){
        Result<List<Customer>> cotsAll = customerService.getCotsAll(page, size);
        return cotsAll;
    }

    @ApiOperation("根据地址查询所有顾客")
    @PostMapping("/getCotsAddress")
    public Result<List<Customer>> getCotsAddress(@ApiParam("地址") @RequestParam(value = "address",required = false) String address,
                                                 @ApiParam("查询页数") @RequestParam(value = "page",required = false,defaultValue = "0" ) int page,
                                                 @ApiParam("查询条数") @RequestParam(value = "size",required = false,defaultValue = "10") int size){
        Result<List<Customer>> cotsAddress = customerService.getCotsAddress(address, page, size);
        return cotsAddress;
    }

    @ApiOperation("根据id删除顾客")
    @PostMapping("/deleteById")
    public String deleteById(@ApiParam("顾客id") @RequestParam(value = "id",required = false) Integer id){
        String res = customerService.deleteById(id);
        return res;
    }
}
