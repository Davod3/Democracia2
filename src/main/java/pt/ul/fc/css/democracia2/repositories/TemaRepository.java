package pt.ul.fc.css.democracia2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pt.ul.fc.css.democracia2.entities.Tema;

/*
 *
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 *
 */
public interface TemaRepository extends JpaRepository<Tema, Long> {
}
