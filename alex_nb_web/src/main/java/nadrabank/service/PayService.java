package nadrabank.service;

import nadrabank.domain.Pay;

import java.util.List;

public interface PayService {
    Pay getPay(Long id);
    Long createPay(Pay pay);
    boolean delete(Long id);
    boolean delete(Pay pay);
    boolean updatePay(Pay pay);
    List getAllPays();
}