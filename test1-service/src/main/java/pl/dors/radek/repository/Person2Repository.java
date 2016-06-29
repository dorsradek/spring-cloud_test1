package pl.dors.radek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.dors.radek.model.Person2;

/**
 * Created by rdors on 2016-06-29.
 */
@Transactional
public interface Person2Repository extends JpaRepository<Person2, Long> {
}
