package pt.ul.fc.css.democracia2.repositories;

/*
 *
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 *
 * Custom Projeto Lei Repository that provides a method to support a Projeto Lei
 *
 */
public interface CustomProjetoLeiRepository {

  /**
   * Records the support of a cidadao with a given id to a Projeto Lei with a given id
   *
   * @param projId The given projeto lei id
   * @param cidId The given cidadao id
   */
  public boolean apoiarProjLei(long projId, long cidId);
}
