package OtherClasses;


    public class HT implements java.io.Serializable {


        //static keyword means the value is the same for every instance of the class
        //final keyword means once the variable is assigned a value it can never be changed (cannot be inherited)
        public static final class Node {
            String key;
            Node next;
            //int count;
            String value;
            Node(String k, String v, Node n) { key = k; value = v; next = n; }

            public String getKey(){
                return key;
            }

            public Node getNext(){
                return next;
            }


        }

        public Node[] table = new Node[8]; // size is always a power of 2
        int size = 0; //number of keys currently present in hash table

        //locate correct index, then traverse Nodes at that index to look for given key
        public boolean contains(String key) {
            int h = key.hashCode(); // h= hash code
            //System.out.println("h= "+h);
            int i = h & (table.length - 1);// bitwise AND operation to get to correct index number i
            //System.out.println("i= "+i);
            for (Node e = table[i]; e != null; e = e.next) {
                if (key.equals(e.key))
                    return true;
            }
            return false;
        }

        public void add(String key, String value) {
            //System.out.print("(key to add: "+ key+")\t");
            int h = key.hashCode();
            int i = h & (table.length - 1);
            //System.out.print("(index: "+ i+")\t");
            Node e = table[i], head= table[i]; //e is index of where key could be

            //search through linked list at index i for key
            while(e!=null){//while not at end of linked list
                if(key.equals(e.key)){//if find key already in linked list
                    //System.out.print("(key found in list with count= "+ e.count+")\t");
                    e.value += value;//increments count of Node containing key
                    //System.out.println("(return occurred)\t"+ "(new Count= "+e.count+")\t");
                    return; //exit add method
                }
                e=e.next;//go to next node
            }

            //System.out.print("(key was not already in list)\t");
            //key was not found if reach this point, so must insert key
            Node newNode= new Node(key, value, head);//new node's next points to head; newNode's count is 1
            //System.out.print("(newNode key= "+ newNode.key+")\t");
            //System.out.print("(newNode count= "+ newNode.count+")\t");
            table[i]= newNode;//newNode is now the top Node at index i
            //System.out.println("END of added node");

            ++size;
            if ((float)size/table.length >= 0.75f) //resize table when table is ~75% full
                resize();
        }

        //my own code for exercise 2
        //returns count of given key
        public String getValue(String key){
            //System.out.print("(INSIDE getCount: key to add: "+ key+")\t");
            int h = key.hashCode(); // h= hash code
            int i = h & (table.length - 1);// bitwise AND operation to get to correct index number i
            //System.out.print("(index: "+ i+")\t");

            for (Node e = table[i]; e != null; e = e.next) {
                if (key.equals(e.key)) {
                    //System.out.println("(found key in list= "+e.key+")\t"+"(count= "+e.count+")\t");
                    return e.value;
                }
            }
            //System.out.println("inside getCount but key NOT found");
            return null;
        }


        //doubles hash table size (ie. makes a new table twice the size)
        public void resize() {
            //System.out.print("(INSIDE resize method)\t");
            Node[] oldTable = table;
            int oldCapacity = oldTable.length;
            //System.out.print("(old table length: "+ oldCapacity+")\t");
            int newCapacity = oldCapacity << 1; //doubles; same as oldCapacity*2
            //System.out.print("(newCapacity= "+ newCapacity+")\t");
            Node[] newTable = new Node[newCapacity];
            //System.out.print("(newTable's length: "+ newTable.length+")\t");

            //puts values from old table into new
            for (int i = 0; i < oldCapacity; ++i) { //for every index of old table
                for (Node e = oldTable[i]; e != null; e = e.next) {//for every Node in linked list
                    int h = e.key.hashCode();
                    int j = h & (newTable.length - 1);
                    newTable[j] = new Node(e.key, e.value, newTable[j]);
                }
            }
            table = newTable;
        }

//    public void remove(String key) {
//        int h = key.hashCode();
//        int i = h & (table.length - 1);
//        Node e = table[i], p = null;
//        while (e != null) {
//            if (key.equals(e.key)) {
//                e.count++;//increments count
//                if (p == null) //if first Node at the index
//                    table[i] = e.next;//sets table[i] to null
//                else
//                    p.next = e.next;
//                break; //terminates while loop immediately
//            }
//            p = e;
//            e = e.next;
//        }
//    }

        //the nested for loops here is the basic structure to traverse all Nodes
        public void printAll() {
            for (int i = 0; i < table.length; ++i)
                for (Node e = table[i]; e != null; e = e.next)
                    System.out.println(e.key);
            //System.out.println(e.key + " count="+ e.count);
        }

        public int getSize(){
            return size;
        }

        public int getTableLength(){
            return table.length;
        }

        //returns Node[] table
        public Node[] getTable(){
            return table;
        }


  /*  private void writeObject(ObjectOutputStream s) throws Exception {
        s.defaultWriteObject();
        s.writeInt(size);
        for (int i = 0; i < table.length; ++i) {
            for (Node e = table[i]; e != null; e = e.next) {
                s.writeObject(e.key);
            }
        }
    }

    //ObjectInputStream class is mainly used to deserialize the primitive data and objects
    //which are written by using ObjectOutputStream
    //NOTE: possibly can only use ObjectInputStream to read a file that was created using ObjectOutputStream
    private void readObject(ObjectInputStream s) throws Exception {
        s.defaultReadObject(); //reads the non-static and non-transient fields of the current class from this stream
        int n = s.readInt(); // readInt() reads a 32-bit int
        for (int i = 0; i < n; ++i)
            add(s.readObject());
    }*/





}
