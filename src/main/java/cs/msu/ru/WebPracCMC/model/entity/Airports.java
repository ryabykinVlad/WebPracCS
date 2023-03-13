package cs.msu.ru.WebPracCMC.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "airports")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Airports {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airport_id", nullable = false, columnDefinition = "serial")
    private Integer id;

    @Column(name = "iata_code", nullable = false)
    @NonNull
    private String iataCode;

    @Column(name = "airport_name", nullable = false)
    @NonNull
    private String airportName;

    @Column(name = "airport_country", nullable = false)
    @NonNull
    private String airportCountry;

    @Column(name = "airport_city", nullable = false)
    @NonNull
    private String airportCity;
}