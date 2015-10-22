package nadrabank.domain;
import javax.persistence.*;
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
    @Column(name = "Last_Name")
    private String lastName;
    @Column(name = "First_Name")
    private String firstName;
    @Column(name = "Second_Name")
    private String secondName;
    @Column(name = "INN")
    private int inn;
    /*@OneToMany(cascade = CascadeType.ALL, // каскадирование
            fetch = FetchType.EAGER,// подргужать все сразу
            mappedBy = "client" )  // включить двунаправленность
    private Set<Lot> lots = new HashSet<>();*/

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

    public int getInn() {
        return inn;
    }
    public void setInn(int inn) {
        this.inn = inn;
    }

   /* public Set<Lot> getLots() {
        return lots;
    }
    public void setLots(Set<Lot> lots) {
        this.lots = lots;
    }*/

    //Конструктора
    public Client() {
    }

    public Client(String lastName, String firstName, String secondName, int inn) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.secondName = secondName;
        this.inn = inn;
    }

/*    public Client(String lastName, String firstName, String secondName, int inn, Set<Lot> lots) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.secondName = secondName;
        this.inn = inn;
        this.lots = lots;
    }*/
}
