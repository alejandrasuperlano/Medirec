package com.medirec.medirec.Security.Model;

import com.medirec.medirec.Models.Doctor;
import com.medirec.medirec.Models.Patient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserSessionDetails implements UserDetails {
    private String completeName;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserSessionDetails(String completeName, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.completeName = completeName;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserSessionDetails build(Patient patient){
        List<GrantedAuthority> authorities =
                patient.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
        return new UserSessionDetails(patient.getUserFirstName() + " " + patient.getUserLastName(),
                patient.getUserEmail(), patient.getUserPassword(), authorities);
    }

    public static UserSessionDetails build(Doctor doctor){
        List<GrantedAuthority> authorities =
                doctor.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
        return new UserSessionDetails(doctor.getUserFirstName() + " " + doctor.getUserLastName(),
                doctor.getUserEmail(), doctor.getUserPassword(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
