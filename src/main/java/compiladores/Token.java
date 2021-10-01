package compiladores;

public class Token {

    public static final int IDENT = 0;
    public static final int NUMERO_INT = 1;
    public static final int ESPACO = 2;    
    public static final int DOIS_PONTOS = 3;
    public static final int ATRIBUICAO = 4;
    public static final int OP_REL_DIF = 5;
    public static final int MENOR_IGUAL = 6;
    public static final int MENOR = 7;
    public static final int MAIOR_IGUAL = 8;
    public static final int MAIOR = 9;   
    public static final int ATRIBUICAO2 = 10;
    public static final int ABRE_PARE = 11;
    public static final int FEC_PARE = 12;
    public static final int VIRGULA = 13;
    public static final int PONTO_VIRGULA = 14;
    public static final int PONTO = 15;
    public static final int OPE_ARIT_SOMA = 16;
    public static final int OPE_ARIT_SUB = 17;
    public static final int OPE_ARIT_DIV = 18;
    public static final int OPE_ARIT_MULT = 19;
    public static final int LAMBIDA = 20;
    public static final int SIFRAO = 21;
    public static final int NUMERO_REAL = 22;
    
    

    public int tipo; 
    public String termo;

    public int getTipo() {
        return this.tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getTermo() {
        return this.termo;
    }

    public void setTermo(String termo) {
        this.termo = termo;
    }


   public String toString(){
       return "Token [" + tipo +"," + termo+"]";
   }
   
}
