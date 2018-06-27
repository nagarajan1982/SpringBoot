package com.bskyb.internettv.thirdparty;

import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {

	public String getParentalControlLevel(String titleId) throws TechnicalFailureException, TitleNotFoundException {
        switch(titleId)
        {
            case "a":
            {
                return "U";
            }
            case "b":
            {
                return "PG";
            }
            case "c":
            {
                return "12";
            }
            case "d":
            {
                return "15";
            }
            case "e":
            {
                return "18";
            }
            case "f":
                throw new TechnicalFailureException("Technical Failure during accessing: "+ titleId);
        }
        throw new TitleNotFoundException("Title not found for id "+ titleId);
    }

}
