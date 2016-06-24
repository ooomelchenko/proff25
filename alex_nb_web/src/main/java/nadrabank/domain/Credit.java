package nadrabank.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

@Entity
@Table(name = "Credits")
public class Credit implements Serializable {
    private static final SimpleDateFormat sdfshort = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "Credit_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name = "ND_NLS")
    private Long id;
    @Column(name = "REGION")
    private String region;
    @Column(name = "MFO")
    private String mfo;
    @Column(name = "TYPE_CODE")
    private String assetTypeCode;
    @Column(name = "GROUP_CODE")
    private String assetGroupCode;
    @Column(name = "TYPE_CLIENT")
    private String clientType;
    @Column(name = "PRODUCT")
    private String product;
    @Column(name = "ZAST")
    private String zast;
    @Column(name = "F_IDCODE")
    private String inn;
    @Column(name = "CLIENT_NAME")
    private String fio;
    @Column(name = "AGREEMENT_NUMBER")
    private String contractNum;
    @Column(name = "START_DATE")
    private Date contractStart;
    @Column(name = "END_DATE")
    private Date contractEnd;
    @Column(name = "FX_NUMBER")
    private String curr;
    @Column(name = "INTEREST")
    private Double interestRate;
    @Column(name = "CONTRACT_SUM_FX")
    private Double contractSumVal;
    @Column(name = "CONTRACT_SUM_UAH")
    private Double contractSumUAH;
    @Column(name = "ACA_UAH_EVA")
    private Double zbRateDay;
    @Column(name = "BODY_UAH")
    private Double bodyUAH;
    @Column(name = "PRC_UAH")
    private Double prcUAH;
    @Column(name = "COMMIS")
    private Double comission;
    @Column(name = "ACA_UAH")
    private Double zb;
    @Column(name = "DPD_NEW")
    private int dpd;
    @Column(name = "FDAT")
    private Date lastPayDate;
    @Column(name = "RV_BEZ_PDV_UAH")
    private Double ratingPriceNoPDV;
    @Column(name = "RV_UAH")
    private Double creditPrice;
    @Column(name = "KAT")
    private String nbuRate;
    @Column(name = "FIN")
    private String ownerClass;
    @Column(name = "TIP_ZASTAVI")
    private String gageType;
    @Column(name = "VID_ZASTAVI")
    private String gageVid;
    @Column(name = "S031")
    private String gageCode;
    @Column(name = "RV_DISCOUNT_UAH")
    private Double discountPrice;
    @Column(name = "FACT_SALE_PRICE_UAH")
    private Double factPrice;
    @Column(name = "IS_IT_SOLD")
    private Boolean isSold;

    @ManyToOne
    private Lot lot;//класс

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }

    public String getMfo() {
        return mfo;
    }
    public void setMfo(String mfo) {
        this.mfo = mfo;
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

    public String getClientType() {
        return clientType;
    }
    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getProduct() {
        return product;
    }
    public void setProduct(String product) {
        this.product = product;
    }

    public String getZast() {
        return zast;
    }
    public void setZast(String zast) {
        this.zast = zast;
    }

    public String getInn() {
        return inn;
    }
    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getFio() {
        return fio;
    }
    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getContractNum() {
        return contractNum;
    }
    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public Date getContractStart() {
        return contractStart;
    }
    public void setContractStart(Date contractStart) {
        this.contractStart = contractStart;
    }

    public Date getContractEnd() {
        return contractEnd;
    }
    public void setContractEnd(Date contractEnd) {
        this.contractEnd = contractEnd;
    }

    public String getCurr() {
        return curr;
    }
    public void setCurr(String curr) {
        this.curr = curr;
    }

    public Double getInterestRate() {
        return interestRate;
    }
    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getContractSumVal() {
        return contractSumVal;
    }
    public void setContractSumVal(Double contractSumVal) {
        this.contractSumVal = contractSumVal;
    }

    public Double getContractSumUAH() {
        return contractSumUAH;
    }
    public void setContractSumUAH(Double contractSumUAH) {
        this.contractSumUAH = contractSumUAH;
    }

    public Double getZbRateDay() {
        return zbRateDay;
    }
    public void setZbRateDay(Double zbRateDay) {
        this.zbRateDay = zbRateDay;
    }

    public Double getBodyUAH() {
        return bodyUAH;
    }
    public void setBodyUAH(Double bodyUAH) {
        this.bodyUAH = bodyUAH;
    }

    public Double getPrcUAH() {
        return prcUAH;
    }
    public void setPrcUAH(Double prcUAH) {
        this.prcUAH = prcUAH;
    }

    public Double getComission() {
        return comission;
    }
    public void setComission(Double comission) {
        this.comission = comission;
    }

    public Double getZb() {
        return zb;
    }
    public void setZb(Double zb) {
        this.zb = zb;
    }

    public int getDpd() {
        return dpd;
    }
    public void setDpd(int dpd) {
        this.dpd = dpd;
    }

    public Date getLastPayDate() {
        return lastPayDate;
    }
    public void setLastPayDate(Date lastPayDate) {
        this.lastPayDate = lastPayDate;
    }

    public Double getCreditPrice() {
        return creditPrice;
    }
    public void setCreditPrice(Double creditPrice) {
        this.creditPrice = creditPrice;
    }

    public String getNbuRate() {
        return nbuRate;
    }
    public void setNbuRate(String nbuRate) {
        this.nbuRate = nbuRate;
    }

    public String getOwnerClass() {
        return ownerClass;
    }
    public void setOwnerClass(String ownerClass) {
        this.ownerClass = ownerClass;
    }

    public String getGageType() {
        return gageType;
    }
    public void setGageType(String gageType) {
        this.gageType = gageType;
    }

    public String getGageVid() {
        return gageVid;
    }
    public void setGageVid(String gageVid) {
        this.gageVid = gageVid;
    }

    public String getGageCode() {
        return gageCode;
    }
    public void setGageCode(String gageCode) {
        this.gageCode = gageCode;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }
    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Double getFactPrice() {
        return factPrice;
    }
    public void setFactPrice(Double factPrice) {
        this.factPrice = factPrice;
    }

    public Boolean getIsSold() {
        return isSold;
    }
    public void setIsSold(Boolean isSold) {
        this.isSold = isSold;
    }

    public Lot getLot() {
        return lot;
    }
    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public Double getRatingPriceNoPDV() {
        return ratingPriceNoPDV;
    }
    public void setRatingPriceNoPDV(Double ratingPriceNoPDV) {
        this.ratingPriceNoPDV = ratingPriceNoPDV;
    }

    public Credit() {
    }

    public Credit(String region, String mfo, String assetTypeCode, String assetGroupCode, String clientType, String product, String zast, String inn, String fio, String contractNum, Date contractStart, Date contractEnd, String curr, Double interestRate, Double contractSumVal, Double contractSumUAH, Double zbRateDay, Double bodyUAH, Double prcUAH, Double comission, Double zb, int dpd, Date lastPayDate, Double ratingPriceNoPDV, Double creditPrice, String nbuRate, String ownerClass, String gageType, String gageVid, String gageCode, Double discountPrice, Double factPrice, Boolean isSold, Lot lot) {
        this.region = region;
        this.mfo = mfo;
        this.assetTypeCode = assetTypeCode;
        this.assetGroupCode = assetGroupCode;
        this.clientType = clientType;
        this.product = product;
        this.zast = zast;
        this.inn = inn;
        this.fio = fio;
        this.contractNum = contractNum;
        this.contractStart = contractStart;
        this.contractEnd = contractEnd;
        this.curr = curr;
        this.interestRate = interestRate;
        this.contractSumVal = contractSumVal;
        this.contractSumUAH = contractSumUAH;
        this.zbRateDay = zbRateDay;
        this.bodyUAH = bodyUAH;
        this.prcUAH = prcUAH;
        this.comission = comission;
        this.zb = zb;
        this.dpd = dpd;
        this.lastPayDate = lastPayDate;
        this.ratingPriceNoPDV = ratingPriceNoPDV;
        this.creditPrice = creditPrice;
        this.nbuRate = nbuRate;
        this.ownerClass = ownerClass;
        this.gageType = gageType;
        this.gageVid = gageVid;
        this.gageCode = gageCode;
        this.discountPrice = discountPrice;
        this.factPrice = factPrice;
        this.isSold = isSold;
        this.lot = lot;
    }

    public Credit(String region, String mfo, String assetTypeCode, String assetGroupCode, String clientType, String product, String zast, String inn, String fio, String contractNum, Date contractStart, Date contractEnd, String curr, Double interestRate, Double contractSumVal, Double contractSumUAH, Double zbRateDay, Double bodyUAH, Double prcUAH, Double comission, Double zb, int dpd, Date lastPayDate, Double ratingPriceNoPDV, Double creditPrice, String nbuRate, String ownerClass, String gageType, String gageVid, String gageCode, Double discountPrice, Double factPrice, Boolean isSold) {
        this.region = region;
        this.mfo = mfo;
        this.assetTypeCode = assetTypeCode;
        this.assetGroupCode = assetGroupCode;
        this.clientType = clientType;
        this.product = product;
        this.zast = zast;
        this.inn = inn;
        this.fio = fio;
        this.contractNum = contractNum;
        this.contractStart = contractStart;
        this.contractEnd = contractEnd;
        this.curr = curr;
        this.interestRate = interestRate;
        this.contractSumVal = contractSumVal;
        this.contractSumUAH = contractSumUAH;
        this.zbRateDay = zbRateDay;
        this.bodyUAH = bodyUAH;
        this.prcUAH = prcUAH;
        this.comission = comission;
        this.zb = zb;
        this.dpd = dpd;
        this.lastPayDate = lastPayDate;
        this.ratingPriceNoPDV = ratingPriceNoPDV;
        this.creditPrice = creditPrice;
        this.nbuRate = nbuRate;
        this.ownerClass = ownerClass;
        this.gageType = gageType;
        this.gageVid = gageVid;
        this.gageCode = gageCode;
        this.discountPrice = discountPrice;
        this.factPrice = factPrice;
        this.isSold = isSold;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "id=" + id +
                ", region='" + region + '\'' +
                ", mfo='" + mfo + '\'' +
                ", assetTypeCode='" + assetTypeCode + '\'' +
                ", assetGroupCode='" + assetGroupCode + '\'' +
                ", clientType='" + clientType + '\'' +
                ", product='" + product + '\'' +
                ", zast='" + zast + '\'' +
                ", inn='" + inn + '\'' +
                ", fio='" + fio + '\'' +
                ", contractNum='" + contractNum + '\'' +
                ", contractStart=" + contractStart +
                ", contractEnd=" + contractEnd +
                ", curr='" + curr + '\'' +
                ", interestRate=" + interestRate +
                ", contractSumVal=" + contractSumVal +
                ", contractSumUAH=" + contractSumUAH +
                ", zbRateDay=" + zbRateDay +
                ", bodyUAH=" + bodyUAH +
                ", prcUAH=" + prcUAH +
                ", comission=" + comission +
                ", zb=" + zb +
                ", dpd=" + dpd +
                ", lastPayDate=" + lastPayDate +
                ", ratingPriceNoPDV=" + ratingPriceNoPDV +
                ", creditPrice=" + creditPrice +
                ", nbuRate='" + nbuRate + '\'' +
                ", ownerClass='" + ownerClass + '\'' +
                ", gageType='" + gageType + '\'' +
                ", gageVid='" + gageVid + '\'' +
                ", gageCode='" + gageCode + '\'' +
                ", discountPrice=" + discountPrice +
                ", factPrice=" + factPrice +
                ", isSold=" + isSold +
                ", lot=" + lot +
                '}';
    }

    public String toShotString() {
        String startDate="";
        String endDate="";
        if(contractStart!=null){startDate=sdfshort.format(contractStart);}
        if(contractEnd!=null){endDate=sdfshort.format(contractEnd);}
        return
                "|"+inn + '|' +
                        contractNum + '|' +
                        fio+ '|' +
                        region + '|' +
                        assetTypeCode + '|' +
                        assetGroupCode+ '|' +
                        clientType+ '|' +
                        startDate+ '|' +
                        endDate+ '|' +
                        curr + '|' +
                        product+ '|' +
                        zb+ '|' +
                        dpd+ '|' +
                        creditPrice;
    }

    public String toShotStr() {
        Formatter f0 = new Formatter();
        Formatter f1 = new Formatter();
        Formatter f2 = new Formatter();
        Formatter f3 = new Formatter();
        String startDate="";
        String endDate="";
        if(contractStart!=null){startDate=sdfshort.format(contractStart);}
        if(contractEnd!=null){endDate=sdfshort.format(contractEnd);}
        return
                "|"+inn + '|' +
                        contractNum + '|' +
                        fio+ '|' +
                        region + '|' +
                        assetTypeCode + '|' +
                        assetGroupCode+ '|' +
                        clientType+ '|' +
                        startDate+ '|' +
                        endDate+ '|' +
                        curr + '|' +
                        product+ '|' +
                        f0.format("%,.2f", zb).toString()+ '|' +
                        dpd+ '|' +
                        f1.format("%,.2f", creditPrice).toString()+ '|' +
                        f2.format("%,.2f", discountPrice).toString()+ '|' +
                        f3.format("%,.2f", factPrice).toString();
    }

}