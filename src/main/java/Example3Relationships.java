import domain.Address;
import domain.Employee;
import domain.Locker;
import domain.Phone;
import domain.Project;
import domain.enums.Gender;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Example3Relationships {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa.hibernate");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        saveEmployeeWithLocker(entityManager);
        saveEmployeeWithPhones(entityManager);
        saveEmployeeWithProjects(entityManager);

        entityManager.close();
        entityManagerFactory.close();
    }

    private static void saveEmployeeWithLocker(EntityManager entityManager) {
        Locker locker = new Locker("Locker 2/210", "Building A: 2 floor, room number 1");
        Employee employee = new Employee(
                "Jan",
                "Kowalski",
                88332350221L,
                LocalDate.now().minusYears(32),
                Gender.MALE,
                BigDecimal.valueOf(2000.00),
                locker
        );
        locker.setEmployee(employee);
        // Zapis do bazy danych
        entityManager.getTransaction().begin();
        entityManager.persist(locker);
        entityManager.persist(employee);
        entityManager.getTransaction().commit();
    }

    private static void saveEmployeeWithPhones(EntityManager entityManager) {
        Employee employee = new Employee(
                "Jan",
                "Kowalski",
                88332330221L,
                LocalDate.now().minusYears(22),
                Gender.MALE,
                BigDecimal.valueOf(2000.00)
        );
        Phone phone1 = new Phone("private", "676989776");
        Phone phone2 = new Phone("business", "576989776");
        // Przypisanie telefonów do pracowników dokonujemy po stronie właściciela relacji
        //employee.setPhones(List.of(phone1, phone2)); // Tak nie działa!
        phone1.setOwner(employee);
        phone2.setOwner(employee);
        // Zapis do bazy danych
        entityManager.getTransaction().begin();
        entityManager.persist(employee);
        entityManager.persist(phone1);
        entityManager.persist(phone2);
        entityManager.getTransaction().commit();
    }

    private static void saveEmployeeWithProjects(EntityManager entityManager) {
        Employee employee = new Employee(
                "Jan",
                "Kowalski",
                88332320221L,
                LocalDate.now().minusYears(32),
                Gender.MALE,
                BigDecimal.valueOf(2800.00)
        );
        Employee employee2 = new Employee(
                "Marta",
                "Wiśniewska",
                90332310221L,
                LocalDate.now().minusYears(30),
                Gender.FEMALE,
                BigDecimal.valueOf(3200.00)
        );
        Project project = new Project("LHO 201", "Logistic");
        Project project2 = new Project("Delegation 1.0", "Delegation");
        Project project3 = new Project("Auto 2.0", "Motorization");
        // Przypisanie pracowników do projektów dokonujemy po stronie właściciela relacji
        project.setEmployees(List.of(employee));
        project2.setEmployees(List.of(employee, employee2));
        project3.setEmployees(List.of(employee2));
        // Zapis do bazy danych
        entityManager.getTransaction().begin();
        entityManager.persist(project);
        entityManager.persist(project2);
        entityManager.persist(project3);
        entityManager.persist(employee);
        entityManager.persist(employee2);
        entityManager.getTransaction().commit();
    }

}
