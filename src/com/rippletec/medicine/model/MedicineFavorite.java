package com.rippletec.medicine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    
    public static final String NAME = "";
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name="",nullable=false)
    private Integer user_id;
    
    @Column(name="",nullable=false)
    private Integer medicine_id;
    
    @Column(name="",nullable=false)
    private Integer type;

}
