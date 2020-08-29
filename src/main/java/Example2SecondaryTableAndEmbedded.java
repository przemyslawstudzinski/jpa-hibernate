
import domain.Address;
import domain.Employee;
import domain.enums.Gender;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Example2SecondaryTableAndEmbedded {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa.hibernate");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        saveEmployee(entityManager);

        entityManager.close();
        entityManagerFactory.close();
    }

    private static void saveEmployee(EntityManager entityManager) {
        Address address = new Address(
                "Szkolna",
                "5c",
                "80-500",
                "Gdansk"
        );
        Employee employee = new Employee(
                "Jan",
                "Kowalski",
                88332300221L,
                LocalDate.now().minusYears(32),
                Gender.MALE,
                BigDecimal.valueOf(2000.00),
                address
        );

        entityManager.getTransaction().begin();
        entityManager.persist(employee);
        entityManager.getTransaction().commit();
    }

}
