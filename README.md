# === 1. Створити машину (Fleet) ===
curl -X POST http://localhost:8082/api/vehicles \
-H "Content-Type: application/json" \
-d '{
    "registrationNumber": "BC9999TX",
    "type": "TRUCK",
    "capacity": 22.0,
    "available": true
}'

# === 2. Створити відправлення (Shipment) ===
curl -X POST http://localhost:8081/api/shipments \
-H "Content-Type: application/json" \
-d '{
    "origin": "Dnipro",
    "destination": "Uzhhorod",
    "status": "NEW"
}'

# === 3. Створити інвойс (Billing) ===
# (створюється для shipment з ID = 2)
curl -X POST http://localhost:8083/api/invoices/create-for-shipment/2 \
-H "Content-Type: application/json" \
-d '{
    "amount": 2500.00,
    "currency": "UAH"
}'
