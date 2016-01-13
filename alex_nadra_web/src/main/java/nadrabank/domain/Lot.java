package nadrabank.domain;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="LOTS")
public class Lot {
    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "LOT_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name = "ID")
    private Long id;
    @Column(name = "Region")
    private String region;
    @Column(name = "Expected_sale_date")
    private LocalDateTime planDate;
    @Column(name = "Sale_Type")
    private String saleType;
    @Column(name = "Work_Stage")
    private String workStage;
    @Column(name = "Sale_Stage")
    private String saleStage;
    @Column(name = "Block_For_Sale")
    private Boolean block;
    @Column(name = "Sold")
    private Boolean isItSold;
    @Column(name = "Comments")
    private String comment;
    @Column(name = "Lot_Created")
    private LocalDateTime lotDate;

    @OneToMany(cascade = CascadeType.ALL, // каскадирование
            fetch = FetchType.EAGER,// подгружать все сразу
            mappedBy = "lot" )  // включить двунаправленность
    private Set<Equipment> equipments = new HashSet<>();
    @ManyToOne
    private Employee employee;//класс
    @ManyToOne
    private Client client;//класс

    //Getters&Setters

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

    public LocalDateTime getPlanDate() {
        return planDate;
    }
    public void setPlanDate(LocalDateTime planDate) {
        this.planDate = planDate;
    }

    public String getSaleType() {
        return saleType;
    }
    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    public String getWorkStage() {
        return workStage;
    }
    public void setWorkStage(String workStage) {
        this.workStage = workStage;
    }

    public String getSaleStage() {
        return saleStage;
    }
    public void setSaleStage(String saleStage) {
        this.saleStage = saleStage;
    }

    public Boolean getBlock() {
        return block;
    }
    public void setBlock(Boolean block) {
        this.block = block;
    }

    public Boolean getIsItSold() {
        return isItSold;
    }
    public void setIsItSold(Boolean isItSold) {
        this.isItSold = isItSold;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getLotDate() {
        return lotDate;
    }
    public void setLotDate(LocalDateTime lotDate) {
        this.lotDate = lotDate;
    }

    public Set<Equipment> getEquipments() {
        return equipments;
    }
    public void setEquipments(Set<Equipment> equipments) {
        this.equipments = equipments;
    }

    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    //Конструктора

    public Lot() {
    }

    public Lot(String region, LocalDateTime planDate, String saleType, String comment, Employee empl, Client client) {
        this.region = region;
        this.planDate = planDate;
        this.saleType = saleType;
        workStage = "Новий";
        saleStage = null;
        block = true;
        isItSold = false;
        this.comment = comment;
        lotDate = LocalDateTime.now();
        this.employee = empl;
        this.client = client;
    }

    @Override
    public String toString() {
        return
                lotDate.getDayOfMonth()+"-"+lotDate.getMonthValue()+"-"+lotDate.getYear()+'|'+
                 region +'|'+
                 planDate.getDayOfMonth()+"-"+planDate.getMonthValue()+"-"+planDate.getYear()+'|'+
                 saleType + '|' +
                 workStage + '|' +
                 saleStage + '|' +
                 block + '|'  +
                 isItSold + '|'  +
                 comment + '|'  +
                 employee.getFio() + '|'  +
                 client.getLastName()+" "+client.getFirstName()+" "+client.getSecondName();
    }
}