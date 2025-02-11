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

import org.squbs.chill.IKryoRegistrar;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.Serializer;

import java.util.ArrayList;

public class IterableRegistrarSerializer extends Serializer<IterableRegistrar> {
  public void write(Kryo kryo, Output output, IterableRegistrar obj) {
    for(IKryoRegistrar kr: obj.getRegistrars()) {
      kryo.writeClassAndObject(output, kr);
      output.flush();
    }
    kryo.writeClassAndObject(output, null);
  }
  public IterableRegistrar read(Kryo kryo, Input input, Class<IterableRegistrar> type) {
    ArrayList<IKryoRegistrar> krs = new ArrayList<IKryoRegistrar>();
    IKryoRegistrar thisKr = (IKryoRegistrar)kryo.readClassAndObject(input);
    while(thisKr != null) {
      krs.add(thisKr);
      thisKr = (IKryoRegistrar)kryo.readClassAndObject(input);
    }
    return new IterableRegistrar(krs);
  }
}

