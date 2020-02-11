package de.lobo.spring.security.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

  public String accessPublicResource() {
    return "You successfully accessed a public resource";
  }

  public String accessUserResource() {
    return "You successfully accessed a user resource";
  }

  @PreAuthorize("hasAnyAuthority('privileged','admin')")
  public String accessPrivilegedResource() {
    return "You successfully accessed a privileged resource";
  }

  @PreAuthorize("hasAuthority('admin')")
  public String accessAdminResource() {
    return "You successfully accessed a admin resource";
  }
}
