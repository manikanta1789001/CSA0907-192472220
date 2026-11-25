import java.util.*;

class Student {
    private String regNo; // mutable only to demonstrate the danger in the experiment
    private final String name;
    Student(String regNo,String name){this.regNo=regNo;this.name=name;}
    void changeRegNo(String newRegNo){this.regNo=newRegNo;}
    String getRegNo(){return regNo;}
    @Override public boolean equals(Object o){
        if(this==o)return true;
        if(!(o instanceof Student))return false;
        Student s=(Student)o;
        return Objects.equals(regNo,s.regNo);
    }
    @Override public int hashCode(){return Objects.hash(regNo);}
    @Override public String toString(){return regNo+" "+name;}
}
public class Attendance {
    public static void main(String[] args) {
        // Test 1: same object scanned twice.
        Student same=new Student("101","Arun");
        Set<Student> t1=new HashSet<>(); t1.add(same); t1.add(same);
        System.out.println("Test 1 - same object twice, expected size 1: "+t1.size());

        // Test 2: different objects but equal registration numbers.
        Set<Student> t2=new HashSet<>();
        t2.add(new Student("102","Divya")); t2.add(new Student("102","Divya"));
        System.out.println("Test 2 - equal regNo, expected size 1: "+t2.size());

        // Test 3: mutate a field used by hashCode after insertion.
        Student changing=new Student("103","Kumar");
        Set<Student> t3=new HashSet<>(); t3.add(changing);
        changing.changeRegNo("999");
        System.out.println("Test 3 - contains after key mutation (may be false): "+t3.contains(changing));
        System.out.println("Do not mutate equality/hash fields while an object is in a HashSet.");

        // Required assignment output.
        Set<Student> present=new HashSet<>();
        present.add(new Student("192311001","Arun"));
        present.add(new Student("192311002","Divya"));
        present.add(new Student("192311001","Arun"));
        System.out.println("Unique students: "+present.size());

        // Alternative 1: TreeSet with comparator on registration number.
        Set<Student> tree=new TreeSet<>(Comparator.comparing(Student::getRegNo));
        tree.add(new Student("201","A")); tree.add(new Student("201","Duplicate"));
        tree.add(new Student("202","B"));
        System.out.println("TreeSet unique by regNo: "+tree.size());

        // Alternative 2: HashMap keyed by regNo.
        Map<String,Student> map=new HashMap<>();
        map.put("301",new Student("301","C"));
        map.put("301",new Student("301","C updated"));
        map.put("302",new Student("302","D"));
        System.out.println("HashMap unique keys: "+map.size());
    }
}
