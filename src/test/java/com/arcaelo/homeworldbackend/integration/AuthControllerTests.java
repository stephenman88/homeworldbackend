package com.arcaelo.homeworldbackend.integration;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "jwtKey=q93givaesj4p9tq3hp48thq349tq34p98tjuihq40et78q34tg4qh834ruq7392t90q347tq23r, jwtExpire=60000, superAdmin=user@google.ca, superPassword=password")
public class AuthControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLoginSuccess(){
        try{
            String token = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"user@google.ca\", \"password\": \"password\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .substring(10, 196);

            System.out.println(token);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/player/test").header("Authorization", "Bearer " + token))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("user@google.ca"));
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
