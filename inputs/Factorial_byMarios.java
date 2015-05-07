class Factorial{
    public static void main(String[] a){
        System.out.println(new Fac().ComputeFac(10));
    }
}

class Fac {
	int a;
	int b;
	int[] number ;
	
    public int ComputeFac(int num){
        int num_aux ;
        int ko;
        a = num;
        b = a;
        b = a+b;
        ko = this;
        
        System.out.println(b);
        b = num;
        //System.out.println(b);
        //number = new int[num];
        //number[1] = b; 
        System.out.println(num);
        if (num < 1)
            num_aux = 1 ;
        else
            num_aux = num * (this.ComputeFac(num-1)) ;
        return num_aux ;
    }
}
