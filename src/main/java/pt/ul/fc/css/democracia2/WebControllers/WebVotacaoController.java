package pt.ul.fc.css.democracia2.WebControllers;

import java.io.File;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import pt.ul.fc.css.democracia2.dtos.CidadaoDTO;
import pt.ul.fc.css.democracia2.dtos.ProjetoLeiDTO;
import pt.ul.fc.css.democracia2.dtos.VotacaoDTO;
import pt.ul.fc.css.democracia2.dtos.VotoDTO;
import pt.ul.fc.css.democracia2.services.CidadaoService;
import pt.ul.fc.css.democracia2.services.ProjetoLeiService;
import pt.ul.fc.css.democracia2.services.VotacaoService;

@Controller
public class WebVotacaoController {

	@Autowired private VotacaoService votService;
	@Autowired private CidadaoService cidService;
	
	// LISTAR VOTACOES HANDLER
	@GetMapping("/{cc_number}/listarVot")
	public String listarVot(final Model model, @PathVariable("cc_number") int cc_number) {
		
		CidadaoDTO cid = cidService.obterCidadao(cc_number);
		
		model.addAttribute("cidadao", cid);
		model.addAttribute("votList", votService.listarVotacoes());
		return "listarVot";
	}

	// CONSULTAR VOTACAO POR ID
	@GetMapping("/{cc_number}/listarVot/{id}")
	public String listarVotacao(final Model model, @PathVariable Long id, @PathVariable("cc_number") int cc_number) {
		Optional<VotacaoDTO> v = votService.getVotacao(id);
		CidadaoDTO cid = cidService.obterCidadao(cc_number);
		
		int defVote = votService.getDefaultVote(id, cc_number);
		
		String votoDel = "";
		
		if (defVote == -1) {
			votoDel = "Nenhum";
		} else if (defVote == 0) {
			votoDel = "Contra";
		} else if (defVote == 1) {
			votoDel = "A favor";
		}
		
		model.addAttribute("votoDel", votoDel);
		model.addAttribute("cidadao", cid);
		if (v.isPresent()) {
			model.addAttribute("votacao", v.get());
			return "votacao_detail";
		} else {
			return "listarVot";
		}
	}

	// VOTAR PROPOSTA
	@PostMapping("/{cc_number}/votarProposta/{id}")
	  public String votarProposta (
	      final Model model,
	      @PathVariable("cc_number") int cc_number,
	      @PathVariable("id") Long id,
	      @RequestParam("voto") int voto) {
		CidadaoDTO cid = cidService.obterCidadao(cc_number);
		model.addAttribute("cidadao", cid);
		VotoDTO vote = new VotoDTO();
		vote.setVoto(voto);
		boolean result = votService.vote(id, cc_number, vote);
	    
		if (result == false) {
			return "redirect:/" + cc_number + "/votarPropostaError";
		}
		
	    return "redirect:/" + cc_number + "/dashboard";
	  }
	
	
	@GetMapping("/{cc_number}/votarPropostaError")
	public String votarError(final Model model, @PathVariable("cc_number") int cc_number) {
		
		CidadaoDTO cid = cidService.obterCidadao(cc_number);
		model.addAttribute("cidadao", cid);
		
		return "votarPropostaError";
	}
}
