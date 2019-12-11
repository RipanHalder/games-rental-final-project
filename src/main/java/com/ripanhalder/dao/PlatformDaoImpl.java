package com.ripanhalder.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ripanhalder.entity.Category;
import com.ripanhalder.entity.Platform;

@Repository
public class PlatformDaoImpl implements PlatformDao {
	
	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactObject;

	@Override
	@Transactional
	public Platform get(String title) {
		// get the current hibernate session
		Session currentSession = sessionFactObject.getCurrentSession();
		
		// now retrieve/read from database using name
		Query<Platform> theQuery = currentSession.createQuery("from Platform where title= :title", Platform.class);
		theQuery.setParameter("title", title);
		theQuery.setMaxResults(1);
		Platform platform = null;
		
		try {
			platform = theQuery.getSingleResult();
		} catch (Exception e) {
			platform = null;
		}
		
		return platform;
	}
	
	@Override
	@Transactional
	public Platform getPlatform(long platformId) {
		
		Session currentSession = sessionFactObject.getCurrentSession();
		
		// now retrieve/read from database using name
		Query<Platform> theQuery = currentSession.createQuery("from Platform where platformId= :platformId", Platform.class);
		theQuery.setParameter("platformId", platformId);
		theQuery.setMaxResults(1);
		Platform platform = null;
		
		try {
			platform = theQuery.getSingleResult();
		} catch (Exception e) {
			platform = null;
		}
		
		return platform;
	}

	@Override
	@Transactional
	public List<Platform> list() {
		// get the current hibernate session
		Session currentSession = sessionFactObject.getCurrentSession();
		Query<Platform> theQuery = currentSession.createQuery("from Platform",Platform.class);
		List<Platform> allPlatforms = theQuery.getResultList();
		return allPlatforms;
	}

	@Override
	@Transactional
	public Platform create(String title) {
		Platform platform = new Platform(title);
		// get the current hibernate session
		Session currentSession = sessionFactObject.getCurrentSession();
		currentSession.saveOrUpdate(platform);
		return platform;
	}

	@Override
	@Transactional
	public Platform create(Platform platform) {
		// get the current hibernate session
		Session currentSession = sessionFactObject.getCurrentSession();
		currentSession.saveOrUpdate(platform);
		return platform;
	}

	@Override
	@Transactional
	public void update(Platform platform) {
		// get the current hibernate session
		Session currentSession = sessionFactObject.getCurrentSession();
		Platform entity = getPlatform(platform.getPlatformId());
        if(entity!=null){
            entity.setTitle(platform.getTitle());
        }
		currentSession.merge(entity);
	}

	@Override
	@Transactional
	public void delete(Platform platform) {
		// get the current hibernate session
		Session currentSession = sessionFactObject.getCurrentSession();
		currentSession.delete(platform);
	}

}
