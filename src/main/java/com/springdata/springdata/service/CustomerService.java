package com.springdata.springdata.service;

import cn.hutool.json.JSONUtil;
import com.springdata.springdata.dto.CotsDTO;
import com.springdata.springdata.entity.Customer;
import com.springdata.springdata.repository.CustomerRs;
import com.springdata.springdata.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRs customerRs;

    /**
     *保存顾客
     */
    public String saveCots(CotsDTO custDTO){
        if (custDTO.getCustName().equals("") || custDTO.getCustName() == null &&
            custDTO.getCustAddress().equals("") || custDTO.getCustAddress() == null){
            return "false";
        }
        String str = JSONUtil.toJsonStr(custDTO);
        Customer customer = JSONUtil.toBean(str, Customer.class);
        Customer save = customerRs.save(customer);
        if (JSONUtil.isNull(save)){
            return "false";
        }
        return "success";
    }
    /**
     *根据姓名删除顾客
     */
    public String delCots(String name){
        if (name == null || name.equals("")){
            return "false";
        }
        int i = customerRs.delCots(name);
        return (i==0)? "false":"success";
    }
    /**
     *查询所有顾客
     */
    public Result<List<Customer>> getCotsAll(int page, int size){
        Result<List<Customer>> result = new Result<>();
        Pageable pageable = PageRequest.of(page,size);
        Page<Customer> all = customerRs.findAll(pageable);
        List<Customer> content = all.getContent();
        result.setDate(content);
        result.setMsg((content.size()>0)?"查询成功":"查询失败");
        return result;
    }
    /**
     *按地址查询所有顾客
     */
    public Result<List<Customer>> getCotsAddress(String address, int page, int size){
        Result<List<Customer>> result = new Result<>();
        if (address == null||address.equals("")){
            result.setMsg("false");
            return result;
        }
        Pageable pageable = PageRequest.of(page,size);
        List<Customer> cotsAddress = customerRs.getCotsAddress(address, pageable);
        result.setDate(cotsAddress);
        result.setMsg((cotsAddress.size()>0)?"查询成功":"查询失败");
        return result;
    }

    /**
     * 根据id删除顾客
     */
    public String deleteById(Integer id){
        if (id == null){
            return "false";
        }
        try {
            customerRs.deleteById(id);
        }catch (Exception e){
            return "false";
        }
        return "success";
    }
}
