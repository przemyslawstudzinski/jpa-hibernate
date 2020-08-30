import domain.MyIDTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Example7EmbeddedId {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa.hibernate");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        MyIDTest myIDTest = new MyIDTest(1L, "ABC");
        myIDTest.setName("Test wielopolowego Id");
        entityManager.getTransaction().begin();
        entityManager.persist(myIDTest);
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
