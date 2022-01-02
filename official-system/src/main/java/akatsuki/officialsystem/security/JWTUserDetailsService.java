package akatsuki.officialsystem.security;

import akatsuki.officialsystem.dao.SluzbenikDAO;
import akatsuki.officialsystem.model.users.Sluzbenik;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JWTUserDetailsService implements UserDetailsService {

    private final SluzbenikDAO korisnikIDao;

    public JWTUserDetailsService(SluzbenikDAO korisnikIDao) {
        this.korisnikIDao = korisnikIDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Sluzbenik> korisnikOptional = korisnikIDao.getByEmail(username);
        if (korisnikOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        } else {
            return new JWTUserDetails(korisnikOptional.get());
        }
    }
}
