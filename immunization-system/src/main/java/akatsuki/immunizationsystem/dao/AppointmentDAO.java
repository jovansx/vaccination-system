package akatsuki.immunizationsystem.dao;

import akatsuki.immunizationsystem.model.appointments.Appointment;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AppointmentDAO implements IDao<Appointment> {

    private final String collectionId = "/db/vaccination-system/termini";
    private final DaoUtils daoUtils;
    private final IModelMapper<Appointment> mapper;

    @Override
    public Optional<Appointment> get(String id) {
        String resourceContent = daoUtils.getResource(collectionId, id);
        if (resourceContent.equals(""))
            return Optional.empty();
        Appointment appointment = mapper.convertToObject(resourceContent);
        if (appointment == null)
            return Optional.empty();
        return Optional.of(appointment);
    }

    @Override
    public Collection<Appointment> getAll() {
        return null;
    }

    @Override
    public int getResourcesCount() {
        return 0;
    }

    @Override
    public String save(Appointment appointment) {
        String id = appointment.formatTimeToString() + "_" + appointment.getPacijentId() + ".xml";
        daoUtils.createResource(collectionId, appointment, id, Appointment.class);
        return id;
    }

    @Override
    public void update(Appointment appointment) {

    }

    @Override
    public void delete(Appointment appointment) {

    }
}
