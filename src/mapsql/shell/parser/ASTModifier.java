/* Generated By:JJTree: Do not edit this line. ASTModifier.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package mapsql.shell.parser;

public
class ASTModifier extends SimpleNode {
  public ASTModifier(int id) {
    super(id);
  }

  public ASTModifier(MapSQL p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MapSQLVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=1a6dc83ef1aacf37365e5bb0db567b50 (do not edit this line) */
