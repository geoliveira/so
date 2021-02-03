public class Main {
    import com.opencsv.bean.CsvToBean;
    import com.opencsv.bean.CsvToBeanBuilder;
    import java.io.IOException;
    import java.io.Reader;
    import java.nio.file.Files;
    import java.nio.file.Paths;
    import java.util.List;
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
        Reader reader = Files.newBufferedReader(Paths.get(args[0]+".csv")); 

        CsvToBean<Process> csvToBean = new CsvToBeanBuilder(reader).withType(Process.class).build();

        List<Process> processes = csvToBean.parse();

        for (Process proc : processes) System.out.println(proc);
    }
}