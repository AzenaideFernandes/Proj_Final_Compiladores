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
    token = scan.nextToken();
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
    if (token != null){
        dc();
        if (token != null && token.getTipo() == Token.IDENT) {
            obtemSimbolo();
            comandos();    
          if (token != null && token.getTipo() == Token.IDENT) {
          obtemSimbolo();
          } else {
            throw new RuntimeException("Erro Sintático esperado 'end'"); 
          } 
        }else {
       throw new RuntimeException("Erro Sintático esperado 'begin'");  
      } 
    }
  }


  /*private void dc(){
    if (token != null){
      dc_v();
      mais_dc();
    } else {

    }  
  }*/

  private void dc() {
    System.out.println("dc");
    if (token != null && (simbolo.equals("real")||simbolo.equals("integer"))) {
      dc_v();
      mais_dc();
    }
  }

  private void mais_dc(){
    if (simbolo.equals(";")) {
      obtemSimbolo();
      dc();
    } else {

    }   
  }

  private void dc_v(){
    if (token != null){
     tipo_var();
      if(simbolo.equals(":")){
       obtemSimbolo();
       variaveis();    
     } else {
      throw new RuntimeException("Erro Sintático esperado ':'");  
     }      
    } 
  }

  private void tipo_var(){
    if (token != null && token.getTipo() == Token.IDENT || token.getTipo() == Token.IDENT ) {
      obtemSimbolo();    
    } else {
      throw new RuntimeException("Erro Sintático esperado 'real' ou 'integer'");
    }
  } 

  private void variaveis(){
    if (token != null && token.getTipo() == Token.IDENT){    
      obtemSimbolo();
      mais_var();
    } else {
         throw new RuntimeException("Erro Sintático esperado 'ident'");
    }        
  }

  private void mais_var(){
    if (simbolo.equals(",")){
      obtemSimbolo();
      variaveis();
    } else {

    }  
  }

  private void comandos(){
    if (token != null){
      comando();
      mais_comandos();
    }
  }

  private void mais_comandos(){
    if (simbolo.equals(";")){
      obtemSimbolo();      
        comandos();
      } else {   
        throw new RuntimeException("Erro Sintático esperado ';'");
      //} else {
      }
  }
  
  
  private void comando(){
  
      if(simbolo.equals("read")){
        if (token != null && token.getTipo() == Token.IDENT){
          obtemSimbolo();
            if (simbolo.equals("(")){
              obtemSimbolo();
                if (token != null && token.getTipo() == Token.IDENT){          
                  obtemSimbolo();
                    if (simbolo.equals(")")){
                     obtemSimbolo();
                    }else {   
                      throw new RuntimeException("Erro Sintático esperado ')'");
                    }
                 }else {   
                    throw new RuntimeException("Erro Sintático esperado 'ident'");
                  }
                }else {   
                  throw new RuntimeException("Erro Sintático esperado '('");
                }
              }
                        
         
      }else if (simbolo.equals("write")){
          if (token != null && token.getTipo() == Token.IDENT){
              obtemSimbolo();
              if (simbolo.equals("(")){
                obtemSimbolo();
                if (token != null && token.getTipo() == Token.IDENT){          
                   obtemSimbolo();
                  if (simbolo.equals(")")){
                       obtemSimbolo();
              }else {   
                throw new RuntimeException("Erro Sintático esperado ')'");
              }
           }else {   
              throw new RuntimeException("Erro Sintático esperado 'ident'");
            }
          }else {   
            throw new RuntimeException("Erro Sintático esperado '('");
          }
        }
       
      }else if (token != null && token.getTipo() == Token.IDENT){         
            obtemSimbolo();
            if (simbolo.equals(":=")){
             obtemSimbolo();              
              expressao();
              }else {   
                throw new RuntimeException("Erro Sintático esperado ':='");
               }         
             
      }else if (simbolo.equals("if")){
         if (token != null && token.getTipo() == Token.IDENT){
            obtemSimbolo();
            condicao();
           if (token != null && token.getTipo() == Token.IDENT){ 
              obtemSimbolo();       
              comandos();
              pfalsas();
              if (simbolo.equals("$")){
             //if (token != null && token.getTipo() == Token.IDENT){
                obtemSimbolo();
              }else {   
                throw new RuntimeException("Erro Sintático esperado '$'");
               }
            }else {   
               throw new RuntimeException("Erro Sintático esperado 'then'");
            }
          }else {           
          throw new RuntimeException("Erro Sintático esperado 'if'");
        }
      }
  }

  private void condicao(){
    if (token != null){
      expressao();
      relacao();
      expressao();   // TENHO DVIDA AQUI...HA UMA RECURÇÃO... O QUE FAZER?
    }
  }

  private void relacao(){
   
      if(simbolo.equals("=")){      
        obtemSimbolo();                
      }else if (simbolo.equals("<>")){       
        obtemSimbolo(); 
      }else if (simbolo.equals(">=")){       
        obtemSimbolo(); 
      }else if (simbolo.equals("<=")){       
        obtemSimbolo(); 
      }else if (simbolo.equals(">")){       
        obtemSimbolo(); 
      }else if (simbolo.equals("<")){       
        obtemSimbolo(); 
      
    }
  }

  private void expressao(){
    if (token != null){
    termo();
    outros_termos();
    }

  }

  private void termo(){
    if (token != null){
    op_un();
    fator();
    mais_fatores();
    }
  }

 private void op_un(){
    if(simbolo.equals("-")){ 
      obtemSimbolo();
    }else{

    }
  }

  private void fator(){
       
     if (token != null && token.getTipo() == Token.IDENT){
        obtemSimbolo();

      } else if (simbolo.equals("numero_int")){
      //} else if (token != null && token.getTipo() == Token.IDENT){
        obtemSimbolo(); 
     
      }else if (simbolo.equals("numero_real")){
      //} else if (token != null && token.getTipo() == Token.IDENT){
        obtemSimbolo(); 
      } else if (simbolo.equals("(")){    
        obtemSimbolo();
        expressao();                          
         if (simbolo.equals(")")){
           obtemSimbolo();          
            }
      }      
        

  }

  private void outros_termos(){
    if (token != null){
      op_ad();
      termo();
      outros_termos();
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
    if (token != null){
    op_mul();
    fator();
    mais_fatores(); 
    }else{

    }        
  }

  
  private void op_mul(){
   // if (token != null && token.getTipo() == Token.IDENT){
     //obtemSimbolo();
    if (simbolo.equals("*")){
      obtemSimbolo();
    } else if (simbolo.equals("/")){
    //} else if (token != null && token.getTipo() == Token.IDENT){
      obtemSimbolo();
     
    } else {
      throw new RuntimeException("Erro Sintático esperado '*' ou '/'");
    }
  
  }          
  

  private void pfalsas(){
    //if (simbolo.equals("else")){
    if (token != null && token.getTipo() == Token.IDENT){    
      obtemSimbolo();
      comandos();      
      } else {
         throw new RuntimeException("Erro Sintático  22");
      //}else{
    }    
  }
}
