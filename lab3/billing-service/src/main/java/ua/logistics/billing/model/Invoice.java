package ua.logistics.billing.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Invoice extends PanacheEntity {
    // id видаляємо, воно є в PanacheEntity

    // Поля робимо public
    public Long shipmentId;
    public double amount;
    public String currency;
    public String status; // PENDING, PAID, CANCELLED
    public LocalDateTime createdAt;
    public LocalDateTime paidAt;

    public Invoice() {}

    public Invoice(Long shipmentId, double amount, String currency, String status) {
        this.shipmentId = shipmentId;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    // Гетери та сетери можна видалити, бо поля public,
    // або залишити, якщо вони використовуються десь у тестах чи DTO мапінгу.
    // Для Active Record вони не обов'язкові.
}