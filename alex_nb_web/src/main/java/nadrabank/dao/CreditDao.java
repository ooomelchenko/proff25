package nadrabank.dao;

import nadrabank.domain.Credit;
import nadrabank.domain.Lot;

import java.util.List;

public interface CreditDao {
    Long create(Credit credit);
    Credit read(Long id);
    boolean update(Credit credit);
    boolean delete(Credit credit);
    List findAll();

    List findAll(int portionNum);

    Long totalCount();

    Credit findByInventar(String invNum);
    List getRegions();
    List getTypes();
    List getCur();

    String makeQueryText(String[] types, String[] regions, String[] cur);

    List <Credit> selectCredits(String[] types, String[] regions, String[] cur, int dpdMin, int dpdMax, double zbMin, double zbMax);

    List selectCrdSum(String[] types, String[] regions, String[] cur, int dpdMin, int dpdMax, double zbMin, double zbMax);

    boolean addCreditsToLot(Lot lot, String[] types, String[] regions, String[] cur, int dpdMin, int dpdMax, double zbMin, double zbMax);

    int delCRDTS(Lot lot);

    List getCreditsByClient(String inn, Long idBars);
}