package com.andrey_lebedenko;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.bskyb.internettv.parental_control_service.ParentalControlConfig;
import com.bskyb.internettv.parental_control_service.ParentalControlService;
import com.bskyb.internettv.parental_control_service.ParentalControlServiceImpl;
import com.bskyb.internettv.thirdparty.MovieService;
import com.bskyb.internettv.thirdparty.TechnicalFailureException;
import com.bskyb.internettv.thirdparty.TitleNotFoundException;


@RunWith(MockitoJUnitRunner.class)
public class ParentalControlServiceTest {

	private static String PC_LEVEL_U = "U";
	private static String PC_LEVEL_PG = "PG";
	private static String PC_LEVEL_12 = "12";
	private ParentalControlService controlService;
	@Mock //MovieService service is mocked by considering that, this service is developed by 'Movie Meta Data team' and is abstracted from us
	private MovieService movieService;
	private Map<String, Integer> levelMap;

	@Before
	public void setUp() throws TitleNotFoundException, TechnicalFailureException {
		levelMap = new TreeMap<>();
		levelMap.put(PC_LEVEL_U, 1);
		levelMap.put(PC_LEVEL_PG, 2);
		levelMap.put(PC_LEVEL_12, 3);
		controlService = new ParentalControlServiceImpl(movieService, new ParentalControlConfig(levelMap));

		when(movieService.getParentalControlLevel("a")).thenReturn(PC_LEVEL_U);
		when(movieService.getParentalControlLevel("b")).thenReturn(PC_LEVEL_PG);
		when(movieService.getParentalControlLevel("c")).thenReturn(PC_LEVEL_12);

	}

	@Test
	public void testIsCustomerAllowedToWatch_Passing_CLevel_U() throws Exception {
		assertThat(controlService.canWatchMovie(PC_LEVEL_U, "a"), is(true));

		assertThat(controlService.canWatchMovie(PC_LEVEL_U, "b"), is(false));

		assertThat(controlService.canWatchMovie(PC_LEVEL_U, "c"), is(false));
	}

	@Test
	public void testIsCustomerAllowedToWatch_Passing_CLevel_PG() throws Exception {
		assertThat(controlService.canWatchMovie(PC_LEVEL_PG, "a"), is(true));

		assertThat(controlService.canWatchMovie(PC_LEVEL_PG, "b"), is(true));

		assertThat(controlService.canWatchMovie(PC_LEVEL_PG, "c"), is(false));
	}

	@Test
	public void testIsCustomerAllowedToWatch_Passing_CLevel_12() throws Exception {
		assertThat(controlService.canWatchMovie(PC_LEVEL_12, "a"), is(true));

		assertThat(controlService.canWatchMovie(PC_LEVEL_12, "b"), is(true));

		assertThat(controlService.canWatchMovie(PC_LEVEL_12, "c"), is(true));
	}

}
