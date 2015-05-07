class Factorial{
    public static void main(String[] a){
        System.out.println(new Fac().Init(11));
    }
}

class Fac {
	int a;
	int b;
	int size;
	int[] number;
	//boolean test1;
    public int Init(int sz){
    int temp;
    int[] test;
	//boolean cond;    
	size = sz ;
	//test1 = false;
	//cond = (true && true) && !test1;
	//test1 = cond;
	//System.out.println(cond);
	//System.out.println(size);
	number = new int[sz] ;
	
	number[0] = 20 ;
	number[1] = 7  ; 
	number[2] = 12 ;
	number[3] = 18 ;
	number[4] = 2  ; 
	number[5] = 11 ;
	number[6] = 6  ; 
	number[7] = 9  ; 
	number[8] = 19 ; 
	number[9] = 10  ;	
	number[10] = (number[0])+10;
	
	
	
	//temp = this.Print(5);
	System.out.println(number[9]);
	temp  = this.ComputeFac(number[9]);
	//test = new int[1] ;
	//test[0] = temp;
	System.out.println(8);
	return 0 ;	
    } 	
    public int ComputeFac(int num){
        int num_aux ;
        a = 1+a;
        b = a +b;
        System.out.println(b);
        if (num < 1)
            num_aux = 1 ;
        else
            num_aux = num * (this.ComputeFac(num-1)) ;
        return num_aux ;
    }
    public int Print(int n){
		int j ;
		
		
		
		j = 0 ;
		System.out.println(size);
		while (j < size) {
			System.out.println(number[j]);
			j = j + 1 ;
		}
		
		return 0 ;
    }   

    
}
