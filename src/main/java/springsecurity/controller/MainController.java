package springsecurity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springsecurity.dto.ResDTO;
import springsecurity.dto.ResWrapper;

@RestController
public class MainController {

    @GetMapping("/me")
    public ResWrapper me() {
        ResDTO resDTO=new ResDTO();
        resDTO.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        ResWrapper resWrapper=new ResWrapper();
        resWrapper.setResponse(resDTO);

        return resWrapper;
    }
}
