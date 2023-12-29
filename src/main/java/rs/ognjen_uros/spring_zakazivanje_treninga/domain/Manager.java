package rs.ognjen_uros.spring_zakazivanje_treninga.domain;

import javax.persistence.*;

@Entity
@Table(indexes = {@Index(columnList = "usernameManager", unique = true), @Index(columnList = "emailManager", unique = true)})
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String emailManager;
    private String firstName;
    private String lastName;
    private String usernameManager;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return emailManager;
    }

    public void setEmail(String email) {
        this.emailManager = email;
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

    public String getUsername() {
        return usernameManager;
    }

    public void setUsername(String username) {
        this.usernameManager = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
