import java.util.*;

public class ListComparison {
    static long time(Runnable action) {
        long s=System.nanoTime(); action.run(); return System.nanoTime()-s;
    }
    public static void main(String[] args) {
        int[] sizes={1000,10000,100000};
        for(int n:sizes) {
            ArrayList<Integer> a=new ArrayList<>();
            LinkedList<Integer> l=new LinkedList<>();
            for(int i=0;i<n;i++){a.add(i);l.add(i);}
            System.out.println("\\nSIZE="+n);

            System.out.printf("ArrayList add-end: %.3f ms%n",time(()->{for(int i=0;i<1000;i++)a.add(i);})/1e6);
            System.out.printf("LinkedList add-end: %.3f ms%n",time(()->{for(int i=0;i<1000;i++)l.add(i);})/1e6);

            System.out.printf("ArrayList front insert 100: %.3f ms%n",time(()->{for(int i=0;i<100;i++)a.add(0,-1);})/1e6);
            System.out.printf("LinkedList front insert 100: %.3f ms%n",time(()->{for(int i=0;i<100;i++)l.add(0,-1);})/1e6);

            int mid=a.size()/2;
            System.out.printf("ArrayList middle insert/remove 100: %.3f ms%n",time(()->{for(int i=0;i<100;i++){a.add(a.size()/2,-1);a.remove(a.size()/2);}})/1e6);
            System.out.printf("LinkedList middle insert/remove 100: %.3f ms%n",time(()->{for(int i=0;i<100;i++){int p=l.size()/2;l.add(p,-1);l.remove(p);}})/1e6);

            System.out.printf("ArrayList get-k 10000: %.3f ms%n",time(()->{long x=0;for(int i=0;i<10000;i++)x+=a.get(a.size()/2);})/1e6);
            System.out.printf("LinkedList get-k 10000: %.3f ms%n",time(()->{long x=0;for(int i=0;i<10000;i++)x+=l.get(l.size()/2);})/1e6);

            System.out.printf("ArrayList iteration: %.3f ms%n",time(()->{long x=0;for(int v:a)x+=v;})/1e6);
            System.out.printf("LinkedList iteration: %.3f ms%n",time(()->{long x=0;for(int v:l)x+=v;})/1e6);
        }

        ArrayDeque<String> upNext=new ArrayDeque<>();
        upNext.addLast("Song A"); upNext.addLast("Song B"); upNext.addFirst("Urgent Song");
        System.out.println("\\nArrayDeque Up Next: "+upNext);
        System.out.println("Play next: "+upNext.removeFirst());
        System.out.println("Remaining: "+upNext);

        // Plain-array front insertion example (manual shifting).
        String[] songs=new String[5]; int count=0;
        songs[count++]="Song A"; songs[count++]="Song B";
        for(int i=count;i>0;i--) songs[i]=songs[i-1];
        songs[0]="Song at front"; count++;
        System.out.println("Plain array after front insertion: "+Arrays.toString(Arrays.copyOf(songs,count)));
    }
}
