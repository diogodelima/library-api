# library

This is a personal project using [Kotlin](https://kotlinlang.org/) with the following frameworks:

- [kotlinx.serialization](https://kotlinlang.org/docs/serialization.html)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data](https://spring.io/projects/spring-data)
- [Spring Security](https://spring.io/projects/spring-security)
- [Jakarta Validation](https://github.com/jakartaee/validation)
- [Jakarta Mail](https://jakartaee.github.io/mail-api/)

The database system used is [MySQL](https://www.mysql.com/)

This project consists of a management system for a library, which can register users, books, make loans, etc.
# Features

- Implemented Spring Security with Bearer Token authentication for secure access to protected endpoints.
- Password reset system with token expiration using [Scheduling Tasks](https://spring.io/guides/gs/scheduling-tasks)