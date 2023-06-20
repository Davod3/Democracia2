package pt.ul.fc.css.democracia2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.ul.fc.css.democracia2.dtos.CidadaoDTO;
import pt.ul.fc.css.democracia2.entities.Cidadao;
import pt.ul.fc.css.democracia2.entities.Delegado;
import pt.ul.fc.css.democracia2.repositories.CidadaoRepository;
import pt.ul.fc.css.democracia2.repositories.DelegadoRepository;

/**
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 */
@Component
public class CidadaoService {

  @Autowired private CidadaoRepository cidRepo;
  @Autowired private DelegadoRepository delRepo;

  /**
   * Dado um número de CC, se existir um cidadão com esse número devolve um cidadaoDTO equivalente
   *
   * @param numCC, número de CC
   * @return cidadaoDTO equivalente
   */
  public CidadaoDTO obterCidadao(int numCC) {
    CidadaoDTO cidDTO = new CidadaoDTO();
    Delegado del = delRepo.findByNumCC(numCC);
    if (del == null) {
      Cidadao cid = cidRepo.findByNumCC(numCC);
      if (cid == null) {
        return null;
      } else {
        cidDTO = dtofy(cid);
        cidDTO.setCidadao(true);
      }
    } else {
      cidDTO = dtofy(del);
      cidDTO.setCidadao(false);
    }
    return cidDTO;
  }

  /**
   * Dado um cidadao, devolve um CidadaoDTO equivalente
   *
   * @param c, cidadao
   * @return CidadaoDTO equivalente
   */
  private static CidadaoDTO dtofy(Cidadao c) {
    CidadaoDTO c2 = new CidadaoDTO();
    c2.setId(c.getId());
    c2.setName(c.getName());
    c2.setNumCC(c.getNumCC());
    return c2;
  }
}
