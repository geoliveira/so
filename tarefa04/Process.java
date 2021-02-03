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

public class Process {
    private const int id, priority, arriveTime;
    private int burstTime, throughput, turnaround,
                timeWait, timeResponse, switchContext;
    
    public Process(int at, int bt, int id, int pr) {
        this.id             = id;
        this.priority       = pr;
        this.arriveTime     = at;
        this.burstTime      = bt;
        this.throughput     = 0; // qtd de proc concluídos por unidade de tempo
        this.turnaround     = 0; // intervalo de tempo [arriveTime, conclusao]
        this.timeResponse   = 0; // intervalo de tempo [arriveTime, 1a resposta]
        this.switchContext  = 0; // qtd de troca de contexto
    }
    
    /* SET FUNCTIONS */
    public void setBurstTime(int bt) {
        this.burstTime = bt;
    }
    
    public void setThroughput(int tp) {
        this.throughput = tp;
    }
    
    public void setTurnaround(int ta) {
        this.turnaround = ta;
    }
    
    public void setTimeResponse(int tr) {
        this.timeResponse = tr;
    }
    
    public void setSwitchContext(int sc) {
        this.switchContext = sc;
    }

    /* GET FUNCTIONS */

    public int setBurstTime(void) {
        return this.burstTime
    }

    public int getThroughput(void) {
        return this.throughput;
    }
    
    public int getTurnaround(void) {
        return this.turnaround;
    }
    
    public int getTimeResponse(void) {
        return this.timeResponse;
    }
    
    public int getSwitchContext(void) {
        return this.switchContext;
    }
}