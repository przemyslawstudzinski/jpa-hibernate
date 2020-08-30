import domain.Author;
import domain.Book;
import domain.Chapter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Exercise5 {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa.hibernate");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        exercise5(entityManager);

        entityManager.close();
        entityManagerFactory.close();
    }

    private static void exercise5(EntityManager entityManager) {
        Author author1 = new Author();
        author1.setFirstName("Olga");
        author1.setLastName("Tokarczuk");
        Book book1 = new Book();
        book1.setTitle("Opowiadania bizarne");
        Book book2 = new Book();
        book2.setTitle("Zgubiona dusza");
        Chapter chapter1 = new Chapter();
        chapter1.setName("Chapter 1 Opowiadania bizarne");
        Chapter chapter2 = new Chapter();
        chapter2.setName("Chapter 2 Opowiadania bizarne");
        book1.setChapters(List.of(chapter1, chapter2));
        Chapter chapter3 = new Chapter();
        chapter3.setName("Chapter 1 Zgubiona dusza");
        Chapter chapter4 = new Chapter();
        chapter4.setName("Chapter 2 Zgubiona dusza");
        book2.setChapters(List.of(chapter3, chapter4));
        author1.setBooks(List.of(book1, book2));

        Author author2 = new Author();
        author2.setFirstName("Christian");
        author2.setLastName("Bauer");
        Author author3 = new Author();
        author3.setFirstName("Gavin");
        author3.setLastName("King");
        Book book3 = new Book();
        book3.setTitle("Java Persistence. Programowanie aplikacji bazodanowych w Hibernate.");
        Chapter chapter5 = new Chapter();
        chapter5.setName("Chapter 1 JPA");
        book3.setChapters(List.of(chapter5));
        author2.setBooks(List.of(book3));
        author3.setBooks(List.of(book3));

        entityManager.getTransaction().begin();
        entityManager.persist(author1);
        entityManager.persist(author2);
        entityManager.persist(author3);
        entityManager.persist(book1);
        entityManager.persist(book2);
        entityManager.persist(book3);
        entityManager.persist(chapter1);
        entityManager.persist(chapter2);
        entityManager.persist(chapter3);
        entityManager.persist(chapter4);
        entityManager.persist(chapter5);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        book3.setTitle("Java Persistence. Programowanie aplikacji bazodanowych w Hibernate. Wydanie 2");
        entityManager.getTransaction().commit();
    }
}
