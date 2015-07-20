/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.commons.common.protocol.matchers


import org.json4s.DefaultFormats
import org.json4s.native.Serialization
import org.specs2.matcher.{AlwaysMatcher, Matcher}
import org.specs2.matcher.Matchers._
import com.ning.http.client.{Response => NingResponse}
import com.wix.commons.common.protocol.api.{Error, Response}


/** This class is
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
trait NingResponseMatchers {

  implicit val formats = DefaultFormats

  def beResponse[V : Manifest](value: Matcher[V] = AlwaysMatcher[V]()): Matcher[NingResponse] = {
    ===(HttpStatus.OK) ^^ { (_: NingResponse).getStatusCode aka "status" } and
    value ^^ { (r: NingResponse) => Serialization.read[Response[V]](r.getResponseBody).value aka "response value" }
  }

  def beError(error: Matcher[Error] = AlwaysMatcher[Error]()): Matcher[NingResponse] = {
    ===(HttpStatus.INTERNAL_SERVER_ERROR) ^^ { (_: NingResponse).getStatusCode aka "status" } and
    error ^^ { (r: NingResponse) => Serialization.read[Response[Any]](r.getResponseBody).error aka "response error" }
  }

  def beForbidden: Matcher[NingResponse] = {
    ===(HttpStatus.FORBIDDEN) ^^ { (_: NingResponse).getStatusCode aka "status" }
  }

  object HttpStatus {
    val OK = 200
    val INTERNAL_SERVER_ERROR = 500
    val FORBIDDEN = 403
  }
}

object NingResponseMatchers extends NingResponseMatchers
