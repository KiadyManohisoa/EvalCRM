package site.easy.to.build.crm.controller.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.models.AuthRequest;
import site.easy.to.build.crm.service.user.AuthService;
import site.easy.to.build.crm.util.RenderResponse;

@RestController
@RequestMapping("/api")
public class AuthRestController {

        AuthService authService;

        public AuthRestController(AuthService authService) {
                this.authService = authService;
        }

        @PostMapping("/login")
        public ResponseEntity<RenderResponse<Map<String, Object>>> authenticateUser(@RequestBody AuthRequest authRequest) {
                try {
                        User user = this.authService.checkLogin(authRequest); 
                        Map<String, Object> userData = new HashMap<>();
                        userData.put("id", user.getId());
                        userData.put("username", user.getUsername());
                        return ResponseEntity.ok(new RenderResponse<>(200, userData, "Authentication successful", null));
                } catch (Exception e) {
                        // e.printStackTrace();
                        return ResponseEntity.badRequest().body(new RenderResponse<>(400, null, "Authentication failed", e.getMessage()));
                }
        }

}