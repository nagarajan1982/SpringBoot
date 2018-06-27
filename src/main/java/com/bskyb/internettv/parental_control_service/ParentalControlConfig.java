package com.bskyb.internettv.parental_control_service;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "parentalControl")
public class ParentalControlConfig {

	private Map<String, Integer> levelMap;

	public ParentalControlConfig() {
        this.levelMap = new TreeMap<String, Integer>();
    }
	public ParentalControlConfig(Map<String, Integer> levelMap) {
        this.levelMap = levelMap;
    }

    public Map<String, Integer> getLevelMap() {
        return this.levelMap;
    }
}
