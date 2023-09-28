package is.hi.hbv501g.hbv1.Persistence.Entities;

import jakarta.persistence.*;

@Entity
@Table(name="patient")
public class Patient extends User
{

    private String address;

    public Patient()
    {
        super();
    }


    public Patient(String name, String email, String password, String kennitala, String phNumber, boolean isAdmin, String address)
    {
        super(name, email, password, kennitala, phNumber, isAdmin);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString()
    {
        return super.toString() + "Patient{" +
                "address='" + address + '\'' +
                '}';
    }
}
