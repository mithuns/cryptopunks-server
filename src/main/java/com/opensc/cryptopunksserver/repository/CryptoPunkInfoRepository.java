package com.opensc.cryptopunksserver.repository;

import com.opensc.cryptopunksserver.model.CryptoPunkInfo;
import org.springframework.data.repository.CrudRepository;

public interface CryptoPunkInfoRepository extends CrudRepository<CryptoPunkInfo, Integer> {
}
