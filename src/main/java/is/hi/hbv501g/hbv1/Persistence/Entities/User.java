package is.hi.hbv501g.hbv1.Persistence.Entities;

import jakarta.persistence.*;

import java.util.List;


/**
 * Users for physiotherapist clinics.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @author  Ástríður Haraldsdóttir Passauer, ahp9@hi.is.
 * @since   2023-09-28
 * @version 1.1
 */
@Entity
@Table(name="users")
public class User
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
    private String ssn;
    private String phoneNumber;
    private String address;
    private boolean isStaff;
    private boolean isPhysiotherapist;
    private boolean isAdmin;
    private String specialization;
    private String description;

    @OneToMany(mappedBy = "staff")
    private List<WaitingListRequest> waitingListRequests;

    @OneToOne(mappedBy = "patient" , cascade = CascadeType.ALL, orphanRemoval = true)
    private WaitingListRequest waitingListRequest;


    /**
     * Create a new empty user.
     */
    public User()
    {
        isStaff = false;
    }


    /**
     * Create a new user.
     *
     * @param name              User's name.
     * @param email             User's e-mail.
     * @param password          User's password.
     * @param ssn               User's SSN.
     * @param phoneNumber       User's phone number.
     * @param address           Patient's address.
     * @param isPhysiotherapist Is user a physiotherapist or a receptionist.
     * @param specialization    Staff member's specialization.
     * @param description       Description of staff member.
     */
    public User(String name, String email, String password, String ssn, String phoneNumber, String address, boolean isStaff,
                boolean isPhysiotherapist, boolean isAdmin, String specialization, String description)
    {
        this.name = name;
        this.email = email;
        this.password = password;
        this.ssn = ssn;
        this.phoneNumber = phoneNumber;

        this.address = address;

        this.isStaff = isStaff;
        this.isPhysiotherapist = isPhysiotherapist;
        this.isAdmin = isAdmin;
        this.specialization = specialization;
        this.description = description;
    }


    public Long getId()
    {
        return id;
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

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public void setPhoneNumber(String phNumber)
    {
        this.phoneNumber = phNumber;
    }


    public String getPhoneNumber()
    {
        return this.phoneNumber;
    }

    public WaitingListRequest getWaitingListRequest() {
        return waitingListRequest;
    }


    public void setWaitingListRequest(WaitingListRequest waitingListRequest) {
        this.waitingListRequest = waitingListRequest;
    }

    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public void setStaff(boolean staff) {
        isStaff = staff;
    }

    public boolean isPhysiotherapist()
    {
        return isPhysiotherapist;
    }


    public void setPhysiotherapist(boolean physiotherapist)
    {
        isPhysiotherapist = physiotherapist;
    }


    public boolean isAdmin()
    {
        return isAdmin;
    }


    public void setAdmin(boolean admin)
    {
        isAdmin = admin;
    }


    public String getSpecialization()
    {
        return specialization;
    }


    public void setSpecialization(String specialization)
    {
        this.specialization = specialization;
    }


    public String getDescription()
    {
        return description;
    }


    public void setDescription(String description)
    {
        this.description = description;
    }


    public List<WaitingListRequest> getWaitingListRequests()
    {
        return waitingListRequests;
    }


    public void setWaitingListRequests(List<WaitingListRequest> waitingListRequests)
    {
        this.waitingListRequests = waitingListRequests;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", ssn='" + ssn + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", isStaff=" + isStaff +
                ", isPhysiotherapist=" + isPhysiotherapist +
                ", isAdmin=" + isAdmin +
                ", specialization='" + specialization + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
