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

import java.util.concurrent.ArrayBlockingQueue;

/** Simple ResourcePool to save on Kryo instances, which
 * are expensive to allocate
 */
public abstract class ResourcePool<T> {
  private ArrayBlockingQueue<T> pool;

  protected abstract T newInstance();

  public ResourcePool(int size) {
    pool = new ArrayBlockingQueue<T>(size);
  }

  public T borrow() {
    try {
      T res = pool.poll();
      if(null == res) {
        return newInstance();
      }
      else {
        return res;
      }
    }
    catch(Exception x) {
      throw new RuntimeException(x);
    }
  }

  public void release(T item) {
    try {
      pool.offer(item);
    }
    catch(Exception x) {
      throw new RuntimeException(x);
    }
  }
}
