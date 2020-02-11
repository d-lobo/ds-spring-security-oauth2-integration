package de.lobo.spring.security.api;

import de.lobo.spring.security.service.DemoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DemoController {
  private final DemoService demoService;

  @GetMapping("/public")
  public ResponseEntity getPublicResource() {
    return ResponseEntity.ok(demoService.accessPublicResource());
  }

  @GetMapping("/user")
  public ResponseEntity getUserResource() {
    return ResponseEntity.ok(demoService.accessUserResource());
  }

  @GetMapping("/privileged")
  public ResponseEntity getPrivilegedResource() {
    return ResponseEntity.ok(demoService.accessPrivilegedResource());
  }

  @GetMapping("/admin")
  public ResponseEntity getAdminResource() {
    return ResponseEntity.ok(demoService.accessAdminResource());
  }
}
