package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;



public class BookedCourt implements Serializable{
	private int id;
	private MiniCourt court;
	private Date checkin;
	private Date checkout;
	private float price;
	private float saleoff;
	private float total;
	private boolean isChecked;
	
	public BookedCourt() {
		super();
	}
	
	public BookedCourt(MiniCourt court, Date checkin, Date checkout, float price, float saleoff, boolean isChecked) {
		super();
		this.court = court;
		this.checkin = checkin;
		this.checkout = checkout;
		this.price = price;
		this.saleoff = saleoff;
		this.isChecked = isChecked;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MiniCourt getCourt() {
		return court;
	}

	public void setCourt(MiniCourt court) {
		this.court = court;
	}

	public Date getCheckin() {
		return checkin;
	}

	public void setCheckin(Date checkin) {
		this.checkin = checkin;
	}

	public Date getCheckout() {
		return checkout;
	}

	public void setCheckout(Date checkout) {
		this.checkout = checkout;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getSaleoff() {
		return saleoff;
	}

	public void setSaleoff(float saleoff) {
		this.saleoff = saleoff;
	}

	public float getTotal() {
		updateTotal();
		return total;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	private void updateTotal() {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		LocalDateTime date1 = LocalDateTime.parse(format.format(checkin).toString(),dtf);
		LocalDateTime date2 = LocalDateTime.parse(format.format(checkout).toString(),dtf);
		total = Duration.between(date1, date2).toDays()*price - saleoff;
	}
}
