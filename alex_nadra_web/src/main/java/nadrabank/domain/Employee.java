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
    @Column(name = "FIO")
    private String fio;
    @Column(name = "Login")
    private String login;
    @Column(name = "Password")
    private String password;
    @Column(name = "Rights")
    private String right;
    @OneToMany(cascade = CascadeType.ALL, // каскадирование
            fetch = FetchType.EAGER,// подргужать все сразу
            mappedBy = "employee" )  // включить двунаправленность
    private Set<Lot> lots = new HashSet<>();
 /*   private enum Rights{
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

    public String getFio() {
        return fio;
    }
    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRight() {
        return right;
    }
    public void setRight(String right) {
        this.right = right;
    }

    public Set<Lot> getLots() {
        return lots;
    }
    public void setLots(Set<Lot> lots) {
        this.lots = lots;
    }

    //Конструктора
    public Employee() {
    }

    public Employee(String region, String fio, String login, String password, String right) {
        this.region = region;
        this.fio = fio;
        this.login = login;
        this.password = password;
        this.right = right;
    }
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", region='" + region + '\'' +
                ", fio='" + fio + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", right='" + right + '\'' +
                '}';
    }
}
