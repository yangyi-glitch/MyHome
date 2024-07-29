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

    public static void main(String[] args) {
        int[] a = {2,5,4,6,8,1,2,6,4,4};
        int n = a.length;
        while (n>0){
            for (int i=0;i<n-1;i++){
                if (a[i]>a[i+1]){
                    int value = a[i];
                    a[i] = a[i+1];
                    a[i+1] = value;
                }
            }
            n--;
        }
        System.out.print("[");
        for(int e:a){
            System.out.print(e+",");
        }
        System.out.println("]");
    }
}
