package is.hi.hbv501g.hbv1.Persistence.Entities.DTOs;

public class LoginDTO
{
    private String username;
    private String password;

    public LoginDTO ()
    {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
