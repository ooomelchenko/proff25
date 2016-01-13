package nadrabank.service;

import nadrabank.domain.Credit;
import nadrabank.domain.Lot;

import java.util.List;

/**
 * Created by HP on 10/15/2015.
 */
public interface CreditService {
    Credit getCredit(Long id);
    boolean createTestCredit();
    boolean createCredit(Credit credit);
    boolean delete(Long id);

    boolean delete(Credit credit);

    void updateCredit(Credit credit);
    List showEquip();
    Credit getByInventar(String invNum);
    List getRegions();
    List getTypes();
    List getCurrencys();

    List<Credit> selectCredits(String[] types, String[] regs, String [] currs, int dpdmin, int dpdmax, double zbmin, double zbmax);

    List getCreditsResults(String[] types, String[] regs, String[] currs, int dpdmin, int dpdmax, double zbmin, double zbmax);

    boolean addCreditsToLot(Lot lot, String[] types, String[] regions, String[] cur, int dpdMin, int dpdMax, double zbMin, double zbMax);
}
