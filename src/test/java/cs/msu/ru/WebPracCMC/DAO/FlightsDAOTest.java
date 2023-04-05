package cs.msu.ru.WebPracCMC.DAO;

import cs.msu.ru.WebPracCMC.model.dao.AirlinesDAO;
import cs.msu.ru.WebPracCMC.model.dao.AirportsDAO;
import cs.msu.ru.WebPracCMC.model.dao.FlightsDAO;
import cs.msu.ru.WebPracCMC.model.entity.Airlines;
import cs.msu.ru.WebPracCMC.model.entity.Airports;
import cs.msu.ru.WebPracCMC.model.entity.Flights;
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
public class FlightsDAOTest {
    @Autowired
    private FlightsDAO flightsDAO;
    @Autowired
    private AirportsDAO airportsDAO;
    @Autowired
    private AirlinesDAO airlinesDAO;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    public void prepare() {
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
    }

    @BeforeAll
    @AfterEach
    @AfterAll
    public void clean() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery("TRUNCATE flights RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE flights_flight_id_seq RESTART WITH  1;").executeUpdate();
            entityManager.createNativeQuery("TRUNCATE airports RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE airports_airport_id_seq RESTART WITH  1;").executeUpdate();
            entityManager.createNativeQuery("TRUNCATE airlines RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE airlines_airline_id_seq RESTART WITH  1;").executeUpdate();
            entityManager.getTransaction().commit();
        }
    }

    @Test
    public void testFlightsByFilter() {
        Collection<Flights> resultCollection = this.flightsDAO.getFlightsByFilter(
                FlightsDAO.getFilterBuilder().departureAirport("Внуко").build()
        );
        Assertions.assertNotNull(resultCollection);
        Assertions.assertEquals(resultCollection.size(), 2);
        Set<Flights> result = new HashSet<>();
        result.addAll(resultCollection);
        Set<Flights> expected = new HashSet<>();
        Airlines expectedAirline = new Airlines(1, "S7 Airlines", "Россия", "84957830707", null, "www.s7.ru");
        Airports expectedDepartureAirport = new Airports(1, "VKO", "Внуково", "Россия", "Москва");
        Airports expectedArrivalAirport = new Airports(2, "LED", "Пулково", "Россия", "Санкт-Петербург");
        LocalDateTime expectedDepartureTime = LocalDateTime.of(2023, 6, 20, 10, 15);
        LocalDateTime expectedArrivalTime = LocalDateTime.of(2023, 6, 20, 12, 10);
        Flights expectedFlight = new Flights(1, expectedAirline, expectedDepartureAirport, expectedArrivalAirport, expectedDepartureTime, expectedArrivalTime, 2500., 200, 150);
        expected.add(expectedFlight);
        expectedAirline = new Airlines(2, "Turkish Airlines", "Турция", null, null, "www.turkishairlines.com");
        expectedDepartureAirport = new Airports(1, "VKO", "Внуково", "Россия", "Москва");
        expectedArrivalAirport = new Airports(3, "IST", "Стамбул", "Россия", "Стамбул");
        expectedDepartureTime = LocalDateTime.of(2023, 1, 2, 3, 45);
        expectedArrivalTime = LocalDateTime.of(2023, 2, 2, 9, 0);
        expectedFlight = new Flights(2, expectedAirline, expectedDepartureAirport, expectedArrivalAirport, expectedDepartureTime, expectedArrivalTime, 34500.50, 250, 0);
        expected.add(expectedFlight);
        Assertions.assertEquals(result, expected);

        resultCollection = this.flightsDAO.getFlightsByFilter(
                FlightsDAO.getFilterBuilder().arrivalAirport("ist").build()
        );
        Assertions.assertNotNull(resultCollection);
        Assertions.assertEquals(resultCollection.size(), 1);
        result = new HashSet<>();
        result.addAll(resultCollection);
        expected = new HashSet<>();
        expectedAirline = new Airlines(2, "Turkish Airlines", "Турция", null, null, "www.turkishairlines.com");
        expectedDepartureAirport = new Airports(1, "VKO", "Внуково", "Россия", "Москва");
        expectedArrivalAirport = new Airports(3, "IST", "Стамбул", "Россия", "Стамбул");
        expectedDepartureTime = LocalDateTime.of(2023, 1, 2, 3, 45);
        expectedArrivalTime = LocalDateTime.of(2023, 2, 2, 9, 0);
        expectedFlight = new Flights(2, expectedAirline, expectedDepartureAirport, expectedArrivalAirport, expectedDepartureTime, expectedArrivalTime, 34500.50, 250, 0);
        expected.add(expectedFlight);
        Assertions.assertEquals(result, expected);

        resultCollection = this.flightsDAO.getFlightsByFilter(
                FlightsDAO.getFilterBuilder().departureAirport("vko").arrivalAirport("ist").build()
        );
        Assertions.assertNotNull(resultCollection);
        Assertions.assertEquals(resultCollection.size(), 1);
        result = new HashSet<>();
        result.addAll(resultCollection);
        expected = new HashSet<>();
        expectedAirline = new Airlines(2, "Turkish Airlines", "Турция", null, null, "www.turkishairlines.com");
        expectedDepartureAirport = new Airports(1, "VKO", "Внуково", "Россия", "Москва");
        expectedArrivalAirport = new Airports(3, "IST", "Стамбул", "Россия", "Стамбул");
        expectedDepartureTime = LocalDateTime.of(2023, 1, 2, 3, 45);
        expectedArrivalTime = LocalDateTime.of(2023, 2, 2, 9, 0);
        expectedFlight = new Flights(2, expectedAirline, expectedDepartureAirport, expectedArrivalAirport, expectedDepartureTime, expectedArrivalTime, 34500.50, 250, 0);
        expected.add(expectedFlight);
        Assertions.assertEquals(result, expected);

        LocalDate departureDate = LocalDate.of(2023, 5, 31);
        resultCollection = this.flightsDAO.getFlightsByFilter(
                FlightsDAO.getFilterBuilder().departureDate(departureDate).build()
        );
        Assertions.assertNotNull(resultCollection);
        Assertions.assertEquals(resultCollection.size(), 2);
        result = new HashSet<>();
        result.addAll(resultCollection);
        expected = new HashSet<>();
        expectedAirline = new Airlines(1, "S7 Airlines", "Россия", "84957830707", null, "www.s7.ru");
        expectedDepartureAirport = new Airports(2, "LED", "Пулково", "Россия", "Санкт-Петербург");
        expectedArrivalAirport = new Airports(1, "VKO", "Внуково", "Россия", "Москва");
        expectedDepartureTime = LocalDateTime.of(2023, 5, 31, 23, 5);
        expectedArrivalTime = LocalDateTime.of(2023, 6, 1, 1, 55);
        expectedFlight = new Flights(5, expectedAirline, expectedDepartureAirport, expectedArrivalAirport, expectedDepartureTime, expectedArrivalTime, 2700.12, 180, 100);
        expected.add(expectedFlight);
        expectedAirline = new Airlines(2, "Turkish Airlines", "Турция", null, null, "www.turkishairlines.com");
        expectedDepartureAirport = new Airports(3, "IST", "Стамбул", "Россия", "Стамбул");
        expectedArrivalAirport = new Airports(1, "VKO", "Внуково", "Россия", "Москва");
        expectedDepartureTime = LocalDateTime.of(2023, 5, 31, 15, 35);
        expectedArrivalTime = LocalDateTime.of(2023, 5, 31, 20, 10);
        expectedFlight = new Flights(6, expectedAirline, expectedDepartureAirport, expectedArrivalAirport, expectedDepartureTime, expectedArrivalTime, 35000., 275, 105);
        expected.add(expectedFlight);
        Assertions.assertEquals(result, expected);
    }
}
