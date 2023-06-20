package pt.ul.fc.css.democracia2.handlers;

import jakarta.transaction.Transactional;
import pt.ul.fc.css.democracia2.entities.StatusProjLei;
import pt.ul.fc.css.democracia2.repositories.ProjetoLeiRepository;

/**
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 *     <p>Handler to close ProjetoLei which have passed the close date threshold
 */
public class FecharProjsLeiHandler {

  private ProjetoLeiRepository projRepo;

  public FecharProjsLeiHandler(ProjetoLeiRepository projRepo) {
    this.projRepo = projRepo;
  }

  /** Close ProjetoLei which have passed the close date treshold */
  @Transactional
  public void fecharProjsLei() {
    this.projRepo.closeProjsLei(StatusProjLei.FECHADO);
  }
}
