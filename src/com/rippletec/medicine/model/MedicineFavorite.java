package com.rippletec.medicine.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Repository;

@Repository
@Entity
@Table(name=MedicineFavorite.TABLE_NAME)
public class MedicineFavorite extends BaseModel{
    
    private static final long serialVersionUID = -4947333524040803889L;
    public static final String CLASS_NAME = "MedicineFavorite";
    public static final String TABLE_NAME = "medicine_favorite";
    
    public static final String USER_ID = "user_id";
    public static final String MEDICINE_ID = "medicine_id";
    public static final String TYPE = "type";
    
    public static final int MEDICINE_CHINESE = 1;
    public static final int MEDICINE_WEST = 2;
    public static final int ENTER_CHINESE = 3;
    public static final int ENTER_WEST = 4;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name=USER_ID)
    private User user;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name=MEDICINE_ID)
    private Medicine medicine;
    
    @Column(name=TYPE,length = 1, nullable=false)
    private Integer type;
    
    public MedicineFavorite() {
    }
    public MedicineFavorite(User user, Medicine medicine, Integer type) {
	super();
	this.user = user;
	this.medicine = medicine;
	this.type = type;
    }

    @Override
    public String toString() {
	return "MedicineFavorite [id=" + id + ", type=" + type + "]";
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public Integer getType() {
        return type;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public void setType(Integer type) {
        this.type = type;
    }

  

}
