package com.ripanhalder.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ripanhalder.entity.Game;

@Repository
public class CanvasjsChartDaoImpl implements CanvasjsChartDao {
	
	@Autowired
	GameDao gameDao;

	@Override
	public List<List<Map<Object, Object>>> getCanvasjsChartData() {
		Map<Object,Object> map = null;
		List<List<Map<Object,Object>>> list = new ArrayList<List<Map<Object,Object>>>();
		List<Map<Object,Object>> dataPoints1 = new ArrayList<Map<Object,Object>>();
		
		List<Game> allGames = gameDao.list();
		int totalRentedGames = 0;
		int yValue = 0;
		for (Game game: allGames) {
			totalRentedGames += game.getCurrentRentalQuantity();
		}
		for (Game game: allGames) {
			map = new HashMap<Object,Object>();
			map.put("name", game.getTitle());
			yValue= (game.getCurrentRentalQuantity()*100)/totalRentedGames;
			map.put("y", yValue);
			dataPoints1.add(map);
		}
		list.add(dataPoints1);
		return list;
	}

}
