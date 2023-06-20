package pt.ul.fc.css.democracia2.handlers;

import java.util.List;

import org.springframework.stereotype.Component;

import pt.ul.fc.css.democracia2.entities.Cidadao;
import pt.ul.fc.css.democracia2.entities.Delegado;
import pt.ul.fc.css.democracia2.entities.Tema;
import pt.ul.fc.css.democracia2.repositories.CidadaoRepository;
import pt.ul.fc.css.democracia2.repositories.DelegadoRepository;
import pt.ul.fc.css.democracia2.repositories.TemaRepository;

/**
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 *     <p>Handler to choose a Delegado for a Citizen
 */
@Component
public class EscolherDelegadoHandler {

  private DelegadoRepository delRepo;
  private CidadaoRepository cidRepo;
  private TemaRepository temaRepo;

  private Cidadao cidadaoCorrente;

  public EscolherDelegadoHandler(
      DelegadoRepository delRepo, CidadaoRepository cidRepo, TemaRepository temaRepo) {

    this.delRepo = delRepo;
    this.cidRepo = cidRepo;
    this.temaRepo = temaRepo;
  }

  /**
   * Assigns a current Cidadao, given a CC number
   *
   * @param numCC: CC number
   */
  public void setCidadaoCorrente(int numCC) {

    Cidadao c = this.cidRepo.findByNumCC(numCC);
    this.cidadaoCorrente = c;
  }

  /**
   * Returns all the Delegado
   *
   * @return Delegado list
   */
  public List<Delegado> getDelegados() {
    return this.delRepo.findAll();
  }

  /**
   * Returns all the Tema
   *
   * @return Tema list
   */
  public List<Tema> getTemas() {
    return this.temaRepo.findAll();
  }

  /**
   * Assign a given Delegado to the current Cidadao, by a given Tema
   *
   * @param d: Delegado
   * @param t: Tema
   * @requires this.cidadaoCorrente != null
   * @requires d != null
   * @requires t != null
   */
  public void escolheDelegado(Delegado d, Tema t) {
    this.cidRepo.addDelegadoToCid(this.cidadaoCorrente.getId(), d.getId(), t.getId());
  }
}
