package com.opensc.cryptopunksserver.helpers;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.opensc.cryptopunksserver.model.Accessory;
import com.opensc.cryptopunksserver.model.CryptoPunkInfo;
import com.opensc.cryptopunksserver.model.CustomDataWrapper;
import com.opensc.cryptopunksserver.repository.AccessoryRepository;
import com.opensc.cryptopunksserver.repository.CryptoPunkInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;


import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class LoadPunksFromJsonData {

    @Autowired
    CryptoPunkInfoRepository punkInfoRepository;

    @Autowired
    AccessoryRepository accessoryRepository;

    @PostConstruct
    public void init(){

        final InputStream is = getClass().getClassLoader().getResourceAsStream("CryptoPunks.json");

        try {
            readJsonStreamAndSaveObjects(is);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //verify 8999 objects saved since json only has upto this number.
        Optional<CryptoPunkInfo> thousandthPunk = punkInfoRepository.findById(8999);
        if(thousandthPunk.get()!=null){

            Assert.isTrue(thousandthPunk.get().getIndex()==8999,"Not all objects saved");
        }


    }
    public void readJsonStreamAndSaveObjects(InputStream in) throws Exception {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        reader.setLenient(true);
        reader.beginObject();

        while (reader.hasNext()) {
            String index = reader.nextName();
            //System.out.println(index);
            CustomDataWrapper customData = gson.fromJson(reader, CustomDataWrapper.class);
            customData.setIndex(Integer.parseInt(index));
            if(!savePunkInfo(customData)){
                throw new Exception("json load failed");
            }
        }
        reader.endObject();
        reader.close();
    }
    public boolean savePunkInfo(CustomDataWrapper data){
        List<Accessory> accessories = new ArrayList<>();
        data.getAccessories().stream().map(elem->accessories.add(new Accessory(elem)));
        accessoryRepository.saveAll(accessories);
        final CryptoPunkInfo punkInfo = new CryptoPunkInfo(data.getIndex(),accessories);
        if(punkInfoRepository.existsById(data.getIndex())){
            return true;
        }else{
            //this should only happen once;
            final CryptoPunkInfo info = punkInfoRepository.save(punkInfo);
            if(info!=null){
                return true;
            }
        }

        return false;
    }
}