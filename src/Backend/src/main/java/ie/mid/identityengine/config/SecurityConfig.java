package ie.mid.identityengine.config;

import ie.mid.identityengine.enums.AuthorityType;
import ie.mid.identityengine.security.AuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String USER = AuthorityType.USER.toString();
    private static final String ADMIN = AuthorityType.ADMIN.toString();
    private static final String PARTY = AuthorityType.PARTY.toString();

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
            .antMatchers(HttpMethod.POST, "/user/**").permitAll()
            .antMatchers(HttpMethod.DELETE,"/user/**").hasAnyAuthority(USER,ADMIN)
            .antMatchers(HttpMethod.GET,"/user/**").hasAnyAuthority(USER,ADMIN)
            .antMatchers(HttpMethod.PUT,"/user/**").hasAnyAuthority(USER,ADMIN)
            //Party controller mappings
            .antMatchers(HttpMethod.POST, "/party/**").permitAll()
            .antMatchers(HttpMethod.GET,"/party/**").permitAll()
            .antMatchers(HttpMethod.PUT,"/party/**").permitAll()
            .antMatchers(HttpMethod.DELETE,"/party/**").permitAll()
            //Certificate controller mappings
            .antMatchers(HttpMethod.GET, "/certificate").permitAll()
            .antMatchers(HttpMethod.POST, "/certificate/**").hasAnyAuthority(ADMIN,USER,PARTY)
            .antMatchers(HttpMethod.PUT, "/certificate/**").hasAnyAuthority(ADMIN,USER,PARTY)
            .antMatchers(HttpMethod.DELETE, "/certificate/**").hasAnyAuthority(ADMIN,USER,PARTY)
            //Key controller mappings
            .antMatchers(HttpMethod.GET, "/key").permitAll()
            .antMatchers(HttpMethod.POST, "/key/**").hasAnyAuthority(ADMIN,USER,PARTY)
            .antMatchers(HttpMethod.PUT, "/key/**").hasAnyAuthority(ADMIN,USER,PARTY)
            .antMatchers(HttpMethod.DELETE, "/key/**").hasAnyAuthority(ADMIN,USER,PARTY)
            //IdentityType controller mappings
            .antMatchers(HttpMethod.GET, "/identitytype/**").permitAll()
            .antMatchers(HttpMethod.POST, "/identitytype").hasAnyAuthority(ADMIN,PARTY)
            .antMatchers(HttpMethod.PUT, "/identitytype/**").hasAnyAuthority(ADMIN,PARTY)
            .antMatchers(HttpMethod.DELETE, "/identitytype/**").hasAnyAuthority(ADMIN,PARTY)
            //Request controller mappings
            .antMatchers("/request/**").hasAnyAuthority(ADMIN,USER,PARTY)
            //Submission controller mappings
            .antMatchers(HttpMethod.GET, "/submission").permitAll()
            .antMatchers(HttpMethod.GET, "/submission/user/**").hasAnyAuthority(ADMIN,USER)
            .antMatchers(HttpMethod.GET, "/submission/party/**").permitAll()
            .antMatchers(HttpMethod.POST, "/submission").hasAnyAuthority(ADMIN,USER,PARTY)
            .antMatchers(HttpMethod.PUT, "/submission/**").permitAll()
            .antMatchers(HttpMethod.DELETE, "/submission/**").hasAnyAuthority(ADMIN,USER,PARTY)
            .and().httpBasic();


    }
}
