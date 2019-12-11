package com.ripanhalder.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.ripanhalder.entity.Category;

public interface CategoryDao {

	public Category get(String title);

	public List<Category> list();

	public Category create(String title);
	
	public Category create(Category category);

	public void update(Category category);

	public void delete(Category category);

	Category getCategory(long categoryId);
}
