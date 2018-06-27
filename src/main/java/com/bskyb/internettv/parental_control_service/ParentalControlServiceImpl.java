package com.bskyb.internettv.parental_control_service;

import static org.springframework.util.StringUtils.isEmpty;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskyb.internettv.thirdparty.MovieService;

@Service
public class ParentalControlServiceImpl implements ParentalControlService {

	private MovieService movieService;
	private Map<String, Integer> levelMap;

	@Autowired
	public ParentalControlServiceImpl(MovieService movieService, ParentalControlConfig pcLevelConfig) {
		this.levelMap = pcLevelConfig.getLevelMap();
		this.movieService = movieService;
	}

	public boolean canWatchMovie(String customerPCLevel, String movieId) throws Exception {
		return !isEmpty(customerPCLevel)
				&& levelMap.containsKey(customerPCLevel.toUpperCase())
				&& isCustomerAllowedToWatch(customerPCLevel, movieService.getParentalControlLevel(movieId));
	}

	private boolean isCustomerAllowedToWatch(String customerLevel, String movieLevel) {
		return levelMap.containsKey(movieLevel.toUpperCase())
				&& levelMap.get(customerLevel) >= levelMap.get(movieLevel);
	}

	public MovieService getMovieService() {
		return movieService;
	}

	public Map<String, Integer> getLevelMap() {
		return levelMap;
	}
}
