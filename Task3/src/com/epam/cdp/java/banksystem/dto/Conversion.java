package com.epam.cdp.java.banksystem.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "conversion")
public class Conversion implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 8562950977272677567L;

    @Id
    @ManyToOne
    @JoinColumn(name = "currency_id", insertable = false, updatable = false)
    private Currency from;

    @Id
    @ManyToOne
    @JoinColumn(name = "currency_id", insertable = false, updatable = false)
    private Currency to;

    @Column(name = "rate")
    private double rate;

    public Currency getFrom() {
	return from;
    }

    public void setFrom(Currency from) {
	this.from = from;
    }

    public Currency getTo() {
	return to;
    }

    public void setTo(Currency to) {
	this.to = to;
    }

    public double getRate() {
	return rate;
    }

    public void setRate(double rate) {
	this.rate = rate;
    }

}
