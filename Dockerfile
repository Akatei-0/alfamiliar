# --- ЭТАП 1: Сборка приложения ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Копируем файлы конфигурации зависимостей для кэширования
COPY pom.xml .
RUN mvn dependency:go-offline

# Копируем исходный код и собираем проект
COPY src ./src
RUN mvn clean package -DskipTests

# --- ЭТАП 2: Запуск приложения ---
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Копируем готовый jar-файл из первого этапа
COPY --from=build /app/target/*.jar app.jar

# Открываем порт (укажите порт вашего приложения, по умолчанию 8080)
EXPOSE 8080

# Команда запуска
ENTRYPOINT ["java", "-jar", "app.jar"]
