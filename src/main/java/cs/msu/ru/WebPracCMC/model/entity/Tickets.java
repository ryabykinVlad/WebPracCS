package cs.msu.ru.WebPracCMC.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Tickets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id", nullable = false, columnDefinition = "serial")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    @ToString.Exclude
    private Clients clientId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "flight_id", nullable = false)
    @ToString.Exclude
    private Flights flightId;

    @Column(name = "purchase_price", nullable = false, columnDefinition = "numeric")
    @NonNull
    private Double purchasePrice;

    @Column(name = "purchase_time", nullable = false, columnDefinition = "timestamp")
    @NonNull
    private LocalDateTime purchaseTime;
}