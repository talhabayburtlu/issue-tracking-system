package com.its.keycloakintegrationservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class UserService {

    public void createUser(Map<String, Object> representationMap) {
        System.out.println(representationMap);
    }

}
