package pt.ul.fc.css.democracia2.services;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.ul.fc.css.democracia2.dtos.DelegadoDTO;
import pt.ul.fc.css.democracia2.entities.Delegado;
import pt.ul.fc.css.democracia2.entities.Tema;
import pt.ul.fc.css.democracia2.handlers.EscolherDelegadoHandler;

/**
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 */
@Component
public class DelegadoService {

  @Autowired private EscolherDelegadoHandler escolherDel;

  /**
   * Dado um número de CC dum cidadão, um tema e um delegado, associa esse delegado com esse tema a
   * esse cidadão
   *
   * @param numCC, numerro de CC do cidadão
   * @param tema
   * @param del, delegado
   */
  @Transactional
  public void escolherDelegado(int numCC, Tema tema, Delegado del) {
    escolherDel.setCidadaoCorrente(numCC);
    escolherDel.escolheDelegado(del, tema);
  }

  /**
   * Devolve a lista de DelegadoDTO associados ao cidadao corrente
   *
   * @return lista de DelegadoDTO
   */
  @Transactional
  public List<DelegadoDTO> getDelsEscolherDel() {
    List<DelegadoDTO> delegadosDTOList = new ArrayList<DelegadoDTO>();
    List<Delegado> delegadosList = escolherDel.getDelegados();
    for (Delegado d : delegadosList) {
      DelegadoDTO d2 = dtofy(d);
      delegadosDTOList.add(d2);
    }
    return delegadosDTOList;
  }

  /**
   * Dado um delegado, devolve um DelegadoDTO equivalente
   *
   * @param d, delegado
   * @return DelegadoDTO
   */
  private static DelegadoDTO dtofy(Delegado d) {
    DelegadoDTO d2 = new DelegadoDTO();
    d2.setId(d.getId());
    d2.setName(d.getName());
    d2.setNumCC(d.getNumCC());
    return d2;
  }
}
