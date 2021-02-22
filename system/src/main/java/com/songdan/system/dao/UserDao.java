package com.songdan.system.dao;

import com.songdan.system.model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {

    public User findByUsername(String username);
}
