package com.codahale.jersey.inject.specs

import org.junit.Test
import com.codahale.jersey.inject.ScalaCollectionStringReaderExtractor
import com.sun.jersey.core.util.MultivaluedMapImpl
import org.scalatest.matchers.ShouldMatchers

class ScalaCollectionStringReaderExtractorSpec extends ShouldMatchers {
    val extractor = new ScalaCollectionStringReaderExtractor[Set]("name", "default", Set)

    @Test def `Extracting a parameter has a name` {
      extractor.getName should equal ("name")
    }

    @Test def `Extracting a parameter has a default value` {
      extractor.getDefaultStringValue should equal ("default")
    }

    @Test def `Extracting a parameter extracts a set of parameter values` {
      val params = new MultivaluedMapImpl()
      params.add("name", "one")
      params.add("name", "two")
      params.add("name", "three")

      val result = extractor.extract(params).asInstanceOf[Set[String]]
      result should equal (Set("one", "two", "three"))
    }

    @Test def `Extracting a parameter uses the default value if no parameter exists` {
      val params = new MultivaluedMapImpl()

      val result = extractor.extract(params).asInstanceOf[Set[String]]
      result should equal (Set("default"))
    }


    @Test def `Extracting a parameter with no default value returns an empty collection` {
      val extractor = new ScalaCollectionStringReaderExtractor[Set]("name", null, Set)
      val params = new MultivaluedMapImpl()

      val result = extractor.extract(params).asInstanceOf[Set[String]]
      result should equal (Set.empty[String])
    }
}
