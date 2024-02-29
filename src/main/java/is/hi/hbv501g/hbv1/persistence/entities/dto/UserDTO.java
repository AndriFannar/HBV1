package is.hi.hbv501g.hbv1.persistence.entities.dto;

import is.hi.hbv501g.hbv1.persistence.entities.enums.UserRole;
import is.hi.hbv501g.hbv1.persistence.entities.User;

public class UserDTO
{
    private Long id;


    // Variables.
    private String name;
    private String email;
    private String ssn;
    private String phoneNumber;
    private String address;
    private String specialization;
    private Long waitingListRequestID;

    private UserRole role;


    public UserDTO()
    {

    }

    public UserDTO (User user)
    {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.ssn = user.getSsn();
        this.phoneNumber = user.getPhoneNumber();
        this.address = user.getAddress();
        this.specialization = user.getSpecialization();
        this.role = user.getRole();
        this.waitingListRequestID = user.getWaitingListRequest().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Long getWaitingListRequestID() {
        return waitingListRequestID;
    }

    public void setWaitingListRequestID(Long waitingListRequestID) {
        this.waitingListRequestID = waitingListRequestID;
    }
}
