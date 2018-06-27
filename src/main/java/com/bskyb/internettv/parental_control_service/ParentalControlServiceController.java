package com.bskyb.internettv.parental_control_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;

import com.bskyb.internettv.ParentalControlServiceApplication;
import com.bskyb.internettv.thirdparty.TechnicalFailureException;
import com.bskyb.internettv.thirdparty.TitleNotFoundException;

@RestController
@RequestMapping("/api/pcControl")
public class ParentalControlServiceController {

	private ParentalControlService parentalControlService;

	@Autowired
	public ParentalControlServiceController(ParentalControlService parentalControlService) {
		this.parentalControlService = parentalControlService;
	}

	@SuppressWarnings("rawtypes")
	@GetMapping()
	public ResponseEntity isMovieAllowedForTheLevel(@RequestParam("customerPCLevel") String  customerPCLevel,
			@RequestParam("movieId") String  movieId) throws Exception {

		try {
			return ResponseEntity.ok(parentalControlService.canWatchMovie(customerPCLevel, movieId));
		}  catch (TitleNotFoundException ex) {
            Logger.getLogger(ParentalControlServiceApplication.class.getName()).log(Level.INFO, ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(HttpStatus.NOT_FOUND);
        } catch (TechnicalFailureException ex) {
            Logger.getLogger(ParentalControlServiceApplication.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return ResponseEntity.ok().body(false);
        }
	}

	public ParentalControlService getParentalControlService() {
		return parentalControlService;
	}
}
