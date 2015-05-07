class test06{
    public static void main(String[] a){
	System.out.println(new Operator().compute());
    }
}

class Operator{
    
    boolean op1bool;
    boolean op2bool;
    int op1int;
    int op2int;
    boolean result;

    public int compute(){
    	int j;
    	
    	j=1;
		while(j<10)
		{
			j=j+1;
		}
		
		return j;
    }
}
