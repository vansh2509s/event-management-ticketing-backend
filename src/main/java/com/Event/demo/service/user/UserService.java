package com.Event.demo.service.user;
import com.Event.demo.user.User;
import com.Event.demo.common.Role;
import java.util.List;
public interface UserService
{
    User createUser(User user);
    User getUserById(long id);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    User changeUserRole(long userId,Role role);
    void enableUser(long userId);
    void disableUser(long userId);

}
