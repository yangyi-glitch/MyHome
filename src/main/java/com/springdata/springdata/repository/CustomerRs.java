package com.springdata.springdata.repository;

import com.springdata.springdata.entity.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CustomerRs extends JpaRepository<Customer,Integer> {
    @Query(value = "select * from cst_customer where cust_address = ?1",nativeQuery = true)
    List<Customer> getCotsAddress(String address, Pageable pageable);

    @Transactional
    @Query(value = "delete from cst_customer where cust_name = ?1",nativeQuery = true)
    @Modifying
    int delCots(String name);
}
