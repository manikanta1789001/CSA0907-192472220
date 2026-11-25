import java.util.*;

public class Pharmacy {
    static long lookupTime(Map<String,Integer> map,int lookups) {
        long sum=0; long start=System.nanoTime();
        for(int i=0;i<lookups;i++) sum+=map.get("M"+(i%map.size()));
        long elapsed=System.nanoTime()-start;
        if(sum==Long.MIN_VALUE)System.out.println(sum);
        return elapsed;
    }
    public static void main(String[] args) {
        int[] sizes={1000,10000,100000};
        for(int n:sizes) {
            Map<String,Integer> hash=new HashMap<>();
            Map<String,Integer> tree=new TreeMap<>();
            Map<String,Integer> linked=new LinkedHashMap<>();
            for(int i=0;i<n;i++) {
                String code="M"+i;
                hash.put(code,i); tree.put(code,i); linked.put(code,i);
            }
            System.out.println("\\nMedicines="+n);
            System.out.printf("HashMap 100000 lookups: %.3f ms%n",lookupTime(hash,100000)/1e6);
            System.out.printf("TreeMap 100000 lookups: %.3f ms%n",lookupTime(tree,100000)/1e6);
            System.out.printf("LinkedHashMap 100000 lookups: %.3f ms%n",lookupTime(linked,100000)/1e6);

            long s=System.nanoTime(); for(Map.Entry<String,Integer> e:tree.entrySet()){} 
            System.out.printf("TreeMap sorted report: %.3f ms%n",(System.nanoTime()-s)/1e6);
            s=System.nanoTime(); for(Map.Entry<String,Integer> e:linked.entrySet()){}
            System.out.printf("LinkedHashMap insertion-order report: %.3f ms%n",(System.nanoTime()-s)/1e6);

            s=System.nanoTime();
            List<String> keys=new ArrayList<>(hash.keySet()); Collections.sort(keys);
            for(String k:keys) hash.get(k);
            System.out.printf("HashMap + sorting for code report: %.3f ms%n",(System.nanoTime()-s)/1e6);
        }

        // Sorted-array alternative: binary search; insertion requires shifting.
        String[] sorted={"M101","M102","M103","M104","M105"};
        System.out.println("\\nBinary search M103 index: "+Arrays.binarySearch(sorted,"M103"));
        System.out.println("Sorted array lookup O(log n), insertion O(n), code report O(n).");
    }
}
