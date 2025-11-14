Система управління транспортною логістикою

Мікросервісна архітектура для управління доставками, побудована на Quarkus з використанням REST та gRPC.
📋 Опис проєкту

Система складається з 4 мікросервісів:

    API Gateway (порт 8080) - точка входу для зовнішніх клієнтів
    Shipments Service (порт 8081) - управління відправленнями
    Fleet Service (порт 8082) - управління транспортом і водіями
    Billing Service (порт 8083) - формування рахунків

🏗️ Архітектура

┌─────────────┐
│ API Gateway │ :8080
└──────┬──────┘
│ REST
├──────────────┐
│              │
▼              ▼
┌──────────┐    ┌──────────┐
│Shipments │    │ Billing  │ :8083
│ Service  │    │ Service  │
│   :8081  │    └────┬─────┘
└────┬─────┘         │
│ gRPC          │ REST
│               │
▼               ▼
┌──────────┐    ┌──────────┐
│  Fleet   │    │Shipments │
│ Service  │◄───┤ Service  │
│   :8082  │    └──────────┘
└──────────┘

Типи комунікації:

    REST: API Gateway ↔ всі сервіси, Billing ↔ Shipments
    gRPC: Shipments ↔ Fleet (синхронна перевірка та резервація транспорту)

🚀 Швидкий старт
Вимоги:

    Java 17+
    Maven 3.8+
    Quarkus CLI (опціонально)

Запуск:
bash

# Термінал 1
cd fleet-service
./mvnw quarkus:dev

# Термінал 2
cd shipments-service
./mvnw quarkus:dev

# Термінал 3
cd billing-service
./mvnw quarkus:dev

# Термінал 4
cd api-gateway
./mvnw quarkus:dev

📡 API Endpoints
Через API Gateway (http://localhost:8080)
Shipments:

    GET /api/shipments - всі відправлення
    GET /api/shipments/{id} - конкретне відправлення
    POST /api/shipments - створити відправлення
    PUT /api/shipments/{id}/assign-vehicle - призначити транспорт (gRPC!)

Fleet:

    GET /api/vehicles - всі транспортні засоби
    GET /api/vehicles/{id} - конкретний транспорт
    GET /api/drivers - всі водії

Billing:

    GET /api/invoices - всі рахунки
    GET /api/invoices/{id} - конкретний рахунок
    POST /api/invoices/create-for-shipment/{id} - створити рахунок (REST!)

Агреговані:

    GET /api/delivery-info/{shipmentId} - повна інформація про доставку

🧪 Тестування
Приклад 1: REST комунікація
bash

# Створити рахунок (Billing → REST → Shipments)
curl -X POST http://localhost:8080/api/invoices/create-for-shipment/1

Приклад 2: gRPC комунікація
bash

# Створити відправлення
curl -X POST http://localhost:8080/api/shipments \
-H "Content-Type: application/json" \
-d '{"origin": "Київ", "destination": "Одеса", "weight": 12.5}'

# Призначити транспорт (Shipments → gRPC → Fleet)
curl -X PUT http://localhost:8080/api/shipments/4/assign-vehicle

🔧 Dev UI & Swagger

    API Gateway: http://localhost:8080/q/dev-ui
    Shipments: http://localhost:8081/q/dev-ui
    Fleet: http://localhost:8082/q/dev-ui
    Billing: http://localhost:8083/q/dev-ui

Swagger UI доступний на /q/swagger-ui кожного сервісу.
📊 Доменна модель
Shipment (Відправлення)

    origin, destination
    weight (вага)
    status (PENDING, ASSIGNED, IN_TRANSIT, DELIVERED)
    assignedVehicleId

Vehicle (Транспорт)

    registrationNumber
    type (TRUCK, VAN, CARGO)
    capacity (тонн)
    available

Driver (Водій)

    name
    licenseNumber
    available

Invoice (Рахунок)

    shipmentId
    amount
    currency
    status (PENDING, PAID, CANCELLED)

🛠️ Технології

    Quarkus - фреймворк
    RESTEasy Reactive - REST API
    gRPC - міжсервісна комунікація
    SmallRye OpenAPI - документація API
    Jackson - JSON серіалізація
    In-memory repositories - ConcurrentHashMap

📝 Структура проєкту

logistics-system/
├── api-gateway/
│   └── src/main/java/ua/logistics/gateway/
│       ├── client/          # REST клієнти
│       └── resource/        # Gateway endpoints
├── shipments-service/
│   └── src/main/
│       ├── java/ua/logistics/shipments/
│       │   ├── model/       # Domain models
│       │   ├── repository/  # Data access
│       │   ├── service/     # Business logic
│       │   ├── resource/    # REST endpoints
│       │   ├── client/      # gRPC client
│       │   └── grpc/        # (не використовується)
│       └── proto/           # (не використовується)
├── fleet-service/
│   └── src/main/
│       ├── java/ua/logistics/fleet/
│       │   ├── model/       # Domain models
│       │   ├── repository/  # Data access
│       │   ├── resource/    # REST endpoints
│       │   └── grpc/        # gRPC service implementation
│       └── proto/
│           └── fleet.proto  # gRPC схема
└── billing-service/
└── src/main/java/ua/logistics/billing/
├── model/           # Domain models
├── dto/             # Data transfer objects
├── repository/      # Data access
├── service/         # Business logic
├── resource/        # REST endpoints
└── client/          # REST client

🎯 Виконані завдання

    ✅ Обрана доменна модель (транспортна логістика)
    ✅ Розплановано архітектуру (4 мікросервіси)
    ✅ Створено фейкові репозиторії (in-memory)
    ✅ Налаштовано REST комунікацію
    ✅ Налаштовано gRPC комунікацію
    ✅ Доступне тестування через Dev UI

👨‍💻 Автор

Лабораторна робота №3 - Мікросервісна архітектура з Quarkus
