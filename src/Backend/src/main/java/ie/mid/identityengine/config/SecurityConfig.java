package ie.mid.identityengine.config;

import ie.mid.identityengine.enums.AuthorityType;
import ie.mid.identityengine.security.AuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String USER = AuthorityType.USER.toString();
    private static final String ADMIN = AuthorityType.ADMIN.toString();
    private static final String PARTY = AuthorityType.PARTY.toString();
    private static final String USER_PATH = getUrl("user");
    private static final String PARTY_PATH = getUrl("party");
    private static final String CERTIFICATE_PATH = getUrl("certificate");
    private static final String KEY_PATH = getUrl("key");
    private static final String IDENTITYTYPE_PATH = getUrl("identitytype");
    private static final String SUBMISSION_PATH = getUrl("submission");

    private static String getUrl(String endpoint) {
        return "/" + endpoint + "/**";
    }

    @Autowired
    private AuthProvider authProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("admin").authorities(ADMIN); //FOR TESTING ONLY
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                //User controller mappings
                .antMatchers(HttpMethod.POST, USER_PATH).permitAll()
                .antMatchers(HttpMethod.DELETE, USER_PATH).hasAnyAuthority(USER, ADMIN)
                .antMatchers(HttpMethod.GET, USER_PATH).permitAll()
                .antMatchers(HttpMethod.PUT, USER_PATH).hasAnyAuthority(USER, ADMIN)

                //Party controller mappings
                .antMatchers(HttpMethod.POST, PARTY_PATH).permitAll()
                .antMatchers(HttpMethod.GET, PARTY_PATH).permitAll()
                .antMatchers(HttpMethod.PUT, PARTY_PATH).hasAnyAuthority(PARTY, ADMIN)
                .antMatchers(HttpMethod.DELETE, PARTY_PATH).hasAnyAuthority(PARTY, ADMIN)

                //Certificate controller mappings
                .antMatchers(HttpMethod.GET, CERTIFICATE_PATH).permitAll()
                .antMatchers(HttpMethod.POST, CERTIFICATE_PATH).hasAnyAuthority(PARTY, ADMIN)
                .antMatchers(HttpMethod.PUT, CERTIFICATE_PATH).hasAnyAuthority(PARTY, ADMIN)
                .antMatchers(HttpMethod.DELETE, CERTIFICATE_PATH).hasAnyAuthority(PARTY, ADMIN)

                //Key controller mappings
                .antMatchers(HttpMethod.GET, KEY_PATH).permitAll()
                .antMatchers(HttpMethod.POST, KEY_PATH).hasAnyAuthority(ADMIN, USER, PARTY)
                .antMatchers(HttpMethod.PUT, KEY_PATH).hasAnyAuthority(ADMIN, USER, PARTY)
                .antMatchers(HttpMethod.DELETE, KEY_PATH).hasAnyAuthority(ADMIN, USER, PARTY)

                //IdentityType controller mappings
                .antMatchers(HttpMethod.GET, IDENTITYTYPE_PATH).permitAll()
                .antMatchers(HttpMethod.POST, IDENTITYTYPE_PATH).hasAnyAuthority(PARTY, ADMIN)
                .antMatchers(HttpMethod.PUT, IDENTITYTYPE_PATH).hasAnyAuthority(PARTY, ADMIN)
                .antMatchers(HttpMethod.DELETE, IDENTITYTYPE_PATH).hasAnyAuthority(PARTY, ADMIN)

                //Request controller mappings
                .antMatchers("/request/**").hasAnyAuthority(ADMIN, USER, PARTY)

                //Submission controller mappings
                .antMatchers(HttpMethod.GET, SUBMISSION_PATH).hasAnyAuthority(USER, PARTY, ADMIN)
                .antMatchers(HttpMethod.GET, "/submission/user/**").hasAnyAuthority(USER, ADMIN)
                .antMatchers(HttpMethod.GET, "/submission/party/**").hasAnyAuthority(PARTY, ADMIN)
                .antMatchers(HttpMethod.POST, SUBMISSION_PATH).hasAnyAuthority(USER, ADMIN)
                .antMatchers(HttpMethod.PUT, SUBMISSION_PATH).hasAnyAuthority(PARTY, ADMIN)
                .antMatchers(HttpMethod.DELETE, SUBMISSION_PATH).hasAnyAuthority(PARTY, ADMIN)

                .and().httpBasic();
    }
}
