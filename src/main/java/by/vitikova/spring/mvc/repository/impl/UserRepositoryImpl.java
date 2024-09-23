package by.vitikova.spring.mvc.repository.impl;

import by.vitikova.spring.mvc.exception.EntityNotFoundException;
import by.vitikova.spring.mvc.exception.OperationException;
import by.vitikova.spring.mvc.model.entity.User;
import by.vitikova.spring.mvc.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Реализация репозитория для работы с сущностью {@link User}.
 */
@Repository
@Transactional
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    private final String FIND_BY_ID_QUERY = "SELECT p FROM user p WHERE p.id = :id";
    private final String DELETE_BY_ID_QUERY = "DELETE FROM user c WHERE c.id = :id";

    /**
     * Находит пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя.
     * @return найденный объект {@link User}.
     * @throws EntityNotFoundException если пользователь не найден.
     */
    @Override
    public User findById(Long id) {
        try {
            TypedQuery<User> query = entityManager.createQuery(FIND_BY_ID_QUERY, User.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (Exception ex) {
            throw new EntityNotFoundException("User with id " + id + " not found: " + ex);
        }
    }

    /**
     * Находит всех пользователей в базе данных.
     *
     * @return список объектов {@link User}.
     * @throws OperationException в случае ошибки при выполнении операции.
     */
    @Override
    public List<User> findAll() {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> rootEntry = criteriaQuery.from(User.class);
            CriteriaQuery<User> all = criteriaQuery.select(rootEntry);
            return entityManager.createQuery(all).getResultList();
        } catch (Exception ex) {
            throw new OperationException(("Find all users exception: " + ex));
        }
    }

    /**
     * Создает нового пользователя в базе данных.
     *
     * @param user объект {@link User}, который нужно создать.
     * @return созданный объект {@link User}.
     * @throws OperationException в случае ошибки при выполнении операции.
     */
    @Override
    public User create(User user) {
        try {
            entityManager.persist(user);
            return user;
        } catch (Exception ex) {
            throw new OperationException(("Create exception: " + ex));
        }
    }

    /**
     * Обновляет информацию о существующем пользователе.
     *
     * @param user объект {@link User} с обновленными данными.
     * @return обновленный объект {@link User}.
     * @throws OperationException в случае ошибки при выполнении операции.
     */
    @Override
    public User update(User user) {
        try {
            return entityManager.merge(user);
        } catch (Exception ex) {
            throw new OperationException(("Update exception: " + ex));
        }
    }

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя, которого нужно удалить.
     */
    @Override
    public void deleteById(Long id) {
        Query query = entityManager.createQuery(DELETE_BY_ID_QUERY);
        query.setParameter("id", id);
        query.executeUpdate();
    }
}