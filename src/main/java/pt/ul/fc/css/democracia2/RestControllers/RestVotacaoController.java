package pt.ul.fc.css.democracia2.RestControllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ul.fc.css.democracia2.dtos.VotacaoDTO;
import pt.ul.fc.css.democracia2.dtos.VotoDTO;
import pt.ul.fc.css.democracia2.services.VotacaoService;

@RestController()
@RequestMapping("api")
public class RestVotacaoController {

  @Autowired private VotacaoService votacaoService;

  @GetMapping("/votacoes")
  List<VotacaoDTO> listarVotacoes() {
    return votacaoService.listarVotacoes();
  }

  @GetMapping("/votacao/{votId}/defaultVote/{numCC}")
  int votoDefaut(@PathVariable Long votId, @PathVariable int numCC) {
    return votacaoService.getDefaultVote(votId, numCC);
  }

  @PostMapping("/votacao/{votId}/vote/{numCC}")
  ResponseEntity<?> vote(
      @PathVariable Long votId, @PathVariable int numCC, @RequestBody VotoDTO vote) {
    if (votacaoService.vote(votId, numCC, vote)) {
      return ResponseEntity.ok().body(null);
    } else {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }
}
