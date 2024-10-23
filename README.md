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

- Необходимо использовать PostgreSql в качестве бд. Также необходимо заранее создать бд spring-mvc.
- Liquibase поднимет необходимые таблицы и роли на основе указанных changelog`ов.

## Описание

При регистрации (/singUp) пользователь создается и добавляется в бд, при этом возвращается токен, который необходимо
передавать во всех запросах (кроме запросов контроллера /auth).

В случае /logout, токен пользователя добавляется в черный список, что значит что пользователь разлогинился и повторно
такой токен использовать нельзя. Для входа в систему необходимо снова залогиниться.

Все токены, у которых истек срок действия, удаляются из черного списка шедулером, так как нет возможности исправить
время жизни токена самостоятельно.

### Роли:

- USER (доступ только к /auth/** и /users/me)
- ADMIN (доступ к /auth/** и /users/**)

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
  "username": "user@mail.com",
  "password": "user@mail.com",
  "passwordConfirm": "user@mail.com",
  "roleList": [
    {
      "name": "USER"
    }
  ]
}
```

```json
{
  "username": "admin@mail.com",
  "password": "admin@mail.com",
  "passwordConfirm": "admin@mail.com",
  "roleList": [
    {
      "name": "ADMIN"
    }
  ]
}
```

```json
{
  "username": "user_admin@mail.com",
  "password": "user_admin@mail.com",
  "passwordConfirm": "user_admin@mail.com",
  "roleList": [
    {
      "name": "ADMIN"
    },
    {
      "name": "USER"
    }
  ]
}
```

Response:

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyX2FkbWluQG1haWwuY29tIiwidXNlcm5hbWUiOiJ1c2VyX2FkbWluQG1haWwuY29tIiwicm9sZXMiOlsiQURNSU4iLCJVU0VSIl0sImV4cCI6MTcyOTcxMzQ2N30.RE6Mt7amIOJGl0p-zPbnhQRqE3aL3N6V6YTq38O_jF8"
}
```

Payload token:

```json
{
  "sub": "user_admin@mail.com",
  "username": "user_admin@mail.com",
  "roles": [
    "ADMIN",
    "USER"
  ],
  "exp": 1729713467
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
  "username": "user_admin@mail.com",
  "password": "user_admin@mail.com"
}
```

Response:

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyX2FkbWluQG1haWwuY29tIiwidXNlcm5hbWUiOiJ1c2VyX2FkbWluQG1haWwuY29tIiwicm9sZXMiOlsiVVNFUiIsIkFETUlOIl0sImV4cCI6MTcyOTcxMzYxNX0.fyDX9AVTuOSsBZZRapQxKWW2TsYjRkpJQnRQcRNsoEc"
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
  "id": "1",
  "login": "user1@mail.com",
  "roleList": [
    {
      "name": "ADMIN"
    },
    {
      "name": "USER"
    }
  ]
}
```

User not found:

```json
{
  "errorMessage": "Entity not found",
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
    "id": "2",
    "login": "user@mail.com",
    "roleList": [
      {
        "name": "USER"
      }
    ]
  },
  {
    "id": "3",
    "login": "admin@mail.com",
    "roleList": [
      {
        "name": "ADMIN"
      }
    ]
  },
  {
    "id": "4",
    "login": "user_admin@mail.com",
    "roleList": [
      {
        "name": "USER"
      },
      {
        "name": "ADMIN"
      }
    ]
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
  "username": "user_admin_2@mail.com",
  "password": "user_admin_2@mail.com",
  "passwordConfirm": "user_admin_2@mail.com",
  "roleList": [
    {
      "name": "ADMIN"
    },
    {
      "name": "USER"
    }
  ]
}
```

Response:

```json
{
  "id": "10",
  "login": "user_admin_2@mail.com",
  "roleList": [
    {
      "name": "ADMIN"
    },
    {
      "name": "USER"
    }
  ]
}
```

User is exists:

```json
{
  "errorMessage": "Username is exists",
  "errorCode": 500
}
```

#### PUT ResponseEntity<UserDto> update(@RequestBody UserUpdateDto personUpdateDto):

Request:

```http request
http://localhost:8080/api/users/10
```

Body:

```json
{
  "id": 10,
  "username": "user_admin_2@mail.com"
}
```

Response:

```json
{
  "id": "10",
  "login": "user_admin_2@mail.com",
  "roleList": [
    {
      "name": "ADMIN"
    },
    {
      "name": "USER"
    }
  ]
}
```

#### DELETE ResponseEntity<Void> deleteById(@PathVariable("id") Long id):

Request:

```http request
http://localhost:8080/api/users/10
```