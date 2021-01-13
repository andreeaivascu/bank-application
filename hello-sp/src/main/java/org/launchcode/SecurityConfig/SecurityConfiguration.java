package org.launchcode.SecurityConfig;



import org.launchcode.data.UserRepository;
import org.launchcode.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.util.ArrayList;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
               // .antMatchers( "/index1").hasAnyRole("USER")
                // .antMatchers( "/index2").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .failureUrl("/login-error.html")
                .permitAll();



    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        ArrayList<User> users=new ArrayList<User>();
        users= (ArrayList<User>) userRepository.findAll();
        String username=null;
        String passwordText=null;
        String role=null;

        for(int i=0;i<users.size();i++) {
            username = "" + users.get(i).getEmail();
            passwordText = "{noop}" + users.get(i).getPassword();


           /* try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(passwordText.getBytes("UTF-8"));
                StringBuilder hexString = new StringBuilder();

                for (int j = 0; j < hash.length; j++) {
                    String hex = Integer.toHexString(0xff & hash[j]);
                    if (hex.length() == 1) hexString.append('0');
                    hexString.append(hex);
                    System.out.println(hexString.toString());
                    passwordText=hexString.toString();
                    System.out.println(passwordText);

                }
            } catch (Exception ex) {
                System.out.println("error");
            }*/

            System.out.println(passwordText);
            role=users.get(i).getRole();
            System.out.println(users.get(i).getRole());


           /* if(!(username.equals("admin@yahoo.com")))
            {role = "USER";
            System.out.println(role);}
            else
            {role = "ADMIN";
            System.out.println(role);}*/

            auth.inMemoryAuthentication()
                    .withUser(username)
                    .password(passwordText) // Spring Security 5 requires specifying the password storage format
                    .roles(role);
        }
    }

}