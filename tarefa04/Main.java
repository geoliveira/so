import java.io.*;

// import java.io.IOException;
// import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public class Main {
    /*
     * algoritmos de escalonamento de processos: 
     *      FCFS; 
     *      SJF (não preemptivo);
     *      SJFP (preemptivo);
     *      Priority (nao preemptivo);
     *      PriorityP (preemptivo);
     *      RR (Round Robin).
     * 
     * parâmetro de entrada 
     *      (1) o nome do arquivo de entrada, (o qual deve conter os processos a serem escalonados;
     *      (2) o nome do algoritmo a ser executado;
     *      (3) o tipo de saída (1 – estatística e 2 – lista de processos);
     *      e qualquer outro parâmetro que você ache necessário para o funcionamento do seu programa. 
     * 
     * O arquivo de entrada deve conter minimamente os seguintes dados: 
     *      <Tempo de chegada>, <ID do Processo>, <Burst Time>, <Prioridade>
     * 
     *  Exemplo da chamada do programa: $ java escalonador processos.csv RR quantum=10
     */
    public static void main(String[] args) throws IOException
    {   
        LinkedList<Process> processes = new LinkedList<Process>();
        BufferedReader reader = Files.newBufferedReader(Paths.get(args[0])); 
        String[] attributes;
        
        try {
            String line;
    
            while ((line = reader.readLine()) != null) {
                attributes = line.split(",");
                Process process = 
                        new Process(
                        Integer.parseInt(attributes[0]), 
                        Integer.parseInt(attributes[1]), 
                        Integer.parseInt(attributes[2]), 
                        Integer.parseInt(attributes[3]), 
                        true);
                
                processes.add(process);
            }  

            attributes = args[2].split("="); // quantum=10

            Scheduler sched = 
                        new Scheduler(
                        processes,
                        Integer.parseInt(attributes[1]));

            sched.execScheduler(args[1]);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }
}