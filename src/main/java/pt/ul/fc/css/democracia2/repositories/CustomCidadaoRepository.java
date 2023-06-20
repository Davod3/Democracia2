package pt.ul.fc.css.democracia2.repositories;

/*
 *
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 *
 * Custom cidadao repository that provides a method to associate delegados and themes to cidadaos.
 *
 */
public interface CustomCidadaoRepository {

  /**
   * Associates a delegado with a given id and a theme with a given id to a cidadao with a given id
   *
   * @param cidId The given cidadao id.
   * @param delId The given delegado id.
   * @param temaId The given theme id.
   */
  public void addDelegadoToCid(long cidId, long delId, long temaId);
}
