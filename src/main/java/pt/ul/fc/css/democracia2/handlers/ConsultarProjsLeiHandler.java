package pt.ul.fc.css.democracia2.handlers;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import pt.ul.fc.css.democracia2.entities.ProjetoLei;
import pt.ul.fc.css.democracia2.entities.StatusProjLei;
import pt.ul.fc.css.democracia2.repositories.ProjetoLeiRepository;

/**
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 *     <p>Handler for consulting Projetos de lei
 */
@Component
public class ConsultarProjsLeiHandler {

  private ProjetoLeiRepository projLeiRepo;

  public ConsultarProjsLeiHandler(ProjetoLeiRepository projLeiRepo) {
    this.projLeiRepo = projLeiRepo;
  }

  /**
   * Returns a list of all the open ProjetoLei
   *
   * @return ProjetoLei list
   */
  public List<ProjetoLei> listarProjsLei() {
    return projLeiRepo.findByStatus(StatusProjLei.ABERTO);
  }

  /**
   * Returns a ProjetoLei with a given id
   *
   * @param id: given id
   * @return ProjetoLei
   */
  public Optional<ProjetoLei> consultarProjLei(Long id) {
    return projLeiRepo.findById(id);
  }
}
