class Factorial{
    public static void main(String[] a){
        System.out.println(new Fac().ComputeFac(10));
    }
}

class Fac {
	int a;
	int b;
	
    public int ComputeFac(int num){
        int num_aux ;
        a = 1+a;
        b = a+b;
        System.out.println(b);
        if (num < 1)
            num_aux = 1 ;
        else
            num_aux = num * (this.ComputeFac(num-1)) ;
        return num_aux ;
    }
}
