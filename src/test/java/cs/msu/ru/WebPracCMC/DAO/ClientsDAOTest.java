package cs.msu.ru.WebPracCMC.DAO;

import cs.msu.ru.WebPracCMC.model.dao.ClientsDAO;
import cs.msu.ru.WebPracCMC.model.entity.Clients;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application.properties")
public class ClientsDAOTest {
    @Autowired
    private ClientsDAO clientsDAO;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    public void prepare() {
        List<Clients> clientsList = new ArrayList<>();
        clientsList.add(new Clients(1, "Петров Иван", "Тест телефон 1", "Тест email 1", "Москва"));
        clientsList.add(new Clients(2, "Иванов Пётр", "Тест телефон 2", "Тест email 2", null));
        clientsList.add(new Clients(3, "Петров Пётр", "Тест телефон 3", null, "Москва"));
        clientsList.add(new Clients(4, "Тест имя 4", "Тест телефон 4", null, null));
        clientsDAO.saveCollection(clientsList);

        Clients client = new Clients(5, "Отдельный клиент", "Отдельный", "Отдельный", "Отдельный");
        clientsDAO.save(client);
    }

    @BeforeAll
    @AfterEach
    @AfterAll
    public void clean() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery("TRUNCATE clients RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE clients_client_id_seq RESTART WITH  1;").executeUpdate();
            entityManager.getTransaction().commit();
        }
    }

    @Test
    public void testGetClientById() {
        Clients resultClient = clientsDAO.getById(5);
        Assertions.assertNotNull(resultClient);
        Clients expectedClient = new Clients(5, "Отдельный клиент", "Отдельный", "Отдельный", "Отдельный");
        Assertions.assertEquals(resultClient, expectedClient);

        Clients emptyClient = clientsDAO.getById(12345);
        Assertions.assertNull(emptyClient);
    }

    @Test
    public void testUpdateClient() {
        Clients client = clientsDAO.getById(4);
        Assertions.assertNotNull(client);
        client.setFullName("Новое имя");
        client.setPhone("Новый телефон");
        client.setEmail("Новый email");
        client.setPreferredCity("Новый город");
        clientsDAO.update(client);

        Clients result = clientsDAO.getById(4);
        Assertions.assertNotNull(result);
        Clients expected = new Clients(4, "Новое имя", "Новый телефон", "Новый email", "Новый город");
        Assertions.assertEquals(result, expected);
    }

    @Test
    public void testDeleteClient() {
        Clients client = clientsDAO.getById(4);
        Assertions.assertNotNull(client);
        clientsDAO.delete(client);

        Clients emptyClient = clientsDAO.getById(4);
        Assertions.assertNull(emptyClient);

        clientsDAO.deleteById(5);
        emptyClient = clientsDAO.getById(5);
        Assertions.assertNull(emptyClient);

        // Delete not existing ID. Program must run correctly
        clientsDAO.deleteById(12345);
    }

    @Test
    public void testClientsByFilter() {
        Collection<Clients> resultCollection = this.clientsDAO.getClientsByFilter(
                ClientsDAO.getFilterBuilder().Id(1).build()
        );
        Assertions.assertNotNull(resultCollection);
        Assertions.assertEquals(resultCollection.size(), 1);
        Set<Clients> result = new HashSet<>();
        result.addAll(resultCollection);
        Set<Clients> expected = new HashSet<>();
        expected.add(new Clients(1, "Петров Иван", "Тест телефон 1", "Тест email 1", "Москва"));
        Assertions.assertEquals(result, expected);

        Collection<Clients> resultEmptyCollection = this.clientsDAO.getClientsByFilter(
                ClientsDAO.getFilterBuilder().Id(12345).build()
        );
        Assertions.assertNotNull(resultEmptyCollection);
        Assertions.assertEquals(resultEmptyCollection.size(), 0);


        resultCollection = this.clientsDAO.getClientsByFilter(
                ClientsDAO.getFilterBuilder().fullName("Иван").build()
        );
        Assertions.assertNotNull(resultCollection);
        Assertions.assertEquals(resultCollection.size(), 2);
        result = new HashSet<>();
        result.addAll(resultCollection);
        expected = new HashSet<>();
        expected.add(new Clients(1, "Петров Иван", "Тест телефон 1", "Тест email 1", "Москва"));
        expected.add(new Clients(2, "Иванов Пётр", "Тест телефон 2", "Тест email 2", null));
        Assertions.assertEquals(result, expected);

        resultEmptyCollection = this.clientsDAO.getClientsByFilter(
                ClientsDAO.getFilterBuilder().fullName("This should never work").build()
        );
        Assertions.assertNotNull(resultEmptyCollection);
        Assertions.assertEquals(resultEmptyCollection.size(), 0);


        resultCollection = this.clientsDAO.getClientsByFilter(
                ClientsDAO.getFilterBuilder().preferredCity("Москва").build()
        );
        Assertions.assertNotNull(resultCollection);
        Assertions.assertEquals(resultCollection.size(), 2);
        result = new HashSet<>();
        result.addAll(resultCollection);
        expected = new HashSet<>();
        expected.add(new Clients(1, "Петров Иван", "Тест телефон 1", "Тест email 1", "Москва"));
        expected.add(new Clients(3, "Петров Пётр", "Тест телефон 3", null, "Москва"));
        Assertions.assertEquals(result, expected);

        resultEmptyCollection = this.clientsDAO.getClientsByFilter(
                ClientsDAO.getFilterBuilder().preferredCity("This should never work").build()
        );
        Assertions.assertNotNull(resultEmptyCollection);
        Assertions.assertEquals(resultEmptyCollection.size(), 0);


        resultCollection = this.clientsDAO.getClientsByFilter(
                ClientsDAO.getFilterBuilder().fullName("Иван").preferredCity("Москва").build()
        );
        Assertions.assertNotNull(resultCollection);
        Assertions.assertEquals(resultCollection.size(), 1);
        result = new HashSet<>();
        result.addAll(resultCollection);
        expected = new HashSet<>();
        expected.add(new Clients(1, "Петров Иван", "Тест телефон 1", "Тест email 1", "Москва"));
        Assertions.assertEquals(result, expected);

        resultEmptyCollection = this.clientsDAO.getClientsByFilter(
                ClientsDAO.getFilterBuilder().fullName("This should never work").preferredCity("Москва").build()
        );
        Assertions.assertNotNull(resultEmptyCollection);
        Assertions.assertEquals(resultEmptyCollection.size(), 0);
    }
}
