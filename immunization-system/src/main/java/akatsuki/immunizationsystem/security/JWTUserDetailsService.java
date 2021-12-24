package akatsuki.immunizationsystem.security;

import akatsuki.immunizationsystem.dao.KorisnikDAO;
import akatsuki.immunizationsystem.model.users.Korisnik;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JWTUserDetailsService implements UserDetailsService {

    private final KorisnikDAO korisnikIDao;

    public JWTUserDetailsService(KorisnikDAO korisnikIDao) {
        this.korisnikIDao = korisnikIDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Korisnik> korisnikOptional = korisnikIDao.getByEmail(username);
        if (korisnikOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        } else {
            return new JWTUserDetails(korisnikOptional.get());
        }
    }
}
