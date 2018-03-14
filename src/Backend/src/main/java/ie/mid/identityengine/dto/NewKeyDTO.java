package ie.mid.identityengine.dto;

public class NewKeyDTO extends KeyDTO{

    private String token;

    public NewKeyDTO() {
    }

    public void setToken(String token){this.token = token;}

    public String getToken(){return token;}
}
