package com.example.restore.Services;

import com.example.restore.Model.User_;
import com.example.restore.Repositories.User_Repository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
@Slf4j
@AllArgsConstructor
public class User_Service implements UserDetailsService
{
    @Autowired
    private User_Repository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User_ user = userRepository.findByUsername(username);
        System.out.println(user);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }
    public void delete(int id) {
        userRepository.deleteById(id);
    }
    public List<User_> getAll() {
        return userRepository.findAll();
    }
}
