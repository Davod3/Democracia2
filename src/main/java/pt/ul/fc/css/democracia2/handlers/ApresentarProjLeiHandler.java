package pt.ul.fc.css.democracia2.handlers;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.stereotype.Component;
import pt.ul.fc.css.democracia2.entities.Delegado;
import pt.ul.fc.css.democracia2.entities.ProjetoLei;
import pt.ul.fc.css.democracia2.entities.Tema;
import pt.ul.fc.css.democracia2.repositories.DelegadoRepository;
import pt.ul.fc.css.democracia2.repositories.ProjetoLeiRepository;
import pt.ul.fc.css.democracia2.repositories.TemaRepository;

/**
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 *     <p>Handler for presenting a new ProjetoLei
 */
@Component
public class ApresentarProjLeiHandler {

  private TemaRepository temaRepo;
  private ProjetoLeiRepository projRepo;
  private DelegadoRepository delRepo;

  private Delegado delegadoCorrente;

  public ApresentarProjLeiHandler(
      DelegadoRepository delRepo, ProjetoLeiRepository projRepo, TemaRepository temaRepo) {
    this.delRepo = delRepo;
    this.projRepo = projRepo;
    this.temaRepo = temaRepo;
  }

  /**
   * Set a current Delegado, given a CC number
   *
   * @param numCC: CC number
   */
  public void setDelegadoCorrente(int numCC) {
    Delegado del = this.delRepo.findByNumCC(numCC);

    if (del != null) {
      this.delegadoCorrente = del;
    }
  }

  /**
   * Returns all the open Tema
   *
   * @return open Tema list
   */
  public List<Tema> getTemas() {
    return this.temaRepo.findAll();
  }

  /**
   * Present a new ProjetoLei, given all the atributes
   *
   * @param titulo: title
   * @param descricao: description
   * @param pdf: pdf file name
   * @param date: close date
   * @param tema: theme
   * @return new ProjetoLei
   */
  public ProjetoLei apresentarProjLei(
      String titulo, String descricao, File pdf, Timestamp date, Tema tema) {
    ProjetoLei proj = new ProjetoLei(titulo, descricao, pdf, date, tema, delegadoCorrente);
    projRepo.save(proj);
    return proj;
  }
}
