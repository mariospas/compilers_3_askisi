package my_src;

import java.util.*;
import java.lang.Exception;

import javax.swing.Spring;

import my_src.Assume;
import syntaxtree.*;
import visitor.*;

public class MiniJavaToSpiglet extends DepthFirstVisitor
{
	LinkedHashMap<String,LinkedHashMap<String,Fun_or_Ident>> Table;
	LinkedHashMap<String,String> DeclClasses;   //classname,extend class
	
	LinkedHashMap<String,LinkedHashMap<Integer,String>> VTable;
	LinkedHashMap<String,LinkedHashMap<Integer,String>> IdsTable;
	
	LinkedHashMap<String,String> arg = new LinkedHashMap<String,String>();

	String spiglet_code = "";
	String class_name = null;
	String extend_class = null;
	String method_call = null;
	String method_name = null;
	int temp_count;
	int label;
	String value = null;
	String expr = null;
	String id_string = null;
	boolean flag = false;
	
	public MiniJavaToSpiglet(Goal n, LinkedHashMap<String,LinkedHashMap<String,Fun_or_Ident>> Table1,
	LinkedHashMap<String,String> DeclClasses1,
	LinkedHashMap<String,LinkedHashMap<Integer,String>> VTable1,
	LinkedHashMap<String,LinkedHashMap<Integer,String>> IdsTable1) throws Exception, SemError
	{
		Table = Table1;
		DeclClasses = DeclClasses1;
		VTable = VTable1;
		IdsTable = IdsTable1;
		temp_count = 0;
		label = 0;
		System.out.println("here in root");
		n.f0.accept(this);
		n.f1.accept(this);
		
	}
	
	public int AssignTemp()
	{
		temp_count++;
		return temp_count;
	}
	
	public int CurrentTemp()
	{
		return temp_count;
	}
	
	public int AssignLabel()
	{
		label++;
		return label;
	}
	
	
	/* Main */
	 /**
	    * <PRE>
	    * f0 -> "class"
	    * f1 -> Identifier()
	    * f2 -> "{"
	    * f3 -> "public"
	    * f4 -> "static"
	    * f5 -> "void"
	    * f6 -> "main"
	    * f7 -> "("
	    * f8 -> "String"
	    * f9 -> "["
	    * f10 -> "]"
	    * f11 -> Identifier()
	    * f12 -> ")"
	    * f13 -> "{"
	    * f14 -> ( VarDeclaration() )*
	    * f15 -> ( Statement() )*
	    * f16 -> "}"
	    * f17 -> "}"
	    * </PRE>
	    */
   public void visit(MainClass n) throws Exception, SemError{
	   System.out.println("here in main");
	   class_name = "MAIN";
	   spiglet_code = "MAIN \n";	       
	   n.f15.accept(this);	       
       spiglet_code +="END\n";
   }
   
   
   /* Type Declaration */
   /**
    * Grammar production:
    * <PRE>
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    * </PRE>
    */
   public void visit(TypeDeclaration n) throws Exception, SemError{
	   System.out.println("here in typedeclar");
	   n.f0.accept(this);
   }
   
   
   /* Class Declaration */
   /**
    * Grammar production:
    * <PRE>
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    * </PRE>
    */
   public void visit(ClassDeclaration n) throws Exception, SemError{
	   System.out.println("here in class declar");
	   n.f1.accept(this);
	   class_name = n.f1.f0.toString();
	   n.f4.accept(this);
   }
   
   //Class extends
 	/**
 	 * Grammar production:
 	 * f0 -> "class"
 	 * f1 -> Identifier()
 	 * f2 -> "extends"
 	 * f3 -> Identifier()
 	 * f4 -> "{"
 	 * f5 -> ( VarDeclaration() )*
 	 * f6 -> ( MethodDeclaration() )*
 	 * f7 -> "}"
 	 */
   public void visit(ClassExtendsDeclaration n) throws Exception, SemError{
	   System.out.println("here in class extend decl");
	   n.f1.accept(this);
	   class_name = n.f1.f0.toString();
	   extend_class = n.f3.f0.toString();
	   n.f6.accept(this);
   }
 	
   
   /**
    * Grammar production:
    * <PRE>
    * f0 -> "public"
    * f1 -> Type()
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( FormalParameterList() )?
    * f5 -> ")"
    * f6 -> "{"
    * f7 -> ( VarDeclaration() )*
    * f8 -> ( Statement() )*
    * f9 -> "return"
    * f10 -> Expression()
    * f11 -> ";"
    * f12 -> "}"
    * </PRE>
    */
   public void visit(MethodDeclaration n) throws Exception, SemError{
	   System.out.println("here in method decl");
	   n.f2.accept(this);
	   method_name = n.f2.f0.toString();
	   arg.clear();
	 
	   /* Save args to TEMPs */
	   arg.put("this","TEMP "+ AssignTemp() );   //this
	   
	   /* find number of arguments */
	   n.f4.accept(this);
	   spiglet_code += "\n"+class_name+"_"+method_name+" [ "+arg.size()+" ]"+"\n";
	   
	   /* Save varDeclarations to TEMPs */
	   n.f7.accept(this);
	   
	   /* Begin Statement */
	   spiglet_code += "BEGIN\n";
	   n.f8.accept(this);
	   
	   spiglet_code += "RETURN\n";
	   value = "right";
	   spiglet_code += "\t";
	   n.f10.accept(this);
	   	
	   spiglet_code += " \nEND\n";
   }   
   
   
   /* FormalParameter */
   /**
    * Grammar production:
    * <PRE>
    * f0 -> Type()
    * f1 -> Identifier()
    * </PRE>
    */
   public void visit(FormalParameter n) throws Exception, SemError{
	   System.out.println("here in formalparameter");
	   arg.put(n.f1.f0.toString(),"TEMP "+ AssignTemp());
   }
   
   
   /**
    * Grammar production:
    * <PRE>
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    * </PRE>
    */
   public void visit(VarDeclaration n) throws Exception, SemError{
	   System.out.println("here in var decl");
	   arg.put(n.f1.f0.toString(),"TEMP "+ AssignTemp());
   }
   
   /* Statement */
   /**
    * Grammar production:
    * <PRE>
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    * </PRE>
    */
   public void visit(Statement n) throws Exception, SemError{
	   System.out.println("here in statement");
	   n.f0.accept(this);      //epistrofh expr
   }	
   
   /* AssignmentStatement */
   /**
    * Grammar production:
    * <PRE>
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    * </PRE>
    */
   public void visit(AssignmentStatement n) throws Exception, SemError{
	   System.out.println("here in Assign State");
	   	
   		//flag = true;
   		value = "right";
   		n.f2.accept(this);
   		
   		value = "left";
   		n.f0.accept(this);     //epistrofh id_string
   		spiglet_code += "TEMP "+CurrentTemp()+"\n";
   		
   		String eq = new String(expr);
   		if( eq != null && eq.equals("this") )
   			spiglet_code += " TEMP 0";
   		//spiglet_code += "\n";
   		expr = new String(id_string);
   }
   
   
   
   /* IfStatement */
   /**
    * Grammar production:
    * <PRE>
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
    * </PRE>
    */
   public void visit(IfStatement n) throws Exception, SemError{
	   System.out.println("here in IF state");
	   value = "right";
	   n.f2.accept(this);
	   String exitLabel = "L"+AssignLabel();
	   spiglet_code += "\tCJUMP TEMP "+CurrentTemp()+" "+exitLabel+"\n";
	   
	   n.f4.accept(this);
	   String finishLabel = "L"+AssignLabel();
	   spiglet_code += "\tJUMP "+finishLabel+"\n";
	   spiglet_code += exitLabel+"\tNOOP\n";
	   
	   n.f6.accept(this);
	   spiglet_code += finishLabel+"\tNOOP\n";
   }
   
   /* WhileStatement */
   /**
    * Grammar production:
    * <PRE>
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    * </PRE>
    */
   public void visit(WhileStatement n) throws Exception, SemError{
	   System.out.println("here in WHILE state");
	   String stateLabel = "L"+AssignLabel();
	   spiglet_code += stateLabel+"\tNOOP\n";
	   value = "right";
	   n.f2.accept(this);
	   String exitLabel = "L"+AssignLabel();
	   spiglet_code += "\tCJUMP TEMP "+CurrentTemp()+" "+exitLabel+"\n";
	   
	   n.f4.accept(this);
	   spiglet_code +="\t JUMP "+stateLabel+"\n";
	   spiglet_code +=exitLabel+"\tNOOP\n";
   }
   		
   
   /* Print Statement */
   /**
    * <PRE>
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    * </PRE>
    */
   public void visit(PrintStatement n) throws Exception, SemError{
	   value = "right";
	   n.f2.accept(this);
	   spiglet_code += "\tPRINT TEMP "+CurrentTemp()+"\n";
	   expr = "print";
   }
   
   
   /* Expression */
   /**
    * <PRE>
    * f0 -> AndExpression()
    *       | CompareExpression()
    *       | PlusExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | ArrayLookup()
    *       | ArrayLength()
    *       | MessageSend()
    *       | PrimaryExpression()
    * </PRE>
    */
   public void visit(Expression n) throws Exception, SemError{
	   n.f0.accept(this);
	   //if(expr != null && !class_name.equals("MAIN")) 
   }
   
   
   /* CompareExpression*/
   /**
    * Grammar production:
    * <PRE>
    * f0 -> PrimaryExpression()
    * f1 -> "&lt;"
    * f2 -> PrimaryExpression()
    * </PRE>
    */
   public void visit(CompareExpression n) throws Exception, SemError{
	   n.f2.accept(this);
	   
	   spiglet_code += "\tMOVE TEMP "+AssignTemp()+" LT ";
	   n.f0.accept(this);
	   spiglet_code +="TEMP "+(CurrentTemp()-1)+"\n";
	   expr = "compare";
   }
   
   /* MinusExpression */
   /**
    * Grammar production:
    * <PRE>
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    * </PRE>
    */
   public void visit(MinusExpression n) throws Exception, SemError{
	    
	   	n.f2.accept(this);
		spiglet_code +="\tMOVE TEMP "+AssignTemp()+" MINUS ";
		n.f0.accept(this);
		spiglet_code +="TEMP "+(CurrentTemp()-1)+"\n";
		
		expr = "MINUS";
   }
   
   /* PlusExpression */
   /**
    * Grammar production:
    * <PRE>
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    * </PRE>
    */
   public void visit(PlusExpression n) throws Exception, SemError{
	    
	   	n.f2.accept(this);
		spiglet_code +="\tMOVE TEMP "+AssignTemp()+" PLUS ";
		n.f0.accept(this);
		spiglet_code +="TEMP "+(CurrentTemp()-1)+"\n";
		
		expr = "PLUS";
   }
   
   
   /* TimesExpression */
   /**
    * Grammar production:
    * <PRE>
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    * </PRE>
    */
   public void visit(TimesExpression n) throws Exception, SemError{
	   	n.f2.accept(this);
		spiglet_code +="\tMOVE TEMP "+AssignTemp()+" TIMES ";
		n.f0.accept(this);
		spiglet_code +="TEMP "+(CurrentTemp()-1)+"\n";
		
		expr = "TIMES";
   }
 
   
   /* PrimaryExpression */
   /**
    * <PRE>
    * f0 -> IntegerLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | ArrayAllocationExpression()
    *       | AllocationExpression()
    *       | NotExpression()
    *       | BracketExpression()
    * </PRE>
    */
   public void visit(PrimaryExpression n) throws Exception, SemError{	 
   		n.f0.accept(this);	   		
   }
   
   /* IntegerLiteral */
   /**
    * Grammar production:
    * <PRE>
    * f0 -> &lt;INTEGER_LITERAL&gt;
    * </PRE>
    */
   public void visit(IntegerLiteral n) throws Exception, SemError{
	   spiglet_code += "\tMOVE TEMP "+AssignTemp()+" "+n.f0.toString() + " \n";
	   expr = "integerliteral";
   }
   
   /* ThisExpression */
   /**
    * Grammar production:
    * <PRE>
    * f0 -> "this"
    * </PRE>
    */
   public void visit(ThisExpression n) throws Exception, SemError{
   		//spiglet_code += " TEMP 0 ";
   		expr = "this";
   }
   
   
   /* Message Send */
   /**
    * <PRE>
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ExpressionList() )?
    * f5 -> ")"
    * </PRE>
    */
   public void visit(MessageSend n) throws Exception, SemError{
	   String func = n.f2.f0.toString();
	   value = "call";
	   n.f0.accept(this);
	   String first = new String(expr);
	   String ident = new String(expr);
	   
	   String loc;
	   String temp = "TEMP 0";
	   
	   if(first.equals("this"))
	   {
		   loc = class_name+"_"+func;
		   first = class_name;
	   }
	   else if(DeclClasses.containsKey(first))
	   {
		   loc = first+"_"+func;
	   }
	   else
	   {
		   Fun_or_Ident foi = Table.get(class_name).get("#"+this.method_name);
		   LinkedHashMap<String, String> args = foi.arg;
		   LinkedHashMap<String, String> vars = foi.var;
		   
		   if( args.containsKey(first) )
		   {
			   first = args.get(first);			   
			   
		   }
		   else if ( vars.containsKey(first) )/* Search identifier's type on declared vars  */
		   {
			   first = vars.get(first);	
		   }
		   else
		   {
			   String className = class_name;
			   String extendClass;
			   foi = Table.get(className).get(first);
			   while(foi == null)
			   {
				   extendClass = this.DeclClasses.get(className);
				   className = extendClass;
				   foi = Table.get(className).get(first);
			   }
			   first = foi.Type;
		   }
		   
		   loc = first+"_"+func;
		   temp = this.arg.get(ident);
		   
	   }
	   
	   int pos = 0;
	   int table_size = VTable.get(first).size();
	   for(int i=0; i<table_size; i++)
	   {
			String name = this.VTable.get(first).get(i);
			if(name.equals(loc))
			{
				pos = i+1;
				break;
			}
	   }
	   
	   int save_temp_this = AssignTemp();
	   spiglet_code += "\tMOVE TEMP "+ save_temp_this +" "+temp+"\n";
	   spiglet_code += "\tHLOAD TEMP "+ AssignTemp() +" TEMP "+ (CurrentTemp() - 1) + " 0\n";
	   int save_temp_num = AssignTemp();
	   spiglet_code += "\tHLOAD TEMP "+ save_temp_num +" TEMP "+ ( CurrentTemp() - 1 ) +" "+ pos*4 + "\n";
	   
	   value = "list";
	   n.f4.accept(this);
	   spiglet_code += "\tMOVE TEMP "+AssignTemp()+" CALL "+"TEMP "+save_temp_num+"( TEMP "+save_temp_this+" TEMP "+(CurrentTemp()-1)+" )\n";
	   
   }
   
   
   
   /* Identifier */
   /**
    * Grammar production:
    * <PRE>
    * f0 -> &lt;IDENTIFIER&gt;
    * </PRE>
    */
   public void visit(Identifier n) throws Exception, SemError{
	   System.out.println("here in IDENT");
	   
	   String id = n.f0.toString();
	   String className = this.class_name;
	   String extendClass = null;
	   boolean exist = false;
	   int pos = 0;
	   
	   //elenxo an briskete mesa sta class ids
	   if(this.value != null)
	   {
			extendClass = DeclClasses.get(className);
			while(extendClass != null)
			{
				if(this.IdsTable.get(className).containsValue(className+"_"+id))
				{
					exist = true;
					break;
				}
				extendClass = DeclClasses.get(extendClass);
				className = extendClass;
			}
			
			
			if(exist)
			{
				int table_size = IdsTable.get(className).size();
				for(int i=0; i<table_size; i++)
				{
					String id_name = this.IdsTable.get(className).get(i);
					if(id_name.equals(className+"_"+id))
					{
						pos = i+1;
						break;
					}
				}
				
				if(value.equals("left"))
				{
					spiglet_code += "\tHSTORE TEMP 0 "+ pos*4 +" ";
				}
				else if(value.equals("right"))
				{
					id_string = " \tHLOAD TEMP "+ AssignTemp() +" TEMP 0 "+ pos*4+"\n";
					spiglet_code += id_string;
					this.expr = new String(id_string);
					return;
				}
				else if(value.equals("left_array"))
				{
					id_string = "\tMOVE TEMP "+ AssignTemp() + " TIMES "+ pos+ " 4\n";
					id_string += " \tHLOAD TEMP "+ AssignTemp() + " PLUS TEMP 0 TEMP "+ (CurrentTemp() -1 )+" 0\n";
					spiglet_code += id_string;
					this.expr = new String(id_string);
					return;
				}
				else if(value.equals("list"))
				{
					spiglet_code += "\tHLOAD TEMP "+ AssignTemp() +" TEMP 0 "+pos*4 + "\n";
				}
				
			}
			else if(value.equals("call"))
			{
				id_string = id;
				this.expr = new String(id_string);
				return;
			}
			else
			{
				if(value.equals("left")) spiglet_code += "\tMOVE "+ arg.get(id)+" ";
				else if( value.equals("right") || value.equals("list") ) spiglet_code += arg.get(id)+" ";
				id_string = arg.get(id)+" ";
				this.expr = new String(id_string);
				return;
			}
	   }
	   else if( arg.get(id) != null )
	   {
		   spiglet_code += arg.get(id)+" ";
	   }
	   id_string = id;
	   this.expr = new String(id_string);
	   return;
	   
	   
	   
   }
   
   
}