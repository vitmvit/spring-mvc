# spring-mvc

Перейдем к созданию рабочего web-приложения. Все ключевые моменты были рассмотрены в предыдущих задачах. Теперь вам
требуется их сопоставить и связать в один проект.
Используя наработки по mvc и hibernate соберите CRUD-приложение.

## Задание:

1. Написать CRUD-приложение. Использовать Spring MVC + Hibernate.
2. Должен быть класс User с произвольными полями (id, name и т.п.).
3. В приложении должна быть страница, на которую выводятся все юзеры с возможностью добавлять, удалять и изменять юзера.
4. Конфигурация Spring через JavaConfig и аннотации, по аналогии с предыдущими проектами. Без использования xml. Без
   Spring Boot.
5. Внесите изменения в конфигурацию для работы с базой данных. Вместо SessionFactory должен использоваться
   EntityManager.

## Технологии:

- Mapstruct
- Lombok
- Spring Core
- Spring MVC
- Hibernate
- Liquibase
- Tomcat 10.1.18

## Настройка и запуск:

- Необходимо использовать PostgreSql в качестве бд. Также необходимо заранее создать таблицу spring-mvc.
- Liquibase поднимет необходимые таблицы и первого пользователя на основе указанных changelog`ов.

## Реализация

### UserController

#### GET ResponseEntity<UserDto> findById(@PathVariable("id") Long id):

Request:

```http request
http://localhost:8080/api/users/1
```

Response:

```json
{
  "id": 1,
  "name": "Name_one",
  "surname": "Lastname_one",
  "passport": {
    "passportSeries": "FG",
    "passportNumber": "7654322"
  }
}
```

User not found:

```json
{
  "errorMessage": "User with id 12 not found: jakarta.persistence.NoResultException: No result found for query [SELECT p FROM user p WHERE p.id = :id]",
  "errorCode": 404
}
```

#### GET ResponseEntity<List<UserDto>> findAll():

Request:

```http request
http://localhost:8080/api/users
```

Response:

```json
[
  {
    "id": 1,
    "name": "Name_one",
    "surname": "Lastname_one",
    "passport": {
      "passportSeries": "FG",
      "passportNumber": "7654322"
    }
  },
  {
    "id": 2,
    "name": "Name_two",
    "surname": "Lastname_two",
    "passport": {
      "passportSeries": "FL",
      "passportNumber": "7655392"
    }
  },
  {
    "id": 4,
    "name": "Name_two",
    "surname": "Lastname_two",
    "passport": {
      "passportSeries": "FK",
      "passportNumber": "7685392"
    }
  }
]
```

#### ResponseEntity<UserDto> create(@RequestBody UserCreateDto personCreateDto):

Request:

```http request
http://localhost:8080/api/users
```

Body:

```json
{
  "name": "Name_two",
  "surname": "Lastname_two",
  "passport": {
    "passportSeries": "FK",
    "passportNumber": "7685392"
  }
}
```

Response:

```json
{
  "id": 4,
  "name": "Name_two",
  "surname": "Lastname_two",
  "passport": {
    "passportSeries": "FK",
    "passportNumber": "7685392"
  }
}
```

Not unique passport:

```json
{
  "errorMessage": "Create exception: org.hibernate.exception.ConstraintViolationException: could not execute statement [ERROR: duplicate key value violates unique constraint \"person_passport_series_passport_number_key\"\n  Detail: Key (passport_series, passport_number)=(FK, 7685392) already exists.] [/* insert for by.vitikova.spring.mvc.model.entity.User */insert into users (name,passport_number,passport_series,surname) values (?,?,?,?) returning id]",
  "errorCode": 500
}
```

#### PUT ResponseEntity<UserDto> update(@RequestBody UserUpdateDto personUpdateDto):

Request:

```http request
http://localhost:8080/api/users
```

Body:

```json
{
  "id": 4,
  "name": "Name_three",
  "surname": "Lastname_three",
  "passport": {
    "passportSeries": "FM",
    "passportNumber": "7655395"
  }
}
```

Response:

```json
{
  "id": 4,
  "name": "Name_three",
  "surname": "Lastname_three",
  "passport": {
    "passportSeries": "FM",
    "passportNumber": "7655395"
  }
}
```

#### DELETE delete(UUID uuid):

Request:

```http request
http://localhost:8080/api/users/5
```

__