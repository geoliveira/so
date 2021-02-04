import java.util.Iterator;
import java.util.LinkedList;

public class Scheduler {
    private Integer time = 0, curTime = 0, quantum = 0;
    private LinkedList<Process> readyProc;
    private LinkedList<Process> waitingProc;
    private LinkedList<Process> finishedProc;

    public Scheduler(LinkedList<Process> p, Integer q) {
        this.readyProc      = p;
        this.waitingProc    = new LinkedList<Process>();
        this.finishedProc   = new LinkedList<Process>();
        this.quantum        = q;
    }
    
    public void execScheduler(String option) {
        switch(option) {
            case "FCFS":
                execFCFS();
                break;

            case "SJF":
                execSJF();
                break;

            case "SJFP":
                execSJFP();
                break;

            case "Priority":
                execPriority();
                break;
            
            case "PriorityP":
                execPriorityP();
                break;

            case "RR":
                execRR();
                break;
            
            default:
                System.out.println("error: the algorithm dont was recognized");
                break;
        }
    }

    public void execFCFS() {
        for (Process proc : readyProc) System.out.println(proc);    
        System.out.println("Quantum="+this.quantum);

        Process p;
        while(readyProc.peekFirst() != null)
        {
            p = readyProc.pollFirst();
            p.setTimeWait(time); // beggining 
            p.setTimeResponse(time);
            time = time+p.getBurstTime(); // update time
            p.setBurstTime(p.getBurstTime()); // operations
            p.setTurnaround(time); // finished
            p.changeState();
            finishedProc.add(p);
        }

        for (Process proc : finishedProc) System.out.println(proc);    
        System.out.println("Quantum="+this.quantum);
    }
    public void execSJF() {}
    public void execSJFP() {}
    public void execPriority() {}
    public void execPriorityP() {}
    public void execRR() {}

/*
    a. Cabeçalho	contendo	o	nome	do	algoritmo	e	a	lista	dos	parâmetros;
    b. Tempo	total de	processamento	(somando	todos	os	processos);
    c. Percentual	de	utilização da CPU (tempo	total-tempo	troca	contexto)/tempo	total);
    d. Media	Throughput dos	processos;
    e. Media	Turnaround dos	processos
    f. Media	Tempo	de	Espera dos	processos
    g. Media	de	Tempo	de	Resposta dos	processos
    h. Média	de	troca	de	contextos;
    i. Numero	de	Processos executados
    
    a. Id	do	processo	escalonado;
    b. Tempo	de	processamento;
 */

}