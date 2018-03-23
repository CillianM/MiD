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
                .antMatchers(HttpMethod.GET, "/user/**").permitAll()
            .antMatchers(HttpMethod.PUT,"/user/**").hasAnyAuthority(USER,ADMIN)
            //Party controller mappings
            .antMatchers(HttpMethod.POST, "/party/**").permitAll()
            .antMatchers(HttpMethod.GET,"/party/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/party/**").hasAnyAuthority(PARTY, ADMIN)
                .antMatchers(HttpMethod.DELETE, "/party/**").hasAnyAuthority(PARTY, ADMIN)
            //Certificate controller mappings
                .antMatchers(HttpMethod.GET, "/certificate").hasAnyAuthority(USER, PARTY, ADMIN)
                .antMatchers(HttpMethod.POST, "/certificate/**").hasAnyAuthority(PARTY, ADMIN)
                .antMatchers(HttpMethod.PUT, "/certificate/**").hasAnyAuthority(PARTY, ADMIN)
                .antMatchers(HttpMethod.DELETE, "/certificate/**").hasAnyAuthority(PARTY, ADMIN)
            //Key controller mappings
            .antMatchers(HttpMethod.GET, "/key").permitAll()
            .antMatchers(HttpMethod.POST, "/key/**").hasAnyAuthority(ADMIN,USER,PARTY)
            .antMatchers(HttpMethod.PUT, "/key/**").hasAnyAuthority(ADMIN,USER,PARTY)
            .antMatchers(HttpMethod.DELETE, "/key/**").hasAnyAuthority(ADMIN,USER,PARTY)
            //IdentityType controller mappings
            .antMatchers(HttpMethod.GET, "/identitytype/**").permitAll()
                .antMatchers(HttpMethod.POST, "/identitytype").hasAnyAuthority(PARTY, ADMIN)
                .antMatchers(HttpMethod.PUT, "/identitytype/**").hasAnyAuthority(PARTY, ADMIN)
                .antMatchers(HttpMethod.DELETE, "/identitytype/**").hasAnyAuthority(PARTY, ADMIN)
            //Request controller mappings
            .antMatchers("/request/**").hasAnyAuthority(ADMIN,USER,PARTY)
            //Submission controller mappings
                .antMatchers(HttpMethod.GET, "/submission").hasAnyAuthority(USER, PARTY, ADMIN)
                .antMatchers(HttpMethod.GET, "/submission/user/**").hasAnyAuthority(USER, ADMIN)
                .antMatchers(HttpMethod.GET, "/submission/party/**").hasAnyAuthority(PARTY, ADMIN)
                .antMatchers(HttpMethod.POST, "/submission").hasAnyAuthority(USER, ADMIN)
                .antMatchers(HttpMethod.PUT, "/submission/**").hasAnyAuthority(PARTY, ADMIN)
                .antMatchers(HttpMethod.DELETE, "/submission/**").hasAnyAuthority(PARTY, ADMIN)
            .and().httpBasic();


    }
}
