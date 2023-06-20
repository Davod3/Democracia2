package pt.ul.fc.css.democracia2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ul.fc.css.democracia2.entities.Delegado;

/*
 *
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 *
 */
public interface DelegadoRepository extends JpaRepository<Delegado, Long> {

  /**
   * Returns a list of delegados whose Citizens Card number matches a given value.
   *
   * @param numCC The given value
   * @return List of Delegado entities
   */
  @Query("SELECT d FROM Delegado d WHERE d.numCC = :numCC")
  public Delegado findByNumCC(@Param("numCC") int numCC);
}
