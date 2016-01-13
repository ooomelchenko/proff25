package nadrabank.domain;
import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
@Table(name="Credits")
public class Credit implements Serializable {
    private static final SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    @Id
    @SequenceGenerator(name = "sequenc", sequenceName = "Credit_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenc")
    @Column(name = "ID_BARS")
    private Long id;
    @Column(name = "ID_LOANS")
    private Long lonsID;
    @Column(name = "PIB")
    private String fio;
    @Column(name = "INN")
    private String inn;
    @Column(name = "REGION")
    private String region;
    @Column(name = "Fillial")
    private String fillial;
    @Column(name = "Val")
    private String curr;
    @Column(name = "ZB")
    private Double totalBorg;
    @Column(name = "B")
    private Double borg;
    @Column(name = "PB")
    private Double prosrBorg;
    @Column(name = "T")
    private Double telo;
    @Column(name = "PT")
    private Double prosrTelo;
    @Column(name = "ZT")
    private Double totalTelo;
    @Column(name = "P")
    private Double proc;
    @Column(name = "PP")
    private Double prosrProc;
    @Column(name = "ZP")
    private Double totalProc;
    @Column(name = "Penya")
    private Double penya;
    @Column(name = "Comission")
    private Double comission;
    @Column(name = "Pvneb")
    private Double procVnebal;
    @Column(name = "Marker")
    private String marker;
    @Column(name = "Pidrozdil")
    private String podrazd;
    @Column(name = "CRDType")
    private String crdtType;
    @Column(name = "Specific")
    private String specific;
    @Column(name = "Opus_Zastav")
    private String gageDescr;
    @Column(name = "Cina_zastavu")
    private Double gagePrice;
    @Column(name = "Date_Start")
    private Date contractStart;
    @Column(name = "Date_End")
    private Date contractEnd;
    @Column(name = "Ndogovor")
    private String contractNum;
    @Column(name = "dpd")
    private int dpd;
    @Column(name = "period")
    private String period;
    @Column (name="dat")
    private Date dat;

    @ManyToOne
    private Lot lot;//класс

    //Getters&Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getLonsID() {
        return lonsID;
    }
    public void setLonsID(Long lonsID) {
        this.lonsID = lonsID;
    }

    public String getFio() {
        return fio;
    }
    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getInn() {
        return inn;
    }
    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }

    public String getFillial() {
        return fillial;
    }
    public void setFillial(String fillial) {
        this.fillial = fillial;
    }

    public String getCurr() {
        return curr;
    }
    public void setCurr(String curr) {
        this.curr = curr;
    }

    public Double getTotalBorg() {
        return totalBorg;
    }
    public void setTotalBorg(Double totalBorg) {
        this.totalBorg = totalBorg;
    }

    public Double getBorg() {
        return borg;
    }
    public void setBorg(Double borg) {
        this.borg = borg;
    }

    public Double getProsrBorg() {
        return prosrBorg;
    }
    public void setProsrBorg(Double prosrBorg) {
        this.prosrBorg = prosrBorg;
    }

    public Double getTelo() {
        return telo;
    }
    public void setTelo(Double telo) {
        this.telo = telo;
    }

    public Double getProsrTelo() {
        return prosrTelo;
    }
    public void setProsrTelo(Double prosrTelo) {
        this.prosrTelo = prosrTelo;
    }

    public Double getTotalTelo() {
        return totalTelo;
    }
    public void setTotalTelo(Double totalTelo) {
        this.totalTelo = totalTelo;
    }

    public Double getProc() {
        return proc;
    }
    public void setProc(Double proc) {
        this.proc = proc;
    }

    public Double getProsrProc() {
        return prosrProc;
    }
    public void setProsrProc(Double prosrProc) {
        this.prosrProc = prosrProc;
    }

    public Double getTotalProc() {
        return totalProc;
    }
    public void setTotalProc(Double totalProc) {
        this.totalProc = totalProc;
    }

    public Double getPenya() {
        return penya;
    }
    public void setPenya(Double penya) {
        this.penya = penya;
    }

    public Double getComission() {
        return comission;
    }
    public void setComission(Double comission) {
        this.comission = comission;
    }

    public Double getProcVnebal() {
        return procVnebal;
    }
    public void setProcVnebal(Double procVnebal) {
        this.procVnebal = procVnebal;
    }

    public String getMarker() {
        return marker;
    }
    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String getPodrazd() {
        return podrazd;
    }
    public void setPodrazd(String podrazd) {
        this.podrazd = podrazd;
    }

    public String getCrdtType() {
        return crdtType;
    }
    public void setCrdtType(String crdtType) {
        this.crdtType = crdtType;
    }

    public String getSpecific() {
        return specific;
    }
    public void setSpecific(String specific) {
        this.specific = specific;
    }

    public String getGageDescr() {
        return gageDescr;
    }
    public void setGageDescr(String gageDescr) {
        this.gageDescr = gageDescr;
    }

    public Double getGagePrice() {
        return gagePrice;
    }
    public void setGagePrice(Double gagePrice) {
        this.gagePrice = gagePrice;
    }

    public Date getContractStart() {
        return contractStart;
    }
    public void setContractStart(Date contractStart) {
        this.contractStart = contractStart;
    }

    public Date getContractEnd() {
        return contractEnd;
    }
    public void setContractEnd(Date contractEnd) {
        this.contractEnd = contractEnd;
    }

    public String getContractNum() {
        return contractNum;
    }
    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public int getDpd() {
        return dpd;
    }
    public void setDpd(int dpd) {
        this.dpd = dpd;
    }

    public String getPeriod() {
        return period;
    }
    public void setPeriod(String period) {
        this.period = period;
    }

    public Date getDat() {
        return dat;
    }
    public void setDat(Date dat) {
        this.dat = dat;
    }

    public Lot getLot() {
        return lot;
    }
    public void setLot(Lot lot) {
        this.lot = lot;
    }

    //Конструктора
    public Credit() {
    }
    public Credit(Long lonsID, String fio, String inn, String region, String fillial, String curr, Double totalBorg, Double borg, Double prosrBorg, Double telo, Double prosrTelo, Double totalTelo, Double proc, Double prosrProc, Double totalProc, Double penya, Double comission, Double procVnebal, String marker, String podrazd, String crdtType, String specific, String gageDescr, Double gagePrice, Date contractStart, Date contractEnd, String contractNum, int dpd, String period, Date dat, Lot lot) {
        this.lonsID = lonsID;
        this.fio = fio;
        this.inn = inn;
        this.region = region;
        this.fillial = fillial;
        this.curr = curr;
        this.totalBorg = totalBorg;
        this.borg = borg;
        this.prosrBorg = prosrBorg;
        this.telo = telo;
        this.prosrTelo = prosrTelo;
        this.totalTelo = totalTelo;
        this.proc = proc;
        this.prosrProc = prosrProc;
        this.totalProc = totalProc;
        this.penya = penya;
        this.comission = comission;
        this.procVnebal = procVnebal;
        this.marker = marker;
        this.podrazd = podrazd;
        this.crdtType = crdtType;
        this.specific = specific;
        this.gageDescr = gageDescr;
        this.gagePrice = gagePrice;
        this.contractStart = contractStart;
        this.contractEnd = contractEnd;
        this.contractNum = contractNum;
        this.dpd = dpd;
        this.period = period;
        this.dat = dat;
        this.lot = lot;
    }

    @Override
    public String toString() {
        return  ""+lot +'|'+
                id +'|'+
                lonsID +'|'+
                fio +'|'+
                inn +'|'+
                region +'|'+
                fillial +'|'+
                curr +'|'+
                totalBorg +'|'+
                borg +'|'+
                prosrBorg +'|'+
                telo +'|'+
                prosrTelo +'|'+
                totalTelo +'|'+
                proc +'|'+
                prosrProc +'|'+
                totalProc +'|'+
                penya +'|'+
                comission +'|'+
                procVnebal +'|'+
                marker +'|'+
                podrazd +'|'+
                crdtType +'|'+
                specific +'|'+
                gageDescr +'|'+
                gagePrice +'|'+
                contractStart +'|'+
                contractEnd +'|'+
                contractNum +'|'+
                dpd +'|'+
                period+'|'+
                dat;
    }

    public String toShotString() {
        return
                "|"+
                        lonsID +'|'+
                        inn + '|' +
                        contractNum +'|'+
                        fio + '|' +
                        region + '|' +
                        crdtType + '|' +
                        marker +'|'+
                        curr + '|' +
                        totalBorg +'|'+
                        dpd +'|'+
                        sdf.format(contractStart)+'|'+
                        sdf.format(contractEnd)+'|'+
                        gageDescr +'|'+
                        gagePrice ;
    }
}