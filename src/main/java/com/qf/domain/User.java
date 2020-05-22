package com.qf.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by 54110 on 2020/5/18.
 */
@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "username")
    private String userName;
    private String address;


}
