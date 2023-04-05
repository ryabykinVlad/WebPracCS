package cs.msu.ru.WebPracCMC.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Clients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", nullable = false, columnDefinition = "serial")
    private Integer clientId;

    @Column(name = "full_name", nullable = false)
    @NonNull
    private String fullName;

    @Column(name = "phone", nullable = false)
    @NonNull
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "preferred_city")
    private String preferredCity;
}