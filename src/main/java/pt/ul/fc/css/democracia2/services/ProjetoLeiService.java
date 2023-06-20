package pt.ul.fc.css.democracia2.services;

import jakarta.transaction.Transactional;
import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.ul.fc.css.democracia2.dtos.ProjetoLeiDTO;
import pt.ul.fc.css.democracia2.entities.ProjetoLei;
import pt.ul.fc.css.democracia2.entities.Tema;
import pt.ul.fc.css.democracia2.handlers.ApoiarProjLeiHandler;
import pt.ul.fc.css.democracia2.handlers.ApresentarProjLeiHandler;
import pt.ul.fc.css.democracia2.handlers.ConsultarProjsLeiHandler;

/**
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 */
@Component
public class ProjetoLeiService {

  @Autowired private ConsultarProjsLeiHandler consultarProjsLeiHandler;
  @Autowired private ApoiarProjLeiHandler apoiarProjetoleiHandler;
  @Autowired private ApresentarProjLeiHandler apresentarProjLeiHandler;

  /**
   * Devolve uma lista de projetos Lei abertos, em formato DTO
   *
   * @return lista ProjetoLeiDTO
   */
  @Transactional
  public List<ProjetoLeiDTO> listarProjsLei() {
    List<ProjetoLeiDTO> projetosDTOList = new ArrayList<ProjetoLeiDTO>();
    List<ProjetoLei> projetosList = consultarProjsLeiHandler.listarProjsLei();
    for (ProjetoLei p : projetosList) {
      ProjetoLeiDTO p2 = dtofy(p);
      projetosDTOList.add(p2);
    }
    return projetosDTOList;
  }

  /**
   * Dado um id, devolve um Optional<ProjetoLeiDTO> correspondente
   *
   * @param id
   * @return Optional<ProjetoLeiDTO>
   */
  public Optional<ProjetoLeiDTO> getProjetoLei(Long id) {
    return consultarProjsLeiHandler.consultarProjLei(id).map(ProjetoLeiService::dtofy);
  }

  /**
   * Dado um id dum projeto lei e um número de CC, adiciona o apoio desse cidadao a esse projeto lei
   *
   * @param id do projeto lei
   * @param numCC do cidadao
   * @return false se nao houver algum dos elementos ou se o cidadao já tiver apoiado esse projeto
   *     lei, senão true
   */
  @Transactional
  public boolean apoiarProjetoLei(Long id, int numCC) {
    Optional<ProjetoLei> projetoLei = consultarProjsLeiHandler.consultarProjLei(id);
    if (projetoLei.isPresent()) {
      apoiarProjetoleiHandler.setCidadaoCorrente(numCC);
      return apoiarProjetoleiHandler.apoiarProjLei(projetoLei.get());
    }
    return false;
  }

  /**
   * Dadas as informações dum Projeto lei, apresenta um novo projeto lei
   *
   * @param numCC, número de CC do delegado que apresenta
   * @param titulo do projeto lei
   * @param desc, descrição do projeto lei
   * @param pdf do projeto lei
   * @param data de validade
   * @param tema do projeto lei
   * @return true
   */
  @Transactional
  public boolean apresentarProjetoLei(
      int numCC, String titulo, String desc, File pdf, String data, Tema tema) {
    String pattern = "yyyy-MM-dd";

    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
      java.util.Date parsedDate = dateFormat.parse(data);
      Timestamp date = new Timestamp(parsedDate.getTime());

      apresentarProjLeiHandler.setDelegadoCorrente(numCC);
      apresentarProjLeiHandler.apresentarProjLei(titulo, desc, pdf, date, tema);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return true;
  }

  // Convert ProjetoLei to ProjetoLeiDTO

  /**
   * Dado um projeto lei, devolve um ProjetoLeiDTO equivalente
   *
   * @param p, projeto lei
   * @return ProjetoLeiDTO
   */
  private static ProjetoLeiDTO dtofy(ProjetoLei p) {
    ProjetoLeiDTO p2 = new ProjetoLeiDTO();
    p2.setId(p.getId());
    p2.setTitulo(p.getTitulo());
    p2.setDescricao(p.getDescricao());
    p2.setPdf(FilePDFService.dtofy(p.getPDF()));
    p2.setDataValidade(p.getDataValidade().toString());
    p2.setTema(p.getTema().getTipo());
    p2.setDelegadoProponente(p.getDelegadoProponente().getName());
    p2.setEstado(p.getEstado());
    return p2;
  }
}
