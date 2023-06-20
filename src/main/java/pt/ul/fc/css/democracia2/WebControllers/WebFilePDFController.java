package pt.ul.fc.css.democracia2.WebControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import pt.ul.fc.css.democracia2.services.ProjetoLeiService;

@Controller
public class WebFilePDFController {
	
	@Autowired private ProjetoLeiService projLeiService;
	
	public WebFilePDFController() {
		super();
	}
	
	@GetMapping("/consultarProjsLei/{id}/pdf/{filename}")
	public ResponseEntity<byte[]> visualizarPDF(@PathVariable String filename, @PathVariable Long id) {
		
		byte[] fileContent = projLeiService.getProjetoLei(id).get().getPdf().getFileData();
		 
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDisposition(ContentDisposition.attachment().filename(filename).build());
		
		return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
	}
}
