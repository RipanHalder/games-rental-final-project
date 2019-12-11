package com.ripanhalder.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "Game")
public class Game {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long gameId;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private float price;
	

	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="fk_categoryId")
	private Category category;
	
	@Column(nullable = false)
	private String developers;
	
	@Column(nullable = false)
	private float ignrating;
	
	@Column(nullable = false)
	private String premium;
	
	@Column(nullable = false)
	private int quanity;
	
	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="fk_platformId")
	private Platform platform;
	
    @Column
    private String imageUrl;
    
    @Column
    private int currentRentalQuantity;
    
	@OneToMany(fetch = FetchType.EAGER, mappedBy="game", cascade = {CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.DETACH, CascadeType.REFRESH}, orphanRemoval=true)
    private Set<Booking> bookings;
	
	public Game() {
		
	}



	public Game(String title, float price, String developers, float ignrating, String premium, int quanity,
			String imageUrl, int currentRentalQuantity) {
		super();
		this.title = title;
		this.price = price;
		this.developers = developers;
		this.ignrating = ignrating;
		this.premium = premium;
		this.quanity = quanity;
		this.imageUrl = imageUrl;
		this.currentRentalQuantity = currentRentalQuantity;
	}



	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}


	public String getDevelopers() {
		return developers;
	}

	public void setDevelopers(String developers) {
		this.developers = developers;
	}

	public float getIgnrating() {
		return ignrating;
	}

	public void setIgnrating(float ignrating) {
		this.ignrating = ignrating;
	}

	public String getPremium() {
		return premium;
	}

	public void setPremium(String premium) {
		this.premium = premium;
	}

	public int getQuanity() {
		return quanity;
	}

	public void setQuanity(int quanity) {
		this.quanity = quanity;
	}
	

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public Set<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(Set<Booking> bookings) {
		this.bookings = bookings;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}



	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}



	public int getCurrentRentalQuantity() {
		return currentRentalQuantity;
	}



	public void setCurrentRentalQuantity(int currentRentalQuantity) {
		this.currentRentalQuantity = currentRentalQuantity;
	}



	public void addBooking(Booking booking) {
		if(bookings == null) {
			bookings = new HashSet<Booking>();
		}
		bookings.add(booking);
		booking.setGame(this);
	}


	@Override
	public String toString() {
		return "Game [gameId=" + gameId + ", title=" + title + ", price=" + price + ", developers=" + developers
				+ ", ignrating=" + ignrating + ", premium=" + premium + ", quanity=" + quanity + "]";
	}

	
}
