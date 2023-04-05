package cs.msu.ru.WebPracCMC.DAO;

import cs.msu.ru.WebPracCMC.model.dao.*;
import cs.msu.ru.WebPracCMC.model.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application.properties")
public class TicketsDAOTest {
    @Autowired
    private AirlinesDAO airlinesDAO;
    @Autowired
    private AirportsDAO airportsDAO;
    @Autowired
    private ClientsDAO clientsDAO;
    @Autowired
    private FlightsDAO flightsDAO;
    @Autowired
    private TicketsDAO ticketsDAO;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    public void prepare() {
        List<Clients> clientsList = new ArrayList<>();
        clientsList.add(new Clients(1, "Петров Иван", "Тест телефон 1", "Тест email 1", "Москва"));
        clientsList.add(new Clients(2, "Иванов Пётр", "Тест телефон 2", "Тест email 2", null));
        clientsList.add(new Clients(3, "Петров Пётр", "Тест телефон 3", null, "Москва"));
        clientsList.add(new Clients(4, "Тест имя 4", "Тест телефон 4", null, null));
        clientsList.add(new Clients(5, "Отдельный клиент", "Отдельный", "Отдельный", "Отдельный"));
        clientsDAO.saveCollection(clientsList);

        List<Airlines> airlinesList = new ArrayList<>();
        airlinesList.add(new Airlines(1, "S7 Airlines", "Россия", "84957830707", null, "www.s7.ru"));
        airlinesList.add(new Airlines(2, "Turkish Airlines", "Турция", null, null, "www.turkishairlines.com"));
        airlinesDAO.saveCollection(airlinesList);

        List<Airports> airportsList = new ArrayList<>();
        airportsList.add(new Airports(1, "VKO", "Внуково", "Россия", "Москва"));
        airportsList.add(new Airports(2, "LED", "Пулково", "Россия", "Санкт-Петербург"));
        airportsList.add(new Airports(3, "IST", "Стамбул", "Россия", "Стамбул"));
        airportsDAO.saveCollection(airportsList);

        List<Flights> flightsList = new ArrayList<>();
        LocalDateTime departureTime = LocalDateTime.of(2023, 6, 20, 10, 15);
        LocalDateTime arrivalTime = LocalDateTime.of(2023, 6, 20, 12, 10);
        flightsList.add(new Flights(1, airlinesDAO.getById(1), airportsDAO.getById(1), airportsDAO.getById(2), departureTime, arrivalTime, 2500., 200, 150));
        departureTime = LocalDateTime.of(2023, 1, 2, 3, 45);
        arrivalTime = LocalDateTime.of(2023, 2, 2, 9, 0);
        flightsList.add(new Flights(2, airlinesDAO.getById(2), airportsDAO.getById(1), airportsDAO.getById(3), departureTime, arrivalTime, 34500.50, 250, 0));
        departureTime = LocalDateTime.of(2023, 4, 14, 12, 0);
        arrivalTime = LocalDateTime.of(2023, 4, 14, 14, 30);
        flightsList.add(new Flights(3, airlinesDAO.getById(1), airportsDAO.getById(2), airportsDAO.getById(1), departureTime, arrivalTime, 3500., 220, 10));
        departureTime = LocalDateTime.of(2023, 3, 4, 23, 55);
        arrivalTime = LocalDateTime.of(2023, 3, 5, 5, 25);
        flightsList.add(new Flights(4, airlinesDAO.getById(2), airportsDAO.getById(3), airportsDAO.getById(2), departureTime, arrivalTime, 40000., 250, 15));
        departureTime = LocalDateTime.of(2023, 5, 31, 23, 5);
        arrivalTime = LocalDateTime.of(2023, 6, 1, 1, 55);
        flightsList.add(new Flights(5, airlinesDAO.getById(1), airportsDAO.getById(2), airportsDAO.getById(1), departureTime, arrivalTime, 2700.12, 180, 100));
        departureTime = LocalDateTime.of(2023, 5, 31, 15, 35);
        arrivalTime = LocalDateTime.of(2023, 5, 31, 20, 10);
        flightsList.add(new Flights(6, airlinesDAO.getById(2), airportsDAO.getById(3), airportsDAO.getById(1), departureTime, arrivalTime, 35000., 275, 105));
        flightsDAO.saveCollection(flightsList);

        List<Tickets> ticketsList = new ArrayList<>();
        LocalDateTime purchaseTime = LocalDateTime.of(2023, 4, 3, 2, 10);
        ticketsList.add(new Tickets(1, clientsDAO.getById(1), flightsDAO.getById(2), 33000., purchaseTime));
        purchaseTime = LocalDateTime.of(2023, 4, 5, 9, 15);
        ticketsList.add(new Tickets(2, clientsDAO.getById(1), flightsDAO.getById(5), 2700.12, purchaseTime));
        purchaseTime = LocalDateTime.of(2022, 12, 1, 15, 15);
        ticketsList.add(new Tickets(3, clientsDAO.getById(2), flightsDAO.getById(5), 3000., purchaseTime));
        purchaseTime = LocalDateTime.of(2023, 1, 2, 3, 45);
        ticketsList.add(new Tickets(4, clientsDAO.getById(3), flightsDAO.getById(5), 2500., purchaseTime));
        ticketsDAO.saveCollection(ticketsList);
    }

    @BeforeAll
    @AfterEach
    @AfterAll
    public void clean() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery("TRUNCATE tickets RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE tickets_ticket_id_seq RESTART WITH  1;").executeUpdate();
            entityManager.createNativeQuery("TRUNCATE flights RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE flights_flight_id_seq RESTART WITH  1;").executeUpdate();
            entityManager.createNativeQuery("TRUNCATE airports RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE airports_airport_id_seq RESTART WITH  1;").executeUpdate();
            entityManager.createNativeQuery("TRUNCATE airlines RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE airlines_airline_id_seq RESTART WITH  1;").executeUpdate();
            entityManager.createNativeQuery("TRUNCATE clients RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE clients_client_id_seq RESTART WITH  1;").executeUpdate();
            entityManager.getTransaction().commit();
        }
    }

    @Test
    public void testTicketsByFilter() {
        Collection<Tickets> resultCollection = this.ticketsDAO.getTicketsByFilter(
                TicketsDAO.getFilterBuilder().clientId(1).build()
        );
        Assertions.assertNotNull(resultCollection);
        Assertions.assertEquals(resultCollection.size(), 2);
        Set<Tickets> result = new HashSet<>();
        result.addAll(resultCollection);
        Set<Tickets> expected = new HashSet<>();
        LocalDateTime purchaseTime = LocalDateTime.of(2023, 4, 3, 2, 10);
        expected.add(new Tickets(1, clientsDAO.getById(1), flightsDAO.getById(2), 33000., purchaseTime));
        purchaseTime = LocalDateTime.of(2023, 4, 5, 9, 15);
        expected.add(new Tickets(2, clientsDAO.getById(1), flightsDAO.getById(5), 2700.12, purchaseTime));
        Assertions.assertEquals(result, expected);

        Collection<Tickets> resultEmptyCollection = this.ticketsDAO.getTicketsByFilter(
                TicketsDAO.getFilterBuilder().clientId(12345).build()
        );
        Assertions.assertNotNull(resultEmptyCollection);
        Assertions.assertEquals(resultEmptyCollection.size(), 0);

        resultCollection = this.ticketsDAO.getTicketsByFilter(
                TicketsDAO.getFilterBuilder().flightId(5).build()
        );
        Assertions.assertNotNull(resultCollection);
        Assertions.assertEquals(resultCollection.size(), 3);
        result = new HashSet<>();
        result.addAll(resultCollection);
        expected = new HashSet<>();
        purchaseTime = LocalDateTime.of(2023, 4, 5, 9, 15);
        expected.add(new Tickets(2, clientsDAO.getById(1), flightsDAO.getById(5), 2700.12, purchaseTime));
        purchaseTime = LocalDateTime.of(2022, 12, 1, 15, 15);
        expected.add(new Tickets(3, clientsDAO.getById(2), flightsDAO.getById(5), 3000., purchaseTime));
        purchaseTime = LocalDateTime.of(2023, 1, 2, 3, 45);
        expected.add(new Tickets(4, clientsDAO.getById(3), flightsDAO.getById(5), 2500., purchaseTime));
        Assertions.assertEquals(result, expected);

        LocalDate departureDate = LocalDate.of(2023, 1, 2);
        resultCollection = this.ticketsDAO.getTicketsByFilter(
                TicketsDAO.getFilterBuilder().departureDate(departureDate).build()
        );
        Assertions.assertNotNull(resultCollection);
        Assertions.assertEquals(resultCollection.size(), 1);
        result = new HashSet<>();
        result.addAll(resultCollection);
        expected = new HashSet<>();
        purchaseTime = LocalDateTime.of(2023, 4, 3, 2, 10);
        expected.add(new Tickets(1, clientsDAO.getById(1), flightsDAO.getById(2), 33000., purchaseTime));
        Assertions.assertEquals(result, expected);

        departureDate = LocalDate.of(2023, 5, 31);
        resultCollection = this.ticketsDAO.getTicketsByFilter(
                TicketsDAO.getFilterBuilder().clientId(3).departureDate(departureDate).build()
        );
        Assertions.assertNotNull(resultCollection);
        Assertions.assertEquals(resultCollection.size(), 1);
        result = new HashSet<>();
        result.addAll(resultCollection);
        expected = new HashSet<>();
        purchaseTime = LocalDateTime.of(2023, 1, 2, 3, 45);
        expected.add(new Tickets(4, clientsDAO.getById(3), flightsDAO.getById(5), 2500., purchaseTime));
        Assertions.assertEquals(result, expected);
    }
}
