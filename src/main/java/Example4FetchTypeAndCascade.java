
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
import java.util.Set;

public class Example4FetchTypeAndCascade {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa.hibernate");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        saveEmployee(entityManager);
        saveEmployeeWithLocker(entityManager);
        entityManager.close();

        // Na potrzeby przykładu, aby nie cachował danych które przed chwilą zapisaliśmy
        entityManager = entityManagerFactory.createEntityManager();
        getProjects(entityManager);
        getLocker(entityManager);
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
                88331330221L,
                LocalDate.now().minusYears(22),
                Gender.MALE,
                BigDecimal.valueOf(2000.00),
                address
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
        // Przypisanie pracowników do projektów dokonujemy po stronie właściciela relacji
        project.setEmployees(List.of(employee, employee2));
        // Zapis do bazy danych
        entityManager.getTransaction().begin();
        entityManager.persist(project);
        // Dzięki ustawieniu kaskadowości nie musimy zapisywać pracowników
        //entityManager.persist(employee);
        //entityManager.persist(employee2);
        entityManager.getTransaction().commit();
    }

    private static void getProjects(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        Project project = entityManager.find(Project.class, 1L); // pobranie projektu po id z bazy danych
        System.out.println("Get project");
        List<Employee> employees = project.getEmployees();
        System.out.println("Get employees");
        employees.stream() // w przypadku FetchType.LAZY w tym momencie jest wykonywane dodatkowe zapytanie pobierające pracowników
                .forEach(System.out::println);
        // UWAGA! - Ustawienie kaskadowości spowoduje usunięcie również pracowników RACZEJ NIE ZALECANE
        entityManager.remove(project);
        entityManager.getTransaction().commit();
    }

    private static void saveEmployeeWithLocker(EntityManager entityManager) {
        Locker locker = new Locker("Locker 3/310", "Building A: 3 floor, room number 1");
        Employee employee = new Employee(
                "Franek",
                "Mięki",
                98331150921L,
                LocalDate.now().minusYears(22),
                Gender.MALE,
                BigDecimal.valueOf(3000.00),
                locker
        );
        locker.setEmployee(employee);
        // Zapis do bazy danych
        entityManager.getTransaction().begin();
        entityManager.persist(locker);
        entityManager.persist(employee);
        entityManager.getTransaction().commit();
    }

    private static void getLocker(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        Locker locker = entityManager.find(Locker.class, 1L); // pobranie szafki po id z bazy danych
        System.out.println(locker);
        locker.setEmployee(null); // Ustawienie orphanRemoval na true spowoduje usunięcie pracownika po odłączeniu go od encji Locker
        entityManager.getTransaction().commit();
    }
}
