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
import javax.persistence.OneToMany;

@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long categoryId;
	
	@Column(name= "title", unique = true, nullable = false)
	private String title;

	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="category", cascade = {CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.DETACH, CascadeType.REFRESH})
	private Set<Game> games;
	
	
	
	public Category() {
		
	}
	

	public Category(String title) {
		super();
		this.title = title;
	}


	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Game> getGames() {
		return games;
	}

	public void setGames(Set<Game> games) {
		this.games = games;
	}

	public void addGame(Game game) {
		if(games==null) {
			games = new HashSet<Game>();
		}
		games.add(game);
	}


	@Override
	public String toString() {
		return this.title;
	}
	
	
}
