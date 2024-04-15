
# KafkaMetrics

## Описание
KafkaMetrics — это система мониторинга, предназначенная для отслеживания работы различных компонентов приложения с использованием Spring Kafka. Система состоит из двух основных компонентов: Producer, который собирает метрики и отправляет их в Kafka, и Consumer, который обрабатывает и анализирует метрики, а также предоставляет REST API для их просмотра.

## Архитектура
Система включает в себя:
- **Metrics Producer**: микросервис, собирающий метрики работы приложения и отправляющий их в Kafka топик `metrics-topic`.
- **Metrics Consumer**: микросервис, принимающий метрики из Kafka топика и анализирующий их. Также предоставляет REST API для доступа к метрикам.

## Функциональность

### Producer Service
Отправка метрик работы приложения в формате JSON на топик `metrics-topic`.
### Отправка метрики
Для отправки метрики в Kafka, сформируйте JSON-сообщение со следующими полями:

```json
{
  "serviceId": "auth-service",
  "cpuUsage": 70.0,
  "memoryUsage": 512.0,
  "errorRate": 0.02
}
```
#### Endpoints
- `POST /metrics`: отправка метрик в Kafka.

### Consumer Service
Приём и обработка метрик, отправленных Producer'ом.

#### REST API
- `GET /metrics`: получение списка всех метрик.
- `GET /metrics/{id}`: получение конкретной метрики по идентификатору.

## Технологии
- Spring Boot
- Spring Kafka
- H2 Database
- Maven

## Запуск проекта
1. Запустите вашу Kafka инстанцию.
2. Клонируйте репозиторий и перейдите в его каталог.
   ```sh
   git clone https://github.com/TaraTheLiberator/KafkaMetrics.git
   cd KafkaMetrics
3. Соберите и запустите приложение с помощью Maven.

```bash
mvn spring-boot:run
