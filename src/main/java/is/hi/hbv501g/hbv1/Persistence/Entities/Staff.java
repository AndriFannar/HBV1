package is.hi.hbv501g.hbv1.Persistence.Entities;

import jakarta.persistence.*;

import java.util.List;


/**
 * Staff members for physiotherapist clinics.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-09-28
 * @version 1.1
 */
@Entity
@Table(name="staff")
public class Staff extends User
{
    // Database primary key.
    @Id
    @SequenceGenerator(
            name="staff_sequence",
            sequenceName = "staff_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "staff_sequence"
    )
    private Long id;

    // Variables.
    private boolean isPhysiotherapist;
    private boolean isAdmin;
    private String specialization;
    private String description;

    @OneToMany(mappedBy = "staff")
    private List<WaitingListRequest> waitingListRequests;


    /**
     * Create a new empty staff member.
     */
    public Staff()
    {
        super();
    }


    /**
     * Create a new staff member.
     *
     * @param name              Name of staff member.
     * @param email             E-mail of staff member.
     * @param password          Chosen password.
     * @param kennitala         Kennilata of staff member.
     * @param phNumber          Phone number of staff member.
     * @param isPhysiotherapist Is user a physiotherapist or a receptionist.
     * @param specialization    Staff member's specialization.
     * @param description       Description of staff member.
     */
    public Staff(String name, String email, String password, String kennitala, String phNumber, boolean isPhysiotherapist, boolean isAdmin, String specialization, String description)
    {
        super(name, email, password, kennitala, phNumber);

        this.isPhysiotherapist = isPhysiotherapist;
        this.isAdmin = isAdmin;
        this.specialization = specialization;
        this.description = description;
    }


    @Override
    public Long getId()
    {
        return id;
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


    @Override
    public String toString()
    {
        return super.toString() + "Staff{" +
                "jobTitle='" + isPhysiotherapist + '\'' +
                ", specialization='" + specialization + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
