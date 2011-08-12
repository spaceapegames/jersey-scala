package com.codahale.jersey.params.specs

import com.codahale.simplespec.Spec
import com.codahale.simplespec.annotation.test
import com.codahale.jersey.params.IntParam
import javax.ws.rs.WebApplicationException

class IntParamSpec extends Spec {
  class `A valid int parameter` {
    val param = IntParam("40")

    @test def `has an int value` = {
      param.value must beEqualTo(40)
    }
  }

  class `An invalid int parameter` {
    @test def `throws a WebApplicationException with an error message` = {
      IntParam("poop") must throwA[WebApplicationException].like {
         case e: WebApplicationException =>
          val response = e.getResponse
          response.getStatus must beEqualTo(400)
          response.getEntity must beEqualTo("Invalid parameter: poop (Must be an integer value.)")
      }
    }
  }
}
