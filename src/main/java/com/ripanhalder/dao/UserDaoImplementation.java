package com.ripanhalder.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ripanhalder.entity.User;

@Repository
public class UserDaoImplementation implements UserDao {

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactObject;

	@Override
	public User searchByUName(String uName) {
		// get the current hibernate session
		Session currentSession = sessionFactObject.getCurrentSession();

		Query<User> q = currentSession.createQuery("from User where userName=:uName", User.class);
		q.setParameter("uName", uName);
		User u = null;
		try {
			u = q.getSingleResult();
		} catch (Exception e) {
			u = null;
		}

		return u;
	}

	@Override
	public void save(User u) {
		// get current hibernate session
		Session cs = sessionFactObject.getCurrentSession();

		// create the user
		cs.saveOrUpdate(u);
	}
	
	@Override
	@Transactional
	public User findUserByUserId(long userId) {
		
		Session currentSession = sessionFactObject.getCurrentSession();
		
		// now retrieve/read from database using name
		Query<User> q = currentSession.createQuery("from User where id= :id", User.class);
		q.setParameter("id", userId);
		q.setMaxResults(1);
		User user = null;
		
		try {
			user = q.getSingleResult();
		} catch (Exception e) {
			user = null;
		}
		
		return user;
	}

}
