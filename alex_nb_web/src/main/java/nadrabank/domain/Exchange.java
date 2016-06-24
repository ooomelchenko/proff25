package nadrabank.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Exchanges")
public class Exchange implements Serializable {
    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "Exch_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name = "ID")
    private Long id;
    @Column(name = "EDRPO")
    private String inn;
    @Column(name = "Exchange_NAME")
    private String companyName;
    @Column(name = "Address_UR")
    private String address;
    @Column(name = "Post_Address")
    private String postIndex;
    @Column(name = "Contact_FIO")
    private String contactFIO;
    @Column(name = "Requizit")
    private String req;
    @Column(name = "EMail")
    private String email;

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

    public String getContactFIO() {
        return contactFIO;
    }
    public void setContactFIO(String contactFIO) {
        this.contactFIO = contactFIO;
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

    //Конструктора
    public Exchange() {
    }
    public Exchange(String inn, String companyName, String address, String postIndex, String contactFIO, String req, String email) {
        this.inn = inn;
        this.companyName = companyName;
        this.address = address;
        this.postIndex = postIndex;
        this.contactFIO = contactFIO;
        this.req = req;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Exchange{" +
                "id=" + id +
                ", inn='" + inn + '\'' +
                ", companyName='" + companyName + '\'' +
                ", address='" + address + '\'' +
                ", postIndex='" + postIndex + '\'' +
                ", contactFIO='" + contactFIO + '\'' +
                ", req='" + req + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
