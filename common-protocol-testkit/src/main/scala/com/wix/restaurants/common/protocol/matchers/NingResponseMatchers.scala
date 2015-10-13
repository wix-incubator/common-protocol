/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.restaurants.common.protocol.matchers


import org.json4s.DefaultFormats
import org.json4s.native.Serialization
import org.specs2.matcher.{AlwaysMatcher, Matcher}
import org.specs2.matcher.Matchers._
import com.ning.http.client.{Response => NingResponse, FluentCaseInsensitiveStringsMap}
import com.wix.restaurants.common.protocol.api.{Error, Response}


/** A trait that can be mixin with the test class or the context's {{{org.specs2.specification.Scope}}}.
  * Another option is to import the methods from the already mixin Companion Object.
  *
  * The trait introduces a matchers for HTTP responses, in this trait, ning's.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
trait NingResponseMatchers {

  implicit val formats = DefaultFormats

  def beResponse[V : Manifest](value: Matcher[V] = AlwaysMatcher[V](),
                               headers: Matcher[FluentCaseInsensitiveStringsMap] = AlwaysMatcher()): Matcher[NingResponse] = {
    ===(HttpStatus.OK) ^^ { (_: NingResponse).getStatusCode aka "status" } and
      haveValue(value) and 
      headers ^^ { (_: NingResponse).getHeaders aka "headers" }
  }

  def beError(error: Matcher[Error] = AlwaysMatcher[Error]()): Matcher[NingResponse] = {
    ===(HttpStatus.INTERNAL_SERVER_ERROR) ^^ { (_: NingResponse).getStatusCode aka "status" } and
    error ^^ { (r: NingResponse) => Serialization.read[Response[Any]](r.getResponseBody).error.get aka "response error" }
  }

  def beForbidden: Matcher[NingResponse] = {
    ===(HttpStatus.FORBIDDEN) ^^ { (_: NingResponse).getStatusCode aka "status" }
  }

  def haveValue[V: Manifest](value: Matcher[V] = AlwaysMatcher[V]()): Matcher[NingResponse] = {
    value ^^ { (r: NingResponse) =>
      Serialization.read[Response[V]](r.getResponseBody).value.get aka "response value" }
  }

  
  object HttpStatus {
    val OK = 200
    val INTERNAL_SERVER_ERROR = 500
    val FORBIDDEN = 403
  }
}

object NingResponseMatchers extends NingResponseMatchers
