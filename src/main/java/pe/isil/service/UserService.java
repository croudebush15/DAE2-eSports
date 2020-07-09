package pe.isil.service;

import pe.isil.model.User;

public interface UserService {

    void save(User user);

    User findByUsername(String username);

}
