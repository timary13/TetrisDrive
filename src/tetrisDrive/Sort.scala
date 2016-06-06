package tetrisDrive

import collection.JavaConversions._
import scala.collection.immutable.ListMap

class Sort (val myMap: java.util.Map[Integer, java.lang.String]){
  
  def printMap(){
  myMap foreach {
    case (key, value) => println (key + "-->" + value)    
    }
  }
  
  def printSort(){
    var myList = ListMap(myMap.toSeq.sortBy(_._1):_*)
    println("sort: ")
    myList foreach {
    case (key, value) => println (key + "-->" + value)
    }
  }
  
  var myList = letsSort(myMap.toList)
  
  def sorting(){
    println("sort: ")
    myList foreach {
    case (value) => println (value)    
    }
  }
  
  
   def letsSort(xs: List[(Integer, String)]): List[(Integer, String)] = {
    val n = xs.length / 2
    if (n == 0) xs
    else {
      def merge(xs: List[(Integer, String)], ys: List[(Integer, String)]): List[(Integer, String)] =
        (xs, ys) match {
          case (Nil, ys) => ys
          case (xs, Nil) => xs
          case (x :: xs1, y :: ys1) =>
            if (x._1 < y._1) x :: merge(xs1, ys)
            else y :: merge(xs, ys1)
        }
      val (left, right) = xs splitAt (n)
      merge(letsSort(left), letsSort(right))
    }
  }
    
}
