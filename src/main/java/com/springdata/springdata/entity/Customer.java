package com.springdata.springdata.entity;


import lombok.Data;

import javax.persistence.*;

/**
 * Hibernate实体类
 */
@Entity
@Table(name = "cst_customer")//映射的表明
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private Integer custId;

    @Column(name = "cust_name")
    private String custName;

    @Column(name = "cust_address")
    private String custAddress;
}
