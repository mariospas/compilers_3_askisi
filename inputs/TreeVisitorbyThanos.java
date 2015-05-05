// The classes are basically the same as the BinaryTree 
// file except the visitor classes and the accept method
// in the Tree class

class TreeVisitor{
    public static void main(String[] a){
	System.out.println(new TV().Start());
    }
}

class A{
	int a;
	public int foo(){return 0;}
}

class B extends A{
	int a;
	int b;
	public int foo2(){return 1;}
	public int foo3(){return 1;}
	public int foo(){return 1;}
}

class C extends B {
	int c;
	public int fooB(){return 1;}
	public int foo(){return 1;}
	public int foo3(){return 1;}
	public int foo4(){return 1;}
	
}

class D extends A{
	public int foo(){return 1;}
	public int foo5(){return 1;}
	public int foo6(){return 1;}
}
