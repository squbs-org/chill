/*
 * Run this script with: scala tuple_serializer.scala
 * and it will generate the Kryo serializers for all Scala tuples
 */

import java.io.PrintWriter
import java.util.Date
import java.text.SimpleDateFormat

val header = """/*


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

import _root_.java.io.Serializable

// DO NOT EDIT: auto generated by tuple_serializers.scala at: %s
// scala tuple_serializers.scala
"""

def timestamp: String = {
  val dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z")
  dateFormat.format(new Date)
}

// Returns a string like A,B,C
// for use inside tuple type parameters
def typeList(i: Int, initC: Char = 'A') = {
  val init = initC.toInt
  (0 until i).map(idx => (idx + init).toChar).mkString(",")
}

// Returns Tuple2[A,B] or Tuple3[A,B,C]
def tupleType(i: Int, initC: Char = 'A') =
  "Tuple%d[%s]".format(i, typeList(i, initC))

def makeWrite(size: Int): String = {
  val head = """  def write(kser : Kryo, out : Output, obj : %s): Unit = {
""".format(tupleType(size))
  val unrolled = (1 to size)
    .map { i =>
      "    kser.writeClassAndObject(out, obj._%d); out.flush;".format(i)
    }
    .mkString("\n")
  head + unrolled + "\n  }\n"
}

def readAndCast(pos: Int): String =
  """      kser.readClassAndObject(in).asInstanceOf[%s]""".format(('A'.toInt + pos - 1).toChar.toString)

def makeRead(size: Int): String = {
  val ttype = tupleType(size)
  val head = """  def read(kser: Kryo, in: Input, cls: Class[%s]) : %s = {
""".format(ttype, ttype)
  val instantiation = """    new %s(
""".format(ttype)
  val reads = (1 to size).map(readAndCast(_)).mkString(",\n")
  head + instantiation + reads + """
    )
  }
"""
}

def makeSerializer(size: Int): String = {
  val tList = typeList(size)
  val tType = tupleType(size)
  ("""class Tuple%dSerializer[%s] extends KSerializer[%s] with Serializable {
  setImmutable(true)
""".format(size, tList, tType)) + makeWrite(size) + makeRead(size) + "}"
}

def register(size: Int): String = {
  val anyArgs = (1 to size).map(i => "Any").mkString(",")
  val ttype = "Tuple%d[%s]".format(size, anyArgs)
  val ser = "new Tuple%dSerializer[%s]".format(size, anyArgs)
  """    newK.register(classOf[%s], %s)""".format(ttype, ser)
}

val typeMap = Map("Long" -> "J", "Int" -> "I", "Double" -> "D")
val spTypes = List("Long", "Int", "Double")

val spPairs = for {
  a <- spTypes
  b <- spTypes
} yield (a, b)

def spTup1(scalaVersion: String)(typeNm: String): String =
  scalaVersion match {
    case "2.10" =>
      """
      class Tuple1TYPESerializer extends KSerializer[Tuple1$mcSHORT$sp] with Serializable {
        setImmutable(true)
        def read(kser : Kryo, in : Input, cls : Class[Tuple1$mcSHORT$sp]) : Tuple1$mcSHORT$sp = {
          new Tuple1$mcSHORT$sp(in.readTYPE)
        }
        def write(kser : Kryo, out : Output, tup : Tuple1$mcSHORT$sp): Unit = {
          out.writeTYPE(tup._1$mcSHORT$sp)
        }
      }

      """.replace("TYPE", typeNm).replace("SHORT", typeMap(typeNm))
    case _ =>
      """
      class Tuple1TYPESerializer extends KSerializer[Tuple1[TYPE]] with Serializable {
        setImmutable(true)
        def read(kser : Kryo, in : Input, cls : Class[Tuple1[TYPE]]) : Tuple1[TYPE] = {
          new Tuple1[TYPE](in.readTYPE)
        }
        def write(kser : Kryo, out : Output, tup : Tuple1[TYPE]): Unit = {
          out.writeTYPE(tup._1)
        }
      }

      """.replace("TYPE", typeNm)
  }

def spTup2(scalaVersion: String)(typeNm1: String, typeNm2: String): String =
  scalaVersion match {
    case "2.10" =>
      """
      class Tuple2TYPE1TYPE2Serializer extends KSerializer[Tuple2$mcSHORT1SHORT2$sp] with Serializable {
        setImmutable(true)
        def read(kser : Kryo, in : Input, cls : Class[Tuple2$mcSHORT1SHORT2$sp]) : Tuple2$mcSHORT1SHORT2$sp = {
          new Tuple2$mcSHORT1SHORT2$sp(in.readTYPE1, in.readTYPE2)
        }
        def write(kser : Kryo, out : Output, tup : Tuple2$mcSHORT1SHORT2$sp): Unit = {
          out.writeTYPE1(tup._1$mcSHORT1$sp)
          out.writeTYPE2(tup._2$mcSHORT2$sp)
        }
      }
      """
        .replace("TYPE1", typeNm1)
        .replace("SHORT1", typeMap(typeNm1))
        .replace("TYPE2", typeNm2)
        .replace("SHORT2", typeMap(typeNm2))
    case _ =>
      """
      class Tuple2TYPE1TYPE2Serializer extends KSerializer[Tuple2[TYPE1, TYPE2]] with Serializable {
        setImmutable(true)
        def read(kser : Kryo, in : Input, cls : Class[Tuple2[TYPE1, TYPE2]]) : Tuple2[TYPE1, TYPE2] = {
          new Tuple2[TYPE1, TYPE2](in.readTYPE1, in.readTYPE2)
        }
        def write(kser : Kryo, out : Output, tup : Tuple2[TYPE1, TYPE2]): Unit = {
          out.writeTYPE1(tup._1)
          out.writeTYPE2(tup._2)
        }
      }
      """
        .replace("TYPE1", typeNm1)
        .replace("TYPE2", typeNm2)
  }

def registerSp1(scalaVersion: String)(typeNm: String): String =
  scalaVersion match {
    case "2.10" =>
      """newK.register(classOf[Tuple1$mcSHORT$sp], new Tuple1TYPESerializer)"""
        .replace("TYPE", typeNm)
        .replace("SHORT", typeMap(typeNm))
    case _ =>
      """newK.register(Class.forName("scala.Tuple1$mcSHORT$sp"), new Tuple1TYPESerializer)"""
        .replace("TYPE", typeNm)
        .replace("SHORT", typeMap(typeNm))
  }

def registerSp2(scalaVersion: String)(typeNm1: String, typeNm2: String): String =
  scalaVersion match {
    case "2.10" =>
      """newK.register(classOf[Tuple2$mcSHORT1SHORT2$sp], new Tuple2TYPE1TYPE2Serializer)"""
        .replace("TYPE1", typeNm1)
        .replace("SHORT1", typeMap(typeNm1))
        .replace("TYPE2", typeNm2)
        .replace("SHORT2", typeMap(typeNm2))
    case _ =>
      """newK.register(Class.forName("scala.Tuple2$mcSHORT1SHORT2$sp"), new Tuple2TYPE1TYPE2Serializer)"""
        .replace("TYPE1", typeNm1)
        .replace("SHORT1", typeMap(typeNm1))
        .replace("TYPE2", typeNm2)
        .replace("SHORT2", typeMap(typeNm2))
  }

def objectHelper(scalaVersion: String): String =
  """object ScalaTupleSerialization extends Serializable {
  def register: IKryoRegistrar = new IKryoRegistrar {
    def apply(newK : Kryo): Unit = {

""" + ((1 to 22).map(size => register(size)).mkString("\n")) + "\n" +
    (spTypes.map(registerSp1(scalaVersion)).mkString("\n")) + "\n" +
    (spPairs.map(t => registerSp2(scalaVersion)(t._1, t._2)).mkString("\n")) + "\n" +
    """    }
  }
}
"""

val scalaVersions = ("scala-2.10", "2.10") :: ("scala-2.11+", "2.11") :: Nil

scalaVersions.foreach { case (dir, version) =>
  ///////////////////////////////////////////////////////////////////
  // Actually output the code here:
  val file = new java.io.File(s"../chill-scala/src/main/$dir/org/squbs/chill/TupleSerializers.scala")
  val writer = new PrintWriter(file)

  writer.println(header.format(timestamp))
  (1 to 22).map(makeSerializer).foreach(writer.println)
  spTypes.map(spTup1(version)).foreach(writer.println)
  spPairs.foreach(t => writer.println(spTup2(version)(t._1, t._2)))

  writer.println(objectHelper(version))
  writer.close()
}
