import java.util.Iterator;

/**
 * Simple but much more efficient implementation of IDnaStrand. \ This
 * implementation uses Link Nodes to represent genomic/DNA data.
 * 
 * @author Jianchao Geng
 * @date October 25th, 2018
 *
 */
public class LinkStrand implements IDnaStrand{
	private class Node {
	   	String info;
	   	Node next;
	   	public Node(String s) {
	      	info = s;
	      	next = null;
	   	}
	   }
	
	   private Node myFirst,myLast;
	   private long mySize;
	   private int myAppends;
	   private int myIndex,myLocalIndex;
	   private Node myCurrent;

	   /**
	    * default constructor, calls to constructer with parameter
	    */
	   public LinkStrand(){
			this("");
		}
	   /**
		 * Create a strand representing s. No error checking is done to see if s
		 * represents valid genomic/DNA data.
		 * 
		 * @param s
		 *            is the source of cgat data for this strand
		 */
	   public LinkStrand(String s) {
			initialize(s);
		}
	   
		/**
		 * Initialize this strand so that it represents the value of source. No
		 * error checking is performed.
		 * 
		 * @param source
		 *            is the source of this enzyme
		 */
	   @Override 
		public void initialize(String source) {
		   Node n = new Node(source);
		   myAppends = 0;
		   mySize=source.length();
		   myFirst=n;
		   myLast=n;
		   myIndex=0;
		   myLocalIndex=0;
		   myCurrent=myFirst;
		}
	   
	   /**
		 * Simply append a strand of dna data to this strand. No error checking is
		 * done. This method isn't efficient; it doesn't use a StringBuilder or a
		 * StringBuffer.
		 * 
		 * @param dna
		 *            is the String appended to this strand
		 */
	   @Override 
		public IDnaStrand append(String dna) {
		   Node next= new Node(dna);
		   myLast.next=next;
		   myLast=next;
		   mySize+=dna.length();
		   myAppends++;
		   return this;
		}
	   
	   @Override 
		public int getAppendCount() {
			return myAppends;
		}
	   
	   /**
	    * return a string of the dna strand
	    */
	   @Override
		public String toString() {
			StringBuilder s = new StringBuilder("");
			Node n = myFirst;
			while (n!=null) {
				s.append(n.info);
				n=n.next;
			}
			return s.toString();
	   }
       
	   /**
	    * reverse the order of a dna strand
	    */
	   @Override 
	   public IDnaStrand reverse() {
		   //StringBuilder copy = new StringBuilder("");
		   StringBuilder last = new StringBuilder(myLast.info);
		   last.reverse();
		   LinkStrand listStrand=new LinkStrand(last.toString());
		   StringBuilder nlast = new StringBuilder(myFirst.info);
		   nlast.reverse();
		   Node newLast=new Node(nlast.toString());
		   Node newFirst=newLast;
		   Node n=myFirst.next;
		   while (n!=null) {
			   StringBuilder s=new StringBuilder(n.info);
			   s.reverse();
			   Node x=new Node(s.toString());
			   x.next=newFirst;
			   newFirst=x;
			   n=n.next;
		   }
		   Node t=newFirst.next;
		   while (t!=null) {
			   listStrand.append(t.info);
			   t=t.next;
		   }
		   return listStrand;
	   }
       
	   /**
	    * Return the gene at designated number of dna strand
	    * 
	    * @param index is the designated place on the dna strand
	    * 
	    */
	   @Override 
	   public char charAt(int index){
		   if (index<0||index>=mySize) 
			   throw new IndexOutOfBoundsException("Illegeal Index");
		   if (index>=myIndex) {
			   int count = myIndex;
			   int dex = myLocalIndex;
			   Node list = myCurrent;
			   while (count != index) {
				   count++;
				   dex++;
				   if (dex >= list.info.length()) {
					   dex = 0;
					   list = list.next;
				   }
			   }
			   myIndex=count;
			   myLocalIndex=dex;
			   myCurrent=list;
			   return list.info.charAt(dex);
		   }
		   else {
			   int count = 0;
			   int dex = 0;
			   Node list = myFirst;
			   while (count != index) {
				   count++;
				   dex++;
				   if (dex >= list.info.length()) {
					   dex = 0;
					   list = list.next;
				   }
			   }
			   myIndex=count;
			   myLocalIndex=dex;
			   myCurrent=list;
			   return list.info.charAt(dex);

		   }

	   }
	   
	   /**
		 * @return the number of base pairs in this strand
		 */
	   @Override
		public long size() {
			return mySize;
		}
	   
	   @Override
		public IDnaStrand getInstance(String source) {
			return new LinkStrand(source);
		}
}
