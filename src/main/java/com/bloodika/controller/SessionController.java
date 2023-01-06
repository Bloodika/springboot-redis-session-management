package com.bloodika.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

@RestController
public class SessionController {
    @GetMapping("/sec")
    public Object getSec(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping("/session")
    public Object getSession(){
        return RequestContextHolder.currentRequestAttributes().getSessionId();
    }
}
