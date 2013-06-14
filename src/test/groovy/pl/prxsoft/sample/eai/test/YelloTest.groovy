package pl.prxsoft.sample.eai.test

/**
 * Created by IntelliJ IDEA.
 * User: Piotr Maśko
 * Date: 14.06.13
 * Time: 16:37
 */

import spock.lang.*

class YellowTest extends spock.lang.Specification {
  def "length of Spock's and his friends' names"() {
    expect:
    name.size() == length

    where:
    name     | length
    "Spock"  | 5
    "Kirk"   | 4
    "Scotty" | 6
    "One"    | 4
  }
}