package by.vitikova.spring.mvc.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Модель пользователя
 */
@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String passwordHash;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "link_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            foreignKey = @ForeignKey(name = "fk_user_to_role")
    )
    private Set<Role> roleList;

    public User(String login, String passwordHash, Set<Role> roleList) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.roleList = roleList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        if (this.role == RoleName.ADMIN) {
//            return List.of(new SimpleGrantedAuthority(ADMIN_ROLE), new SimpleGrantedAuthority(USER_ROLE));
//        }
//        if (this.role == RoleName.JOURNALIST) {
//            return List.of(new SimpleGrantedAuthority(JOURNALIST_ROLE), new SimpleGrantedAuthority(USER_ROLE));
//        }
//        if (this.role == RoleName.SUBSCRIBER) {
//            return List.of(new SimpleGrantedAuthority(SUBSCRIBER_ROLE), new SimpleGrantedAuthority(USER_ROLE));
//        }
//        return List.of(new SimpleGrantedAuthority(USER_ROLE));

        return this.getRoleList()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return getPasswordHash();
    }

    @Override
    public String getUsername() {
        return login;
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
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}