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

package org.squbs.chill;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;

public class SingleRegistrar<T> implements IKryoRegistrar {
  final Class<T> klass;
  final Serializer<T> serializer;
  public SingleRegistrar(Class<T> cls, Serializer<T> ser) {
    klass = cls;
    serializer = ser;
  }
  @Override
  public void apply(Kryo k) { k.register(klass, serializer); }
}
