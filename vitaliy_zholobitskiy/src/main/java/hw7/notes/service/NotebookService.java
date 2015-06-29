package hw7.notes.service;

import hw7.notes.domain.Notebook;

import java.util.Date;
import java.util.List;

public interface NotebookService {
    Long add(Notebook notebook);
    List<Notebook> findAll();
    void changePrice(Long id, double price);
    void changeSerialVendor(Long id, String serial, String vendor);
    boolean delete(Long id);
    boolean deleteByModel(String model);
    boolean deleteByVendor(String vendor);
    List<Notebook> findByVendor(String vendor);
    List<Notebook> findByPriceManufDate(Double price, Date date);
    List<Notebook> findBetweenPriceLtDateByVendor(Double priceFrom, Double priceTo, Date date, String vendor);
}
