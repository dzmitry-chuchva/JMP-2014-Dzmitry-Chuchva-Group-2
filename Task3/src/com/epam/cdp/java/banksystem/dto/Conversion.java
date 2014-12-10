package com.epam.cdp.java.banksystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "conversion")
public class Conversion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "conversion_id", unique = true, nullable = false)
	private long id;

	@ManyToOne
	@JoinColumn(name = "from_id", referencedColumnName = "currency_id", insertable = false, updatable = false)
	private Currency from;

	@ManyToOne
	@JoinColumn(name = "to_id", referencedColumnName = "currency_id", insertable = false, updatable = false)
	private Currency to;

	@Column(name = "rate")
	private double rate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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
