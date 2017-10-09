package com.example.demo.services;

import com.example.demo.contractinterfaces.PersonContract;
import org.adridadou.ethereum.propeller.EthereumFacade;
import org.springframework.stereotype.Service;

/**
 * Created by vandebroeck.k on 9/10/2017.
 */
@Service("ContractPublisherService")
public interface ContractPublisherService {
    PersonContract publishAndMapContract(EthereumFacade ethereum) throws Exception;

}
