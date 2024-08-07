package com.oracle.football.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements UserDetails {

  @Getter
  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Getter
  @Setter
  @Column(nullable = false, unique = true)
  private String email;

  @Getter
  @Setter
  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String password;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserPlayerReview> reviews;

  public User() {
  }

  public User(String email, String name, String password) {
    this.email = email;
    this.name = name;
    this.password = password;
  }

  public User(Integer id, String email, String name, String password) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.password = password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
    User user = (User) o;
    return Objects.equals(id, user.id)
        && Objects.equals(name, user.name)
        && Objects.equals(email, user.email)
        && Objects.equals(password, user.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, email, password);
  }

  @Override
  public String toString() {
    return "User {" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", username='" + email + '\'' +
        ", password='" + password + '\'' +
        '}';
  }

}