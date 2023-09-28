package is.hi.hbv501g.hbv1.Persistence.Entities;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class User
{
    @Id
    @SequenceGenerator(
            name="patient_sequence",
            sequenceName = "patient_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "patient_sequence"
    )
    private Long id;
    private String name;
    private String email;
    private String password;
    private String kennitala;
    private String phoneNumber;
    private boolean isAdmin;


    public User()
    {
    }


    public User(String name, String email, String password, String kennitala, String phNumber, boolean isAdmin) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.kennitala = kennitala;
        this.phoneNumber = phNumber;
        this.isAdmin = isAdmin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id_counter) {
        this.id = id_counter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    public void setKennitala(String kennitala){
        this.kennitala = kennitala;
    }

    public String getKennitala(){
        return this.kennitala;
    }

    public void setPhoneNumber(String phNumber){
        this.phoneNumber = phNumber;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public void setIsAdmin(boolean isAdmin){
        this.isAdmin = isAdmin;
    }

    public boolean getIsAdmin(){
        return this.isAdmin;
    }

}
