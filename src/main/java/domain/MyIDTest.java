package domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class MyIDTest {

    @EmbeddedId
    private MyId id;

    private String name;

    public MyIDTest(Long id, String code ) {
        this.id = new MyId(id, code);
    }

    public MyId getId() {
        return id;
    }

    public void setId(MyId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
