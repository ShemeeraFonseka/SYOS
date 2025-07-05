package dao;

import model.User;

public interface LoginDAO {
    User loginCustomer(String username, String password);
}
