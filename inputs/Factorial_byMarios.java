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
        //a = 1+a;
        //a = b;
        //b = a+b;
        //System.out.println(b);
        b = num;
        //System.out.println(b);
        number = new int[num];
        if (num < 1)
            num_aux = 1 ;
        else
            num_aux = num * (this.ComputeFac(num-1)) ;
        return num_aux ;
    }
}
