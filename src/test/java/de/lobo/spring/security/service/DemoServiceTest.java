package de.lobo.spring.security.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DemoServiceTest {

  @Autowired
  private DemoService demoService;

  @Test
  public void test_public() {
    demoService.accessPublicResource();
  }

  @Test(expected = AuthenticationCredentialsNotFoundException.class)
  public void test_privileged_should_fail_missing_auth() {
    demoService.accessPublicResource();
    demoService.accessPrivilegedResource();
  }

  @Test(expected = AccessDeniedException.class)
  @WithMockUser(authorities = "foobar")
  public void test_privileged_should_fail_bad_permission() {
    demoService.accessPublicResource();
    demoService.accessPrivilegedResource();
  }

  @Test
  @WithMockUser(authorities = "privileged")
  public void test_privileged_should_succeed() {
    demoService.accessPublicResource();
    demoService.accessPrivilegedResource();
  }

  @Test
  @WithMockUser(authorities = "admin")
  public void test_admin_should_succeed() {
    demoService.accessPublicResource();
    demoService.accessPrivilegedResource();
    demoService.accessAdminResource();
  }
}
