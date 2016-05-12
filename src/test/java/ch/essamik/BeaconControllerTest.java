package ch.essamik;

import ch.essamik.model.Beacon;
import ch.essamik.repositories.BeaconRepository;
import com.google.gson.Gson;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LbsBackendApplication.class)
@WebAppConfiguration
public class BeaconControllerTest {


    @Autowired
    private BeaconRepository beaconRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private Gson gson = new Gson();


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private Beacon beacon101 = new Beacon("Beacon101", "f7826da6-4fa2-4e98-8024-bc5b71e0893e", 100 ,1);
    private Beacon beacon102 = new Beacon("Beacon102", "f7826da6-4fa2-4e98-8024-bc5b71e0893e", 100 ,2);
    private Beacon beacon103 = new Beacon("Beacon103", "f7826da6-4fa2-4e98-8024-bc5b71e0893e", 100 ,3);
    private Beacon beacon104 = new Beacon("Sensor1", "4dd96110-ae33-41c6-b29c-800848129036", 345 ,2);
    private Beacon beacon105 = new Beacon("ProximitySensor", "86690f51-4b60-4fa2-a4d4-5520745acc4a", 1 ,2);

    @Before
    public void setup() throws Exception {
        //Give the WebAppContext to the mock framework
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.beaconRepository.deleteAll();

        this.beaconRepository.save(beacon101);
        this.beaconRepository.save(beacon102);
        this.beaconRepository.save(beacon103);
        this.beaconRepository.save(beacon104);
        this.beaconRepository.save(beacon105);
    }

    @Test
    public void getAllBeacons() throws Exception {
        mockMvc.perform(get("/beacons/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    public void removeAllBeacons() throws Exception {
        this.beaconRepository.deleteAll();
        mockMvc.perform(get("/beacons/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getBeaconByName() throws Exception {
        mockMvc.perform(get("/beacons/" + beacon101.getName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.name", is(beacon101.getName())))
                .andExpect(jsonPath("$.uuid", is(beacon101.getUuid())))
                .andExpect(jsonPath("$.major", is(beacon101.getMajor())))
                .andExpect(jsonPath("$.minor", is(beacon101.getMinor())));
    }

    @Test
    public void getUnknownBeaconByName() throws Exception {
        mockMvc.perform(get("/beacons/" + "UKNW Beacon"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateBeacon() throws Exception {
        Beacon beaconToUpdate = beaconRepository.findByName(this.beacon103.getName());

        beaconToUpdate.setName("Updated Beacon");
        beaconToUpdate.setVendorId("A104");

        String jsonBeacon = gson.toJson(beaconToUpdate);

        mockMvc.perform(put("/beacons/")
                .content(jsonBeacon)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.name", is(beaconToUpdate.getName())))
                .andExpect(jsonPath("$.vendorId", is(beaconToUpdate.getVendorId())))
                .andExpect(jsonPath("$.uuid", is(beaconToUpdate.getUuid())))
                .andExpect(jsonPath("$.major", is(beaconToUpdate.getMajor())))
                .andExpect(jsonPath("$.minor", is(beaconToUpdate.getMinor())));
    }

    @Test
    public void createBeacon() throws Exception {
        Beacon beaconToCreate = new Beacon("New beacon", "8661af32-4b60-4712-a4d4-5520745bdd", 1231, 12312);
        beaconToCreate.setVendorId("AAAA");

        String jsonBeacon = gson.toJson(beaconToCreate);

        mockMvc.perform(post("/beacons")
                .content(jsonBeacon)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(beaconToCreate.getName())))
                .andExpect(jsonPath("$.vendorId", is(beaconToCreate.getVendorId())))
                .andExpect(jsonPath("$.uuid", is(beaconToCreate.getUuid())))
                .andExpect(jsonPath("$.major", is(beaconToCreate.getMajor())))
                .andExpect(jsonPath("$.minor", is(beaconToCreate.getMinor())));
    }
}
