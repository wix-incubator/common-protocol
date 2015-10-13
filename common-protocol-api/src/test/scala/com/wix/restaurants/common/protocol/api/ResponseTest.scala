package com.wix.restaurants.common.protocol.api

import org.json4s.DefaultFormats
import org.json4s.native.Serialization
import org.specs2.mutable.SpecWithJUnit

case class TestClass(str: String)

class Json4sResponseParser {
  implicit val formats = DefaultFormats

  def parse(str: String): Response[TestClass] = {
    Serialization.read[Response[TestClass]](str)
  }
}

class ResponseTest extends SpecWithJUnit {
  private val parser = new Json4sResponseParser

  "parsing JSON" should {
    "correctly produce an object if error field is missing" in {
      val obj = Response[TestClass](
        value = TestClass(
          str = "some string"
        )
      )

      val json = "{\"value\":{\"str\":\"some string\"}}" // hard-coded serialization to force missing error field
      val parsed = parser.parse(json)

      parsed must beEqualTo(obj)
    }

    "correctly produce an object if value field is missing" in {
      val obj = Response[TestClass](
        error = Error(
          code = "some code",
          description = "some description"
        )
      )

      val json = "{\"error\":{\"code\":\"some code\",\"description\":\"some description\"}}" // hard-coded serialization to force missing value field
      val parsed = parser.parse(json)

      parsed must beEqualTo(obj)
    }
  }
}
