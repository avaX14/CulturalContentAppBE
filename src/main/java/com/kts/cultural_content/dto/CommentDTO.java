package com.kts.cultural_content.dto;

import java.util.Date;

import com.kts.cultural_content.model.Comment;


public class CommentDTO {

    private Long id;

    private String text;

    private UserDTO author;

    private Date date;

    private Long offerId;

    public CommentDTO() {
    }

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.author = new UserDTO(comment.getAuthor());
        this.date = comment.getDate();
        this.offerId = comment.getOffer().getId();
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

    public UserDTO getAuthor() {
        return this.author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getOfferId() {
        return this.offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public CommentDTO id(Long id) {
        setId(id);
        return this;
    }

    public CommentDTO text(String text) {
        setText(text);
        return this;
    }

    public CommentDTO author(UserDTO author) {
        setAuthor(author);
        return this;
    }

    public CommentDTO date(Date date) {
        setDate(date);
        return this;
    }

    public CommentDTO offerId(Long offerId) {
        setOfferId(offerId);
        return this;
    }

   
}
