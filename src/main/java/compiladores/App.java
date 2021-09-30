package compiladores;


public class App 
{
    public static void main( String[] args )
    {
       /* LexScanner scan = new LexScanner("codigoteste.txt");
        Token token = null;
        do{
            token = scan.nextToken();
            System.out.println(token);
        }while(token != null);*/

     // Sintatico sintatico = new Sintatico ("codigoteste.txt");
        //sintatico.analise();

      SintaticoSem sintaticoSem = new SintaticoSem ("codigoteste.txt");
        sintaticoSem.analise();
    }
}
