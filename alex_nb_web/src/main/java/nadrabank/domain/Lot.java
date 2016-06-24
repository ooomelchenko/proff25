package nadrabank.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "LOTS")
public class Lot implements Serializable {
    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "LOT_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name = "ID")
    private Long id;
    @Column(name = "LotNum")
    private String lotNum;
    @Column(name = "Status")
    private String workStage;
    @Column(name = "Sold")
    private Boolean isItSold;
    @Column(name = "Comments")
    private String comment;
    @Column(name = "Lot_Created")
    private Date lotDate;
    @Column(name = "BID_Stage")
    private String bidStage;
    @Column(name = "COUNT_OF_PARTICIPANTS")
    private int countOfParticipants;
    @Column(name = "Start_PRICE")
    private BigDecimal startPrice;
    @Column(name = "FIRST_Start_PRICE")
    private BigDecimal firstStartPrice;
    @Column(name = "FACT_PRICE")
    private BigDecimal factPrice;
    @Column(name = "CUSTOMER")
    private String customerName;
    @Column(name = "RESULT_Status")
    private String status;
    @Column(name = "ACT_SIGNED_DATE")
    private Date actSignedDate;

    @ManyToOne
    private User user;//класс
    @ManyToOne
    private Bid bid;//класс

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkStage() {
        return workStage;
    }
    public void setWorkStage(String workStage) {
        this.workStage = workStage;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getLotDate() {
        return lotDate;
    }
    public void setLotDate(Date lotDate) {
        this.lotDate = lotDate;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getItSold() {
        return isItSold;
    }
    public void setItSold(Boolean itSold) {
        isItSold = itSold;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }
    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public BigDecimal getFactPrice() {
        return factPrice;
    }
    public void setFactPrice(BigDecimal factPrice) {
        this.factPrice = factPrice;
    }

    public String getLotNum() {
        return lotNum;
    }
    public void setLotNum(String lotNum) {
        this.lotNum = lotNum;
    }

    public Bid getBid() {
        return bid;
    }
    public void setBid(Bid bid) {
        this.bid = bid;
    }

    public String getBidStage() {
        return bidStage;
    }
    public void setBidStage(String bidStage) {
        this.bidStage = bidStage;
    }

    public int getCountOfParticipants() {
        return countOfParticipants;
    }
    public void setCountOfParticipants(int countOfParticipants) {
        this.countOfParticipants = countOfParticipants;
    }

    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getFirstStartPrice() {
        return firstStartPrice;
    }
    public void setFirstStartPrice(BigDecimal firstStartPrice) {
        this.firstStartPrice = firstStartPrice;
    }

    public Date getActSignedDate() {
        return actSignedDate;
    }
    public void setActSignedDate(Date actSignedDate) {
        this.actSignedDate = actSignedDate;
    }

    //Конструктора
    public Lot() {
    }

    public Lot(String comment, User user, Date lotDate) {
        this.workStage = "Новий лот";
        this.isItSold = false;
        this.comment = comment;
        this.user = user;
        this.lotDate = lotDate;
        this.countOfParticipants=0;
        this.bidStage="Перші торги";
    }

    public Lot(String lotNum, String workStage, Boolean isItSold, String comment, Date lotDate, String bidStage, int countOfParticipants, BigDecimal startPrice, BigDecimal firstStartPrice, BigDecimal factPrice, String customerName, String status, User user, Bid bid) {
        this.lotNum = lotNum;
        this.workStage = workStage;
        this.isItSold = isItSold;
        this.comment = comment;
        this.lotDate = lotDate;
        this.bidStage = bidStage;
        this.countOfParticipants = countOfParticipants;
        this.startPrice = startPrice;
        this.firstStartPrice = firstStartPrice;
        this.factPrice = factPrice;
        this.customerName = customerName;
        this.status = status;
        this.user = user;
        this.bid = bid;
    }

    @Override
    public String toString() {
        return "Lot{" +
                "id=" + id +
                ", lotNum='" + lotNum + '\'' +
                ", workStage='" + workStage + '\'' +
                ", isItSold=" + isItSold +
                ", comment='" + comment + '\'' +
                ", lotDate=" + lotDate +
                ", bidStage='" + bidStage + '\'' +
                ", countOfParticipants=" + countOfParticipants +
                ", startPrice=" + startPrice +
                ", firstStartPrice=" + firstStartPrice +
                ", factPrice=" + factPrice +
                ", customerName='" + customerName + '\'' +
                ", status='" + status + '\'' +
                ", actSignedDate=" + actSignedDate +
                ", user=" + user +
                ", bid=" + bid +
                '}';
    }
}