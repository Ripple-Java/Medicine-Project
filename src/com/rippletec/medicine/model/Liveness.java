package com.rippletec.medicine.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Repository;

@Entity
@Repository
@Table(name=Liveness.TABLE_NAME)
public class Liveness extends BaseModel {

    private static final long serialVersionUID = -6998784511849237742L;
    
    public static final String CLASS_NAME = "Liveness";
    public static final String TABLE_NAME = "liveness";
    
    public static final String USER_ID = "user_id";
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name=USER_ID)
    private User user;
    
    @Column(name="count", length=10, nullable=false)
    private Integer count;
    
    @Column(name="time", nullable=false)
    @Temporal(TemporalType.DATE)
    private Date time;
    
    public Liveness() {
    }

    public Liveness(User user, Integer count, Date time) {
	super();
	this.user = user;
	this.count = count;
	this.time = time;
    }

    @Override
    public String toString() {
	return "Liveness [id=" + id + ", count=" + count + ", time=" + time
		+ "]";
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Integer getCount() {
        return count;
    }

    public Date getTime() {
        return time;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    
    


}
