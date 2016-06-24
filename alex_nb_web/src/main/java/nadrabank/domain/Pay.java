package nadrabank.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "PAYMENTS")
public class Pay implements Serializable {
    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "PAY_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name = "ID")
    private Long id;
    @Column(name = "Pay_Date")
    private Date date;
    @Column(name = "Pay_Sum")
    private BigDecimal paySum;

    @ManyToOne
    private Lot lot;//класс

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getPaySum() {
        return paySum;
    }
    public void setPaySum(BigDecimal paySum) {
        this.paySum = paySum;
    }

    public Lot getLot() {
        return lot;
    }
    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public Pay() {
    }

    public Pay(Lot lot, Date date, BigDecimal paySum) {
        this.lot = lot;
        this.date = date;
        this.paySum = paySum;
    }
}
