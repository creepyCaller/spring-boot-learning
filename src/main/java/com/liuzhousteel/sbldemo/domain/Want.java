package com.liuzhousteel.sbldemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Want implements Serializable {

    public Want(String name, int amount, double price, String remark, Date date, int status) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.remark = remark;
        this.date = date;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int amount;

    private double price;

    private String remark;

    private Date date;

    private int status;

}