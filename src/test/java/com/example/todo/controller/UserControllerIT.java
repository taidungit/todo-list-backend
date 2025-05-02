package com.example.todo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.example.todo.domain.User;
import com.example.todo.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
@Transactional
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    public void init(){
        this.userRepository.deleteAll();
    }
    @Test
    public void createUser_shouldReturnUser_whenUserValid() throws Exception{
        
        //arrange
        User userInput =new User(null,"Tai dung","taidung@gmail.com");
        //action
        String result=this.mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsBytes(userInput)))
        .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        //assert
        System.out.println("Result: "+result);
        User outputUser = objectMapper.readValue(result, User.class);

		assertEquals(userInput.getName(), outputUser.getName());
    }



    @Test
    public void getAllUsers() throws Exception{
        
        //arrange
        User user1= new User(null,"user1","user1@gmail.com");
        User user2= new User(null,"user2","user2@gmail.com");
        List<User> data=List.of(user1,user2);
        this.userRepository.saveAll(data);
        //action   
        String resultStr=this.mockMvc.perform(get("/users")).andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();
        List<User>result=this.objectMapper.readValue(resultStr,new TypeReference<List<User>>(){});
        //assert
        assertEquals(2, result.size());
        assertEquals("user1@gmail.com", result.get(0).getEmail());
    }

    @Test
    public void getUserById() throws Exception{
        ;
        //arrange
        User user=new User(null,"user1","user1@gmail.com");
        User userInput=this.userRepository.saveAndFlush(user);
        //action
        String resultStr=this.mockMvc.perform(get("/users/{id}",userInput.getId()))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();
        User userOutput=this.objectMapper.readValue(resultStr,User.class);
        //assert
        assertEquals("user1", userOutput.getName());
    }

    @Test
    public void getUserById_shouldEmpty_whenIdisInvalid() throws Exception{
        //arrange

        //action
        this.mockMvc.perform(get("/users/{id}",0))
        .andExpect(status().isNotFound());
        //assert
    }

    @Test
    public void updateUser() throws Exception{
        //arrange
        
        User user =new User(null,"old","old@gmail.com");
        User userInput=this.userRepository.saveAndFlush(user);
        User userUpdate= new User(userInput.getId(),"update","update@gmail.com");
        //action
        String resultStr=this.mockMvc.perform(put("/users/{id}",userUpdate.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsBytes(userUpdate)))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        User userOutput= this.objectMapper.readValue(resultStr,User.class);
        //assert
        assertEquals("update", userOutput.getName());
    }

    @Test
    public void deleteUser() throws Exception{
        //arrange
        
        User user=new User(null,"delete","delete@gmai.com");
        User inputUser=this.userRepository.saveAndFlush(user);
        //action
        this.mockMvc.perform(delete("/users/{id}",inputUser.getId()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
        //assert
        long count=this.userRepository.count();
        assertEquals(0, count);
    }
}
