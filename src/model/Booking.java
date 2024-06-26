package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Booking implements Serializable{
	private int id;
	private Date bookedDate;
	private float total;
	private float saleoff;
	private String note;
	private Client client;
	private User creator;
	private ArrayList<BookedCourt> bookedCourt;
	
	public Booking() {
		super();
		bookedCourt = new ArrayList<BookedCourt>();
	}
	
	public Booking(Date bookedDate, float saleoff, Client client, User creator, ArrayList<BookedCourt> bookedCourt) {
		super();
		this.bookedDate = bookedDate;
		this.saleoff = saleoff;
		this.client = client;
		this.creator = creator;
		this.bookedCourt= bookedCourt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getBookedDate() {
		return bookedDate;
	}

	public void setBookedDate(Date bookedDate) {
		this.bookedDate = bookedDate;
	}

	public float getTotal() {
		updateTotal();
		return total;
	}

	public float getSaleoff() {
		return saleoff;
	}

	public void setSaleoff(float saleoff) {
		this.saleoff = saleoff;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public ArrayList<BookedCourt> getBookedCourt() {
		return bookedCourt;
	}

	public void setBookedCourt(ArrayList<BookedCourt> bookedCourt) {
		this.bookedCourt = bookedCourt;
	}
	
	private void updateTotal(){
		if(bookedCourt != null) {
			for(BookedCourt bc: bookedCourt) {
				total += bc.getTotal();
			}
		}
	}
}
