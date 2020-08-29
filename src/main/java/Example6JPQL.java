import domain.Employee;
import domain.Locker;
import domain.enums.Gender;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Example6JPQL {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa.hibernate");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        addEmployees(entityManager);

        entityManager.getTransaction().begin();
        TypedQuery<Employee> typedQuery = entityManager.createQuery("SELECT e FROM Employee e WHERE e.id = 1", Employee.class);
        Employee employee = typedQuery.getSingleResult();
        System.out.println(employee);
        System.out.println(employee.getLocker());
        System.out.println();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        TypedQuery<Employee> queryThree = entityManager.createQuery("SELECT e FROM Employee e WHERE e.salary > 3000", Employee.class);
        List<Employee> employees = queryThree.getResultList();
        employees.stream().forEach(System.out::println);
        System.out.println();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        typedQuery = entityManager.createQuery("SELECT e FROM Employee e WHERE e.id = :id", Employee.class);
        typedQuery.setParameter("id", 4L);
        employee = typedQuery.getSingleResult();
        System.out.println(employee);
        System.out.println();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        TypedQuery<String> typedQuery2 = entityManager.createQuery(
                "SELECT concat(e.firstName, ' ', e.lastName) FROM Employee e WHERE e.id = ?1", String.class);
        typedQuery2.setParameter(1, 4L);
        String employeeName = typedQuery2.getSingleResult();
        System.out.println(employeeName);
        System.out.println();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT e.firstName, e.lastName, l.name FROM Employee e JOIN e.locker l");
        List<Object[]> resultList = query.getResultList();
        for (Object[] singleResult : resultList) {
            String firstName = (String) singleResult[0];
            String lastName = (String) singleResult[1];
            String name = (String) singleResult[2];
            System.out.println("Imię: " + firstName + ", Nazwisko: " + lastName + ", Szafka: " + name);
        }
        System.out.println();
        entityManager.getTransaction().commit();

        // Named Queries
        entityManager.getTransaction().begin();
        typedQuery2 = entityManager.createNamedQuery("getAllLockerNames", String.class);
        List<String> lockerNames = typedQuery2.getResultList();
        for (String lockerName : lockerNames) {
            System.out.println("Szafka: " + lockerName);
        }
        System.out.println();
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    private static void addEmployees(EntityManager entityManager) {
        Employee employee = new Employee("Jan", "Kowalski",98332320221L, LocalDate.now().minusYears(22), Gender.MALE, BigDecimal.valueOf(3000.00));
        Employee employee2 = new Employee("Anna", "Nowak", 68332320221L, LocalDate.now().minusYears(52), Gender.FEMALE, BigDecimal.valueOf(5300.00));
        Employee employee3 = new Employee("Paweł", "Wiśniewski",78332320221L, LocalDate.now().minusYears(42), Gender.MALE, BigDecimal.valueOf(4100.00));
        Employee employee4 = new Employee("Piotr", "Kot",96332320221L, LocalDate.now().minusYears(24), Gender.MALE, BigDecimal.valueOf(3650.00));
        Employee employee5 = new Employee("Katarzyna", "Lis",89332320221L, LocalDate.now().minusYears(31), Gender.FEMALE, BigDecimal.valueOf(2100.50));
        Locker locker = new Locker("Locker 2/210", "Building A: 2 floor, room number 1");
        employee.setLocker(locker);
        entityManager.getTransaction().begin();
        entityManager.persist(locker);
        entityManager.persist(employee);
        entityManager.persist(employee2);
        entityManager.persist(employee3);
        entityManager.persist(employee4);
        entityManager.persist(employee5);
        entityManager.getTransaction().commit();
    }
}
