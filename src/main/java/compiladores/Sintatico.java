package compiladores;

public class Sintatico {

  private LexScanner scan;
  private String simbolo;
  private Token token; 

  public Sintatico(String arq){
    scan = new LexScanner(arq);
  }

  public void analise() {
    obtemSimbolo();
    programa();
    if (simbolo.equals("")) {
      System.out.println("Tudo Certo !!!");      
    } else {
      throw new RuntimeException("Erro Sintático esperado fim de cadeia");
    }
  }

  private void obtemSimbolo(){
    Token token = scan.nextToken();
    simbolo = "";
    if (token != null) {
      simbolo = token.getTermo();
      System.out.println(simbolo);
    }
  }

  private void programa(){
    if (simbolo.equals("program")) {
      obtemSimbolo();
      
       if (token != null && token.getTipo() == Token.IDENT) {
        obtemSimbolo();
        corpo(); 
        if (simbolo.equals(".")) {
          obtemSimbolo();
       
          } else {
            throw new RuntimeException("Erro Sintático esperado '.'"); 
           }         
        }  
    } else {
        throw new RuntimeException("Erro Sintático esperado 'program'"); 
    }
    
  }
    
  private void corpo(){
    dc();
    if (token != null && token.getTipo() == Token.IDENT) {
      obtemSimbolo();
    //if (simbolo.equals("begin")) {
     // obtemSimbolo();
      comandos();
      if (token != null && token.getTipo() == Token.IDENT) {
        obtemSimbolo();

     // } if (simbolo.equals("end")){
        //obtemSimbolo();
     // }
     }if (token != null && token.getTipo() == Token.IDENT) {
        obtemSimbolo();
      }
    }
  }

  private void dc(){
    dc_v();
      mais_dc();   
  }

  private void mais_dc(){
    if (simbolo.equals(";")) {
      obtemSimbolo();
      dc();
    }   
  }

  private void dc_v(){
    tipo_var();
    if(simbolo.equals(":")){
      obtemSimbolo();
      variaveis();
    
    } else {
     throw new RuntimeException("Erro Sintático esperado ':'");        
    } 
  }

  private void tipo_var(){
    if (token != null && token.getTipo() == Token.IDENT || token.getTipo() == Token.IDENT ) {
      obtemSimbolo();
    //if(simbolo.equals("real") || simbolo.equals("integer")){
      //obtemSimbolo();
    } else {
      throw new RuntimeException("Erro Sintático esperado 'real' ou 'integer'");
    }
  } 

  private void variaveis(){
    if (token != null && token.getTipo() == Token.IDENT){
    //if (simbolo.equals("ident")){
      obtemSimbolo();
      mais_var();

    } else {
          throw new RuntimeException("Erro Sintático esperado '.'");
    }        
  }

  private void mais_var(){
    if (simbolo.equals(",")){
      obtemSimbolo();
      variaveis();
    }  
  }

  private void comandos(){
    comando();
     mais_comandos();
  }

  private void mais_comandos(){
    if (simbolo.equals(";")){
      obtemSimbolo();      
        comandos();
     } else {   
        throw new RuntimeException("Erro Sintático esperado ';'");
    }
  }
  
  private void comando(){
    switch(simbolo){
      case "read":
      if (token != null && token.getTipo() == Token.IDENT){
        obtemSimbolo();
        }if (simbolo.equals("(")){
          obtemSimbolo();
            }if (token != null && token.getTipo() == Token.IDENT){
          //}if (simbolo.equals("ident")){
            obtemSimbolo();
            }if (simbolo.equals(")")){
              obtemSimbolo();
        }
        break;
      case "write":
        if (token != null && token.getTipo() == Token.IDENT){
          obtemSimbolo();
          }if (simbolo.equals(")")){
          obtemSimbolo();
            }if (token != null && token.getTipo() == Token.IDENT){
          //}if (simbolo.equals("ident")){
            obtemSimbolo();
            }if (simbolo.equals(")")){
              obtemSimbolo();
        }         
        break;
      case "ident":
        if (token != null && token.getTipo() == Token.IDENT){
        obtemSimbolo();
          }if (simbolo.equals(":")){
          obtemSimbolo();
            }if (simbolo.equals("=")){
            obtemSimbolo();
            expressao();
        }
        break;

      case "if":
         if (token != null && token.getTipo() == Token.IDENT){
        obtemSimbolo();
        condicao();
          }if (token != null && token.getTipo() == Token.IDENT){
          //}if (simbolo.equals("then")){
          obtemSimbolo();
          comandos();
          pfalsas();
          }if (simbolo.equals("$")){
            obtemSimbolo();
          }    
        break; 
      default:   // O QUE SIGNIFICA AQUI NO CODIGO?
      throw new RuntimeException("Erro Sintático esperado 'c' ou 'e'");
    }

  }

  private void condicao(){
    expressao();
    relacao();
    expressao();   // TENHO DVIDA AQUI...HA UMA RECURÇÃO... O QUE FAZER?

  }

  private void relacao(){
    switch(simbolo){
      case "=":
        obtemSimbolo();                
        break;

      case "<>":
        obtemSimbolo();               
        break;

      case ">=":
        obtemSimbolo();        
        break;

      case "<=": 
        obtemSimbolo();        
        break;

        case ">": 
        obtemSimbolo();        
        break;

        case "<": 
        obtemSimbolo();        
        break;

      default:   // O QUE SIGNIFICA AQUI NO CODIGO?
      throw new RuntimeException("Erro Sintático ");
    }

  }

  private void expressao(){
    termo();
    outros_termos();

  }

  private void termo(){
    op_un();
    fator();
    mais_fatores();

  }

 private void op_un(){
    if(simbolo.equals("-")){ 
      obtemSimbolo();
    }
  }

  private void fator(){
    switch(simbolo){
      case "ident":
      if (token != null && token.getTipo() == Token.IDENT){
        obtemSimbolo();
      }                
        break;

      case "numero_int":                
        if (token != null && token.getTipo() == Token.IDENT){
          obtemSimbolo();
        }
        break;

      case "numero_real":
      if (token != null && token.getTipo() == Token.IDENT){
        obtemSimbolo(); 
      }       
        break;

      case "(>)":  
        obtemSimbolo();
        expressao();                          
        if (simbolo.equals(")")){
          obtemSimbolo();          
          }    
        break;

      default:   // O QUE SIGNIFICA AQUI NO CODIGO?
      throw new RuntimeException("Erro Sintático ");
    }

  }

  private void outros_termos(){
      op_ad();
      termo();
      outros_termos();      
  } 
  
  private void op_ad(){
    if (simbolo.equals("+")  || simbolo.equals("-")){
      obtemSimbolo();
    } else {
      throw new RuntimeException("Erro Sintático esperado '+' ou '-'");
    }     
  }

  private void mais_fatores(){
    op_mul();
    fator();
    mais_fatores();         
  }

  private void op_mul(){
    if (simbolo.equals("*")  || simbolo.equals("/")){
      obtemSimbolo();
    } else {
      throw new RuntimeException("Erro Sintático esperado '*' ou '/'");
    }     
  }

  private void pfalsas(){
    if (token != null && token.getTipo() == Token.IDENT){
    //if(simbolo.equals("else")){
      obtemSimbolo();      
      } else {
         throw new RuntimeException("Erro Sintático ");
      }
    
  }

}
