package pt.ul.fc.css.democracia2.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.ul.fc.css.democracia2.dtos.VotacaoDTO;
import pt.ul.fc.css.democracia2.dtos.VotoDTO;
import pt.ul.fc.css.democracia2.entities.Votacao;
import pt.ul.fc.css.democracia2.handlers.ListarVotacoesHandler;
import pt.ul.fc.css.democracia2.handlers.VotarPropostaHandler;

/**
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 */
@Component
public class VotacaoService {

  @Autowired private ListarVotacoesHandler listarVotacoesHandler;
  @Autowired private VotarPropostaHandler votarPropostaHandler;

  /**
   * Devolve uma lista de VotacaoDTO com todas as votacoes em curso
   *
   * @return lista de VotacaoDTO
   */
  public List<VotacaoDTO> listarVotacoes() {
    List<VotacaoDTO> votacaoListDTO = new ArrayList<VotacaoDTO>();
    List<Votacao> votacaoList = listarVotacoesHandler.listarVotacoesCorrentes();
    for (Votacao v : votacaoList) {
      VotacaoDTO v2 = dtofy(v);
      votacaoListDTO.add(v2);
    }
    return votacaoListDTO;
  }

  /**
   * Dado um id de votação e um número de CC dum cidadão, se existirem os dois elementos e o cidadão
   * tiver um delegado associado a essa votacao, devolve o voto desse delegado
   *
   * @param votId, id da votação
   * @param numCC, número de CC
   * @return o voto associado ao delegado, ou -1
   */
  public int getDefaultVote(Long votId, int numCC) {
    if (votarPropostaHandler.registerCidadao(numCC) != 1) {
      return -1;
    }
    votarPropostaHandler.selectVotacao(votId);
    return votarPropostaHandler.defaultVote();
  }

  /**
   * Dado um id de votação, um número de CC dum cidadão e um VotoDTO, se existir essa votação e esse
   * cidadão e o cidadão não tiver votado nessa votação devolve true, senão devolve false
   *
   * @param votId, id da votação
   * @param numCC, numero de cidadão
   * @param vote, votoDTO
   * @return true ou false
   */
  public boolean vote(Long votId, int numCC, VotoDTO vote) {
    if (votarPropostaHandler.registerCidadao(numCC) != 1) {
      return false;
    }
    votarPropostaHandler.selectVotacao(votId);
    return votarPropostaHandler.votar(vote.getVoto());
  }

  /**
   * Dada uma votação, devolve um VotacaoDTO equivalente
   *
   * @param v, votação
   * @return VotacaoDTO equivalente
   */
  private static VotacaoDTO dtofy(Votacao v) {
    VotacaoDTO v2 = new VotacaoDTO();
    v2.setId(v.getId());
    v2.setDataFecho(v.getDataFecho());
    v2.setTema(v.getTema());
    v2.setNumVotosAtivosSIM(v.getNumVotosAtivosSIM());
    v2.setNumVotosAtivosNAO(v.getNumVotosAtivosNAO());
    v2.setListaDelegados(v.getListaDelegados());
    v2.setListaCidadaosVotantes(v.getListaCidadaosVotantes());
    v2.setEstado(v.getEstado());
    return v2;
  }

  /**
   * Dado um id devolve um Optional<VotacaoDTO> da votacao com esse id
   *
   * @param id da votação
   * @return Optional da votação
   */
  public Optional<VotacaoDTO> getVotacao(Long id) {
    return listarVotacoesHandler.consultarVotacao(id).map(VotacaoService::dtofy);
  }
}
