/*


Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package org.squbs.chill

import _root_.java.math.{BigDecimal => JBigDecimal}
import _root_.scala.math.BigDecimal

private class BigDecimalSerializer extends KSerializer[BigDecimal] {
  override def read(kryo: Kryo, input: Input, cls: Class[BigDecimal]): BigDecimal = {
    val jBigDec = kryo.readClassAndObject(input).asInstanceOf[JBigDecimal]
    BigDecimal(jBigDec)
  }

  override def write(kryo: Kryo, output: Output, obj: BigDecimal): Unit =
    kryo.writeClassAndObject(output, obj.bigDecimal)
}
