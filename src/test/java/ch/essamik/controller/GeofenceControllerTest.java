package ch.essamik.controller;

import ch.essamik.LbsBackendApplication;
import ch.essamik.model.Geofence;
import ch.essamik.repositories.GeofenceRepository;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LbsBackendApplication.class)
@WebAppConfiguration
public class GeofenceControllerTest {

    @Autowired
    private GeofenceRepository geofenceRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private Gson gson = new Gson();

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private Geofence geofenceArea1 = new Geofence("GeofenceArea1",89.39,40.40, 500f);
    private Geofence geofenceArea2 = new Geofence("GeofenceArea2",69.39,40.40, 500f);
    private Geofence geofenceArea3 = new Geofence("GeofenceArea3",49.39,40.40, 500f);

    private static final String UNKNOWN_GEOFENCE = "unknown geofence";
    private static final String BASE_ROUTE = "/geofences/";

    @Before
    public void setup() throws Exception{

        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.geofenceRepository.deleteAll();

        this.geofenceRepository.save(geofenceArea1);
        this.geofenceRepository.save(geofenceArea2);
        this.geofenceRepository.save(geofenceArea3);
    }

    @Test
    public void getAllGeofences() throws Exception{
       mockMvc.perform(get(BASE_ROUTE))
               .andExpect(status().isOk())
               .andExpect(content().contentType(contentType))
               .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void createGeofence() throws  Exception{
        Geofence geofenceToCreate = new Geofence("New Geofence",29.39,40.40,500F);

        String jsonGeofence = gson.toJson(geofenceToCreate);

        mockMvc.perform(post(BASE_ROUTE)
                .content(jsonGeofence)
                .contentType(contentType))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",is(geofenceToCreate.getName())))
                .andExpect(jsonPath("$.longitude", is(geofenceToCreate.getLongitude())))
                .andExpect(jsonPath("$.latitude", is(geofenceToCreate.getLatitude())))
                //TODO Check for better solution to test this
                .andExpect(jsonPath("$.radius", is(500.0)));
    }

    @Test
    public void getGeofenceByName() throws Exception{
        mockMvc.perform(get(BASE_ROUTE + geofenceArea1.getName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.name",is(geofenceArea1.getName())))
                .andExpect(jsonPath("$.longitude", is(geofenceArea1.getLongitude())))
                .andExpect(jsonPath("$.latitude", is(geofenceArea1.getLatitude())))
                //TODO Check for better solution to test this
                .andExpect(jsonPath("$.radius", is(500.0)));
    }

    @Test
    public void getUnkonwGeofenceByNameReturnNotNound() throws Exception{
        mockMvc.perform(get(BASE_ROUTE + UNKNOWN_GEOFENCE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateGeofence() throws Exception {
        Geofence geofenceToUpdate = geofenceRepository.findByName(this.geofenceArea1.getName());

        geofenceToUpdate.setName("Updated Geofence");

        String jsonGeofence = gson.toJson(geofenceToUpdate);

        mockMvc.perform(put(BASE_ROUTE)
                .content(jsonGeofence)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.name", Matchers.is(geofenceToUpdate.getName())))
                .andExpect(jsonPath("$.longitude", Matchers.is(geofenceToUpdate.getLongitude())))
                .andExpect(jsonPath("$.latitude", Matchers.is(geofenceToUpdate.getLatitude())))
                //TODO Check for better solution to test this
                .andExpect(jsonPath("$.radius", is(500.0)));
    }
}
