public class Element implements Comparable<Element> {

    private int key; 
    
    //constructor
    Element(int i) 
    { 
        this.key = i; 
    } 
    
    //get
    int getKey() 
    { 
        return key; 
    } 
    
    //set
    void setKey(int set) 
    { 
        this.key = set; 
    } 

    //compare key of the element
    public int compareTo(Element o) 
    {
        if (o.getKey() > this.getKey()) 
        {
            return -1; 
        } 
        if (o.getKey() < this.getKey()) 
        {
            return 1; 
        } 
        return 0; 
    } 
    
    //tostring
    public String toString() 
    { 
        return key + ""; 
    } 

} 