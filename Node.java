import java.util.ArrayList;

public class Node<T extends Element> 
{
        Node<T> left; 
        Node<T> right; 
        
        ArrayList<T> list = new ArrayList<T>(); 
        
        int rank;
        int cKey; 
        int size; 

        public Node()
        {
        	
        }
        public String toString() 
        { 
            return " cKey : " + cKey + "\n left :" + left + "\n right : " + right; 

        } 

    } 
