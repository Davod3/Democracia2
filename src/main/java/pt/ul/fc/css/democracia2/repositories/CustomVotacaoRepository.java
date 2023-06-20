package pt.ul.fc.css.democracia2.repositories;

import pt.ul.fc.css.democracia2.entities.Cidadao;

/*
 *
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 *
 * A Custom Votacao Repository that provides a method to add a vote to a Votacao
 *
 */
public interface CustomVotacaoRepository {

  /**
   * Sets the vote of a given cidadao on a votacao with a given id to a given option
   *
   * @param option The given option
   * @param votacaoId The given votacao id
   * @param cid The given cidadao
   * @requires option == 1 || option == 0
   */
  public boolean votarEmVotacao(int option, long votacaoId, Cidadao cid);
}
