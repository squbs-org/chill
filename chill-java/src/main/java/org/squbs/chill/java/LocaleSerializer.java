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

package org.squbs.chill.java;

import com.esotericsoftware.kryo.serializers.JavaSerializer;

import org.squbs.chill.IKryoRegistrar;
import org.squbs.chill.SingleRegistrar;

import java.util.Locale;

/** The java serializer uses an cache of allocated instances so
 * it is probably a bit hard to beat, so why bother
 */
public class LocaleSerializer extends JavaSerializer {
   static public IKryoRegistrar registrar() {
      return new SingleRegistrar(Locale.class, new LocaleSerializer());
    }
}
