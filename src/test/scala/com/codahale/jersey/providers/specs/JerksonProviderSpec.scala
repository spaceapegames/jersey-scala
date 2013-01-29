package com.codahale.jersey.providers.specs

import org.junit.Test
import javax.ws.rs.core.MediaType
import com.codahale.jersey.providers.JerksonProvider
import javax.ws.rs.WebApplicationException
import java.io.{ ByteArrayOutputStream, ByteArrayInputStream }
import org.scalatest.matchers.ShouldMatchers

class JerksonProviderSpec extends ShouldMatchers {
  private val provider = new JerksonProvider[Array[Int]]

  @Test def `An array of ints is writable` {
    provider.isWriteable(Array.empty[Int].getClass, null, null, MediaType.APPLICATION_JSON_TYPE) should equal(true)
  }

  @Test def `An array of ints is readable` {
    provider.isReadable(Array.empty[Int].getClass, null, null, MediaType.APPLICATION_JSON_TYPE) should equal(true)
  }

  val entity = new ByteArrayInputStream("[1, 2, 3]".getBytes)

  @Test def `Parsing an application/json request entity returns an array of the given type` {
    provider.readFrom(classOf[Array[Int]], null, null, null, null, entity) should equal(Array(1, 2, 3))
  }

  @Test def `Parsing an malformed application/json request entity throws a 400 Bad Request WebApplicationException` {
    val entity = new ByteArrayInputStream("[1, 2".getBytes)
    val thrown = evaluating {
      provider.readFrom(classOf[Array[Int]], null, null, null, null, entity)
    } should produce[WebApplicationException]
    val response = thrown.getResponse
    response.getStatus should equal(400)
    response.getEntity should equal("Malformed JSON. Unexpected end-of-input: expected close marker for ARRAY at character offset 14.")
  }

  @Test def `Rendering an application/json response entity produces a compact JSON array` {
    val output = new ByteArrayOutputStream
    provider.writeTo(Array(1, 2, 3), null, null, null, MediaType.APPLICATION_JSON_TYPE, null, output)

    output.toString should equal("[1,2,3]")
  }
}
