package com.ripanhalder.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ripanhalder.entity.Game;
import com.ripanhalder.entity.Platform;
import com.ripanhalder.entity.Role;

@Repository
public class GameDaoImpl implements GameDao {

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactObject;
	
		
	@Override
	@Transactional
	public Game save(Game game) {
		// get the current hibernate session
		Session currentSession = sessionFactObject.getCurrentSession();
		
		// create the game
		currentSession.save(game);
		return game;
		
	}

	@Override
	@Transactional
	public void delete(Game game) {
		// get the current hibernate session
		Session currentSession = sessionFactObject.getCurrentSession();
		
		// delete the game
		currentSession.delete(game);
	}

	@Override
	@Transactional
	public List<Game> list() {
		// get the current hibernate session
		Session currentSession = sessionFactObject.getCurrentSession();
		
		// now retrieve/read from database using name
		Query<Game> theQuery = currentSession.createQuery("from Game",Game.class);
		List<Game> allGames = theQuery.getResultList();
		return allGames;
		
	}
	
	@Override
	@Transactional
	public Game getGame(long gameId) {
		
		Session currentSession = sessionFactObject.getCurrentSession();
		
		// now retrieve/read from database using name
		Query<Game> theQuery = currentSession.createQuery("from Game where gameId= :gameId", Game.class);
		theQuery.setParameter("gameId", gameId);
		theQuery.setMaxResults(1);
		Game game = null;
		
		try {
			game = theQuery.getSingleResult();
			
		} catch (Exception e) {
			game = null;
		}
		
		return game;
	}
	
	@Override
	@Transactional
	public void update(Game game) {
		// get the current hibernate session
				Session currentSession = sessionFactObject.getCurrentSession();
				
				Game entity = getGame(game.getGameId());
		        if(entity!=null){
		            entity.setDevelopers(game.getDevelopers());
		            entity.setIgnrating(game.getIgnrating());
		            entity.setImageUrl(game.getImageUrl());
		            entity.setPremium(game.getPremium());
		            entity.setPrice(game.getPrice());
		            entity.setQuanity(game.getQuanity());
		            entity.setTitle(game.getTitle());
		            entity.setCurrentRentalQuantity(game.getCurrentRentalQuantity());
		        }
		        currentSession.merge(entity);
	}

}
