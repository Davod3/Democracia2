package pt.ul.fc.css.democracia2.WebControllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import pt.ul.fc.css.democracia2.entities.ProjetoLei;
import pt.ul.fc.css.democracia2.repositories.TemaRepository;
import pt.ul.fc.css.democracia2.services.CidadaoService;
import pt.ul.fc.css.democracia2.services.ProjetoLeiService;
import pt.ul.fc.css.democracia2.services.TemaService;

@Controller
public class WebProjetoLeiController {

  @Autowired private ProjetoLeiService projLeiService;
  @Autowired private CidadaoService cidService;
  @Autowired private TemaService temaService;
  @Autowired private TemaRepository temaRepo;

  public WebProjetoLeiController() {
    super();
  }

  // APRESENTAR PROJETO LEI HANDLER

  @GetMapping("/{cc_number}/apresentarProjLei")
  public String apresentarProjLei(final Model model, @PathVariable("cc_number") int cc_number) {
    // model.addAttribute("temasList", projLeiService.listarTemas());
    CidadaoDTO cid = cidService.obterCidadao(cc_number);

    if (cid == null) {
      return "login";
    } else if (cid.isCidadao()) {
      return "redirect:/" + cc_number + "/dashboard";
    }
    model.addAttribute("cidadao", cid);
    model.addAttribute("projetoLei", new ProjetoLei());
    model.addAttribute("temas", temaService.getTemas());
    return "apresentarProjLei";
  }

  @PostMapping("/{cc_number}/apresProjLei")
  public String apresProjLei(
      final Model model,
      @PathVariable("cc_number") int cc_number,
      @RequestParam("titulo") String titulo,
      @RequestParam("descricao") String descricao,
      @RequestParam("tema") String temaID,
      @RequestParam("validade") String data,
      @RequestParam("pdf") MultipartFile pdf) {

    if (titulo.isEmpty() || descricao.isEmpty() || data.isEmpty() || pdf.isEmpty()) {
      model.addAttribute(
          "error", "Falha ao apresentar Projeto de Lei! Preencha todos os campos necessários!");
      return "apresentarProjLei";
    }

    File f = convertMultiplepartFileToFile(pdf);

    projLeiService.apresentarProjetoLei(
        cc_number, titulo, descricao, f, data, temaRepo.findById(Long.valueOf(temaID)).get());

    return "redirect:/" + cc_number + "/dashboard";
  }

  private File convertMultiplepartFileToFile(MultipartFile pdf) {
    try {

      String fileName = pdf.getOriginalFilename();

      System.out.println(fileName);

      String[] splitFileName = fileName.split("\\.");

      File file = File.createTempFile(splitFileName[0], "." + splitFileName[1]);

      try (FileOutputStream fos = new FileOutputStream(file)) {
        fos.write(pdf.getBytes());
      }
      return file;
    } catch (IOException e) {
      return null;
    }
  }

  // CONSULTAR PROJETOS LEI HANDLER
  @GetMapping("/{cc_number}/consultarProjsLei")
  public String consultarProjsLei(final Model model, @PathVariable("cc_number") int cc_number) {
    CidadaoDTO cid = cidService.obterCidadao(cc_number);
    model.addAttribute("cidadao", cid);
    model.addAttribute("projetoLeiList", projLeiService.listarProjsLei());
    return "consultarProjsLei";
  }

  // CONSULTAR PROJETO POR ID
  @GetMapping("/{cc_number}/consultarProjsLei/{id}")
  public String consultarProjLei(
      final Model model, @PathVariable("id") Long id, @PathVariable("cc_number") int cc_number) {
    Optional<ProjetoLeiDTO> p = projLeiService.getProjetoLei(id);
    CidadaoDTO cid = cidService.obterCidadao(cc_number);
    model.addAttribute("cidadao", cid);
    if (p.isPresent()) {
      model.addAttribute("projetoLei", p.get());
      return "projetoLei_detail";
    } else {
      return "consultarProjsLei";
    }
  }

  // APOIAR PROJETOS LEI HANDLER
  @PostMapping("/{cc_number}/consultarProjsLei/{id}")
  public String apoiarProjetoLei(
      final Model model, @PathVariable("cc_number") int cc_number, @PathVariable("id") Long id) {

    boolean apoioRegistado = projLeiService.apoiarProjetoLei(id, cc_number);

    if (!apoioRegistado) {

      model.addAttribute("numcc", cc_number);
      return "falhaApoio";
    }

    return "redirect:/" + cc_number + "/dashboard";
  }
}
