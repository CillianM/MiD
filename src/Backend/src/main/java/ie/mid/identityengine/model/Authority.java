package ie.mid.identityengine.model;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {

    private String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Authority{");
        sb.append("authority='").append(authority).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
