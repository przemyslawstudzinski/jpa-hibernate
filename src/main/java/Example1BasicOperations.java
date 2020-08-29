
import domain.Employee;
import domain.enums.Gender;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class Example1BasicOperations {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa.hibernate");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

//        saveEmployee(entityManager);
//        getEmployee(entityManager);
//        updateEmployee(entityManager);
//        refreshEmployee(entityManager);
//        deleteEmployee(entityManager);
//        detachAndMergeEmployee(entityManager);

        entityManager.close();
        entityManagerFactory.close();
    }

    private static void saveEmployee(EntityManager entityManager) {
        Employee employee = new Employee(
                "Jan",
                "Kowalski",
                88332300221L,
                LocalDate.now().minusYears(32),
                Gender.MALE
        );

        entityManager.getTransaction().begin();
        entityManager.persist(employee); // zapisanie pracownika do bazy danych
        entityManager.getTransaction().commit();
    }

    private static void getEmployee(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        Employee employee = entityManager.find(Employee.class, 1L); // pobranie pracownika po id z bazy danych
        System.out.println(employee.toString());
        entityManager.getTransaction().commit();
    }

    private static void updateEmployee(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        Employee employee = entityManager.find(Employee.class, 1L); // pobranie pracownika po id z bazy danych
        employee.setFirstName("Tomek"); // zmiana zostanie wysłana przez Hibernate do bazy danych tzn. dirty checking
        entityManager.getTransaction().commit();
    }

    private static void refreshEmployee(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        Employee employee = entityManager.find(Employee.class, 1L); // pobranie pracownika po id z bazy danych
        employee.setFirstName("Stanisław");
        employee.setLastName("Nowak");
        System.out.println(employee.toString());
        entityManager.refresh(employee); // odświeżenie pracownika stanem jaki znajduje się w bazie danych
        System.out.println(employee.toString());
        entityManager.getTransaction().commit();
    }

    private static void deleteEmployee(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        Employee employee = entityManager.find(Employee.class, 1L); // pobranie pracownika po id z bazy danych
        entityManager.remove(employee); // usunięcie pracownika z bazy danych
        entityManager.getTransaction().commit();
    }

    private static void detachAndMergeEmployee(EntityManager entityManager) {
        Employee employee = new Employee(
                "Marta",
                "Wiśniewska",
                90332300221L,
                LocalDate.now().minusYears(30),
                Gender.FEMALE
        );

        entityManager.getTransaction().begin();
        entityManager.persist(employee);
        System.out.println(employee);
        employee.setFirstName("Aleksandra"); // ta zmiana zostanie uwzględniona - dirty checking
        entityManager.flush();               // bezpośrednie wypchnięcie zmian do bazy - na potrzeby przykładu
        entityManager.detach(employee);      // odłączenie od kontekstu persystencji
        employee.setLastName("Nowak");       // ta zmiana zostanie pominięta
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Employee employeeFromDb = entityManager.find(Employee.class, employee.getId()); // pobranie pracownika po id z bazy danych
        System.out.println(employeeFromDb);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.merge(employee); // ponowne dołączenie od kontekstu persystencji – uwzględnienie ostatniej zmiany
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Employee employeeFromDbAfterMerge = entityManager.find(Employee.class, employee.getId());
        System.out.println(employeeFromDbAfterMerge);
        entityManager.getTransaction().commit();
    }

}
