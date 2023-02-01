package kz.postavshik.userservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/all")
    public String all() {
        return "Hello World!";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String authenticatedAdmin() {
        return "Hello authenticated ADMIN!";
    }

    @GetMapping("/provider")
    @PreAuthorize("hasAuthority('PROVIDER')")
    public String authenticatedProvider() {
        return "Hello authenticated PROVIDER!";
    }

    @GetMapping("/client")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String authenticatedClient() {
        return "Hello authenticated CLIENT!";
    }

    @GetMapping("/authenticated")
    @PreAuthorize("hasAuthority('CLIENT') or hasAuthority('PROVIDER') or hasAuthority('ADMIN')")
    public String authenticated() {
        return "Hello authenticated user!";
    }

}
