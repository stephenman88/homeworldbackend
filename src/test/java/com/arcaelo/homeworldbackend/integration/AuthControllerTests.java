package com.arcaelo.homeworldbackend.integration;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLoginSuccess(){
        try{
            mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"user\", \"password\": \"password\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        }catch(Exception e){
            fail("AuthControllerTest testLoginSuccess: " + e.getMessage());
        }
    }

    @Test
    void testLoginFailure(){
        try{
            mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"notUser\", \"password\": \"password\"}"))
                .andExpect(MockMvcResultMatchers.status().is(401));

            mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"user\", \"password\": \"notpassword\"}"))
                .andExpect(MockMvcResultMatchers.status().is(401));
        }catch(Exception e){
            fail("AuthControllerTest testLoginFailure: " + e.getMessage());
        }
    }
}
