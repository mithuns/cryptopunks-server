package com.opensc.cryptopunksserver.controller;

import com.opensc.cryptopunksserver.model.Accessory;
import com.opensc.cryptopunksserver.model.CryptoPunkInfo;
import com.opensc.cryptopunksserver.repository.AccessoryRepository;
import com.opensc.cryptopunksserver.repository.CryptoPunkInfoRepository;
import com.opensc.cryptopunksserver.service.CryptoPunkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@RestController
@RequestMapping("/cryptopunks")
@PropertySource(value={"classpath:application.properties"})
public class CryptoPunksRestController {

    @Autowired
    CryptoPunkInfoRepository cryptoPunkInfoRepository;

    @Autowired
    AccessoryRepository accessoryRepository;

    @Autowired
    CryptoPunkService service;

    @RequestMapping()
    public List<CryptoPunkInfo> getAllCryptoPunksOnSale(){

        final List<CryptoPunkInfo> results = new ArrayList<>();
        cryptoPunkInfoRepository.findAll().forEach(element->results.add(element));
        return results;

    }

    @RequestMapping("/{index}")
    public String getCryptoPunkDetails(@PathVariable("index") String index){
        return "Details on the requested PunkID:-"+index;
    }
}