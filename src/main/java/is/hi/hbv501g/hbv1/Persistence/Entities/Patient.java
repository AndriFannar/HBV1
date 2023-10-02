package is.hi.hbv501g.hbv1.Persistence.Entities;

import jakarta.persistence.*;


/**
 * Patients for physiotherapist clinics.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @author  Ástríður Haraldsdóttir Passauer, ahp9@hi.is.
 * @since   2023-09-28
 * @version 1.1
 */
@Entity
@Table(name="patient")
public class Patient extends User
{
    // Variables.
    private String address;

    @OneToOne(mappedBy = "patient" , cascade = CascadeType.ALL, orphanRemoval = true)
    private WaitingListRequest waitingListRequest;

    /**
     * Create a new empty patient.
     */
    public Patient()
    {
        super();
    }


    /**
     * Create a new patient.
     *
     * @param name      Name of patient.
     * @param email     E-mail of patient.
     * @param password  Chosen password.
     * @param kennitala Kennilata of patient.
     * @param phNumber  Phone number of patient.
     * @param address   Patient's address.
     */
    public Patient(String name, String email, String password, String kennitala, String phNumber, String address)
    {
        super(name, email, password, kennitala, phNumber);

        this.address = address;
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


    @Override
    public String toString() {
        return "Patient{" +
                "address='" + address + '\'' +
                ", waitingListRequest=" + waitingListRequest +
                '}';
    }
}