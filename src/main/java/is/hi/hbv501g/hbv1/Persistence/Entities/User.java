package is.hi.hbv501g.hbv1.Persistence.Entities;

import jakarta.persistence.*;


/**
 * Users for physiotherapist clinics.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @author  Ástríður Haraldsdóttir Passauer, ahp9@hi.is.
 * @since   2023-09-28
 * @version 1.1
 */
@MappedSuperclass
public abstract class User
{
    // Database primary key.
    @Id
    @SequenceGenerator(
            name="user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;

    // Variables.
    private String name;
    private String email;
    private String password;
    private String kennitala;
    private String phoneNumber;


    /**
     * Create a new empty user.
     */
    public User()
    {

    }


    /**
     * Create a new user.
     *
     * @param name      Username.
     * @param email     User e-mail.
     * @param password  User password.
     * @param kennitala User's kennitala.
     * @param phoneNumber  User's phone number.
     */
    public User(String name, String email, String password, String kennitala, String phNumber)
    {
        this.name = name;
        this.email = email;
        this.password = password;
        this.kennitala = kennitala;
        this.phoneNumber = phNumber;
    }


    public Long getId()
    {
        return id;
    }


    public void setId(Long id_counter)
    {
        this.id = id_counter;
    }


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }


    public String getEmail()
    {
        return email;
    }


    public void setEmail(String email)
    {
        this.email = email;
    }


    public void setPassword(String password)
    {
        this.password = password;
    }


    public String getPassword()
    {
        return this.password;
    }


    public void setKennitala(String kennitala)
    {
        this.kennitala = kennitala;
    }


    public String getKennitala()
    {
        return this.kennitala;
    }


    public void setPhoneNumber(String phNumber)
    {
        this.phoneNumber = phNumber;
    }


    public String getPhoneNumber()
    {
        return this.phoneNumber;
    }
}
