public class Process {
    private Integer id, priority, arriveTime,
                    burstTime, throughput, turnaround,
                    timeWait, timeResponse, switchContext;
    private Boolean state;
    
    public Process(Integer id, Integer pr, Integer at, Integer bt, Boolean s) {
        this.id             = id;
        this.priority       = pr;
        this.arriveTime     = at;
        this.burstTime      = bt;
        this.throughput     = 0; // qtd de proc concluídos por unidade de tempo
        this.turnaround     = 0; // intervalo de tempo [arriveTime, conclusao]
        this.timeWait       = 0; // tempo na fila de wait
        this.timeResponse   = 0; // intervalo de tempo [arriveTime, 1a resposta]
        this.switchContext  = 0; // qtd de troca de contexto
        this.state          = s;
    }
    
    /* SET FUNCTIONS */
    public void setBurstTime(Integer bt) {
        this.burstTime = this.burstTime-bt;
    }
    
    public void setThroughput(Integer tp) {
        this.throughput = tp;
    }
    
    public void setTurnaround(Integer ta) {
        this.turnaround = ta;
    }
    
    public void setTimeWait(Integer tw) {
        this.timeWait = tw;
    }

    public void setTimeResponse(Integer tr) {
        this.timeResponse = tr;
    }
    
    public void setSwitchContext(Integer sc) {
        this.switchContext = sc;
    }
    
    public void changeState() {
        (this.state) = (this.state) ? false : true;
    }

    /* GET FUNCTIONS */
    public Integer getId() {
        return this.id;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public Integer getArriveTime() {
        return this.arriveTime;
    }

    public Integer getBurstTime() {
        return this.burstTime;
    }

    public Integer getThroughput() {
        return this.throughput;
    }
    
    public Integer getTurnaround() {
        return this.turnaround;
    }
    
    public Integer getTimeWait() {
        return this.timeWait;
    }

    public Integer getTimeResponse() {
        return this.timeResponse;
    }
    
    public Integer getSwitchContext() {
        return this.switchContext;
    }
        
    public Boolean getState() {
        return (this.state);
    }

    @Override
    public String toString() {
        return ("Id="+getId()+
               ", Priority="+getPriority()+
               ", ArriveTime="+getArriveTime()+
               ", BurstTime="+getBurstTime()+
               ", Throughput="+getThroughput()+
               ", Turnaround="+getTurnaround()+
               ", TimeWait="+getTimeWait()+
               ", TimeResponse="+getTimeResponse()+
               ", SwitchContext="+getSwitchContext()+
               ", State="+getState());
    }
}