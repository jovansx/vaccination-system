package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.model.appointments.Appointment;
import akatsuki.immunizationsystem.model.documents.Interesovanje;
import akatsuki.immunizationsystem.model.types.TPodnosilacInteresovanja;
import akatsuki.immunizationsystem.model.types.TTipVakcine;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final Environment env;

    @Async
    public void notifyPatientAboutReservedAppointment(Interesovanje interesovanje, Appointment appointment) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("jovansimic995@gmail.com");
//        mail.setTo(interesovanje.getPodnosilac().getEmail());
        mail.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
        mail.setSubject("Potvrda rezervacije termina vakcinacije");

        TPodnosilacInteresovanja p = interesovanje.getPodnosilac();
        StringBuilder vakcine = new StringBuilder();
        for (TTipVakcine vakcina : interesovanje.getVakcine().getVakcina()) {
            vakcine.append("\t-\t");
            vakcine.append(vakcina.getNazivVakcine().getValue());
            vakcine.append("\n");
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        String dateString = formatter.format(appointment.getTermin().toGregorianCalendar().getTime());
        mail.setText("Pozdrav " + p.getIme().getValue() + " " + p.getPrezime().getValue() + ",\n\n"
                + "Samo da Vas obavestimo da smo primili Vase interesovanje za:" + "\n"
                + vakcine + "\n"
                + "Vas termin je zakazan za: " + dateString + "\n\n"
                + "Srdacan pozdrav!!!");

        javaMailSender.send(mail);
    }
}
