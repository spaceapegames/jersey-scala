package com.codahale.jersey.params.specs

import org.junit.Test
import com.codahale.jersey.params.LongParam
import javax.ws.rs.WebApplicationException
import org.scalatest.matchers.ShouldMatchers

class LongParamSpec extends ShouldMatchers {
  private val param = LongParam("40")

  @Test def `A valid long parameter has an int value` = {
    param.value should equal(40L)
  }

  @Test def `An invalid long parameter throws a WebApplicationException with an error message` = {
    val thrown = evaluating {
      LongParam("poop")
    } should produce[WebApplicationException]
    val response = thrown.getResponse
    response.getStatus should equal(400)
    response.getEntity should equal("Invalid parameter: poop (Must be an integer value.)")
  }
}
