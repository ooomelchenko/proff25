package hw7.springnotes.domain;

import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Created by ПК on 25.06.2015.
 */
@Component
@Entity
@Table(name = "VENDORS" )

public class Vendor {
    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "SEQ_VENDORS_ID",
            allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    private Long id;

    private String name;

    public Vendor(){}
    public Vendor(String name){
        this.name=name;
    }
    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
    }

}
