# WEB приложение "Управление счетом"

---

Для запуска необходимо установить Docker, запустить единственный контейнер и саму программу.

Для основного доступа используйте http://localhost:8080/index.html

Пользователи: user - password, user2 - password2  
Admin: admin - password

---

Для запуска необходимо установить Maven (у меня 3.9.8), JDK и Docker.  

После настройки вышеупомянутого и запуска Docker необходимо выполнить ряд команд:

- cd путь_к_папке_проекта (переходим в папку проекта)
- docker-compose up
- mvn install
- java  -jar target/demo-account-management-app-0.0.1-SNAPSHOT.jar
