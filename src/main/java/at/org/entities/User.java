
package at.org.entities;

public class User {
  private int age;
  private String email;
  private String name;

  public User() {
  }

  public User(int age, String email, String name) {
    this.age = age;
    this.email = email;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setName(String name) {
    this.name = name;
  }

}
