/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.commons.common.protocol.matchers


import org.specs2.matcher.{AlwaysMatcher, Matcher}
import org.specs2.matcher.Matchers._
import com.wix.commons.common.protocol.api.Error


/** A trait that can be mixin with the test class or the context's {{{org.specs2.specification.Scope}}}.
  * Another option is to import the methods from the already mixin Companion Object.
  *
  * The trait introduces a matchers for the {{{com.wix.commons.common.protocol.api.Error}}} object.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
trait ErrorMatchers {

  def beError(code: Matcher[String] = AlwaysMatcher(),
              description: Matcher[String] = AlwaysMatcher()): Matcher[Error] = {
    code ^^ { (_: Error).code aka "error code" } and
    description ^^ { (_: Error).description aka "error description" }
  }
}

object ErrorMatchers extends ErrorMatchers

