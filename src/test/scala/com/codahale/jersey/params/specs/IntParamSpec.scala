package com.codahale.jersey.params.specs

import org.junit.Test
import com.codahale.jersey.params.IntParam
import javax.ws.rs.WebApplicationException
import org.scalatest.matchers.ShouldMatchers

class IntParamSpec extends ShouldMatchers {
  val param = IntParam("40")

  @Test def `A valid int parameter has an int value` = {
    param.value should equal(40)
  }

  @Test def `An invalid int parameter throws a WebApplicationException with an error message` = {
    val thrown = evaluating {
      IntParam("poop")
    } should produce[WebApplicationException]
    val response = thrown.getResponse
    response.getStatus should equal(400)
    response.getEntity should equal("Invalid parameter: poop (Must be an integer value.)")
  }
}
