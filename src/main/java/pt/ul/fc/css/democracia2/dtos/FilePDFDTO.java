package pt.ul.fc.css.democracia2.dtos;

import org.springframework.stereotype.Component;

@Component
public class FilePDFDTO {

  private String filename;

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public byte[] getFileData() {
    return fileData;
  }

  public void setFileData(byte[] fileData) {
    this.fileData = fileData;
  }

  private byte[] fileData;
}
