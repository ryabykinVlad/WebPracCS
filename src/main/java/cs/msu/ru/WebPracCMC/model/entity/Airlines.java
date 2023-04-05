package cs.msu.ru.WebPracCMC.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "airlines")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Airlines {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airline_id", nullable = false, columnDefinition = "serial")
    private Integer airlineId;

    @Column(name = "airline_name", nullable = false)
    @NonNull
    private String airlineName;

    @Column(name = "registration_country", nullable = false)
    @NonNull
    private String registrationCountry;

    @Column(name = "airline_phone")
    private String airlinePhone;

    @Column(name = "airline_email")
    private String airlineEmail;

    @Column(name = "airline_website")
    private String airlineWebsite;
}