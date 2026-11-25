public class RedesignValidation {
    static String original(int n) {
        String result="";
        for(int i=0;i<n;i++) result=result+"Field"+i+"|";
        return result;
    }
    static String redesigned(int n) {
        StringBuilder result=new StringBuilder(n*8);
        for(int i=0;i<n;i++) result.append("Field").append(i).append('|');
        return result.toString();
    }
    static long measure(boolean useBuilder,int n,int repetitions) {
        long checksum=0, start=System.nanoTime();
        for(int r=0;r<repetitions;r++)
            checksum+=(useBuilder?redesigned(n):original(n)).length();
        long elapsed=System.nanoTime()-start;
        if(checksum==0)System.out.println("impossible");
        return elapsed;
    }
    public static void main(String[] args) {
        int[] sizes={100,1000,5000};
        int repetitions=5;
        for(int n:sizes) {
            long oldTime=measure(false,n,repetitions);
            long newTime=measure(true,n,repetitions);
            boolean same=original(n).equals(redesigned(n));
            System.out.println("\\nFields="+n+", repetitions="+repetitions);
            System.out.printf("Original String: %.3f ms%n",oldTime/1e6);
            System.out.printf("Redesigned StringBuilder: %.3f ms%n",newTime/1e6);
            System.out.println("Outputs identical: "+same);
        }
        System.out.println("\\nUse repeated runs and compare averages; JVM warm-up and GC affect timings.");
    }
}
