package by.vitikova.spring.mvc.model.entity;

import by.vitikova.spring.mvc.constant.RoleName;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static by.vitikova.spring.mvc.constant.Constant.ADMIN_ROLE;
import static by.vitikova.spring.mvc.constant.Constant.USER_ROLE;

/**
 * Модель пользователя
 */
@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
@EqualsAndHashCode
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String passwordHash;
    private RoleName role;

    /**
     * Конструктор с параметрами.
     *
     * @param login        логин пользователя
     * @param passwordHash пароль пользователя
     * @param role         роль пользователя
     */
    public User(String login, String passwordHash, RoleName role) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == RoleName.ADMIN) {
            return List.of(new SimpleGrantedAuthority(ADMIN_ROLE), new SimpleGrantedAuthority(USER_ROLE));
        }
        return List.of(new SimpleGrantedAuthority(USER_ROLE));
    }

    @Override
    public String getPassword() {
        return getPasswordHash();
    }

    @Override
    public String getUsername() {
        return login;
    }

    /**
     * /* {@inheritDoc}
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * /* {@inheritDoc}
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * /* {@inheritDoc}
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}