package com.example.todo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.todo.domain.User;
import com.example.todo.repository.UserRepository;
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    @Test
    public void createUser_shouldReturnUser_whenEmailIsValid(){
        //arange
        User inputUser = new User(null,"taidung","dungmount75@gmail.com");
        User outputUser = new User(1L,"taidung","dungmount75@gmail.com");

        when(this.userRepository.existsByEmail(inputUser.getEmail())).thenReturn(false);

        when(this.userRepository.save(any())).thenReturn(outputUser);

        //act
        User result=this.userService.createUser(inputUser);
        //assert
        assertEquals(1L, result.getId());
    }

    @Test
    public void createUser_shouldThrowException_whenEmailInValid(){
        //arange
        User inputUser = new User(null,"taidung","dungmount75@gmail.com");
        when(this.userRepository.existsByEmail(inputUser.getEmail())).thenReturn(true);

        //act
        Exception ex=assertThrows(IllegalArgumentException.class, ()->{
            this.userService.createUser(inputUser);
        });
        //assert
        assertEquals("Email already exists", ex.getMessage());
    }

    
    @Test
    public void getAllUsers_shouldReturnAllUsers(){
        //arrange
        List<User>outputUsers=new ArrayList<>();
        outputUsers.add(new User(1L,"tai dung","dungmount75@gmail.com"));
        outputUsers.add(new User(2L,"tai thanh","taithanh@gmail.com"));
        when(this.userRepository.findAll()).thenReturn(outputUsers);
        //act
        this.userService.getAllUsers();
        //assert
        assertEquals("dungmount75@gmail.com", outputUsers.get(0).getEmail());
    }

    @Test
    public void getUserById_shouldReturnOptionalUser(){
        //arrange
        Long inputId = 1L;
        User inputUser=new User(1L,"tai dung","dungmount75@gmail.com");
        Optional<User>optionalUser = Optional.of(inputUser);
        when(this.userRepository.findById(inputId)).thenReturn(optionalUser);

        //act
        Optional<User>outputUser=this.userService.getUserById(inputId);
        //assert
        assertEquals(true,outputUser.isPresent());
    }

    @Test
    public void deleteUser_shouldReturnVoid_whenUserExists(){
        //arrange
        Long inputId=1L;
        when(this.userRepository.existsById(inputId)).thenReturn(true);
        //act
        this.userService.deleteUser(inputId);
        //assert
        verify(this.userRepository).deleteById(inputId);
    }

    @Test
    public void deleteUser_shouldThrowException_whenUserNotExists(){
        //arrange
        Long inputId = 1L;
        when(this.userRepository.existsById(inputId)).thenReturn(false);
        //act
        Exception ex=assertThrows(NoSuchElementException.class, ()->{
            this.userService.deleteUser(inputId);
        });
        //assert
        assertEquals("User not found", ex.getMessage());

    }

    @Test
    public void updatedUser_shouldRetunrVoid_whenIdExists(){
        //arrange
        Long inputId=1L;
        User inputUser = new User(1L,"taidungold","dungmount75old@gmail.com");
        User outputUser = new User(1L,"taidungnew","dungmount75new@gmail.com");
        when(this.userRepository.findById(inputId)).thenReturn(Optional.of(inputUser));
        when(this.userRepository.save(any())).thenReturn(outputUser);

        //act
        User updateUser =this.userService.updateUser(inputId, outputUser);
        //assert
        assertEquals("taidungnew", updateUser.getName());
    }



}
