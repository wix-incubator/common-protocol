/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.restaurants.common.protocol.matchers


import java.io.InputStream
import java.net.URI
import java.nio.ByteBuffer
import java.util
import org.json4s.native.Serialization
import org.specs2.mutable.SpecWithJUnit
import com.ning.http.client.{Cookie, FluentCaseInsensitiveStringsMap}
import com.ning.http.client.{Response => NingResponse}
import com.wix.restaurants.common.protocol.api.{Error, Response}
import com.wix.restaurants.common.protocol.matchers.NingResponseMatchers._
import com.wix.restaurants.common.protocol.matchers.testmodel.TestBody


/** The Unit-Test of the [[NingResponseMatchers]] class.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
class NingResponseMatchersTest extends SpecWithJUnit {

  val resValue = TestBody(
    fieldNumber = 333,
    fieldBoolean = true,
    fieldString = "kuki buki",
    fieldObject = Map("kuki" -> "buki"),
    fieldArray = Seq("schnuftsik"))
  val error = Error("some code", "some desc")

  val regexEscape: String => String = _.replaceAll("\\(", "\\\\(").replaceAll("\\)", "\\\\)")


  "beResponse" should {
    "match Response value" in {
      val response = Response(resValue)
      val httpRespone = TestResponse(200, Serialization.write(response))

      httpRespone must beResponse(value = ===(resValue))
    }

    "produce clear description when failing on non 200 status code" in {
      val someNon200Status = 333
      val response = Response(resValue)
      val httpRespone = TestResponse(someNon200Status, Serialization.write(response))

      (httpRespone must beResponse(value = ===(resValue))) must
        throwAn[Exception](s"status '$someNon200Status' is not equal to '200'")
    }

    "produce clear description when failing on non matching body" in {
      val otherResValue = resValue.copy(fieldNumber = 33333)
      val response = Response(otherResValue)
      val httpRespone = TestResponse(200, Serialization.write(response))

      (httpRespone must beResponse(value = ===(resValue))) must
        throwAn[Exception](
          s"response value '${regexEscape(otherResValue.toString)}'\\s* is not equal to \\s*'${regexEscape(resValue.toString)}'")
    }
  }


  "beError" should {
    "match the error" in {
      val response = Response(error = error)
      val httpRespone = TestResponse(500, Serialization.write(response))

      httpRespone must beError(error = ===(error))
    }

    "produce clear description when failing on non 500 status code" in {
      val someNon500Status = 333
      val response = Response(error = error)
      val httpRespone = TestResponse(someNon500Status, Serialization.write(response))

      (httpRespone must beError(error = ===(error))) must
        throwAn[Exception](s"status '$someNon500Status' is not equal to '500'")
    }

    "produce clear description when failing on non matching body" in {
      val otherError = error.copy(code = "33333")
      val response = Response(otherError)
      val httpRespone = TestResponse(500, Serialization.write(response))

      (httpRespone must beError(error = ===(error))) must
        throwAn[Exception](
          s"response error '${regexEscape(otherError.toString)}'\\s* is not equal to \\s*'${regexEscape(error.toString)}'")
    }
  }


  "beForbidden" should {
    "match 403 status code" in {
      val httpRespone = TestResponse(403, "some body")

      httpRespone must beForbidden
    }

    "produce clear description when failing on non 403 status code" in {
      val someNon403Status = 333
      val httpRespone = TestResponse(someNon403Status, "some body")

      (httpRespone must beForbidden) must
        throwAn[Exception](s"status '$someNon403Status' is not equal to '403'")
    }
  }



  /** A dummy [[NingResponse]] object, used for the sake of the test.
    *
    * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
    */
  case class TestResponse(status: Int, body: String) extends NingResponse {
    override def getStatusCode: Int = status

    override def getResponseBody: String = body

    override def getResponseBodyExcerpt(maxLength: Int,
                                        charset: String): String = throw new UnsupportedOperationException
    override def getResponseBodyExcerpt(maxLength: Int): String = throw new UnsupportedOperationException
    override def getResponseBodyAsByteBuffer: ByteBuffer = throw new UnsupportedOperationException
    override def getResponseBodyAsStream: InputStream = throw new UnsupportedOperationException
    override def getResponseBodyAsBytes: Array[Byte] = throw new UnsupportedOperationException
    override def isRedirected: Boolean = throw new UnsupportedOperationException
    override def getCookies: util.List[Cookie] = throw new UnsupportedOperationException
    override def hasResponseBody: Boolean = throw new UnsupportedOperationException
    override def getHeaders(name: String): util.List[String] = throw new UnsupportedOperationException
    override def getHeaders: FluentCaseInsensitiveStringsMap = throw new UnsupportedOperationException
    override def getStatusText: String = throw new UnsupportedOperationException
    override def hasResponseHeaders: Boolean = throw new UnsupportedOperationException
    override def getResponseBody(charset: String): String = throw new UnsupportedOperationException
    override def getContentType: String = throw new UnsupportedOperationException
    override def hasResponseStatus: Boolean = throw new UnsupportedOperationException
    override def getUri: URI = throw new UnsupportedOperationException
    override def getHeader(name: String): String = throw new UnsupportedOperationException
  }
}

