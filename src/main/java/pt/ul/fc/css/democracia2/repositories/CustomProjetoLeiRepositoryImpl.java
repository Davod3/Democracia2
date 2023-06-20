package pt.ul.fc.css.democracia2.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import pt.ul.fc.css.democracia2.entities.Cidadao;
import pt.ul.fc.css.democracia2.entities.ProjetoLei;
import pt.ul.fc.css.democracia2.entities.StatusProjLei;
import pt.ul.fc.css.democracia2.entities.Tema;
import pt.ul.fc.css.democracia2.entities.Votacao;

/*
 *
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 *
 * Implements the Custom Projeto Lei interface
 *
 */
public class CustomProjetoLeiRepositoryImpl implements CustomProjetoLeiRepository {

  @Autowired private EntityManagerFactory emf;
  private final long MIN_SUPPORT_THRESHOLD = 10000;
  private final int MIN_EXPIRATION_DAYS = 15;
  private final int MAX_EXPIRATION_MONTHS = 2;

  @Override
  public boolean apoiarProjLei(long projId, long cidId) {

    EntityManager em = null;
    boolean result = false;

    try {

      em = this.emf.createEntityManager();
      em.getTransaction().begin();

      ProjetoLei proj = em.find(ProjetoLei.class, projId);
      Cidadao cidCorrente = em.find(Cidadao.class, cidId);

      if (proj != null) {

        if (cidCorrente != null) {

          result = proj.addApoiante(cidCorrente);

          long numApoiantes = proj.getListaApoiantes().size();

          if (numApoiantes >= MIN_SUPPORT_THRESHOLD && proj.getEstado() == StatusProjLei.ABERTO) {

            // Change into votacao

            Timestamp closeDate = getCloseDate(proj.getDataValidade());
            Tema t = proj.getTema();

            Votacao v = new Votacao(closeDate, t);

            // Set remaining fields
            v.addVotoDelegado(proj.getDelegadoProponente(), 1);

            em.persist(v);
            proj.fecharProjeto();
          }

          em.persist(proj);
        }
      }

      em.getTransaction().commit();

    } catch (Exception e) {

      em.getTransaction().rollback();

    } finally {

      em.close();
      return result;
    }
  }

  /**
   * Calculates the new close date of the votacao, based on the old close date of the projeto lei.
   *
   * @param closeDate The close date of the projeto lei that originated the votacao.
   * @return The close date of the new votacao.
   */
  private Timestamp getCloseDate(Timestamp closeDate) {

    long daysToEnd =
        ChronoUnit.DAYS.between(LocalDate.now(), closeDate.toLocalDateTime().toLocalDate());

    long monthsToEnd =
        ChronoUnit.MONTHS.between(LocalDate.now(), closeDate.toLocalDateTime().toLocalDate());

    if (daysToEnd < MIN_EXPIRATION_DAYS) {

      return Timestamp.valueOf(
          closeDate.toLocalDateTime().plusDays(MIN_SUPPORT_THRESHOLD - daysToEnd));

    } else if (monthsToEnd > MAX_EXPIRATION_MONTHS) {

      return Timestamp.valueOf(
          closeDate.toLocalDateTime().minusMonths(monthsToEnd - MAX_EXPIRATION_MONTHS));
    } else {

      return closeDate;
    }
  }
}
