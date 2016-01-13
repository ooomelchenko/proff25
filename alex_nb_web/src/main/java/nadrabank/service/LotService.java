package nadrabank.service;

import nadrabank.domain.Lot;

import java.util.List;

public interface LotService {
    Lot getLot(Long id);
    Long createLot(Lot lot);
    boolean delete(Long id);
    boolean delete(Lot lot);
    void updateLot(Lot lot);
    List getLots();
    Double lotSum(Lot lot);
    Long lotCount(Lot lot);

    boolean delLot(Lot lot);

    boolean delLot(Long lotId);
}