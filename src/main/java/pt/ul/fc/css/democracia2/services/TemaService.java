package pt.ul.fc.css.democracia2.services;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.ul.fc.css.democracia2.dtos.TemaDTO;
import pt.ul.fc.css.democracia2.entities.Tema;
import pt.ul.fc.css.democracia2.handlers.ApresentarProjLeiHandler;
import pt.ul.fc.css.democracia2.handlers.EscolherDelegadoHandler;

/**
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 */
@Component
public class TemaService {

  @Autowired private ApresentarProjLeiHandler apresentarProjLeiHandler;
  @Autowired private EscolherDelegadoHandler escolherDel;

  /**
   * Devolve uma lista de todos os temas em fromato DTO
   *
   * @return lista TemaDTO
   */
  @Transactional
  public List<TemaDTO> getTemas() {
    List<TemaDTO> temasDTOList = new ArrayList<TemaDTO>();
    List<Tema> temasList = apresentarProjLeiHandler.getTemas();
    for (Tema t : temasList) {
      TemaDTO t2 = dtofy(t);
      temasDTOList.add(t2);
    }
    return temasDTOList;
  }

  /**
   * Devolve os temas associados ao delegado corrente
   *
   * @return lista de TemaDTO
   */
  @Transactional
  public List<TemaDTO> getTemasEscolherDel() {
    List<TemaDTO> temasDTOList = new ArrayList<TemaDTO>();
    List<Tema> temasList = escolherDel.getTemas();
    for (Tema t : temasList) {
      TemaDTO t2 = dtofy(t);
      temasDTOList.add(t2);
    }
    return temasDTOList;
  }

  /**
   * Dado um tema devolve um TemaDTO equivalente
   *
   * @param t, tema
   * @return TemaDTO
   */
  private static TemaDTO dtofy(Tema t) {
    TemaDTO t2 = new TemaDTO();
    t2.setId(Long.toString(t.getId()));
    t2.setPai(t.getPai());
    t2.setTipo(t.getTipo());

    return t2;
  }
}
