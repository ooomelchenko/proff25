package nadrabank.domain;
import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Entity
@Table(name="LOTS")
public class Lot implements Serializable {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss", Locale.ENGLISH);
    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "LOT_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name = "ID")
    private Long id;
    @Column(name = "Region")
    private String region;
    @Column(name = "Status")
    private String workStage;
    @Column(name = "Sold")
    private Boolean isItSold;
    @Column(name = "Comments")
    private String comment;
    @Column(name = "Lot_Created")
    private Date lotDate;

    @OneToMany(fetch = FetchType.LAZY,// подргужать все сразу
            mappedBy = "lot" )  // включить двунаправленность
    private Set<Credit> credits = new HashSet<>();

    @ManyToOne
    private User user;//класс
    @ManyToOne
    private Client client;//класс

    public boolean addCredit(Credit credit){
        return credits.add(credit);
    }
    public boolean removeCredit(Credit credit){
        return credits.remove(credit);
    }
    public boolean removeCredits(Set<Credit> cred){
       return credits.removeAll(cred);
    }
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

    public String getWorkStage() {
        return workStage;
    }
    public void setWorkStage(String workStage) {
        this.workStage = workStage;
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

    public Set<Credit> getCredits() {
        return credits;
    }
    public void setCredits(Set<Credit> credits) {
        this.credits = credits;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
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
    public Lot(String comment, User user, Client client) {
        this.region = "Головний офіс";
        this.workStage = "Новий лот";
        this.isItSold = false;
        this.comment = comment;
        this.lotDate = new Date();
        this.user = user;
        this.client = client;
    }

    @Override
    public String toString() {
        return
                ""+ region +'|'+
                 client.getCompanyName()+'|'+
                 workStage + '|' +
                 user.getLogin() + '|'  +
                 comment + '|'  +
                 sdf.format(lotDate);
    }
}