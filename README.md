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
6. Реализовать авторизацию, с возможностью иметь несколько ролей для одного пользователя.
7. Все CRUD-операции должны быть доступны только пользователю с ролью admin по url: /admin/. Добавление/изменение юзера
   должно быть с ролями за один запрос.
8. Пользователь с ролью user имеет доступ только по своему url /user и получать данные только о себе. Доступ к этому url
   должен быть только у пользователей с ролью user и admin. Не забывайте про несколько ролей у пользователя!
9. Настройте logout с любого url

## Технологии:

- Mapstruct
- Lombok
- Liquibase
- Spring Boot
- Spring Security
- Spring MVC
- Spring Data

## Настройка и запуск:

- Необходимо использовать PostgreSql в качестве бд. Также необходимо заранее создать таблицу spring-mvc.
- Liquibase поднимет необходимые таблицы и первого пользователя на основе указанных changelog`ов.

## Реализация

### AuthController

#### POST ResponseEntity<JwtDto> signUp(@RequestBody SignUpDto dto):

Request:

```http request
http://localhost:8080/api/auth/signUp
```

Body:

```json
{
  "login": "user4@mail.com",
  "passwordHash": "user4@mail.com",
  "passwordHashConfirm": "user4@mail.com",
  "roleList": [
    {
      "name": "USER"
    }
  ]
}
```

Response:

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyNEBtYWlsLmNvbSIsInVzZXJuYW1lIjoidXNlcjRAbWFpbC5jb20iLCJyb2xlIjpbIlVTRVIiXSwiZXhwIjoxNzI3NTg5NjY5fQ.PsAdxWQEG3RHvQ4k1WcxQ6cdjdZSMHXy3y7MAriiZKU"
}
```

#### POST ResponseEntity<JwtDto> signIn(@RequestBody SignInDto dto):

Request:

```http request
http://localhost:8080/api/auth/signIn
```

Body:

```json
{
  "login": "user4@mail.com",
  "passwordHash": "user4@mail.com"
}
```

Response:

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyNEBtYWlsLmNvbSIsInVzZXJuYW1lIjoidXNlcjRAbWFpbC5jb20iLCJyb2xlIjpbIkFETUlOIl0sImV4cCI6MTcyNzU4OTc1M30.RIK-ouYj9Ro3DFvpfm-Jft4S8Ugs-9DjWoCKgk8tTPM"
}
```

#### POST ResponseEntity<Boolean> check(@RequestHeader(AUTHORIZATION_HEADER) String auth):

Request:

```http request
http://localhost:8080/api/auth/check
```

Header Authorization with bearer token.

Response if token valid:

```json
true
```

Response if token invalid:

```json
false
```

### UserController

Для работы с данным контроллером пользователь должен быть авторизован в системе.

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