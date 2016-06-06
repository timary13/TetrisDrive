package tetrisDrive

import scala.collection.JavaConversions._

class GenerateStat (transLog: java.util.List[String]) {
  val log = transLog.toList
  var countLeft = 0
  var countRight = 0
  var countAcceleration = 0
  var countDeceleration = 0
  var str = ""
  var stat: java.util.ArrayList[Integer] = new java.util.ArrayList[Integer]()
  
  def generate = {
    
    str match {
      case "acceleration" =>  countAcceleration += 1
      case "deceleration" =>  countDeceleration += 1
      case "left" =>  countLeft += 1
      case "right" =>  countRight += 1
      case _ => println("no match")
    }
    
  }

  def resStat = {
    stat.add(countAcceleration)
    stat.add(countDeceleration)
    stat.add(countLeft)
    stat.add(countRight)
  }
  
  def getLogStat = stat
  
  def outLoop (i: Int) {
    if (i < log.length ){ //пока есть строки
      str = log(i).toString()    //читаем строку
      generate
      outLoop(i+1)
    }
    else
      resStat
  }
  
  outLoop(0)
  
}
