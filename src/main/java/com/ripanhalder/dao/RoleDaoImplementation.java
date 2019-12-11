package com.ripanhalder.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ripanhalder.entity.Role;

@Repository
public class RoleDaoImplementation implements RoleDao {

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactObject;

	@Override
	public Role searchRoleByName(String roleName) {

		// get the current hibernate session
		Session currentSession = sessionFactObject.getCurrentSession();

		// now retrieve/read from database using name
		Query<Role> q = currentSession.createQuery("from Role where name=:roleName", Role.class);
		q.setParameter("roleName", roleName);
		
		Role theRole = null;
		
		try {
			theRole = q.getSingleResult();
		} catch (Exception e) {
			theRole = null;
		}
		
		return theRole;
	}
}
