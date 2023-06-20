package democracia2.javafx.services;

public class PersonDTO {

  public Long id;
  public String name;
  public String numCC;
  public boolean cidadao;

  public PersonDTO(Long id, String name, String numCC, boolean cidadao) {

    this.id = id;
    this.name = name;
    this.numCC = numCC;
    this.cidadao = cidadao;
  }
}
