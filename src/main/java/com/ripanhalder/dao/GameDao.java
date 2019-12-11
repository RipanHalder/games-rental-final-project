package com.ripanhalder.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.ripanhalder.entity.Game;


public interface GameDao {

	public Game save(Game game);

    public void delete(Game game);
    
    public List<Game> list();
    
    public Game getGame(long gameId);
    
    public void update(Game game);
	
}
