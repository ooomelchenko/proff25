package nadrabank.service;

import nadrabank.domain.Credit;
import nadrabank.domain.Lot;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by HP on 10/15/2015.
 */
public interface CreditService {
    Credit getCredit(Long id);
    boolean createCredit(Credit credit);
    boolean delete(Long id);
    boolean delete(Credit credit);
    boolean updateCredit(Credit credit);

    List <Credit> getAllCredits();

    List <Credit> getCreditsByPortion(int num);

    @Transactional(readOnly = true)
    List <Credit> getCreditsByPortion(int num, int isSold, int isInLot, int clientType, int isNbu, int isFondDec);

    @Transactional(readOnly = true)
    Long countOfFilteredCredits(int isSold, int isInLot, int clientType, int isNbu, int isFondDec);

    Long getTotalCountOfCredits();

    List showEquip();
    Credit getByInventar(String invNum);
    List getRegions();
    List getTypes();
    List getCurrencys();

    List<Credit> selectCredits(String[] types, String[] regs, String [] currs, int dpdmin, int dpdmax, double zbmin, double zbmax);

    List getCreditsResults(String[] types, String[] regs, String[] currs, int dpdmin, int dpdmax, double zbmin, double zbmax);

    boolean addCreditsToLot(Lot lot, String[] types, String[] regions, String[] cur, int dpdMin, int dpdMax, double zbMin, double zbMax);

    List getCreditsByClient(String inn, Long id);

    boolean updateCredit(String userLogin, Credit crdt);

    @Transactional(readOnly = true)
    List getCrditsByLotId(Long lotId);

    @Transactional(readOnly = true)
    List getCredits_SuccessBids(Date startBids, Date endBids);
}
