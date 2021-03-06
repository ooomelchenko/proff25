package web.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scala.collection.mutable.HashSet;
import scala.collection.mutable.Set;

import javax.persistence.*;

/**
 * Created by george on 19.07.15.
 */
@Component
@Entity
@Table(name = "ORDERS")
public class Orders {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "move_from")
    private String moveFrom;

    @Column(name = "move_to")
    private String moveTo;

    @Column(name = "price")
    private String price;

    @Column(name = "created_date")
    private String date;

    public String print(){
        return "ID: "+id+", Move From: "+moveFrom+", Move To: "+moveTo;
    }

    public Orders() {
    }

    public Orders(Integer id, String moveFrom, String moveTo, String price, String date) {
        this.id = id;
        this.moveFrom = moveFrom;
        this.moveTo = moveTo;
        this.price = price;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMoveFrom() {
        return moveFrom;
    }

    public void setMoveFrom(String moveFrom) {
        this.moveFrom = moveFrom;
    }

    public String getMoveTo() {
        return moveTo;
    }

    public void setMoveTo(String moveTo) {
        this.moveTo = moveTo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
