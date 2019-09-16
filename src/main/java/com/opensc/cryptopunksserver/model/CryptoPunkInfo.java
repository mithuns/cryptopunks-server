package com.opensc.cryptopunksserver.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class CryptoPunkInfo {

    @Id
    private int index;

    @OneToMany
    private List<Accessory> accessoryList;

}
