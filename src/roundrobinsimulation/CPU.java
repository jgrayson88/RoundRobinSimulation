
package roundrobinsimulation;

/**
 to run processes that are ready to leave the ready queue
 */
public class CPU {
    public CPU(int quantum)
    {
       timeQuantum = quantum; 
       numberOfCompletedProcesses = 0; //to calculate throughput
    }
    public int run(Process p)
    {
        if(p.getRemainingBurstTime()>timeQuantum)
        {
            p.updateRemainingBurstTime();
            return timeQuantum;//time cpu spent running for process for entire quantum
            
        }
        else if(p.getRemainingBurstTime()<timeQuantum)
        {
            int run = p.getRemainingBurstTime();
            p.updateRemainingBurstTime();
            numberOfCompletedProcesses++;
            return run;//time cpu sent running process for partial quantum
        }
        else
        {
            p.updateRemainingBurstTime();
            numberOfCompletedProcesses++;
            return timeQuantum;
        }
    }
    public int getNumberOfCompletedProcesses()//to calculate throughput
    {
        return numberOfCompletedProcesses;
    }
    private int timeQuantum;
    private int numberOfCompletedProcesses;
}
