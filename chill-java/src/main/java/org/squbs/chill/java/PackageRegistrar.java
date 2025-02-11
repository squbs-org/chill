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

/**
 * Creates a registrar for all the serializers in the chill.java package
 */
public class PackageRegistrar {
  
  static public IKryoRegistrar all() {
    return new IterableRegistrar(
        ArraysAsListSerializer.registrar(),
        BitSetSerializer.registrar(),
        PriorityQueueSerializer.registrar(),
        RegexSerializer.registrar(),
        SqlDateSerializer.registrar(),
        SqlTimeSerializer.registrar(),
        TimestampSerializer.registrar(),
        URISerializer.registrar(),
        InetSocketAddressSerializer.registrar(),
        UUIDSerializer.registrar(),
        LocaleSerializer.registrar(),
        SimpleDateFormatSerializer.registrar(),
        UnmodifiableCollectionSerializer.registrar(),
        UnmodifiableListSerializer.registrar(),
        UnmodifiableMapSerializer.registrar(),
        UnmodifiableSetSerializer.registrar(),
        UnmodifiableSortedMapSerializer.registrar(),
        UnmodifiableSortedSetSerializer.registrar()
    );
  }
}
