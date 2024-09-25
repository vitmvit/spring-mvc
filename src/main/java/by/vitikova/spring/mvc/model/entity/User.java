package by.vitikova.spring.mvc.model.entity;

import by.vitikova.spring.mvc.model.RoleName;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static by.vitikova.spring.mvc.constant.Constant.*;

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
        if (this.role == RoleName.SUPPORT) {
            return List.of(new SimpleGrantedAuthority(SUPPORT_ROLE), new SimpleGrantedAuthority(USER_ROLE));
        }
        if (this.role == RoleName.VET) {
            return List.of(new SimpleGrantedAuthority(VET_ROLE), new SimpleGrantedAuthority(USER_ROLE));
        }

        if (this.role == RoleName.EDITOR) {
            return List.of(new SimpleGrantedAuthority(EDITOR_ROLE), new SimpleGrantedAuthority(USER_ROLE));
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