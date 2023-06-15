# Описание проекта
Данный проект представляет собой сервис задач "TODO список"
# Стек технологий
- Java 17
- SpringBoot 2.7.6
  - Junit
  - AssertJ
  - Thymeleaf
  - Mockito
- Log4j 1.2.17
- Slf4j 1.7.30
- Hibernate 5.6.11.Final
- H2database 2.1.214
- Bootstrap 5.2.3
- PostgreSQL 14
- Checkstyle-plugin 3.1.2
- Liquibase 4.15.0
# Требования к окружению
- Java 17
- Maven 3.8
- PostgreSQL 14
# Запуск проекта
1. В PostgreSQL создать базу данных todo ```jdbc:postgresql://127.0.0.1:5432/todo```
2. Собрать jar файл с помощью ```mvn install```
3. Запустить приложение с помощью собранного jar-файла ```java -jar target/job4j_todo-1.0.jar```
4. Перейти по адресу ```http://localhost:8080/index```
# Взаимодействие с приложением

### Главная страница / список всех задач
![](https://github.com/apereslavtsev/job4j_todo/blob/master/img/main.PNG)

### Страница создания задачи
![](https://github.com/apereslavtsev/job4j_todo/blob/master/img/create.PNG)

### Страница просмотра информации о задаче
![](https://github.com/apereslavtsev/job4j_todo/blob/master/img/one.PNG)

### Страница редактирования задачи
![](https://github.com/apereslavtsev/job4j_todo/blob/master/img/update.PNG)