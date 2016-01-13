package nadrabank.service;

import nadrabank.domain.Lot;

import java.util.List;

public interface LotService {
    Lot getLot(Long id);
    boolean createTestLot();
    Long createLot(Lot lot);
    boolean delete(Long id);
    void updateLot(Lot lot);
    List showLot();
}