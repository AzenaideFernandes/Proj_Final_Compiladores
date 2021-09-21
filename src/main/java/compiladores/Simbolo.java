package compiladores;

public class Simbolo {
  private String nome;
  private int tipo;

  public Simbolo(int tipo, String nome){
    this.tipo = tipo;
    this.nome = nome;
  }

  public String getNome() {
    return this.nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public int getTipo() {
    return this.tipo;
  }

  public void setTipo(int tipo) {
    this.tipo = tipo;
  }

  
}
