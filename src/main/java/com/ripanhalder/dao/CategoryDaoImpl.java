package com.ripanhalder.dao;

import java.util.List;



import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ripanhalder.entity.Category;
import com.ripanhalder.entity.Game;

@Repository
public class CategoryDaoImpl implements CategoryDao {

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactObject;
	
	@Override
	@Transactional
	public Category get(String title) {
		// get the current hibernate session
		Session currentSession = sessionFactObject.getCurrentSession();
		
		// now retrieve/read from database using name
		Query<Category> theQuery = currentSession.createQuery("from Category where title= :title", Category.class);
		theQuery.setParameter("title", title);
		theQuery.setMaxResults(1);
		Category category = null;
		
		try {
			category = theQuery.getSingleResult();
		} catch (Exception e) {
			category = null;
		}
		
		return category;
	}
	
	@Override
	@Transactional
	public Category getCategory(long categoryId) {
		
		Session currentSession = sessionFactObject.getCurrentSession();
		
		// now retrieve/read from database using name
		Query<Category> theQuery = currentSession.createQuery("from Category where categoryId= :categoryId", Category.class);
		theQuery.setParameter("categoryId", categoryId);
		theQuery.setMaxResults(1);
		Category category = null;
		
		try {
			category = theQuery.getSingleResult();
		} catch (Exception e) {
			category = null;
		}
		
		return category;
	}

	@Override
	@Transactional
	public List<Category> list() {
		// get the current hibernate session
		Session currentSession = sessionFactObject.getCurrentSession();
		Query<Category> theQuery = currentSession.createQuery("from Category",Category.class);
		List<Category> allCategories = theQuery.getResultList();
		return allCategories;
	}

	@Override
	@Transactional
	public Category create(String title) {
		Category cat = new Category(title);
		// get the current hibernate session
		Session currentSession = sessionFactObject.getCurrentSession();
		currentSession.saveOrUpdate(cat);
		return cat;
	}

	@Override
	@Transactional
	public void update(Category category) {
		// get the current hibernate session
		Session currentSession = sessionFactObject.getCurrentSession();
		Category entity = getCategory(category.getCategoryId());
        if(entity!=null){
            entity.setTitle(category.getTitle());
        }
		currentSession.merge(entity);
	}

	@Override
	@Transactional
	public void delete(Category category) {
		// get the current hibernate session
		Session currentSession = sessionFactObject.getCurrentSession();
		currentSession.delete(category);
	}
	
	@Override
	@Transactional
	public Category create(Category category) {
		Session currentSession = sessionFactObject.getCurrentSession();
		currentSession.save(category);
		return category;
	}

}
