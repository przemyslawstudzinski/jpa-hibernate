import domain.Bird;
import domain.Fish;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Example5Inheritance {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa.hibernate");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(new Bird("Orzeł", 2));
        entityManager.persist(new Fish("Karp", 7));
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
