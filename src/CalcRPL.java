import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

import ObjEmp.IntegerEmp;
import ObjEmp.VectorEmp;
import exception.IncompatibleOperands;

public class CalcRPL
{
    private static final String USAGE_STRING = "Usage : java CalcRPL [normal|log|replay] [local|remote]";
    private static final String LOG_FILE = "../log/log.txt";
    private static final int PORT = 1234;

    private InputStream inputUser;
    private PrintStream outputUser;
    private FileWriter outputLog;
    private ServerSocket serverSocket;
    private Socket socket;


    public CalcRPL()
    {
        this.inputUser = null;
        this.outputUser = null;
        this.outputLog = null;
        this.serverSocket = null;
        this.socket = null;
    }


    public void closeResources()
    {
        try
        {
            if(inputUser != null)
                inputUser.close();
            if(outputUser != null)
                outputUser.close();
            if(outputLog != null)
                outputLog.close();
            if(socket != null)
                socket.close();
            if(serverSocket != null)
                serverSocket.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }


    public void exitOnError(String error)
    {
        System.err.println(error);
        closeResources();
        System.exit(-1);
    }


    public void init(String[] args)
    {
        if(args.length != 2)
            exitOnError(USAGE_STRING);
        
        String mode = args[0];
        String host = args[1];
    

        switch(host)
        {
            case "local":
                inputUser = System.in;
                outputUser = System.out;
                break;
            case "remote":
                try {
                serverSocket = new ServerSocket(PORT);
                socket = serverSocket.accept();
                inputUser = socket.getInputStream();
                outputUser = new PrintStream(socket.getOutputStream());
                } catch(IOException e) {
                    exitOnError("Une erreur est survenue lors de la création de de la socket");
                }    
                break;
            default:
                exitOnError(USAGE_STRING);
        }
        switch(mode)
        {
            case "normal":
                break;
            case "log":
                try{
                    outputLog = new FileWriter(LOG_FILE);
                }
                catch(IOException e) {
                    exitOnError("Une erreur est survenue lors de l'écriture du fichier de journalisation");
                }
                break;
            case "replay":
                try{
                    inputUser = new FileInputStream(LOG_FILE);
                }
                catch(FileNotFoundException e) {
                    exitOnError("Le fichier de journalisation est introuvable");
                }
                break;
            default:
                exitOnError(USAGE_STRING);
        }
    }


    public void RPLInterpreter()
    {
        PileRPL pile = new PileRPL(10);
        Scanner scanner = new Scanner(inputUser);
        boolean quit = false;
        String input = "";        
        while(!quit)
        {
            try {
                input = scanner.next();
            }
            catch(NoSuchElementException e) {
                exitOnError("Le programme n'a pas été terminé correctement");
            }

          
            if(input.matches("^-?[0-9]+$"))
            {
                try {
                    if(!pile.push(new IntegerEmp(Integer.parseInt(input))))
                        outputUser.println("La pile est pleine, impossible d'insérer " + input);
                }
                catch(NumberFormatException e) {
                    outputUser.println("Problème de format: Le nombre entré n'est pas un entier sur 4 octets");
                }
            }
            else if(input.matches("^[(]-?[0-9]+[,]-?[0-9]+[)]$"))
            {
                try {
                    StringTokenizer st = new StringTokenizer(input, "(,)");
                    int x = Integer.parseInt(st.nextToken());
                    int y = Integer.parseInt(st.nextToken());
                    if(!pile.push(new VectorEmp(x, y)))
                        outputUser.println("La pile est pleine, impossible d'insérer " + input);
                }
                catch(NumberFormatException e) {
                    outputUser.println("Problème de format: Au moins une des coordonnées du vecteur n'est pas un entier sur 4 octets");
                }
            }
            else
            {
                try {  
                    switch(input) {
                        case "+":
                            pile.add();
                            break;
                        case "-":
                        pile.substract();
                            break;
                        case "*":
                            pile.multiply();
                            break;
                        case "/":
                            pile.divide();
                            break;
                        case "q":
                            quit = true;
                            break;
                        case "RESET":
                            pile.empty();
                            break;
                        case "SUPP":
                            pile.pop();
                            break;
                        default:
                            outputUser.println("Symbole inconnu : " + input);
                            break;
                    }
                }
                catch(IncompatibleOperands e) {
                    outputUser.println("Erreur: Calcul avec des opérandes incompatibles");
                }
            }

            outputUser.println(pile);
            if(outputLog != null)
            {
                try {
                    outputLog.append(input + "\n");
                }
                catch (IOException e) {
                    System.out.println("Une erreur est survenue lors de l'écriture du fichier de journalisation");
                }
            }
        }
        scanner.close();
    }


    public static void main(String[] args)
    {
        CalcRPL calcRPL = new CalcRPL();
        calcRPL.init(args);
        calcRPL.RPLInterpreter();
        calcRPL.closeResources();
    }
}