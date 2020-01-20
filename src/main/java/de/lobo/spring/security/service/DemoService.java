package de.lobo.spring.security.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

  public String accessPublicResource() {
    return "You successfully accessed a public resource";
  }

  //TODO: implement mapping of auth0-scopes to spring-security-roles
 // @Secured("ROLE_USER")
  public String accessUserResource() {
    return "You successfully accessed a user resource";
  }
  //TODO: implement mapping of auth0-scopes to spring-security-roles
 // @Secured("ROLE_PRIVILEGED")
  public String accessPrivilegedResource() {
    return "You successfully accessed a privileged resource";
  }
  //TODO: implement mapping of auth0-scopes to spring-security-roles
 // @Secured("ROLE_ADMIN")
  public String accessAdminResource() {
    return "You successfully accessed a admin resource";
  }
}
