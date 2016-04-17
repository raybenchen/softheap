import java.util.ArrayList;

public class SoftHeap <E extends Element>
{
	private double R;
	private  PriorityQueue<E> linkedTree = null;
	
	public SoftHeap()
	{
		R = Math.abs(Math.log(0.333) / Math.log(2)) + 4;
	}
	
	public static int log2(int n) 
	{
        if (n <= 0) 
        {
            throw new IllegalArgumentException();
        }
        return 31 - Integer.numberOfLeadingZeros(n);
    } 
	
	PriorityQueue<E> makeHeap(E e) 
	{ 
        PriorityQueue<E> P = new PriorityQueue<E>(); 
        P.first = makeTree(e); 
        P.rank = 0; 
        return P; 
    } 
	
	Tree<E> makeTree(E e) 
	{ 
        Tree<E> T = new Tree<E>(); 
        T.root = makeNode(e); 
        T.next = null; 
        T.prev = null; 
        T.rank = 0; 
        T.sufMin = T; 
        return T; 
    } 
	
	Node<E> makeNode(E e) 
	{ 
        Node<E> x = new Node<E>(); 
        x.list.add(e); 
        x.cKey = e.getKey(); 
        x.rank = 0; 
        x.right = null; 
        x.left = null; 
        x.size = 1; 
        return x; 
    } 
	
	public void insertEle(E e) 
	{ 
        if (linkedTree == null) 
        {
            linkedTree = makeHeap(e); 
        } else 
        { 
            linkedTree = insert(linkedTree, e); 
        } 
    } 
	
	public PriorityQueue<E> insert(PriorityQueue<E> P, E e) 
	{ 
		return meld(P, makeHeap(e)); 
	} 
	
	public PriorityQueue<E> meld(PriorityQueue<E> P, PriorityQueue<E> Q) { 
        mergeInto(P, Q); 
        repeatedCombine(P, P.rank); 
        return P; 
    }
	
	public void mergeInto(PriorityQueue<E> P, PriorityQueue<E> Q) 
	{
        Tree<E> T1 = P.first; 
        Tree<E> T2 = Q.first; 
        insertTree(P, T1, T2); 
    } 
	
	public void insertTree(PriorityQueue<E> P, Tree<E> T1, Tree<E> T2) {
        Tree<E> temp = T1.next; 
        T1.next = T2; 
        T2.prev = T1; 
        if (temp != null) {
            T2.next = temp; 
            temp.prev = T1; 
        } 
    } 

	public void repeatedCombine(PriorityQueue<E> Q, int rank) 
	{
		Tree<E> T = Q.first; 
	    Tree<E> newTree = null; 
	    int largestRank = 0;
	    Tree<E> lastTree = null; 
	        
	    while ((T != null) && (T.next != null)) 
	    { 
	    	largestRank = T.rank; 
	        lastTree = T; 
	        
	        if (T.rank == T.next.rank) 
	        { 
	        	T.root = combine(T.root, T.next.root); 
	            T.rank = T.root.rank; 
	            T.next = T.next.next; 
	       
	            if (T.next != null) 
	            {
	            	T.next.prev = T; 
	            } 
	            
	            largestRank = T.rank; 
	            lastTree = T; 
	            } 
	        else 
	        { 
	        	T = T.next; 
	        } 
	    } 
	    T = newTree; 
	    if (largestRank > Q.rank) 
	    { 
	    	Q.rank = largestRank; 
	    } 
	    updateSuffixMin(lastTree); 
	}
	
	public Node<E> combine(Node<E> x, Node<E> y) { 
        Node<E> z = new Node<E>(); 
        z.left = x; 
        z.right = y; 
        z.rank = x.rank + 1; 
        if (z.rank <= R) { 
            z.size = 1; 
        } else { 
            z.size = (3 * x.size + 1) / 2;
        } 
        sift(z); 
        return z; 
    } 
	
	public void updateSuffixMin(Tree<E> T) 
	{ 
        while (T != null) 
        { 
            if (T.next != null) 
            {
                if (T.root.cKey <= T.next.sufMin.root.cKey) 
                {
                    T.sufMin = T; 
                } else 
                { 
                    T.sufMin = T.next.sufMin; 
                } 
            } else 
            { 
                T.sufMin = T; 
            } 
            T = T.prev; 
        } 
    }
	
	public void sift(Node<E> x) 
	{ 
		Node<E> tempNode = null; 
	    while ((x.list.size() < x.size) && (!leaf(x))) 
	    { 
	    	if ((x.left == null) || ((x.right != null) && (x.left.cKey > x.right.cKey))) 
	        {
	    		tempNode = x.left; 
	            x.left = x.right; 
	            x.right = tempNode; 
	        } 

	        x.list.addAll(x.left.list); 
	        x.cKey = x.left.cKey; 
	        x.left.list.clear(); 
	        if (leaf(x.left)) 
	        { 
	        	x.left = null; 
	        } 
	        else 
	        { 
	        	sift(x.left); 
	        } 
	   } 
	} 
	public boolean leaf(Node<E> x) 
	{ 
		return ((x.left == null) && (x.right == null));
	} 
	
	public E extractMin() { 
        PriorityQueue<E> P = linkedTree; 
        if (P.first == null)
            return null; 
        Tree<E> T = P.first.sufMin; 
        Node<E> x = T.root; 
        E e = pickAndRemoveElem(x.list); 
        if (x.list.size() <= (x.size) / 2) 
        {
            if (!leaf(x)) 
            { 
                sift(x); 
                updateSuffixMin(T); 
            } 

        } 
        if ((x.list == null) || (x.list.size() == 0)) 
        {
            removeTree(P, T); 
        } 
        return e; 
    }
	
	public void removeTree(PriorityQueue<E> P, Tree<E> T) 
	{
        P.first = T.next; 
        P.first.prev = null; 
    } 
	
	public E pickAndRemoveElem(ArrayList<E> list) 
	{ 
		return (list.remove(list.size() - 1));
	} 
	
	void init() 
	{ 
        R = Math.abs(Math.log(0.333) / Math.log(2)) + 4;
    }
}
