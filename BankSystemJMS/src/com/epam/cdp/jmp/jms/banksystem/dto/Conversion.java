package com.epam.cdp.jmp.jms.banksystem.dto;


public class Conversion {
	private long id;

	private Currency from;

	private Currency to;

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
