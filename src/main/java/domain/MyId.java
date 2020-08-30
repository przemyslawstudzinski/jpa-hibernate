package domain;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MyId implements Serializable {

    private Long id;
    private String code;

    public MyId() {
    }

    public MyId(Long id, String code) {
        this.id = id;
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
