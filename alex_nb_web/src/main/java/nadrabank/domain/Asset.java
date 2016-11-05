package nadrabank.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ASSETS")
public class Asset implements Serializable {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "Assets_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name = "MY_ID")
    private Long id;
    @Column(name = "TYPE_CODE")
    private String assetTypeCode;
    @Column(name = "GROUP_CODE")
    private String assetGroupCode;
    @Column(name = "INVENT")
    private String inn;
    @Column(name = "ASSET_NAME")
    private String asset_name;
    @Column(name = "ASSET_DESCRIPTION")
    private String asset_descr;
    @Column(name = "VIDDIL")
    private String viddil;
    @Column(name = "BALANCE_ACCOUNT")
    private String balanceAccount;
    @Column(name = "EKSPL_VVedeno_DATE")
    private Date eksplDate;
    @Column(name = "PERVISNA_VARTIST_UAH")
    private BigDecimal originalPrice;
    @Column(name = "BALANCE_COST_UAH")
    private BigDecimal zb;
    @Column(name = "RV_BEZ_PDV_UAH")
    private BigDecimal rvNoPdv;
    @Column(name = "RV_UAH")
    private BigDecimal rv;
    @Column(name = "STAN_OCINKI")
    private String evaluationStatus;
    @Column(name = "REGION")
    private String region;
    /*@Column(name = "BID_PRICE_UAH")
    private BigDecimal bidPrice;*/
    @Column(name = "FACT_SALE_PRICE_UAH")
    private BigDecimal factPrice;
    @Column(name = "IS_IT_SOLD")
    private boolean isSold;
    @Column(name = "NBU_APPROVE")
    private boolean approveNBU;
    @Column(name = "FOND_DEC_DATE")
    private Date fondDecisionDate;
    @Column(name = "FOND_DECISION")
    private String fondDecision;
    @Column(name = "FOND_DECISION_NUM")
    private String decisionNumber;
    @Column(name = "ACCEPTED_PRICE")
    private BigDecimal acceptPrice;
    @Column(name = "PROPOSITION")
    private String proposition;
    @Column(name = "PAYMENTS_BID")
    private BigDecimal paysBid;
    @Column(name = "PAYMENTS_CUSTOMER")
    private BigDecimal paysCustomer;
    @Column(name = "LAST_BID_PAY_DATE")
    private Date bidPayDate;
    @Column(name = "LAST_CUSTOMER_PAY_DATE")
    private Date customerPayDate;
    @Column(name = "PLAN_SALE_DATE")
    private Date planSaleDate;

    @ManyToOne
    private Lot lot;//класс

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getAssetTypeCode() {
        return assetTypeCode;
    }
    public void setAssetTypeCode(String assetTypeCode) {
        this.assetTypeCode = assetTypeCode;
    }

    public String getAssetGroupCode() {
        return assetGroupCode;
    }
    public void setAssetGroupCode(String assetGroupCode) {
        this.assetGroupCode = assetGroupCode;
    }

    public String getInn() {
        return inn;
    }
    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getAsset_name() {
        return asset_name;
    }
    public void setAsset_name(String asset_name) {
        this.asset_name = asset_name;
    }

    public String getAsset_descr() {
        return asset_descr;
    }
    public void setAsset_descr(String asset_descr) {
        this.asset_descr = asset_descr;
    }

    public String getViddil() {
        return viddil;
    }
    public void setViddil(String viddil) {
        this.viddil = viddil;
    }

    public String getBalanceAccount() {
        return balanceAccount;
    }
    public void setBalanceAccount(String balanceAccount) {
        this.balanceAccount = balanceAccount;
    }

    public Date getEksplDate() {
        return eksplDate;
    }
    public void setEksplDate(Date eksplDate) {
        this.eksplDate = eksplDate;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }
    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getZb() {
        return zb;
    }
    public void setZb(BigDecimal zb) {
        this.zb = zb;
    }

    public BigDecimal getRvNoPdv() {
        return rvNoPdv;
    }
    public void setRvNoPdv(BigDecimal rvNoPdv) {
        this.rvNoPdv = rvNoPdv;
    }

    public BigDecimal getRv() {
        return rv;
    }
    public void setRv(BigDecimal rv) {
        this.rv = rv;
    }

    public String getEvaluationStatus() {
        return evaluationStatus;
    }
    public void setEvaluationStatus(String evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }

    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }

    public BigDecimal getFactPrice() {
        return factPrice;
    }
    public void setFactPrice(BigDecimal factPrice) {
        this.factPrice = factPrice;
    }

    public boolean isSold() {
        return isSold;
    }
    public void setSold(boolean sold) {
        isSold = sold;
    }

    public Lot getLot() {
        return lot;
    }
    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public boolean isApproveNBU() {
        return approveNBU;
    }
    public void setApproveNBU(boolean approveNBU) {
        this.approveNBU = approveNBU;
    }

    public Date getFondDecisionDate() {
        return fondDecisionDate;
    }
    public void setFondDecisionDate(Date fondDecisionDate) {
        this.fondDecisionDate = fondDecisionDate;
    }

    public String getFondDecision() {
        return fondDecision;
    }
    public void setFondDecision(String fondDecision) {
        this.fondDecision = fondDecision;
    }

    public String getDecisionNumber() {
        return decisionNumber;
    }
    public void setDecisionNumber(String decisionNumber) {
        this.decisionNumber = decisionNumber;
    }

    public BigDecimal getAcceptPrice() {
        return acceptPrice;
    }
    public void setAcceptPrice(BigDecimal acceptPrice) {
        this.acceptPrice = acceptPrice;
    }

    public String getProposition() {
        return proposition;
    }
    public void setProposition(String proposition) {
        this.proposition = proposition;
    }

    public BigDecimal getPaysBid() {
        return paysBid;
    }
    public void setPaysBid(BigDecimal paysBid) {
        this.paysBid = paysBid;
    }

    public BigDecimal getPaysCustomer() {
        return paysCustomer;
    }
    public void setPaysCustomer(BigDecimal paysCustomer) {
        this.paysCustomer = paysCustomer;
    }

    public Date getBidPayDate() {
        return bidPayDate;
    }
    public void setBidPayDate(Date bidPayDate) {
        this.bidPayDate = bidPayDate;
    }

    public Date getCustomerPayDate() {
        return customerPayDate;
    }
    public void setCustomerPayDate(Date customerPayDate) {
        this.customerPayDate = customerPayDate;
    }

    public Date getPlanSaleDate() {
        return planSaleDate;
    }
    public void setPlanSaleDate(Date planSaleDate) {
        this.planSaleDate = planSaleDate;
    }

    public Asset() {
    }

    @Override
    public String toString() {
        return "Asset{" +
                "id=" + id +
                ", assetTypeCode='" + assetTypeCode + '\'' +
                ", assetGroupCode='" + assetGroupCode + '\'' +
                ", inn='" + inn + '\'' +
                ", asset_name='" + asset_name + '\'' +
                ", asset_descr='" + asset_descr + '\'' +
                ", viddil='" + viddil + '\'' +
                ", balanceAccount='" + balanceAccount + '\'' +
                ", eksplDate=" + eksplDate +
                ", originalPrice=" + originalPrice +
                ", zb=" + zb +
                ", rvNoPdv=" + rvNoPdv +
                ", rv=" + rv +
                ", evaluationStatus='" + evaluationStatus + '\'' +
                ", region='" + region + '\'' +
                ", factPrice=" + factPrice +
                ", isSold=" + isSold +
                ", approveNBU=" + approveNBU +
                ", fondDecisionDate=" + fondDecisionDate +
                ", fondDecision='" + fondDecision + '\'' +
                ", decisionNumber='" + decisionNumber + '\'' +
                ", acceptPrice=" + acceptPrice +
                ", proposition='" + proposition + '\'' +
                ", paysBid=" + paysBid +
                ", paysCustomer=" + paysCustomer +
                ", bidPayDate=" + bidPayDate +
                ", customerPayDate=" + customerPayDate +
                ", planSaleDate=" + planSaleDate +
                ", lot=" + lot +
                '}';
    }
}
