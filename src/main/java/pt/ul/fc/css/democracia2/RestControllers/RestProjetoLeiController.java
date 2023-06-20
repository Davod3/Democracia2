package pt.ul.fc.css.democracia2.RestControllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ul.fc.css.democracia2.dtos.ProjetoLeiDTO;
import pt.ul.fc.css.democracia2.services.ProjetoLeiService;

@RestController()
@RequestMapping("api")
public class RestProjetoLeiController {

  @Autowired private ProjetoLeiService projetoLeiService;

  @GetMapping("/projetosLei")
  List<ProjetoLeiDTO> listarProjetosLei() {
    return projetoLeiService.listarProjsLei();
  }

  @GetMapping("/projetosLei/{id}")
  ResponseEntity<?> consultarProjetoLei(@PathVariable Long id) {
    Optional<ProjetoLeiDTO> proj = projetoLeiService.getProjetoLei(id);
    if (proj.isPresent()) {
      return ResponseEntity.ok().body(proj.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/projetosLei/{projId}/apoiar/{numCC}")
  ResponseEntity<?> apoiarProjetoLei(@PathVariable Long projId, @PathVariable int numCC) {
    if (projetoLeiService.apoiarProjetoLei(projId, numCC)) {
      return ResponseEntity.ok().body(null);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
