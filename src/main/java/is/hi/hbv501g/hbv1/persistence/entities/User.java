package is.hi.hbv501g.hbv1.persistence.entities;

import is.hi.hbv501g.hbv1.persistence.entities.enums.UserRole;
import is.hi.hbv501g.hbv1.persistence.entities.dto.SignUpDTO;
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
    private String specialization;

    private UserRole role;


    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WaitingListRequest> waitingListRequests;

    @OneToOne(mappedBy = "patient" , cascade = CascadeType.ALL, orphanRemoval = true)
    private WaitingListRequest waitingListRequest;


    /**
     * Create a new empty user.
     */
    public User()
    {
        role = UserRole.USER;
    }

    /**
     * Create a new user.
     *
     * @param signUpDTO Signup information.
     */

    public User(SignUpDTO signUpDTO)
    {
        this.name = signUpDTO.getName();
        this.email = signUpDTO.getEmail();
        this.ssn = signUpDTO.getSsn();
        this.address = signUpDTO.getAddress();
        this.phoneNumber = signUpDTO.getPhoneNumber();
        this.password = signUpDTO.getPassword();

        role = UserRole.USER;
    }


    /**
     * Create a new user.
     *
     * @param name              User's name.
     * @param email             User's e-mail.
     * @param password          User's password.
     * @param ssn               User's SSN.
     * @param phoneNumber       User's phone number.
     * @param address           User's address.
     * @param role              User's role.
     * @param specialization    User's member's specialization.
     */
    public User(String name, String email, String password, String ssn, String phoneNumber, String address,
                UserRole role, String specialization)
    {
        this.name = name;
        this.email = email;
        this.password = password;
        this.ssn = ssn;
        this.phoneNumber = phoneNumber;
        this.role = role;

        this.address = address;
        this.specialization = specialization;
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

    public void setRole(UserRole role)
    {
        this.role = role;
    }

    public UserRole getRole()
    {
        return this.role;
    }

    public boolean isStaffMember()
    {
        return this.role.isStaffMember();
    }

    public boolean isElevatedUser()
    {
        return this.role.isElevatedUser();
    }

    public String getSpecialization()
    {
        return specialization;
    }

    public void setSpecialization(String specialization)
    {
        this.specialization = specialization;
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
                ", userRole=" + role +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}
