//
// Generated by JTB 1.3.2 DIT@UoA patched
//

package syntaxtree;

/**
 * Grammar production:
 * f0 -> "int"
 */
public class IntegerType implements Node {
   public NodeToken f0;

   public IntegerType(NodeToken n0) {
      f0 = n0;
   }

   public IntegerType() {
      f0 = new NodeToken("int");
   }

   public void accept(visitor.Visitor v) throws Exception{
      v.visit(this);
   }
   public <R,A> R accept(visitor.GJVisitor<R,A> v, A argu) throws Exception{
      return v.visit(this,argu);
   }
   public <R> R accept(visitor.GJNoArguVisitor<R> v) throws Exception{
      return v.visit(this);
   }
   public <A> void accept(visitor.GJVoidVisitor<A> v, A argu) throws Exception{
      v.visit(this,argu);
   }
}

