package compiladores;

import java.util.HashMap;
import java.util.Map;

public class Sintatico {

  private LexScanner scan;
  private String simbolo;
  private Token token;
  private Map<String, Simbolo> tabelaSimbolo = new HashMap<>(); 

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
    System.out.println("corpo");
    if (token != null){
        dc();
        if (simbolo.equals("begin")) {        
            obtemSimbolo();
            comandos();
            if (simbolo.equals("end")) {  
                obtemSimbolo();
          } else {
            throw new RuntimeException("Erro Sintático esperado 'end'"); 
          } 
        }else {
       throw new RuntimeException("Erro Sintático esperado 'begin'");  
      } 
    }
  }

  private void dc() {
    System.out.println("dc");
    if (token != null && (simbolo.equals("real")||simbolo.equals("integer"))) {
      dc_v();
      mais_dc();
    }
  }

  private void mais_dc(){
    System.out.println("mais_dc");
    if (token != null){
      if (simbolo.equals(";")) {
        obtemSimbolo();
        dc();       
      }
    } 
  }

  private void dc_v(){
    System.out.println("dc_v");
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
    System.out.println("tipo_var");
    if (token != null && token.getTipo() == Token.IDENT || token.getTipo() == Token.IDENT ) {
      obtemSimbolo();    
    } else {
      throw new RuntimeException("Erro Sintático erro em '6' ");
    }
  } 

  private void variaveis(){
    System.out.println("variaveis");
    if (token != null && token.getTipo() == Token.IDENT){    
      obtemSimbolo();
      mais_var();
    } else {
         throw new RuntimeException("Erro Sintático erro em '7'");
    }        
  }

  private void mais_var(){
    System.out.println("mais_var");
    if (token != null){
      if (simbolo.equals(",")){
       obtemSimbolo();
       variaveis();      
      }
    }
  }

  private void comandos(){
    System.out.println("comandos");
    if (token != null){
      comando();
      mais_comandos();
    }
  }

  private void mais_comandos(){
    System.out.println("mais_comandos");
    if (token != null){
      if (simbolo.equals(";")){
        obtemSimbolo();      
          comandos();       
      }
    }
  }
    
  private void comando(){
    System.out.println("comando");
  
      if(simbolo.equals("read")){        
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
         
      }else if (simbolo.equals("write")){          
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

       
     /* }else if (token != null && token.getTipo() == Token.IDENT){         
            obtemSimbolo();
            if (simbolo.equals(":=")){
             obtemSimbolo();              
              expressao();
              }else {   
                throw new RuntimeException("Erro Sintático esperado ':='");
               } */        
             
      }else if (simbolo.equals("if")){               
            obtemSimbolo();
            condicao();
            if (simbolo.equals("then")){           
              obtemSimbolo();       
              comandos();
              pfalsa();
              if (simbolo.equals("$")){             
                obtemSimbolo();
              }else {   
                throw new RuntimeException("Erro Sintático esperado '$'");
               }
            }else {   
               throw new RuntimeException("Erro Sintático esperado 'then'");
            }
         // }else {           
         // throw new RuntimeException("Erro Sintático esperado 'if'");
        //} 
        
      }else if (token != null && token.getTipo() == Token.IDENT){         
        obtemSimbolo();
        if (simbolo.equals(":=")){
         obtemSimbolo();              
          expressao();
          }else {   
            throw new RuntimeException("Erro Sintático esperado ':='");
           }        
      }            
  }

  private void condicao(){
    System.out.println("condicao");
    if (token != null){
      expressao();
      relacao();
      expressao();   
    }
  }

  private void relacao(){
    System.out.println("relacao");
   
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
    System.out.println("expressao");
    if (token != null){
    termo();
    outros_termos();
    }
  }

  private void termo(){
    System.out.println("termo");
    if (token != null){
    op_un();
    fator();
    mais_fatores();
    }
  }

 private void op_un(){
  System.out.println("op_un");
    if (token != null){
      if(simbolo.equals("-")){ 
        obtemSimbolo();      
      }
    }
  }

  private void fator(){
    System.out.println("fator");
       
     if (token != null && token.getTipo() == Token.IDENT){
        obtemSimbolo();      
      } else if (token != null && token.getTipo() == Token.NUMERO){
        obtemSimbolo();   
      } else if (token != null && token.getTipo() == Token.NUMERO){
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
    System.out.println("outros_termos");
    if (token != null && (simbolo.equals("+")  || simbolo.equals("-"))){
        op_ad();
        termo();
        outros_termos();      
    }      
  } 
  
  private void op_ad(){
    System.out.println("op_ad");
    if (simbolo.equals("+")  || simbolo.equals("-")){
      obtemSimbolo();
    } else {
      throw new RuntimeException("Erro Sintático esperado '+' ou '-'");
    }     
  }

  private void mais_fatores(){
    System.out.println("mais_fatores");    
    if (token != null && (simbolo.equals("*")  || simbolo.equals("/"))){
      op_mul();
      fator();
      mais_fatores(); 
     }           
  }
  
  private void op_mul(){
    System.out.println("op_mul");      
    if (simbolo.equals("*")){
      obtemSimbolo();   
    } else if (simbolo.equals("/")){    
      obtemSimbolo();     
      } else {
      throw new RuntimeException("Erro Sintático esperado '*' ou '/'");
      }    
  }         
 
  private void pfalsa(){
    System.out.println("pfalsa");
    if (token != null){
    if (simbolo.equals("else")){        
        obtemSimbolo();
        comandos();      
        } else {
         throw new RuntimeException("Erro Sintático  22");
        } 
    }   
  }
}
