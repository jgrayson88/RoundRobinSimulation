
package roundrobinsimulation;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
/**
 creates ready queue, creates process, and runs Round Robin
 */
public class Simulation {
    public Simulation(ArrayList<Process> p, int quantum)
    {
        processArrayList = p;
        timeQuantum = quantum;
        cpu=new CPU(timeQuantum);
        readyQueue = new LinkedList<Process>();
        clock = new Clock();
        finishedProcesses = new ArrayList<Process>();
        totalNumOfProcessesInSystem = 5;
        for(int i =0;i<p.size();i++)
        {
            p.get(i).setInitialArrivalTime();
            p.get(i).setInitialBurstTime();
        }
    }
    public void runRoundRobinSimulation()
    {
        ProcessCreator(null);//creates process by adding to ready queue
        RoundRobin();
        
    }
    public void ProcessCreator(Process currentProcess)//checks if process arrival time =0 and burst time != 0, if so, process ready to execute
    {
        for(int i=0;i<processArrayList.size();i++)//from process 0 to process n
        {
            Process p =processArrayList.get(i);//create process i object
            if(p.checkIfReadytoExecute()==true && p.checkIfProcessFinished()==0 && (p != currentProcess) && !readyQueue.contains(p))//check if process i is ready to execute
            {
                readyQueue.add(p);//place process that is ready to execute in ready queue
                System.out.println("NEW Process " + p.getProcessName() + " added to ready queue");
            }
        }
    }

    public void RoundRobin()
    {
        boolean done = false;

        while(!done)
        {
        Process currentProcess = readyQueue.peek();//get process at head of queue 
        if(currentProcess != null){
        int runTimeOfPrcoessOnCPU=cpu.run(currentProcess);
        clock.updateTimeSinceStartOfSimulator(runTimeOfPrcoessOnCPU);
        clock.updateArrivalTimeOfProcessesWaitingToGetToReadyQueue(processArrayList,runTimeOfPrcoessOnCPU);
        clock.updateWaitTimeOfProcessesInReadyQueue(readyQueue,runTimeOfPrcoessOnCPU, currentProcess);
        ProcessCreator(currentProcess);
        if(currentProcess.checkIfProcessFinished()==1)
        {
            System.out.println("Process " + currentProcess.getProcessName() + " completed execution");

              processArrayList.remove(currentProcess);
              System.out.println("Process " + currentProcess.getProcessName() + " removed from ready queue");
            finishedProcesses.add(readyQueue.remove());//remove from ready queue and add to finished process list
            
            if(finishedProcesses.size()==totalNumOfProcessesInSystem)
        {
            this.EndingSimulation();
            done=true;
        }
        }
        else
        {
            currentProcess.updateContextSwitch();//process switched out of cpu=>context switch updated
            currentProcess.getCurrentProcessInfo();
            readyQueue.remove();//removed from head of ready queue
            readyQueue.add(currentProcess);//added to tail of ready queue
            ProcessCreator(currentProcess);
        }
        }
        else//if currentProcess is null
        {
            
            clock.updateTimeSinceStartOfSimulator(timeQuantum);
            clock.updateArrivalTimeOfProcessesWaitingToGetToReadyQueue(processArrayList,timeQuantum);
            ProcessCreator(null);
        }
        }
    }
    public void EndingSimulation()
    {
        int totalWaitTimeOfAllProcesses=0;
        int totalTurnAroundTimeOfAllProcesses = 0;
        int totalContextSwitchesOfAllProcesses = 0;
        int wait = 0;
        for(int i =0;i<finishedProcesses.size();i++){
        finishedProcesses.get(i).setTurnAroundTime();}
        
        int p1TurnAround= finishedProcesses.get(0).getTurnAroundTime();
        int p5TurnAround= finishedProcesses.get(1).getTurnAroundTime();
        int p3TurnAround= finishedProcesses.get(2).getTurnAroundTime();
        int p4TurnAround= finishedProcesses.get(3).getTurnAroundTime();
        int p2TurnAround= finishedProcesses.get(4).getTurnAroundTime();
        int maxTurnAround = Math.max( p1TurnAround, Math.max( p2TurnAround, Math.max( p3TurnAround, Math.max(p4TurnAround, p5TurnAround))));
        totalTurnAroundTimeOfAllProcesses = p1TurnAround+p2TurnAround+p3TurnAround+p4TurnAround+p5TurnAround;
            
        for(int i =0;i<finishedProcesses.size();i++){
        wait = wait + finishedProcesses.get(i).getTotalWaitTime();}
        for(int i =0;i<finishedProcesses.size();i++)
        {
            totalWaitTimeOfAllProcesses = wait;
            
            totalContextSwitchesOfAllProcesses = totalContextSwitchesOfAllProcesses + finishedProcesses.get(i).getContextSwitch();
        }
        double averageWaitTime = totalWaitTimeOfAllProcesses/totalNumOfProcessesInSystem;
        double averageTurnAroundTime = totalTurnAroundTimeOfAllProcesses/totalNumOfProcessesInSystem;
        System.out.println();
        System.out.println();
        System.out.println("*****RESULTS****");
        System.out.println("Average wait time: " + averageWaitTime);
        System.out.println("Average turnaround time: " + averageTurnAroundTime);
        //double throughput = ((double)cpu.getNumberOfCompletedProcesses()/maxTurnAround) *100.0;
        double throughput= ((double)cpu.getNumberOfCompletedProcesses()/clock.getTimeSinceStartOfSimulator()) *100.0;
        System.out.println("Throughput: " + throughput + "%");
        double runningTimeMinusContextSwitch=clock.getTimeSinceStartOfSimulator()-totalContextSwitchesOfAllProcesses;
        double utilization= (runningTimeMinusContextSwitch/clock.getTimeSinceStartOfSimulator())*100.0;
        System.out.println("CPU utilization: " + utilization + "%");
        
    }
    
    private ArrayList<Process> processArrayList;
    private int timeQuantum;
    private CPU cpu;
    private Queue<Process> readyQueue;
    private Clock clock;
    private ArrayList<Process> finishedProcesses;
    private static int totalNumOfProcessesInSystem;
}
