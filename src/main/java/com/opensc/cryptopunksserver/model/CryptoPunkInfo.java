package com.opensc.cryptopunksserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CryptoPunkInfo {

    @Id
    private  Integer index;

    @OneToMany
    private List<Accessory> accessoryList = new ArrayList<>();
}
