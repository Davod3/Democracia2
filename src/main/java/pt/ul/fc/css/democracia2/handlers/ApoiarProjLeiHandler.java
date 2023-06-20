package pt.ul.fc.css.democracia2.handlers;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.stereotype.Component;
import pt.ul.fc.css.democracia2.entities.Cidadao;
import pt.ul.fc.css.democracia2.entities.ProjetoLei;
import pt.ul.fc.css.democracia2.entities.Tema;
import pt.ul.fc.css.democracia2.entities.Votacao;
import pt.ul.fc.css.democracia2.repositories.CidadaoRepository;
import pt.ul.fc.css.democracia2.repositories.ProjetoLeiRepository;
import pt.ul.fc.css.democracia2.repositories.VotacaoRepository;

/**
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 *     <p>Handler for the supporting a ProjetoLei use case
 */
@Component
public class ApoiarProjLeiHandler {

  private Cidadao cidCorrente;
  private CidadaoRepository cidRepo;
  private ProjetoLeiRepository projRepo;
  private VotacaoRepository votRepo;
  private static final long MIN_SUPPORT_THRESHOLD = 10000;
  private static final int MIN_EXPIRATION_DAYS = 15;
  private static final int MAX_EXPIRATION_MONTHS = 2;

  public ApoiarProjLeiHandler(
      CidadaoRepository cidRepo, ProjetoLeiRepository projRepo, VotacaoRepository votRepo) {

    this.cidRepo = cidRepo;
    this.projRepo = projRepo;
    this.votRepo = votRepo;
  }

  /**
   * Sets a current Cidadao, given a CC number
   *
   * @param numCC: CC number
   * @return Cidadao
   */
  public Cidadao setCidadaoCorrente(int numCC) {

    Cidadao c = this.cidRepo.findByNumCC(numCC);
    this.cidCorrente = c;
    return c;
  }

  /**
   * Returns all the open ProjetoLei
   *
   * @return ProjetoLei list
   */
  public List<ProjetoLei> getProjsLei() {
    return new ConsultarProjsLeiHandler(this.projRepo).listarProjsLei();
  }

  /**
   * Sets the support of the current Cidadao to a given ProjetoLei
   *
   * @param proj:ProjetoLei
   * @requires this.cidCorrente != null
   * @requires proj != null
   * @return true if the operation was successful
   */
  public boolean apoiarProjLei(ProjetoLei proj) {

    return this.projRepo.apoiarProjLei(proj.getId(), this.cidCorrente.getId());
  }

  /*
   * Method used to test support thresholds
   */
  public long apoiarProjLeiTeste(ProjetoLei proj) {

    proj.addApoiante(this.cidCorrente);

    long numApoiantes = 10000; // Simulate the moment before changing into votacao
    long id = 0;

    if (numApoiantes >= MIN_SUPPORT_THRESHOLD) {

      // Change into votacao
      Timestamp closeDate = getCloseDate(proj.getDataValidade());
      Tema t = proj.getTema();

      Votacao v = new Votacao(closeDate, t);

      // Set remaining fields
      v.addVotoDelegado(proj.getDelegadoProponente(), 1);

      votRepo.save(v);
      proj.fecharProjeto();

      id = v.getId();
    }
    this.projRepo.save(proj);
    return id;
  }

  /**
   * Calculates the new close date of the votacao, based on the old close date of the projeto lei.
   *
   * @param closeDate The close date of the projeto lei that originated the votacao.
   * @return The close date of the new votacao.
   */
  private Timestamp getCloseDate(Timestamp closeDate) {

    long daysToEnd =
        ChronoUnit.DAYS.between(LocalDate.now(), closeDate.toLocalDateTime().toLocalDate());

    long monthsToEnd =
        ChronoUnit.MONTHS.between(LocalDate.now(), closeDate.toLocalDateTime().toLocalDate());

    if (daysToEnd < MIN_EXPIRATION_DAYS) {

      return Timestamp.valueOf(
          closeDate.toLocalDateTime().plusDays(MIN_SUPPORT_THRESHOLD - daysToEnd));

    } else if (monthsToEnd > MAX_EXPIRATION_MONTHS) {

      return Timestamp.valueOf(
          closeDate.toLocalDateTime().minusMonths(monthsToEnd - MAX_EXPIRATION_MONTHS));
    } else {

      return closeDate;
    }
  }
}
