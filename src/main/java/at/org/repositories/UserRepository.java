
package at.org.repositories;

import java.util.ArrayList;
import java.util.List;

import at.org.entities.User;

public class UserRepository {
  private final List<User> users = new ArrayList<>();

  public void addUser(User user) {
    users.add(user);
  }

  public List<User> getAllUsers() {
    return new ArrayList<>(users);
  }

  public User findByEmail(String email) {
    return users.stream().filter(user -> user.getEmail().equals(email)).findFirst().orElse(null);
  }

}
