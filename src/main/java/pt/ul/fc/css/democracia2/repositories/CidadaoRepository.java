package pt.ul.fc.css.democracia2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ul.fc.css.democracia2.entities.Cidadao;

/*
 *
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 *
 */
public interface CidadaoRepository extends JpaRepository<Cidadao, Long>, CustomCidadaoRepository {

  /**
   * Returns a Cidadao whose card number matches a given value.
   *
   * @param numCC The given value.
   * @return The Cidadao.
   */
  @Query("SELECT c FROM Cidadao c WHERE c.numCC = :numCC")
  public Cidadao findByNumCC(@Param("numCC") int numCC);
}
