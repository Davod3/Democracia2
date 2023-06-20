package pt.ul.fc.css.democracia2.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pt.ul.fc.css.democracia2.entities.Cidadao;
import pt.ul.fc.css.democracia2.entities.Delegado;
import pt.ul.fc.css.democracia2.entities.Votacao;

/*
 *
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 *
 * Implements the Custom Votacao Repository interface.
 *
 */
public class CustomVotacaoRepositoryImpl implements CustomVotacaoRepository {

  @Autowired private EntityManagerFactory emf;

  @Override
  public boolean votarEmVotacao(int option, long votacaoId, Cidadao cid) {

    EntityManager em = null;

    boolean result = false;

    try {

      em = this.emf.createEntityManager();
      em.getTransaction().begin();

      Votacao v = em.find(Votacao.class, votacaoId);

      if (v != null && cid != null) {

        if (cid instanceof Delegado) {

          // Record public vote

          result = v.addVotoDelegado((Delegado) cid, option);

        } else {

          // Record private vote
          result = v.addVotoAtivo(cid, option);
        }

        em.persist(v);

        em.getTransaction().commit();
      }

    } catch (Exception e) {

      em.getTransaction().rollback();

    } finally {

      em.close();

      return result;
    }
  }
}
