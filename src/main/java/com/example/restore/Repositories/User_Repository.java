package com.example.restore.Repositories;

import com.example.restore.Model.User_;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface User_Repository extends JpaRepository<User_, Integer> {
    @Query("select u from User_ u where u.username = :username")
    User_ findByUsername(@Param("username") String username);
}
