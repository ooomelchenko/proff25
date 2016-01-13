package nadrabank.domain;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Clients")
public class Client {
    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "Clint_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name = "ID")
    private Long id;
    @Column(name = "INN")
    private String inn;
    @Column(name = "Passport")
    private String passport;
    @Column(name = "Last_Name")
    private String lastName;
    @Column(name = "First_Name")
    private String firstName;
    @Column(name = "Second_Name")
    private String secondName;

    @OneToMany(cascade = CascadeType.ALL, // каскадирование
            fetch = FetchType.EAGER,// подргужать все сразу
            mappedBy = "client" )  // включить двунаправленность
    private Set<Lot> lots = new HashSet<>();

    //Getters&Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    public String getInn() {
        return inn;
    }
    public void setInn(String inn) {
        this.inn = inn;
    }

    public Set<Lot> getLots() {
        return lots;
    }
    public void setLots(Set<Lot> lots) {
        this.lots = lots;
    }

    public String getPassport() {
        return passport;
    }
    public void setPassport(String passport) {
        this.passport = passport;
    }


    //Конструктора
    public Client() {
    }

    public Client(String inn, String passport, String lastName, String firstName, String secondName) {
        this.inn = inn;
        this.passport = passport;
        this.lastName = lastName;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", inn=" + inn +
                ", passport='" + passport + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                '}';
    }
}