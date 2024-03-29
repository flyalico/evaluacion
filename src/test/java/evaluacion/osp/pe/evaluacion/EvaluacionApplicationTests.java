package evaluacion.osp.pe.evaluacion;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EvaluacionApplicationTests {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    @Before
    public void setup(){
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void contextLoads() throws Exception{
        this.mvc.perform(MockMvcRequestBuilders.get("/api/peliculas"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.equalTo(6)));
    }

}
