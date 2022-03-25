package com.servico.service;

import com.servico.model.entity.User;

public interface UserService {
    User getUserById(Long id);
    User saveUser(User user);
}
