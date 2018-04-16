package jpastart.reserve.model;

import javax.persistence.*;
import java.util.Date;

@Entity // Table과 매핑되는 기본단위
@Table(name = "user") // 테이블 이름
public class User {

    @Id // primary key
    private String email;
    
    private String name;
    
    @Temporal(TemporalType.TIMESTAMP) // Date 타입 매핑
    @Column(name = "create_date") // 컬럼이름 명시. 명시 않으면 필드명과 컬럼명을 매핑.
    private Date createDate;
    
    // JPA 는 객체생성시 인자가 없는 기본생성자를 사용한다.
    protected User() {
    }
    
    public User(String email, String name, Date createDate) {
        this.email = email;
        this.name = name;
        this.createDate = createDate;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getName() {
        return name;
    }
    
    public Date getCreateDate() {
        return createDate;
    }
    
    public void changeName(String newName) {
        this.name = newName;
    }
}
