/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.restaurants.common.protocol.matchers


import org.specs2.mutable.SpecWithJUnit
import com.wix.restaurants.common.protocol.api.Error
import com.wix.restaurants.common.protocol.matchers.ErrorMatchers._


/** The Unit-Test of the [[Error]] class.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
class ErrorMatchersTest extends SpecWithJUnit {
  val code = "some error code"
  val desc = "some error description"
  val error = Error(code, desc)


  "beError" should {
    "match code" in {
      error must beError(code = ===(code))
    }

    "match description" in {
      error must beError(description = ===(desc))
    }

    "match both code and description" in {
      error must beError(code = ===(code), description = ===(desc))
    }

    "produce clear description when failing to match code" in {
      val someOtherErrorCode = "some other code"

      (error must beError(code = ===(someOtherErrorCode))) must
        throwAn[Exception](message = s"error code '$code' is not equal to '$someOtherErrorCode'")
    }

    "produce clear description when failing to match description" in {
      val someOtherErrorDescription = "some other description"

      (error must beError(description = ===(someOtherErrorDescription))) must
        throwAn[Exception](message = s"error description '$desc'\\s* is not equal to \\s*'$someOtherErrorDescription'")
    }
  }
}
