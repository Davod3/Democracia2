package pt.ul.fc.css.democracia2.services;

import org.springframework.stereotype.Component;
import pt.ul.fc.css.democracia2.dtos.FilePDFDTO;
import pt.ul.fc.css.democracia2.entities.FilePDF;

/**
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 */
@Component
public class FilePDFService {

  /**
   * Dado um FilePDF, devolve um FilePDFDTO equivalente
   *
   * @param f1, FilePDF
   * @return FilePDFDTO equivalente
   */
  public static FilePDFDTO dtofy(FilePDF f1) {
    FilePDFDTO f2 = new FilePDFDTO();
    f2.setFilename(f1.getFilename());
    f2.setFileData(f1.getFileData());

    return f2;
  }
}
