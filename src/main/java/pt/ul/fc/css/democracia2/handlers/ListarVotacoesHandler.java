package pt.ul.fc.css.democracia2.handlers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import pt.ul.fc.css.democracia2.entities.ProjetoLei;
import pt.ul.fc.css.democracia2.entities.StatusVotacao;
import pt.ul.fc.css.democracia2.entities.Votacao;
import pt.ul.fc.css.democracia2.repositories.VotacaoRepository;

/*
 *
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 *
 */
@Component
public class ListarVotacoesHandler {

  private VotacaoRepository votRepo;

  public ListarVotacoesHandler(VotacaoRepository votRepo) {
    this.votRepo = votRepo;
  }

  /**
   * Returns a list with currently active Votacoes
   *
   * @return The list of Votacoes
   */
  public List<Votacao> listarVotacoesCorrentes() {
    return votRepo.findByStatus(StatusVotacao.ABERTA);
  }
  
  /**
   * Returns a ProjetoLei with a given id
   *
   * @param id: given id
   * @return ProjetoLei
   */
  public Optional<Votacao> consultarVotacao(Long id) {
    return votRepo.findById(id);
  }
}
