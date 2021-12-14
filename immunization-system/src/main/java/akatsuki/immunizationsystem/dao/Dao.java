package akatsuki.immunizationsystem.dao;

import java.util.Collection;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> get(String id);
    Collection<T> getAll();
    String save(T t);
    void update(T t);
    void delete(T t);
}