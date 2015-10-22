package nadrabank.domain;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="EQUIPMENT")
public class Equipment {
    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "EQUIP_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name = "ID")
    private Long id;
    @Column(name = "ID_SALEBASE")
    private int idBase;
    @Column(name = "MBO")
    private String mbo;
    @Column (name= "Invent_number")
    private String invNum;
    @Column (name= "Balance_count")
    private String balCount;
    @Column(name = "Key_Asset_Group")
    private int keyAssGr;
    @Column(name = "Asset_Name")
    private String assetName;
    @Column(name = "Stuff_Name")
    private String stuffName;
    @Column(name = "Model_Description")
    private String modelDescription;
    @Column(name = "Region_Name")
    private String region;
    @Column(name = "Location")
    private String location;
    @Column(name = "Storage")
    private String storage;
    @Column(name = "Condition")
    private String condition;
    @Column(name = "Damage_Description")
    private String damageDescr;
    @Column(name = "DATE_BALANSE")
    private Date dateIntroBalance;
    @Column(name = "Cost_At_BalanceDate")
    private Double costAtBalanceDate;
    @Column(name = "Residual_Count")
    private int residualCount;
    @Column(name = "Residual_Cost")
    private Double residualCost;
    @Column(name = "Fact_Count")
    private int factCount;
    @Column(name = "Fact_Cost")
    private Double factCost;
    @Column(name = "Overplus_count")
    private int overPlusCount;
    @Column(name = "Overplus_sum")
    private Double overPlusSum;
    @Column(name = "Absence_count")
    private int absenceCount;
    @Column(name = "Absence_sum")
    private Double absenceSum;
    @Column(name = "Out_Reason")
    private String outReason;

/*    @ManyToOne
    private Lot lot;//класс*/

    //Getters&Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getIdBase() {
        return idBase;
    }
    public void setIdBase(int idBase) {
        this.idBase = idBase;
    }
    public String getMbo() {
        return mbo;
    }
    public void setMbo(String mbo) {
        this.mbo = mbo;
    }
    public String getInvNum() {
        return invNum;
    }
    public void setInvNum(String invNum) {
        this.invNum = invNum;
    }
    public String getBalCount() {
        return balCount;
    }
    public void setBalCount(String balCount) {
        this.balCount = balCount;
    }
    public int getKeyAssGr() {
        return keyAssGr;
    }
    public void setKeyAssGr(int keyAssGr) {
        this.keyAssGr = keyAssGr;
    }
    public String getAssetName() {
        return assetName;
    }
    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }
    public String getStuffName() {
        return stuffName;
    }
    public void setStuffName(String stuffName) {
        this.stuffName = stuffName;
    }
    public String getModelDescription() {
        return modelDescription;
    }
    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getStorage() {
        return storage;
    }
    public void setStorage(String storage) {
        this.storage = storage;
    }
    public String getCondition() {
        return condition;
    }
    public void setCondition(String condition) {
        this.condition = condition;
    }
    public String getDamageDescr() {
        return damageDescr;
    }
    public void setDamageDescr(String damageDescr) {
        this.damageDescr = damageDescr;
    }
    public Date getDateIntroBalance() {
        return dateIntroBalance;
    }
    public void setDateIntroBalance(Date dateIntroBalance) {
        this.dateIntroBalance = dateIntroBalance;
    }
    public Double getCostAtBalanceDate() {
        return costAtBalanceDate;
    }
    public void setCostAtBalanceDate(Double costAtBalanceDate) {
        this.costAtBalanceDate = costAtBalanceDate;
    }
    public int getResidualCount() {
        return residualCount;
    }
    public void setResidualCount(int residualCount) {
        this.residualCount = residualCount;
    }
    public Double getResidualCost() {
        return residualCost;
    }
    public void setResidualCost(Double residualCost) {
        this.residualCost = residualCost;
    }
    public int getFactCount() {
        return factCount;
    }
    public void setFactCount(int factCount) {
        factCount = factCount;
    }
    public Double getFactCost() {
        return factCost;
    }
    public void setFactCost(Double factCost) {
        this.factCost = factCost;
    }
    public int getOverPlusCount() {
        return overPlusCount;
    }
    public void setOverPlusCount(int overPlusCount) {
        this.overPlusCount = overPlusCount;
    }
    public Double getOverPlusSum() {
        return overPlusSum;
    }
    public void setOverPlusSum(Double overPlusSum) {
        this.overPlusSum = overPlusSum;
    }
    public int getAbsenceCount() {
        return absenceCount;
    }
    public void setAbsenceCount(int absenceCount) {
        this.absenceCount = absenceCount;
    }
    public Double getAbsenceSum() {
        return absenceSum;
    }
    public void setAbsenceSum(Double absenceSum) {
        this.absenceSum = absenceSum;
    }
    public String getOutReason() {
        return outReason;
    }
    public void setOutReason(String outReason) {
        this.outReason = outReason;
    }
/*    public Lot getLot() {
        return lot;
    }
    public void setLot(Lot lot) {
        this.lot = lot;
    }*/

//Конструктора

    public Equipment() {
        idBase = 0;
        mbo = "defoult";
        invNum = "defoult";
        balCount = "defoult";
        keyAssGr = 0;
        assetName = "defoult";
        stuffName = "defoult";
        modelDescription = "defoult";
        region = "defoult";
        location = "defoult";
        storage = "defoult";
        condition = "defoult";
        damageDescr = "defoult";
        dateIntroBalance = new Date();
        costAtBalanceDate = 0.;
        residualCount = 0;
        residualCost = 0.;
        factCount = 0;
        factCost = 0.;
        overPlusCount = 0;
        overPlusSum = 0.;
        absenceCount = 0;
        absenceSum = 0.;
        outReason = "defoult";
    }
    public Equipment(int idBase) {
        this.idBase = idBase;
        mbo = "defoult";
        invNum = "defoult";
        balCount = "defoult";
        keyAssGr = 0;
        assetName = "defoult";
        stuffName = "defoult";
        modelDescription = "defoult";
        region = "defoult";
        location = "defoult";
        storage = "defoult";
        condition = "defoult";
        damageDescr = "defoult";
        dateIntroBalance = new Date();
        costAtBalanceDate = 0.;
        residualCount = 0;
        residualCost = 0.;
        factCount = 0;
        factCost = 0.;
        overPlusCount = 0;
        overPlusSum = 0.;
        absenceCount = 0;
        absenceSum = 0.;
        outReason = "defoult";
    }
   /* public Equipment(int idBase, String mbo, String invNum, String balCount, int keyAssGr, String assetName, String stuffName, String modelDescription, String region, String location, String storage, String condition, String damageDescr, Date dateIntroBalance, Double costAtBalanceDate, int residualCount, Double residualCost, int factCount, Double factCost, int overPlusCount, Double overPlusSum, int absenceCount, Double absenceSum, String outReason) {
        this.idBase = idBase;
        this.mbo = mbo;
        this.invNum = invNum;
        this.balCount = balCount;
        this.keyAssGr = keyAssGr;
        this.assetName = assetName;
        this.stuffName = stuffName;
        this.modelDescription = modelDescription;
        this.region = region;
        this.location = location;
        this.storage = storage;
        this.condition = condition;
        this.damageDescr = damageDescr;
        this.dateIntroBalance = dateIntroBalance;
        this.costAtBalanceDate = costAtBalanceDate;
        this.residualCount = residualCount;
        this.residualCost = residualCost;
        this.factCount = factCount;
        this.factCost = factCost;
        this.overPlusCount = overPlusCount;
        this.overPlusSum = overPlusSum;
        this.absenceCount = absenceCount;
        this.absenceSum = absenceSum;
        this.outReason = outReason;
    }
    public Equipment(int idBase, String mbo, String invNum, String balCount, int keyAssGr, String assetName, String stuffName, String modelDescription, String region, String location, String storage, String condition, String damageDescr, Date dateIntroBalance, Double costAtBalanceDate, int residualCount, Double residualCost, int factCount, Double factCost, int overPlusCount, Double overPlusSum, int absenceCount, Double absenceSum, String outReason, Lot lot) {
        this.idBase = idBase;
        this.mbo = mbo;
        this.invNum = invNum;
        this.balCount = balCount;
        this.keyAssGr = keyAssGr;
        this.assetName = assetName;
        this.stuffName = stuffName;
        this.modelDescription = modelDescription;
        this.region = region;
        this.location = location;
        this.storage = storage;
        this.condition = condition;
        this.damageDescr = damageDescr;
        this.dateIntroBalance = dateIntroBalance;
        this.costAtBalanceDate = costAtBalanceDate;
        this.residualCount = residualCount;
        this.residualCost = residualCost;
        this.factCount = factCount;
        this.factCost = factCost;
        this.overPlusCount = overPlusCount;
        this.overPlusSum = overPlusSum;
        this.absenceCount = absenceCount;
        this.absenceSum = absenceSum;
        this.outReason = outReason;
        this.lot = lot;
    }*/
}