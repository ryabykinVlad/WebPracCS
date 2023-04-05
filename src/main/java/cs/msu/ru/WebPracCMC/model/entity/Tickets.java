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
    private Integer ticketId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    @NonNull
    private Clients clientId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "flight_id")
    @ToString.Exclude
    @NonNull
    private Flights flightId;

    @Column(name = "purchase_price", nullable = false, columnDefinition = "numeric")
    @NonNull
    private Double purchasePrice;

    @Column(name = "purchase_time", nullable = false, columnDefinition = "timestamp")
    @NonNull
    private LocalDateTime purchaseTime;
}