package nadrabank.domain;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Equipments")
public class Equipment {
    @Id
    @SequenceGenerator(name = "sequenc", sequenceName = "Equipment_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenc")
    @Column(name = "ID")
    private Long id;
    @Column(name = "Asset_Name")
    private String assetName;
    @Column(name = "Base_ID")
    private String baseID;
    @Column(name = "MFO")
    private int mfo;
    @Column(name = "Asset_group_code")
    private int assGrpKod;
    @Column(name = "Asset_podgroup_code")
    private int assPodgrpKod;
    @Column(name = "Asset_podgroup_name")
    private String assPodgrpName;
    @Column(name = "Inventar_number")
    private String inventNum;
    @Column(name = "Stuff_name")
    private String stuffName;
    @Column(name = "Region")
    private String region;
    @Column(name = "Locality")
    private String locality;
    @Column(name = "Fillial")
    private int fillialNum;
    @Column(name = "Balance_count")
    private Long balCount;
    @Column(name = "Balance_date")
    private Date balDate;
    @Column(name = "Rresidual_cost_UAH")
    private Double residualCost;
    @Column(name = "Market_price_PDV")
    private Double marketPrice;
   @Column(name = "Condition")
    private String condition;
    @Column(name = "Rater")
    private String raterName;
    @Column(name = "Last_Rate_Date")
    private Date lastRateDate;
    @Column(name = "Plan_Sale_Date")
    private Date planSaleDate;
    @Column(name = "Adv_Marketing_ActionPlan")
    private String actionPlan;
    @Column(name = "Car_Location")
    private String carLocation;
    @Column(name = "Car_Number")
    private String carNum;
    @Column(name = "VIN_Code")
    private String vinCode;
    @Column(name = "Manuf_year")
    private int year;
    @Column(name = "Fuel_Type")
    private String fuelType;
    @Column(name = "Was_Crashed")
    private String wasItCrashed;
    @Column(name = "Transmission")
    private String transmission;
    @Column(name = "Color")
    private String color;
    @Column(name = "Engine_Capacity")
    private String engineCapacity;
    @Column(name = "Range_km")
    private int range;
    @Column(name = "Property_Type")
    private String propertyType;
    @Column(name = "Street_H")
    private String street;
    @Column(name = "House_num")
    private String house;
    @Column(name = "Room_num")
    private String room;
    @Column(name = "Square_total")
    private int squareSum;
    @Column(name = "Square_Live")
    private int squareLive;
    @Column(name = "Room_count")
    private int roomCount;
    @Column(name = "Floor_CountFloor")
    private String floor_countFloor;
    @Column(name = "Gas")
    private String isGas;
    @Column(name = "Water")
    private String isWater;
    @Column(name = "Electric")
    private String isElectric;
    @Column(name = "Land_Purpose")
    private String landPurpose;
    @Column(name = "Kadastr_number")
    private String kadastrNum;
    @Column(name = "Model_Description")
    private String modelDescr;
    @Column(name = "Storage")
    private String storage;
    @Column(name = "Condition_Inspect")
    private String conditionInspect;
    @Column(name = "Damage_Descr")
    private String damageDescr;
    @Column(name = "Ekspluat_From_Date")
    private Date usingFromDate;
    @Column(name = "Cost_at_Ekspluat_Date_UAH")
    private Double costAtEksplDate;
    @Column(name = "Residual_Cost_At_Balance_UAH")
    private Double residualCostAtBalance;
    @ManyToOne
    private Lot lot;//класс

    //Getters&Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getAssetName() {
        return assetName;
    }
    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getBaseID() {
        return baseID;
    }
    public void setBaseID(String baseID) {
        this.baseID = baseID;
    }

    public int getMfo() {
        return mfo;
    }
    public void setMfo(int mfo) {
        this.mfo = mfo;
    }

    public int getAssGrpKod() {
        return assGrpKod;
    }
    public void setAssGrpKod(int assGrpKod) {
        this.assGrpKod = assGrpKod;
    }

    public int getAssPodgrpKod() {
        return assPodgrpKod;
    }
    public void setAssPodgrpKod(int assPodgrpKod) {
        this.assPodgrpKod = assPodgrpKod;
    }

    public String getAssPodgrpName() {
        return assPodgrpName;
    }
    public void setAssPodgrpName(String assPodgrpName) {
        this.assPodgrpName = assPodgrpName;
    }

    public String getInventNum() {
        return inventNum;
    }
    public void setInventNum(String inventNum) {
        this.inventNum = inventNum;
    }

    public String getStuffName() {
        return stuffName;
    }
    public void setStuffName(String stuffName) {
        this.stuffName = stuffName;
    }

    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }

    public String getLocality() {
        return locality;
    }
    public void setLocality(String locality) {
        this.locality = locality;
    }

    public int getFillialNum() {
        return fillialNum;
    }
    public void setFillialNum(int fillialNum) {
        this.fillialNum = fillialNum;
    }

    public Long getBalCount() {
        return balCount;
    }
    public void setBalCount(Long balCount) {
        this.balCount = balCount;
    }

    public Date getBalDate() {
        return balDate;
    }
    public void setBalDate(Date balDate) {
        this.balDate = balDate;
    }

    public Double getResidualCost() {
        return residualCost;
    }
    public void setResidualCost(Double residualCost) {
        this.residualCost = residualCost;
    }

    public Double getMarketPrice() {
        return marketPrice;
    }
    public void setMarketPrice(Double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getCondition() {
        return condition;
    }
    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getRaterName() {
        return raterName;
    }
    public void setRaterName(String raterName) {
        this.raterName = raterName;
    }

    public Date getLastRateDate() {
        return lastRateDate;
    }
    public void setLastRateDate(Date lastRateDate) {
        this.lastRateDate = lastRateDate;
    }

    public Date getPlanSaleDate() {
        return planSaleDate;
    }
    public void setPlanSaleDate(Date planSaleDate) {
        this.planSaleDate = planSaleDate;
    }

    public String getActionPlan() {
        return actionPlan;
    }
    public void setActionPlan(String actionPlan) {
        this.actionPlan = actionPlan;
    }

    public String getCarLocation() {
        return carLocation;
    }
    public void setCarLocation(String carLocation) {
        this.carLocation = carLocation;
    }

    public String getCarNum() {
        return carNum;
    }
    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getVinCode() {
        return vinCode;
    }
    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public String getFuelType() {
        return fuelType;
    }
    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getWasItCrashed() {
        return wasItCrashed;
    }
    public void setWasItCrashed(String wasItCrashed) {
        this.wasItCrashed = wasItCrashed;
    }

    public String getTransmission() {
        return transmission;
    }
    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }
    public void setEngineCapacity(String engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public int getRange() {
        return range;
    }
    public void setRange(int range) {
        this.range = range;
    }

    public String getPropertyType() {
        return propertyType;
    }
    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }
    public void setHouse(String house) {
        this.house = house;
    }

    public String getRoom() {
        return room;
    }
    public void setRoom(String room) {
        this.room = room;
    }

    public int getRoomCount() {
        return roomCount;
    }
    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public String getFloor_countFloor() {
        return floor_countFloor;
    }
    public void setFloor_countFloor(String floor_countFloor) {
        this.floor_countFloor = floor_countFloor;
    }

    public String getIsGas() {
        return isGas;
    }
    public void setIsGas(String isGas) {
        this.isGas = isGas;
    }

    public String getIsWater() {
        return isWater;
    }
    public void setIsWater(String isWater) {
        this.isWater = isWater;
    }

    public String getIsElectric() {
        return isElectric;
    }
    public void setIsElectric(String isElectric) {
        this.isElectric = isElectric;
    }

    public String getLandPurpose() {
        return landPurpose;
    }
    public void setLandPurpose(String landPurpose) {
        this.landPurpose = landPurpose;
    }

    public String getKadastrNum() {
        return kadastrNum;
    }
    public void setKadastrNum(String kadastrNum) {
        this.kadastrNum = kadastrNum;
    }

    public String getModelDescr() {
        return modelDescr;
    }
    public void setModelDescr(String modelDescr) {
        this.modelDescr = modelDescr;
    }

    public String getStorage() {
        return storage;
    }
    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getConditionInspect() {
        return conditionInspect;
    }
    public void setConditionInspect(String conditionInspect) {
        this.conditionInspect = conditionInspect;
    }

    public String getDamageDescr() {
        return damageDescr;
    }
    public void setDamageDescr(String damageDescr) {
        this.damageDescr = damageDescr;
    }

    public Date getUsingFromDate() {
        return usingFromDate;
    }
    public void setUsingFromDate(Date usingFromDate) {
        this.usingFromDate = usingFromDate;
    }

    public Double getCostAtEksplDate() {
        return costAtEksplDate;
    }
    public void setCostAtEksplDate(Double costAtEksplDate) {
        this.costAtEksplDate = costAtEksplDate;
    }

    public Double getResidualCostAtBalance() {
        return residualCostAtBalance;
    }
    public void setResidualCostAtBalance(Double residualCostAtBalance) {
        this.residualCostAtBalance = residualCostAtBalance;
    }

    public int getSquareSum() {
        return squareSum;
    }
    public void setSquareSum(int squareSum) {
        this.squareSum = squareSum;
    }

    public int getSquareLive() {
        return squareLive;
    }
    public void setSquareLive(int squareLive) {
        this.squareLive = squareLive;
    }

    public Lot getLot() {
        return lot;
    }
    public void setLot(Lot lot) {
        this.lot = lot;
    }
    //Конструктора

    public Equipment() {
    }
    public Equipment(String assetName) {
        this.assetName = assetName;
        baseID = "baseID";
        mfo = 1111;
        assGrpKod = 1111;
        assPodgrpKod = 1111;
        assPodgrpName = "assPodgrpName";
        inventNum = "inventNum";
        stuffName = "stuffName";
        region = "region";
        locality = "locality";
        fillialNum = 10;
        balCount = 28L;
        balDate = new Date();
        residualCost = 3346.78;
        marketPrice = 3255.65;
        condition = "new";
        raterName = "defoult";
        lastRateDate = new Date();
        planSaleDate = new Date();
        actionPlan = "actionPlan";
        carLocation = "carLocation";
        carNum = "AA1010IX";
        vinCode = "1j2me14121";
        year = 2006;
        fuelType = "gas";
        wasItCrashed = "Ні";
        transmission = "автоматична";
        color = "black";
        engineCapacity = "2.0";
        range = 10000;
        propertyType = "propertyType";
        street = "street";
        house = "house";
        room = "room";
        squareSum = 100;
        squareLive = 80;
        roomCount = 4;
        floor_countFloor = "floor_countFloor";
        isGas = "isGas";
        isWater = "isWater";
        isElectric = "isElectric";
        landPurpose = "landPurpose";
        kadastrNum = "kadastrNum";
        modelDescr = "modelDescr";
        storage = "storage";
        conditionInspect = "conditionInspect";
        damageDescr = "damageDescr";
        usingFromDate = new Date();
        costAtEksplDate = 1000000.00;
        residualCostAtBalance = 100000.00;
        lot=null;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", assetName='" + assetName + '\'' +
                ", baseID='" + baseID + '\'' +
                ", mfo=" + mfo +
                ", assGrpKod=" + assGrpKod +
                ", assPodgrpKod=" + assPodgrpKod +
                ", assPodgrpName='" + assPodgrpName + '\'' +
                ", inventNum='" + inventNum + '\'' +
                ", stuffName='" + stuffName + '\'' +
                ", region='" + region + '\'' +
                ", locality='" + locality + '\'' +
                ", fillialNum=" + fillialNum +
                ", balCount=" + balCount +
                ", balDate=" + balDate +
                ", residualCost=" + residualCost +
                ", marketPrice=" + marketPrice +
                ", condition='" + condition + '\'' +
                ", raterName='" + raterName + '\'' +
                ", lastRateDate=" + lastRateDate +
                ", planSaleDate=" + planSaleDate +
                ", actionPlan='" + actionPlan + '\'' +
                ", carLocation='" + carLocation + '\'' +
                ", carNum='" + carNum + '\'' +
                ", vinCode='" + vinCode + '\'' +
                ", year=" + year +
                ", fuelType='" + fuelType + '\'' +
                ", wasItCrashed='" + wasItCrashed + '\'' +
                ", transmission='" + transmission + '\'' +
                ", color='" + color + '\'' +
                ", engineCapacity='" + engineCapacity + '\'' +
                ", range=" + range +
                ", propertyType='" + propertyType + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", room='" + room + '\'' +
                ", squareSum=" + squareSum +
                ", squareLive=" + squareLive +
                ", roomCount=" + roomCount +
                ", floor_countFloor='" + floor_countFloor + '\'' +
                ", isGas='" + isGas + '\'' +
                ", isWater='" + isWater + '\'' +
                ", isElectric='" + isElectric + '\'' +
                ", landPurpose='" + landPurpose + '\'' +
                ", kadastrNum='" + kadastrNum + '\'' +
                ", modelDescr='" + modelDescr + '\'' +
                ", storage='" + storage + '\'' +
                ", conditionInspect='" + conditionInspect + '\'' +
                ", damageDescr='" + damageDescr + '\'' +
                ", usingFromDate=" + usingFromDate +
                ", costAtEksplDate=" + costAtEksplDate +
                ", residualCostAtBalance=" + residualCostAtBalance +
                ", lot=" + lot.getId() +
                '}';
    }
}