package pt.ul.fc.css.democracia2.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pt.ul.fc.css.democracia2.entities.Cidadao;
import pt.ul.fc.css.democracia2.entities.Delegado;
import pt.ul.fc.css.democracia2.entities.Tema;

/*
 *
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 *
 * Implements the CustomCidadaoRepository interface
 *
 */
public class CustomCidadaoRepositoryImpl implements CustomCidadaoRepository {

  @Autowired EntityManagerFactory emf;

  @Override
  public void addDelegadoToCid(long cidId, long delId, long temaId) {

    EntityManager em = null;

    try {

      em = emf.createEntityManager();
      em.getTransaction().begin();

      Cidadao cid = em.find(Cidadao.class, cidId);
      Delegado del = em.find(Delegado.class, delId);
      Tema t = em.find(Tema.class, temaId);

      if (cid != null && del != null && t != null) {

        cid.addTemaDelegado(t, del);
      }

      em.getTransaction().commit();

    } catch (Exception e) {

      em.getTransaction().rollback();

    } finally {

      em.close();
    }
  }
}
