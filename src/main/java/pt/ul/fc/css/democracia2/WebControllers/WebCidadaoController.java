package pt.ul.fc.css.democracia2.WebControllers;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import pt.ul.fc.css.democracia2.dtos.CidadaoDTO;
import pt.ul.fc.css.democracia2.dtos.DelegadoDTO;
import pt.ul.fc.css.democracia2.entities.Delegado;
import pt.ul.fc.css.democracia2.entities.Tema;
import pt.ul.fc.css.democracia2.repositories.CidadaoRepository;
import pt.ul.fc.css.democracia2.repositories.DelegadoRepository;
import pt.ul.fc.css.democracia2.repositories.TemaRepository;
import pt.ul.fc.css.democracia2.services.CidadaoService;
import pt.ul.fc.css.democracia2.services.DelegadoService;
import pt.ul.fc.css.democracia2.services.TemaService;

@Controller
public class WebCidadaoController {

	@Autowired private CidadaoService cidService;
	@Autowired private TemaService temaService;
	@Autowired private CidadaoRepository cidRepo;
	@Autowired private DelegadoService delService;
	@Autowired private TemaRepository temaRepo;
	@Autowired private DelegadoRepository delRepo;
	
	public WebCidadaoController() {
		super();
	}
	
	
	// DASHBOARD
	
	@GetMapping("/{cc_number}/dashboard")
	public String dashboard(final Model model, @PathVariable("cc_number") int cc_number) {
		CidadaoDTO cid = cidService.obterCidadao(cc_number);
		
		if(cid == null) {
			return "login";
		}
		model.addAttribute("cidadao", cid);
		return "dashboard";
	}
	
	@PostMapping("/login")
	public String loginForm(@RequestParam("cc_number") Integer cc_number) {
		
		if (cc_number == null) {
			return "login";
		}
		
		return "redirect:/" + cc_number + "/dashboard";
	}
	
	
	@GetMapping("/")
	public String index() {
		return "login";
	}
	
	// ESCOLHER DELEGADO HANDLER
	@GetMapping("/{cc_number}/escolherDel")
	public String escolherDelegado(final Model model, @PathVariable("cc_number") int cc_number) {
		CidadaoDTO cid = cidService.obterCidadao(cc_number);
		
		model.addAttribute("cidadao", cid);
		model.addAttribute("temas", temaService.getTemasEscolherDel());
		model.addAttribute("delegados", delService.getDelsEscolherDel());
		return "escolherDel";
	}


	@PostMapping("/{cc_number}/escDel")
	  public String escDel(
	      final Model model,
	      @PathVariable("cc_number") int cc_number,
	      @RequestParam("tema") String temaID,
	      @RequestParam("delegado") String delID) {

		Tema t = temaRepo.findById(Long.valueOf(temaID)).get();
		Delegado d = delRepo.findById(Long.valueOf(delID)).get();
	    delService.escolherDelegado(cc_number, t, d);

	    return "redirect:/" + cc_number + "/dashboard";
	  }
}
