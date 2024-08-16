package com.springdata.springdata.entity;


import lombok.Data;
import org.springframework.expression.spel.ast.Literal;

import javax.persistence.*;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

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
