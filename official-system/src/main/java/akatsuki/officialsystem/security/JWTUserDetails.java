package akatsuki.officialsystem.security;

import akatsuki.officialsystem.model.users.Sluzbenik;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class JWTUserDetails implements UserDetails {

    private final Sluzbenik user;

    public JWTUserDetails(Sluzbenik user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.singletonList(new SimpleGrantedAuthority(user.getTip().name().toUpperCase()));
        return null;
    }

    @Override
    public String getPassword() {
        return user.getSifra();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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

    public long getId() {
        return Long.parseLong(user.getIdBroj());
    }

}
