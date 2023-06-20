package pt.ul.fc.css.democracia2.RestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ul.fc.css.democracia2.dtos.CidadaoDTO;
import pt.ul.fc.css.democracia2.services.CidadaoService;

@RestController()
@RequestMapping("api")
public class RestCidadaoController {

  @Autowired CidadaoService cidadaoService;

  @GetMapping("/obterCidadao/{numCC}")
  ResponseEntity<CidadaoDTO> obterCidadao(@PathVariable int numCC) {
    CidadaoDTO cid = cidadaoService.obterCidadao(numCC);
    if (cid != null) {
      return ResponseEntity.ok().body(cid);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
