package cs.msu.ru.WebPracCMC.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Flights {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id", nullable = false, columnDefinition = "serial")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "airline_id", nullable = false)
    @ToString.Exclude
    private Airlines airlineId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "departure_airport_id", nullable = false)
    @ToString.Exclude
    private Airports departureAirportId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "arrival_airport_id", nullable = false)
    @ToString.Exclude
    private Airports arrivalAirportId ;

    @Column(name = "departure_time", nullable = false, columnDefinition = "timestamp")
    @NonNull
    private LocalDateTime departureTime;

    @Column(name = "arrival_time", nullable = false, columnDefinition = "timestamp")
    @NonNull
    private LocalDateTime arrivalTime;

    @Column(name = "current_price", nullable = false, columnDefinition = "numeric")
    @NonNull
    private Double currentPrice;

    @Column(name = "total_seats", nullable = false)
    @NonNull
    private Integer totalSeats;

    @Column(name = "available_seats", nullable = false)
    @NonNull
    private Integer availableSeats;
}