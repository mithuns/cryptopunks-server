package com.opensc.cryptopunksserver.controller;

import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/cryptopunks")
@PropertySource(value={"classpath:application.properties"})
public class CryptoPunksRestController {

    @RequestMapping()
    public String getAllCryptoPunksOnSale(){
        return "RETURN ALL PUNKS ON SALE";
    }

    @RequestMapping("/{index}")
    public String getCryptoPunkDetails(@PathVariable("index") String index){
        return "Details on the requested PunkID:-"+index;
    }
}