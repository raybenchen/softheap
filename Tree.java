 public class Tree<T  extends Element> 
 {
        Node<T> root; 
        Tree<T> next; 
        Tree<T> prev; 
        Tree<T> sufMin; 
        int rank; 
        
        public Tree()
        {
        	
        }
        
        public String toString() 
        { 
            return "" + root; 
        } 
    } 
