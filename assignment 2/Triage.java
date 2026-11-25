import java.util.*;

class Patient {
    final String name; final int severity; final long arrival;
    Patient(String name,int severity,long arrival){this.name=name;this.severity=severity;this.arrival=arrival;}
    @Override public String toString(){return name+"(S"+severity+")";}
}
public class Triage {
    private long counter=0;
    private final Comparator<Patient> order=Comparator.comparingInt((Patient p)->p.severity)
        .thenComparingLong(p->p.arrival);
    private final PriorityQueue<Patient> queue=new PriorityQueue<>(order);
    void admit(String name,int severity){queue.add(new Patient(name,severity,counter++));}
    Patient next(){return queue.poll();}

    public static void main(String[] args) {
        Triage er=new Triage();
        // Eight-patient hand-checkable test.
        er.admit("Ravi",3); er.admit("Meena",1); er.admit("John",3);
        er.admit("Asha",5); er.admit("Kumar",1); er.admit("Lata",2);
        er.admit("Sara",4); er.admit("Vijay",2);
        System.out.println("Treatment order:");
        Patient p; while((p=er.next())!=null)System.out.print(p+" ");
        System.out.println();

        // Alternative 1: LinkedList maintained in sorted order.
        Comparator<Patient> cmp=Comparator.comparingInt((Patient x)->x.severity)
            .thenComparingLong(x->x.arrival);
        LinkedList<Patient> sorted=new LinkedList<>();
        Patient[] sample={new Patient("A",3,0),new Patient("B",1,1),new Patient("C",1,2)};
        for(Patient x:sample) {
            int i=0; while(i<sorted.size() && cmp.compare(sorted.get(i),x)<=0)i++;
            sorted.add(i,x);
        }
        System.out.println("Sorted LinkedList next: "+sorted.removeFirst());

        // Alternative 2: five FIFO queues, one per severity (1..5).
        @SuppressWarnings("unchecked")
        Queue<Patient>[] levels=new Queue[6];
        for(int i=1;i<=5;i++)levels[i]=new ArrayDeque<>();
        levels[3].add(new Patient("Ravi",3,0));
        levels[1].add(new Patient("Meena",1,1));
        levels[1].add(new Patient("Kumar",1,2));
        System.out.print("Five queues order: ");
        for(int severity=1;severity<=5;severity++)
            while(!levels[severity].isEmpty())System.out.print(levels[severity].remove()+" ");
        System.out.println();

        System.out.println("PriorityQueue add/remove: O(log n); peek: O(1); space: O(n).");
        System.out.println("Aging: periodically remove a waiting patient, update effective priority, then reinsert.");
    }
}
