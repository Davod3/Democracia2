package pt.ul.fc.css.democracia2.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ul.fc.css.democracia2.entities.StatusVotacao;
import pt.ul.fc.css.democracia2.entities.Votacao;

/*
 *
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 *
 */
public interface VotacaoRepository extends JpaRepository<Votacao, Long>, CustomVotacaoRepository {

  /**
   * Returns a list of Votacao entities that match a given state.
   *
   * @param estado The given state.
   * @return A list of Votacao entities.
   */
  @Query("SELECT v FROM Votacao v WHERE v.estado = :estado")
  public List<Votacao> findByStatus(@Param("estado") StatusVotacao estado);
}
