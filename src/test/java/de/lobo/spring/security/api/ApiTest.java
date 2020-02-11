package de.lobo.spring.security.api;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.lobo.spring.security.service.DemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest
@RunWith(SpringRunner.class)
public class ApiTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private DemoService demoService;

  @Test
  public void test_no_auth() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/public")).andExpect(status().is2xxSuccessful());
    mvc.perform(MockMvcRequestBuilders.get("/user")).andExpect(status().isUnauthorized());
    mvc.perform(MockMvcRequestBuilders.get("/privileged")).andExpect(status().isUnauthorized());
    mvc.perform(MockMvcRequestBuilders.get("/admin")).andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser
  public void test_w_auth() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/public")).andExpect(status().is2xxSuccessful());
    mvc.perform(MockMvcRequestBuilders.get("/user")).andExpect(status().is2xxSuccessful());
    mvc.perform(MockMvcRequestBuilders.get("/privileged")).andExpect(status().isForbidden());
    mvc.perform(MockMvcRequestBuilders.get("/admin")).andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(authorities = "privileged")
  public void test_w_auth_and_privileged_permission() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/public")).andExpect(status().is2xxSuccessful());
    mvc.perform(MockMvcRequestBuilders.get("/user")).andExpect(status().is2xxSuccessful());
    mvc.perform(MockMvcRequestBuilders.get("/privileged")).andExpect(status().is2xxSuccessful());
    mvc.perform(MockMvcRequestBuilders.get("/admin")).andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(authorities = "admin")
  public void test_w_auth_and_admin_permission() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/public")).andExpect(status().is2xxSuccessful());
    mvc.perform(MockMvcRequestBuilders.get("/user")).andExpect(status().is2xxSuccessful());
    mvc.perform(MockMvcRequestBuilders.get("/privileged")).andExpect(status().is2xxSuccessful());
    mvc.perform(MockMvcRequestBuilders.get("/admin")).andExpect(status().is2xxSuccessful());
  }
}
