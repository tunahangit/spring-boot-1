package com.example.spring1.entities;

import java.util.Date;

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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name="post")
@Data
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id" ,nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE) // user silindiğinde bütün postlarını da sil.
	User user;
	
	String title;
	String text;
	
	@Temporal(TemporalType.TIMESTAMP)
	Date createDate;
}
