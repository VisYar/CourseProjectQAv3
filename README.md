
[![Build status](https://ci.appveyor.com/api/projects/status/9fu3y455tj6e6p0n?svg=true)](https://ci.appveyor.com/project/VisYar/courseprojectqav3-af5bg)

# Курсовой проект по модулю «Автоматизация тестирования» для профессии «Инженер по тестированию»

## Документация
1. [Текст задания](https://github.com/netology-code/aqa-qamid-diplom/blob/main/README.md)
2. [План автоматизации](https://github.com/VisYar/CourseProjectQAv3/blob/master/reports/Plan.md)
3. [Отчёт о проведенном тестировании](https://github.com/VisYar/CourseProjectQAv3/blob/master/reports/Report.md)
4. [Отчет о проведённой автоматизации тестирования](https://github.com/VisYar/CourseProjectQAv3/blob/master/reports/Summary.md)

## **Инструкция для запуска автотестов**
1. Клонировать проект:`https://github.com/VisYar/CourseProjectQAv3`
2. Открыть проект в IntelliJ IDEA
3. Запустить Docker Desktop

### Подключение SUT к MySQL
1. В терминале 1 в корне проекта запустить контейнеры: `docker-compose up -d`
2. В терминале 2 запустить приложение: `java -jar .\artifacts\aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:3366/app`
3. Проверить, что приложение успешно запустилось (ввести URL в браузере Сhrome: `http://localhost/8080`)
4. В терминале 3 запустить тесты: `.\gradlew clean test -DdbUrl=jdbc:mysql://localhost:3366/app`
5. Создать отчёт Allure и открыть в браузере `.\gradlew allureServe`
6. Закрыть отчёт в терминаме 3:   `CTRL + C --> y --> Enter`
7. Остановить приложение в терминале 2: `CTRL + C`
8. Остановить контейнеры в терминале 1:`docker-compose down`

### Подключение SUT к PostgreSQL
1. В терминале 1 в корне проекта запустить контейнеры: `docker-compose up -d`
2. В терминале 2 запустить приложение: `java -jar .\artifacts\aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/app`
3. Проверить, что приложение успешно запустилось (ввести URL в браузере Сhrome: `http://localhost/8080`)
4. В файлах application.properties и build.gradle снять комментарии с `For PostgreSQL`  и закомментировать `For MySQL`
5. В терминале 3 запустить тесты: `.\gradlew clean test -DdbUrl=jdbc:postgresql://localhost:5432/app`
6. Создать отчёт Allure и открыть в браузере `.\gradlew allureServe`
7. Закрыть отчёт в терминале 3:   `CTRL + C --> y --> Enter`
8. Остановить приложение в терминале 2: `CTRL + C`
9. Остановить контейнеры в терминале 1:`docker-compose down`
