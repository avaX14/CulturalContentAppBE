package com.kts.cultural_content.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @ManyToOne()
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private CulturalOffer offer;

    @Column(name = "created_at")
    private Date date;



    public Comment() {
    }

    public Comment(Long id, String text, User author, CulturalOffer offer, Date date) {
        this.id = id;
        this.text = text;
        this.author = author;
        this.offer = offer;
        this.date = date;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return this.author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public CulturalOffer getOffer() {
        return this.offer;
    }

    public void setOffer(CulturalOffer offerId) {
        this.offer = offerId;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Comment id(Long id) {
        setId(id);
        return this;
    }

    public Comment text(String text) {
        setText(text);
        return this;
    }

    public Comment author(User author) {
        setAuthor(author);
        return this;
    }

    public Comment offer(CulturalOffer offer) {
        setOffer(offer);
        return this;
    }

    public Comment date(Date date) {
        setDate(date);
        return this;
    }

  

}
