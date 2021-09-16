package compiladores;

public class Sintatico {

  private LexScanner scan;
  private String simbolo;

  public Sintatico(String arq){
    scan = new LexScanner(arq);
  }

  public void analise() {
    obtemSimbolo();
    programa();
    if (simbolo.equals(" ")) {
      System.out.println("Tudo Certo !!!");      
    } else {
      throw new RuntimeException("Erro Sintático esperado fim de cadeia");
    }
  }

  private void obtemSimbolo(){
    Token token = scan.nextToken();
    simbolo = " ";
    if (token != null) {
      simbolo = token.getTermo();
      System.out.println(simbolo);
    }
  }

  private void programa(){
    if (simbolo.equals("program")) {
      obtemSimbolo();
      
       if (simbolo.equals("ident")) {
        obtemSimbolo();
        corpo();

      //} else {
       // throw new RuntimeException("Erro Sintático esperado 'ident'"); 
     // }
       // if (simbolo.equals(".")){
        //  obtemSimbolo();
        
      

        } else {
          throw new RuntimeException("Erro Sintático esperado '.'"); 
        }         
      
    } else {
        throw new RuntimeException("Erro Sintático esperado 'program'"); 
    }
  }
    
  private void corpo(){
    dc();
    if (simbolo.equals("begin")) {
      obtemSimbolo();
      comandos();

      } if (simbolo.equals("end")){
        obtemSimbolo();
      }
  }

  private void dc(){
    dc_v();
      mais_dc();
   // if ("cadeia vazia"){
    // NAO SEI O QUE FAZER *************
   // }
  }

  private void mais_dc(){
    if (simbolo.equals(";")) {
      obtemSimbolo();
      dc();
    }
   // if ("cadeia vazia"){
    // NAO SEI O QUE FAZER *************
   // }
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
    if(simbolo.equals("real") || simbolo.equals("integer")){
      obtemSimbolo();
    } else {
      throw new RuntimeException("Erro Sintático esperado 'real' ou 'integer'");
    }
  } 

  private void variaveis(){
    if (simbolo.equals("ident")){
      obtemSimbolo();
      mais_var();

    } else {
          throw new RuntimeException("Erro Sintático esperado '.'");
    }        
  }

  private void mais_var(){
    variaveis();
  //  if("cadeia vazia"){
     // NAO SEI O QUE FAZER ************* 
   // }
  }

  private void comandos(){
    comando();
    mais_comandos();
  }

  private void mais_comandos(){
    switch(simbolo){
      case ";":
        obtemSimbolo();
        comandos();
      case "cadeia vazia":
      // NAO SEI O QUE FAZER ************* 
        break; 
      default:
      throw new RuntimeException("Erro Sintático esperado ';' ou ... ");
    }
  }
  
  private void comando(){
    switch(simbolo){
      case "read":
        obtemSimbolo();
        if (simbolo.equals("(")){
          obtemSimbolo();
          }if (simbolo.equals("ident")){
            obtemSimbolo();
            }if (simbolo.equals(")")){
              obtemSimbolo();
        }
        break;
      case "write":
        obtemSimbolo();
        if (simbolo.equals(")")){
          obtemSimbolo();
          }if (simbolo.equals("ident")){
            obtemSimbolo();
            }if (simbolo.equals(")")){
              obtemSimbolo();
        }         
        break;
      case "ident":
        obtemSimbolo();
        if (simbolo.equals(":")){
          obtemSimbolo();
          }if (simbolo.equals("=")){
            obtemSimbolo();
            expressao();
        }
        break;

      case "if":
        obtemSimbolo();
        condicao();
        if (simbolo.equals("then")){
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

      case "<":
        obtemSimbolo();
        if (simbolo.equals(">")){
          obtemSimbolo();          
        }         
        break;

      case ">":
        obtemSimbolo();
        if (simbolo.equals("=")){
          obtemSimbolo();          
        }
        break;

    /*  case ">":  // O QUE FAZER??
        obtemSimbolo();
        condicao();                          // COMO RESOLVER?
        if (simbolo.equals("=")){
          obtemSimbolo();          
          }    
        break;*/

      default:   // O QUE SIGNIFICA AQUI NO CODIGO?
      throw new RuntimeException("Erro Sintático esperado 'c' ou 'e'");
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
    if(simbolo.equals("-") || simbolo.equals("cadeia vazia")){
      obtemSimbolo();
    }  // O QUE FAZER???
  }

  private void fator(){
    switch(simbolo){
      case "ident":
        obtemSimbolo();                
        break;

      case "numero_int":
        obtemSimbolo();        
        break;

      case "numero_real":
        obtemSimbolo();        
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

      if (simbolo.equals("cadeia vazia")){    //????
          obtemSimbolo();          
        }
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

    if (simbolo.equals("cadeia vazia")){    //????
          obtemSimbolo();          
        
    } else {
      throw new RuntimeException("Erro Sintático ");
    }     
  }

  private void op_mul(){
    if (simbolo.equals("*")  || simbolo.equals("/")){
      obtemSimbolo();
    } else {
      throw new RuntimeException("Erro Sintático esperado '*' ou '/'");
    }     
  }

  private void pfalsas(){
    if(simbolo.equals("else")){
      obtemSimbolo();
      if (simbolo.equals("cadeia vazia")){    //????
        obtemSimbolo(); 
      } else {
         throw new RuntimeException("Erro Sintático esperado 'f' ou 'g'");
       }
    }
  }

}
