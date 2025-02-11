package org.squbs.chill

class StreamSerializer[T]() extends KSerializer[Stream[T]] {
  def write(kser: Kryo, out: Output, stream: Stream[T]): Unit =
    kser.writeClassAndObject(out, stream.toList)

  def read(kser: Kryo, in: Input, cls: Class[Stream[T]]): Stream[T] =
    kser.readClassAndObject(in).asInstanceOf[List[T]].toStream
}
