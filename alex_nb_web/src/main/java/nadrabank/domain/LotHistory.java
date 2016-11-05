package nadrabank.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "LOTS_HISTORY")
public class LotHistory implements Serializable {
    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "LOT_HISTORY_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name = "ID_KEY")
    private Long idKey;
    @Column(name = "CHANGE_DATE")
    private Date changeDate;
    @Column(name = "USER_NAME")
    private String userName;
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
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "BID_ID")
    private Long bidId;


    public Long getIdKey() {
        return idKey;
    }
    public void setIdKey(Long idKey) {
        this.idKey = idKey;
    }

    public Date getChangeDate() {
        return changeDate;
    }
    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getLotNum() {
        return lotNum;
    }
    public void setLotNum(String lotNum) {
        this.lotNum = lotNum;
    }

    public String getWorkStage() {
        return workStage;
    }
    public void setWorkStage(String workStage) {
        this.workStage = workStage;
    }

    public Boolean getItSold() {
        return isItSold;
    }
    public void setItSold(Boolean itSold) {
        isItSold = itSold;
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

    public BigDecimal getStartPrice() {
        return startPrice;
    }
    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public BigDecimal getFirstStartPrice() {
        return firstStartPrice;
    }
    public void setFirstStartPrice(BigDecimal firstStartPrice) {
        this.firstStartPrice = firstStartPrice;
    }

    public BigDecimal getFactPrice() {
        return factPrice;
    }
    public void setFactPrice(BigDecimal factPrice) {
        this.factPrice = factPrice;
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

    public Date getActSignedDate() {
        return actSignedDate;
    }
    public void setActSignedDate(Date actSignedDate) {
        this.actSignedDate = actSignedDate;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBidId() {
        return bidId;
    }
    public void setBidId(Long bidId) {
        this.bidId = bidId;
    }

    //Конструктора
    public LotHistory() {
    }

    public LotHistory(String userName, Lot lot) {
        this.changeDate = new Date();
        this.userName = userName;
        this.id = lot.getId();
        this.lotNum = lot.getLotNum();
        this.workStage = lot.getWorkStage();
        this.isItSold = lot.getItSold();
        this.comment = lot.getComment();
        this.lotDate = lot.getLotDate();
        this.bidStage = lot.getBidStage();
        this.countOfParticipants = lot.getCountOfParticipants();
        this.startPrice = lot.getStartPrice();
        this.firstStartPrice = lot.getFirstStartPrice();
        this.factPrice = lot.getFactPrice();
        this.customerName = lot.getCustomerName();
        this.status = lot.getStatus();
        this.actSignedDate = lot.getActSignedDate();
        if(lot.getUser()!=null)
        this.userId = lot.getUser().getId();
        if(lot.getBid()!=null)
        this.bidId = lot.getBid().getId();
    }

    public LotHistory(Date changeDate, String userName, Long id, String lotNum, String workStage, Boolean isItSold, String comment, Date lotDate, String bidStage, int countOfParticipants, BigDecimal startPrice, BigDecimal firstStartPrice, BigDecimal factPrice, String customerName, String status, Date actSignedDate, User user, Bid bid) {
        this.changeDate = changeDate;
        this.userName = userName;
        this.id = id;
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
        this.actSignedDate = actSignedDate;
        this.userId = user.getId();
        this.bidId = bid.getId();
    }

}