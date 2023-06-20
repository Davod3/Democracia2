package pt.ul.fc.css.democracia2.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import org.springframework.lang.NonNull;

/**
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 */
@Embeddable
public class FilePDF {

  @NonNull private String filename;

  @NonNull @Lob private byte[] fileData;

  protected FilePDF() {}

  public FilePDF(File pdf) {
    this.filename = pdf.getName();
    // Get fileData

    FileInputStream fis = null;
    try {
      fis = new FileInputStream(pdf);
      this.fileData = fis.readAllBytes();
    } catch (IOException e) {
      System.out.println("Ficheiro não foi encontrado");
    } finally {
      if (fis != null) {
        try {
          fis.close();
        } catch (IOException e) {
          System.out.println("Não foi possível ler o ficheiro");
        }
      }
    }
  }

  public String getFilename() {
    return filename;
  }

  public byte[] getFileData() {
    return fileData;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(fileData);
    result = prime * result + Objects.hash(filename);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof FilePDF)) return false;
    FilePDF other = (FilePDF) obj;
    return Arrays.equals(fileData, other.fileData) && Objects.equals(filename, other.filename);
  }
}
