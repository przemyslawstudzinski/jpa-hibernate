package domain;

import domain.enums.Gender;

//import javax.persistence.Access;
//import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
import javax.persistence.Transient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "external_employee") // name -> zdefiniowana nazwa tabeli, w przeciwnym wypadku brana jest nazwa klasy
@SecondaryTable( // name -> zdefniowane nazwy dla drugiej tabeli i PrimaryKeyJoinColumn -> kolumny łączącej wraz z nazwą
        name = "salary",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "employee_id")
)
//@Access(AccessType.FIELD) // ustawienie sposobu dostępu do pól, domyślnie "field" bezpośrednio poprzez pole, inna możliwość "PROPERTY" przez gettery i settery
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id generowane automatycznie, wykorzystuje typ kolumny AUTO-INCREMENT
    private Long id;

    @Column(nullable = false, length = 100) // nullable -> wartość nie może być null, length -> maksymalna długość 100 znaków
    private String firstName;

    @Column(columnDefinition = "VARCHAR(200) NOT NULL") // columnDefinition -> określenie kolumny językiem SQL
    private String lastName;

    @Column(name = "pesel", unique = true) // name -> zmiana domyślnej nazwy kolumny, unique -> wartość musi być unikalna
    private long peselPLCode;

    private LocalDate birthDate;

//    @Temporal(TemporalType.DATE)    // przydatne do określania czasu z pakietu java.util np. java.util.Date
//    Date birthDate2;

    @Transient // nie podlega mapowaniu przez ORM, pole pominięte w bazie danych
    private short age;

    @Enumerated(value = EnumType.STRING) // zapisanie wartości enumów w bazie danych w postaci String, domyślnie EnumType.ORDINAL w postaci Integer
    private Gender gender;

    @Column(table = "salary", precision = 10, scale = 2) // table -> tabela gdzie zostanie dodane pole
    private BigDecimal salary;                                  // precision -> precycja 2 znaków, scale ->  w tym 2 miejsca po przecinku

    @Embedded
    private Address address;

    @OneToOne
    private Locker locker;

    @OneToMany(mappedBy = "owner")
    //@JoinColumn(name = "employee_id") // -> bez tej adnotacji utoworzona zostałaby dodatkowa tabela przechowująca relacje (w przypadku relacji jednokierunkowej)
    private List<Phone> phones;

    @ManyToMany(mappedBy = "employees")
    private List<Project> projects;

    public Employee() {
    }

    public Employee(String firstName, String lastName, long peselPLCode, LocalDate birthDate, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.peselPLCode = peselPLCode;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public Employee(String firstName, String lastName, long peselPLCode, LocalDate birthDate, Gender gender, BigDecimal salary) {
        this(firstName, lastName, peselPLCode, birthDate, gender);
        this.salary = salary;
    }

    public Employee(String firstName, String lastName, long peselPLCode, LocalDate birthDate, Gender gender,
                    BigDecimal salary, Address address) {
        this(firstName, lastName, peselPLCode, birthDate, gender, salary);
        this.address = address;
    }

    public Employee(String firstName, String lastName, long peselPLCode, LocalDate birthDate, Gender gender,
                    BigDecimal salary, Address address, Locker locker) {
        this(firstName, lastName, peselPLCode, birthDate, gender, salary, address);
        this.locker = locker;
    }

    public Employee(String firstName, String lastName, long peselPLCode, LocalDate birthDate, Gender gender,
                    BigDecimal salary, Locker locker) {
        this(firstName, lastName, peselPLCode, birthDate, gender, salary);
        this.locker = locker;
    }

    public Employee(String firstName, String lastName, long peselPLCode, LocalDate birthDate, Gender gender,
                    BigDecimal salary, Address address, Locker locker, List<Phone> phones) {
        this(firstName, lastName, peselPLCode, birthDate, gender, salary, address, locker);
        this.phones = phones;
    }

    public Employee(String firstName, String lastName, long peselPLCode, LocalDate birthDate, Gender gender,
                    BigDecimal salary, List<Phone> phones) {
        this(firstName, lastName, peselPLCode, birthDate, gender, salary);
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", peselPLCode=" + peselPLCode +
                ", birthDate=" + birthDate +
                ", age=" + age +
                ", gender=" + gender +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getPeselPLCode() {
        return peselPLCode;
    }

    public void setPeselPLCode(long peselPLCode) {
        this.peselPLCode = peselPLCode;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Locker getLocker() {
        return locker;
    }

    public void setLocker(Locker locker) {
        this.locker = locker;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
