/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.commons.common.protocol.matchers

import com.ning.http.client.Response.ResponseBuilder
import org.specs2.mutable.SpecWithJUnit
import org.specs2.specification.Scope

/** This class is
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
class NingResponseMatchersTest extends SpecWithJUnit {
  val builder = new ResponseBuilder()


  trait Ctx extends Scope {
    builder.reset()
  }


  "x" should {
    "ss" in {
      builder.accumulate()
    }
  }

}
