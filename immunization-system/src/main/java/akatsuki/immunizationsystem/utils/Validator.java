package akatsuki.immunizationsystem.utils;

import org.springframework.stereotype.Component;

@Component
public class Validator {

    public boolean isJmbgValid(String jmbg) {
        String jmbgRegex = "(0[1-9]|[12]\\d|3[01])(0[1-9]|1[012])(9[0-9]{2}|0[01][0-9]|02[01])([0-8][0-9]|9[0-6])([0-9][0-9][0-9])([0-9])";

        return jmbg.matches(jmbgRegex);
    }
}
