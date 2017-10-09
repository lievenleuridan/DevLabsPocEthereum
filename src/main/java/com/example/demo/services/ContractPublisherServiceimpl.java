package com.example.demo.services;

import com.example.demo.contractinterfaces.PersonContract;
import org.adridadou.ethereum.propeller.EthereumFacade;
import org.adridadou.ethereum.propeller.solidity.SolidityContractDetails;
import org.adridadou.ethereum.propeller.values.EthAddress;
import org.adridadou.ethereum.propeller.values.SoliditySourceFile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.concurrent.CompletableFuture;

import static com.example.demo.builders.EthereumAccountBuilder.accountBuilder;

/**
 * Created by vandebroeck.k on 9/10/2017.
 */
@Service("ContractPublisherService")
public class ContractPublisherServiceimpl implements ContractPublisherService {

    @Override
    public PersonContract publishAndMapContract(EthereumFacade ethereum) throws Exception {
        SolidityContractDetails compiledContract =
                ethereum.compile(SoliditySourceFile.from(new File("src/main/resources/contracts/PersonContract.sol"))).findContract("PersonContract").get();

        CompletableFuture<EthAddress> futureAddress = ethereum.publishContract(compiledContract, accountBuilder());
        return ethereum.createContractProxy(compiledContract, futureAddress.get(), accountBuilder(), PersonContract.class);
    }
}
