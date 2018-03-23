package admin.dto;

public class NewUserDTO extends UserDTO {

    private String userToken;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
