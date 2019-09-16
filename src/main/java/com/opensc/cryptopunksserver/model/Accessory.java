package com.opensc.cryptopunksserver.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Accessory {

    @Id
    @GeneratedValue
    private int id;

    private String name;

}
