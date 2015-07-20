/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.commons.common.protocol.matchers.testmodel


/** This class is
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
case class TestBody(fieldNumber: Int,
                    fieldBoolean: Boolean,
                    fieldString: String,
                    fieldObject: Map[String, String],
                    fieldArray: Seq[String]) {
  val fieldNull: AnyRef = null
}
