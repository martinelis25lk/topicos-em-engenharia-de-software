package br.com.lasanhaspec.carservice.domain.models;

import br.com.lasanhaspec.carservice.domain.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;




//pq implements usa userdetails?
@Entity
@Table(name = "users")
public class User implements  UserDetails{



    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true, nullable = false)
    private String email;



    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private String password;// vai ser armazenada como hash, e não como texto puro

    private String username;
    //userdetails obriga vc a implementar esses metodos abaixo:

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority(role.name()));
    }


    @Override
    public String getPassword(){
        return password;
    }


    @Override
    public String getUsername(){
        return email;
    }


    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {return role;}

    public void setRole(Role role) {this.role = role;}


}
