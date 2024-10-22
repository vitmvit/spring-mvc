package by.vitikova.spring.mvc.model.entity;

import by.vitikova.spring.mvc.constant.RoleName;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleName name;

    public Role(RoleName name) {
        setName(name);
    }

//    @ManyToMany
//    @JoinTable(
//            name = "link_user_role",
//            joinColumns = @JoinColumn(name = "role_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id"),
//            foreignKey = @ForeignKey(name = "fk_role_to_user")
//    )
//    private List<User> userList;
}