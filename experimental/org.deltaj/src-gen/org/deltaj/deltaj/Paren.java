/**
 * <copyright>
 * </copyright>
 *
 */
package org.deltaj.deltaj;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Paren</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.deltaj.deltaj.Paren#getExpression <em>Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.deltaj.deltaj.DeltajPackage#getParen()
 * @model
 * @generated
 */
public interface Paren extends Expression
{
  /**
   * Returns the value of the '<em><b>Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Expression</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Expression</em>' containment reference.
   * @see #setExpression(Expression)
   * @see org.deltaj.deltaj.DeltajPackage#getParen_Expression()
   * @model containment="true"
   * @generated
   */
  Expression getExpression();

  /**
   * Sets the value of the '{@link org.deltaj.deltaj.Paren#getExpression <em>Expression</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Expression</em>' containment reference.
   * @see #getExpression()
   * @generated
   */
  void setExpression(Expression value);

} // Paren
