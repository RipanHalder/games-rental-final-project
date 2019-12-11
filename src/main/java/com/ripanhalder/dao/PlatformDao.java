package com.ripanhalder.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.ripanhalder.entity.Platform;;

public interface PlatformDao {

	public Platform get(String title);

	public List<Platform> list();

	public Platform create(String title);
	
	public Platform create(Platform platform);

	public void update(Platform platform);

	public void delete(Platform platform);

	Platform getPlatform(long platformId);
}
