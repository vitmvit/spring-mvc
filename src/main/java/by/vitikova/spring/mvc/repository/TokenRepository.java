package by.vitikova.spring.mvc.repository;

import by.vitikova.spring.mvc.model.entity.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TokenRepository extends JpaRepository<BlackList, Long> {

    boolean existsByUsername(String username);

    void deleteByUsername(String username);

    void deleteAllByExpBefore(LocalDateTime exp);
}