package nadrabank.domain;
import javax.persistence.*;
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
    @Column(name = "Expected_sale_date")
    private Date planDate;
    @Column(name = "Sale_Type")
    private String saleType;
    @Column(name = "Block_For_Sale")
    private Boolean block;
    @Column(name = "Sale_Status")
    private String saleStatus;
    @Column(name = "Sold")
    private Boolean isItSold;
    @Column(name = "Comments")
    private String comment;
    @Column(name = "Lot_Created")
    private Date lotDate;
    /*@OneToMany(cascade = CascadeType.ALL, // каскадирование
            fetch = FetchType.EAGER,// подгружать все сразу
            mappedBy = "lot" )  // включить двунаправленность
    private Set<Equipment> equipments = new HashSet<>();
    @ManyToOne
    private Client client;//класс
    @ManyToOne
    private Employee employee;//класс*/

    //Getters&Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

   /* public Set<Equipment> getEquipments() {
        return equipments;
    }
    public void setEquipments(Set<Equipment> equipments) {
        this.equipments = equipments;
    }

    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }*/

    public Date getPlanDate() {
        return planDate;
    }
    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public String getSaleType() {
        return saleType;
    }
    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    public Boolean getBlock() {
        return block;
    }
    public void setBlock(Boolean block) {
        this.block = block;
    }

    public String getSaleStatus() {
        return saleStatus;
    }
    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
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

    public Date getLotDate() {
        return lotDate;
    }
    public void setLotDate(Date lotDate) {
        this.lotDate = lotDate;
    }

    //Конструктора
    public Lot() {
        planDate=null;
        saleType="defoult";
        block=false;
        saleStatus="defoult";
        isItSold=false;
        comment="no comments";
        lotDate=new Date();
     /*   client=null;
        employee=null;*/
    }

/*    public Lot(Employee employee) {
        this.employee = employee;
    }
    public Lot(Employee employee, Client client) {
        this.client = client;
        this.employee = employee;
    }*/

    public Lot(Date planDate, String saleType, Boolean block, String saleStatus, Boolean isItSold, String comment, Date lotDate) {
        this.planDate = planDate;
        this.saleType = saleType;
        this.block = block;
        this.saleStatus = saleStatus;
        this.isItSold = isItSold;
        this.comment = comment;
        this.lotDate = lotDate;
    }

 /*   public Lot(Date lotDate, Date planDate, String saleType, Boolean block, String saleStatus, Boolean isItSold, String comment, Client client, Employee employee) {
        this.lotDate = lotDate;
        this.planDate = planDate;
        this.saleType = saleType;
        this.block = block;
        this.saleStatus = saleStatus;
        this.isItSold = isItSold;
        this.comment = comment;
        *//*this.client = client;
        this.employee = employee;*//*
    }

    public Lot(Date planDate, String saleType, Boolean block, String saleStatus, Boolean isItSold, String comment, Date lotDate, Set<Equipment> equipments, Client client, Employee employee) {
        this.planDate = planDate;
        this.saleType = saleType;
        this.block = block;
        this.saleStatus = saleStatus;
        this.isItSold = isItSold;
        this.comment = comment;
        this.lotDate = lotDate;
        *//*this.equipments = equipments;
        this.client = client;
        this.employee = employee;*//*
    }*/
}