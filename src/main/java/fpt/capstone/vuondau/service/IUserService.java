package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findByUsername(String username);
}
