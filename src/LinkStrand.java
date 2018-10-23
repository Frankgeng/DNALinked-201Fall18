import java.util.Iterator;

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

	   public LinkStrand(){
			this("");
		}
	   
	   public LinkStrand(String s) {
			initialize(s);
		}
	   
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

	   @Override 
	   public char charAt(int index){
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
	   
	   @Override
		public long size() {
			return mySize;
		}
	   
	   @Override
		public IDnaStrand getInstance(String source) {
			return new LinkStrand(source);
		}
}
