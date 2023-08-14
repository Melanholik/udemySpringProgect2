package springProject.dao;

import java.util.List;
import java.util.Optional;

public interface ObjectDAO<T> {

    List<T> get();

    Optional<T> getById(int id);

    void update(T t);

    void add(T t);

    void deleteById(int id);
}
