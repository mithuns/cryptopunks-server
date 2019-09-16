package com.opensc.cryptopunksserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomDataWrapper {
    private int index;
    private String gender;
    private List<String > accessories;
}
