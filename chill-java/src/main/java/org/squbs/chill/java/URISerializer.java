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

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import org.squbs.chill.IKryoRegistrar;
import org.squbs.chill.SingleRegistrar;

import java.net.URI;

public class URISerializer extends Serializer<java.net.URI> {

    static public IKryoRegistrar registrar() {
      return new SingleRegistrar(URI.class, new URISerializer());
    }

    @Override
    public void write(Kryo kryo, Output output, URI uri) {
        output.writeString(uri.toString());
    }

    @Override
    public URI read(Kryo kryo, Input input, Class<URI> uriClass) {
        return URI.create(input.readString());
    }
}
