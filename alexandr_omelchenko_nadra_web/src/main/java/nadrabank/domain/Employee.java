package nadrabank.domain;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Employees")
public class Employee {
    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "Employee_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name = "ID")
    private Long id;
    @Column(name = "Region")
    private String region;
    @Column(name = "Last_Name")
    private String lastName;
    @Column(name = "First_Name")
    private String firstName;
    @Column(name = "Second_Name")
    private String secondName;
    /*@OneToMany(cascade = CascadeType.ALL, // каскадирование
            fetch = FetchType.EAGER,// подргужать все сразу
            mappedBy = "employee" )  // включить двунаправленность
    private Set<Lot> lots = new HashSet<>();
    private enum Rights{
        User, UpUser, Admin
    }*/
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

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
/*

    public Set<Lot> getLots() {
        return lots;
    }
    public void setLots(Set<Lot> lots) {
        this.lots = lots;
    }
*/

    //Конструктора
    public Employee() {
    }

    public Employee(String region, String lastName, String firstName, String secondName) {
        this.region = region;
        this.lastName = lastName;
        this.firstName = firstName;
        this.secondName = secondName;
    }

/*    public Employee(String region, String lastName, String firstName, String secondName, Set<Lot> lots) {
        this.region = region;
        this.lastName = lastName;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lots = lots;
    }*/
}