package com.andrey_lebedenko;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import com.bskyb.internettv.ParentalControlServiceApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParentalControlServiceApplication.class)
@WebAppConfiguration
public class ParentalControlServiceApplicationTests {

    private final String URL = "/api/pcControl?";
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private static final String movieIdParam="movieId";
    private static final String pgLevelParam="customerPCLevel";

    /**
     * Build application and configure MockMVC environment for it.
     */
    @Before
    public void setup()
    {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    /**
     * Actual test of the REST service
     * @throws Exception
     */
    @Test
    public void contextLoads() throws Exception
    {
        // ALL MATCH
        mockMvc.perform(MockMvcRequestBuilders.get(URL+pgLevelParam+"=U&"+movieIdParam+"=a"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));

        mockMvc.perform(MockMvcRequestBuilders.get(URL+pgLevelParam+"=PG&"+movieIdParam+"=b"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));

        mockMvc.perform(MockMvcRequestBuilders.get(URL+pgLevelParam+"=12&"+movieIdParam+"=c"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));

        mockMvc.perform(MockMvcRequestBuilders.get(URL+pgLevelParam+"=15&"+movieIdParam+"=d"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));

        mockMvc.perform(MockMvcRequestBuilders.get(URL+pgLevelParam+"=18&"+movieIdParam+"=e"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));

        // ALL TRUE (level higher or equal)
        mockMvc.perform(MockMvcRequestBuilders.get(URL+pgLevelParam+"=PG&"+movieIdParam+"=a"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));

        mockMvc.perform(MockMvcRequestBuilders.get(URL+pgLevelParam+"=12&"+movieIdParam+"=b"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));

        mockMvc.perform(MockMvcRequestBuilders.get(URL+pgLevelParam+"=15&"+movieIdParam+"=c"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));

        mockMvc.perform(MockMvcRequestBuilders.get(URL+pgLevelParam+"=18&"+movieIdParam+"=d"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));

        mockMvc.perform(MockMvcRequestBuilders.get(URL+pgLevelParam+"=18&"+movieIdParam+"=e"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));

        // ALL FALSE
        mockMvc.perform(MockMvcRequestBuilders.get(URL+pgLevelParam+"=U&"+movieIdParam+"=b"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"));

        mockMvc.perform(MockMvcRequestBuilders.get(URL+pgLevelParam+"=PG&"+movieIdParam+"=c"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"));

        mockMvc.perform(MockMvcRequestBuilders.get(URL+pgLevelParam+"=12&"+movieIdParam+"=d"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"));

        mockMvc.perform(MockMvcRequestBuilders.get(URL+pgLevelParam+"=15&"+movieIdParam+"=e"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"));

        // ALL EXCEPTIONS

        // AS PER THIS: "Indicate that the customer cannot watch this movie" from the task description returns false instead of 503 SERVICE UNAVAILABLE
        //mockMvc.perform(MockMvcRequestBuilders.get(URL+pgLevelParam+"=18&"+movieIdParam+"=f"))
        //        .andExpect(MockMvcResultMatchers.status().isServiceUnavailable());

        mockMvc.perform(MockMvcRequestBuilders.get(URL+pgLevelParam+"=18&"+movieIdParam+"=f"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"));

        mockMvc.perform(MockMvcRequestBuilders.get(URL+pgLevelParam+"=18&"+movieIdParam+"=x"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        // BAD REQUEST
        mockMvc.perform(MockMvcRequestBuilders.get(URL+pgLevelParam+"=18"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.get(URL+movieIdParam+"=t"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // GENERAL REQUEST OVERFLOW CHECK
        char[] c = new char[1000000];
        Arrays.fill(c, '\0');
        mockMvc.perform(MockMvcRequestBuilders.get(URL+new String(c)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
