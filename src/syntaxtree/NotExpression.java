//
// Generated by JTB 1.3.2 DIT@UoA patched
//

package syntaxtree;

/**
 * Grammar production:
 * f0 -> "!"
 * f1 -> Clause()
 */
public class NotExpression implements Node {
   public NodeToken f0;
   public Clause f1;

   public NotExpression(NodeToken n0, Clause n1) {
      f0 = n0;
      f1 = n1;
   }

   public NotExpression(Clause n0) {
      f0 = new NodeToken("!");
      f1 = n0;
   }

   public void accept(visitor.Visitor v) throws Exception{
      v.visit(this);
   }
   public <R,A> R accept(visitor.GJVisitor<R,A> v, A argu) throws Exception{
      return v.visit(this,argu);
   }
   public <R> R accept(visitor.GJNoArguVisitor<R> v) throws Exception {
      return v.visit(this);
   }
   public <A> void accept(visitor.GJVoidVisitor<A> v, A argu) throws Exception{
      v.visit(this,argu);
   }
}

