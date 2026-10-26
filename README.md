# Alfamiliar Assistant

Приложение с веб-интерфейсом и backend на Java / Spring Boot.

## Что входит в релиз

| Файл                              | Описание                               |
| --------------------------------- | -------------------------------------- |
| `alfamiliar-assistant-v1.0.0.jar` | Backend на Java / Spring Boot          |
| `index.html`                      | Основной веб-интерфейс                 |
| `demo.html`                       | Автономная демоверсия                  |
| `alfamiliar-docker.zip`           | Архив с готовым Docker-образом backend |

## 1. Автономная демоверсия

1. Скачайте `demo.html` из раздела Releases.
2. Откройте файл двойным щелчком мыши.
3. Демоверсия запустится в браузере.

Установка Java, Docker и других зависимостей не требуется.

## 2. Запуск backend через Java

Установите Java совместимой версии и проверьте её наличие:

```bash
java -version
```

### Windows

Откройте PowerShell в папке с JAR-файлом и выполните:

```powershell
java -jar .\alfamiliar-assistant-v1.0.0.jar
```

### Linux

Откройте терминал в папке с JAR-файлом и выполните:

```bash
java -jar ./alfamiliar-assistant-v1.0.0.jar
```

Дождитесь запуска приложения. Чтобы остановить backend, нажмите `Ctrl+C`.

## 3. Запуск веб-интерфейса

1. Запустите backend одним из способов, описанных выше.
2. Откройте `index.html` в браузере.

Если интерфейс не подключается к backend, проверьте адрес API в `index.html` и порт запущенного сервера.

## 4. Запуск backend через Docker

В архиве `alfamiliar-docker.zip` находится готовый образ:

`docker-files/alfamiliar-docker.tar`

Собирать образ самостоятельно не нужно.

### Windows

Установите и запустите Docker Desktop.

Распакуйте `alfamiliar-docker.zip`, затем откройте PowerShell в папке `docker-files`.

Загрузите образ:

```powershell
docker load -i .\alfamiliar-docker.tar
```

Посмотрите имя и тег загруженного образа:

```powershell
docker images
```

Запустите контейнер, подставив имя и тег из предыдущей команды:

```powershell
docker run -d --name alfamiliar-assistant -p 8080:8080 ИМЯ_ОБРАЗА:ТЕГ
```

### Linux

Установите Docker и распакуйте `alfamiliar-docker.zip`.

Перейдите в папку с образом:

```bash
cd docker-files
```

Загрузите образ:

```bash
docker load -i alfamiliar-docker.tar
```

Посмотрите имя и тег:

```bash
docker images
```

Запустите контейнер:

```bash
docker run -d --name alfamiliar-assistant -p 8080:8080 ИМЯ_ОБРАЗА:ТЕГ
```

Замените `ИМЯ_ОБРАЗА:ТЕГ` на фактические значения из `docker images`.

Если backend использует порт `8080`, он будет доступен по адресу:

`http://localhost:8080`

### Управление Docker-контейнером

Остановить контейнер:

```bash
docker stop alfamiliar-assistant
```

Запустить снова:

```bash
docker start alfamiliar-assistant
```

Посмотреть логи:

```bash
docker logs alfamiliar-assistant
```

## Решение проблем

**Java не найдена.** Установите Java и проверьте команду `java -version`.

**Порт занят.** Запустите JAR на другом порту, например:

```bash
java -jar alfamiliar-assistant-v1.0.0.jar --server.port=8081
```

**Docker-контейнер не запускается.** Проверьте список контейнеров и логи:

```bash
docker ps -a
docker logs alfamiliar-assistant
```

**Интерфейс не подключается к backend.** Проверьте адрес API и порт в настройках frontend.
