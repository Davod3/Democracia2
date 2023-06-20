package pt.ul.fc.css.democracia2.repositories;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ul.fc.css.democracia2.entities.ProjetoLei;
import pt.ul.fc.css.democracia2.entities.StatusProjLei;

/*
 *
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 *
 */
public interface ProjetoLeiRepository
    extends JpaRepository<ProjetoLei, Long>, CustomProjetoLeiRepository {

  /**
   * Returns a list of entities ProjetoLei in a given state.
   *
   * @param estado The given state.
   * @return List of ProjetoLei entities.
   */
  @Query("SELECT p FROM ProjetoLei p WHERE p.estado = :estado")
  public List<ProjetoLei> findByStatus(@Param("estado") StatusProjLei estado);

  /**
   * Updates all Projetos Lei to a given estado if their due date is passed.
   *
   * @param estado The given estado
   */
  @Transactional
  @Modifying
  @Query(
      "UPDATE ProjetoLei p SET p.estado = :estado WHERE p.estado <> :estado AND p.dataValidade <"
          + " CURRENT_TIMESTAMP")
  public void closeProjsLei(@Param("estado") StatusProjLei estado);
}
