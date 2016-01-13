package nadrabank.domain;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Company")
public class Client implements Serializable {
    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "Company_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name = "ID")
    private Long id;
    @Column(name = "EDRPO")
    private String inn;
    @Column(name = "COMPANY_NAME")
    private String companyName;
    @Column(name = "Address_UR")
    private String address;
    @Column(name = "Post_Address")
    private String postIndex;
    @Column(name = "Director")
    private String dirFIO;
    @Column(name = "Requizit")
    private String req;
    @Column(name = "Mail")
    private String email;

    @OneToMany (
         //   @OneToMany (cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH}, // каскадирование
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

    public String getInn() {
        return inn;
    }
    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostIndex() {
        return postIndex;
    }
    public void setPostIndex(String postIndex) {
        this.postIndex = postIndex;
    }

    public String getDirFIO() {
        return dirFIO;
    }
    public void setDirFIO(String dirFIO) {
        this.dirFIO = dirFIO;
    }

    public String getReq() {
        return req;
    }
    public void setReq(String req) {
        this.req = req;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

/*    public Set<Lot> getLots() {
        return lots;
    }
    public void setLots(Set<Lot> lots) {
        this.lots = lots;
    }*/

    //Конструктора
    public Client() {
    }
    public Client(String inn, String companyName, String address, String postIndex, String dirFIO, String req, String email) {
        this.inn = inn;
        this.companyName = companyName;
        this.address = address;
        this.postIndex = postIndex;
        this.dirFIO = dirFIO;
        this.req = req;
        this.email = email;
    }
    @Override
    public String toString() {
        return
                email + '|' +
                id +'|' +
                inn + '|' +
                companyName + '|' +
                address + '|' +
                postIndex + '|' +
                dirFIO + '|' +
                req;
    }
}
