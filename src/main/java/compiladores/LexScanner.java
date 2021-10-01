package compiladores;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LexScanner {
    private char[] conteudo;
    private int estado;
    private int pos;

    public LexScanner(String arq) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(arq));
            conteudo = (new String(bytes)).toCharArray();
        } catch(IOException e) {
           e.printStackTrace();
      }
    }   

    public Token nextToken() {
      if (isEOF()) {
        return null;
      }
        estado = 0;
        char c;
        Token token = null;
        String termo = "";
        while(true) {
           if (isEOF()) {
               pos = conteudo.length + 1;
           }
            c = nextChar();
            if(c==0){
                return null;
            }
            switch(estado) {
                case 0:
                    if(isLetra(c)) {
                        termo += c;
                        estado = 1;
                    } else if (isDigito(c)) {
                        termo += c;
                        estado = 3;
                    } else if (isEspaco(c)) {
                        estado = 0;
                    } else if (c == ':') {
                        estado = 9;
                        termo += c; 
                    } else if (c == '<') {
                        estado = 12;
                        termo += c;
                    } else if (c == '>') {
                        estado = 16;
                        termo += c;
                    } else if (c == '=') {
                        estado = 19;
                        termo += c;
                    } else if (c == '(') {
                        estado = 20;
                        termo += c;
                    } else if (c == ')') {
                        estado = 21;
                        termo += c;
                    } else if (c == '/') {
                        estado = 22;
                        termo += c;
                    } else if (c == ',') {
                        estado = 23;
                        termo += c;
                    } else if (c == ';') {
                        estado = 24;
                        termo += c;
                    } else if (c == '.') {
                        estado = 25;
                        termo += c; 
                    } else if (c == '+') {
                        estado = 26;
                        termo += c; 
                    } else if (c == '-') {
                        estado = 27;
                        termo += c; 
                    } else if (c == '*') {
                        estado = 28;
                        termo += c; 
                    } else if (c == '$') {
                        estado = 29;
                        termo += c;           
                    } else {
                        throw new RuntimeException("Token não reconhecido");
                    }
                    break;
                case 1:
                    if (isLetra(c) || isDigito(c)) {
                        estado = 1;
                        termo += c;
                                                             
                    } else {    
                        estado = 2;
                     }
                    break;
                case 2:
                    token = new Token();
                    token.setTipo(Token.IDENT);
                    token.setTermo(termo);
                    pos -= 2;
                    return token;
                case 3:
                    if(isDigito(c)) {
                        estado = 3;
                        termo += c;
                    } else if (!isLetra(c)) {                   
                        estado = 4;                     
                    } else if (c=='.') {    
                        estado =6;
                    } else {
                        throw new RuntimeException("Numero não reconhecido");
                    }
                    break;

                case 4:
                    token = new Token();
                    token.setTipo(Token.NUMERO_INT);
                    token.setTermo(termo);
                    pos -= 2;
                    return token;    

                case 6:                    
                    if(isDigito(c)) {
                        estado = 7;
                        termo += c;
                    } else if (!isLetra(c)) {                   
                        estado = 8;                 
                        pos -= 2;
                    }
                    break;

                case 7:                    
                    if(isDigito(c)) {
                        estado = 7;
                        termo += c;                
                        pos -= 2;
                    }
                    break;

                case 8:
                    token = new Token();
                    token.setTipo(Token.NUMERO_REAL);
                    token.setTermo(termo);
                    pos -= 2;
                    return token;    

                
                case 9:
                    if (c == '=') {
                        termo += c;
                        token = new Token();
                        token.setTipo(Token.ATRIBUICAO);
                        token.setTermo(termo);
                        return token;
                    } else {
                         token = new Token();
                        token.setTipo(Token.DOIS_PONTOS);
                        token.setTermo(termo);
                        pos--;
                        return token;
                    }
                    
                case 12:
                    if (c == '>') {
                        termo += c;
                        token = new Token();
                        token.setTipo(Token.OP_REL_DIF);
                        token.setTermo(termo);
                        return token;
                    }else if (c == '=') {
                        termo += c;
                        token = new Token();
                        token.setTipo(Token.MENOR_IGUAL);
                        token.setTermo(termo);
                        return token; 
                                   
                    } else {
                        token = new Token();
                        token.setTipo(Token.MENOR);
                        token.setTermo(termo);
                        pos--;
                        return token;
                    }
                case 16:
                    if (c == '=') {
                        termo += c;
                        token = new Token();
                        token.setTipo(Token.MAIOR_IGUAL);
                        token.setTermo(termo);
                        return token;
                    } else {
                        token = new Token();
                        token.setTipo(Token.MAIOR);
                        token.setTermo(termo);
                        pos--;
                        return token;
                    }
                case 19:
                    token = new Token();
                    token.setTipo(Token.ATRIBUICAO2);
                    token.setTermo(termo);
                    back();
                    return token;
                case 20:
                    token = new Token();
                    token.setTipo(Token.ABRE_PARE);
                    token.setTermo(termo);
                    back();
                    return token;
                case 21:
                    token = new Token();
                    token.setTipo(Token.FEC_PARE);
                    token.setTermo(termo);
                    back();
                    return token;
                case 22:
                    token = new Token();
                    token.setTipo(Token.OPE_ARIT_DIV);
                    token.setTermo(termo);
                    back();
                    return token;
                case 23:
                    token = new Token();
                    token.setTipo(Token.VIRGULA);
                    token.setTermo(termo);
                    back();
                    return token;
                case 24:
                    token = new Token();
                    token.setTipo(Token.PONTO_VIRGULA);
                    token.setTermo(termo);
                    back();
                    return token;
                case 25:
                    token = new Token();
                    token.setTipo(Token.PONTO);
                    token.setTermo(termo);
                    back();
                    return token;
                case 26:
                    token = new Token();
                    token.setTipo(Token.OPE_ARIT_SOMA);
                    token.setTermo(termo);
                    back();
                    return token;
                case 27:
                    token = new Token();
                    token.setTipo(Token.OPE_ARIT_SUB);
                    token.setTermo(termo);
                    back();
                    return token;
                case 28:
                    token = new Token();
                    token.setTipo(Token.OPE_ARIT_MULT);
                    token.setTermo(termo);
                    back();
                    return token;
                case 29:
                    token = new Token();
                    token.setTipo(Token.SIFRAO);
                    token.setTermo(termo);
                    back();
                    return token;
                
                
                
                 
            }

        }
    }

    private boolean isLetra(char c) {
        return (c >= 'a' && c<= 'z' ) || ( c >= 'A' && c<= 'Z') || c =='_';
    }

    private boolean isDigito(char c) {
        return c >='0' && c <= '9';
    }

    private boolean isEspaco(char c) {
        return c ==' ' || c == '\n' || c == '\t' || c == '\r';
    }

    
    private boolean isEOF() {
        return pos >= conteudo.length;
    }

    private char nextChar() {
        if(isEOF()){
            return 0;
        }
        return conteudo[pos++];
    }

    private void back() {
         pos-= 1;
    }
}
