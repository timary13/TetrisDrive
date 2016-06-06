package tetrisDrive

import collection.JavaConversions._

class ReadableLogs (fileLog: java.util.List[String]) {
  val log = fileLog.toArray()
  var transLog: java.util.ArrayList[String] = new java.util.ArrayList[String]()
  var curStr = ""
  var nextStr = ""
  var nextSpeed: Int = 0
  var curSpeed: Int = 0
  var curCoord: Int = 0
  var nextCoord: Int = 0
  
  implicit def javaToScalaInt(d: java.lang.Integer) = d.intValue
  
  def translateLog = {
    var valCurStr: List[String] = curStr.split(' ').toList //получаем координаты игрока в 2 момента времени
    var valNextStr: List[String] = nextStr.split(' ').toList
    
    curSpeed = javaToScalaInt(valCurStr.get(2).toInt)    //получаем скорости игрока
    nextSpeed = javaToScalaInt(valNextStr.get(2).toInt) 
    var subSpeed: Int = nextSpeed-curSpeed

    var isFaster: Int = 0
    if ( subSpeed > 0)
      isFaster = -1
    else if ( subSpeed < 0)
      isFaster = 1
      
    
    curCoord = javaToScalaInt(valCurStr.get(0).toInt)    //координаты
    nextCoord = javaToScalaInt(valNextStr.get(0).toInt)
    var subCoord: Int = nextCoord - curCoord
    var isLeft: Int = 0
    if ( subCoord < 0 )
      isLeft = -1
    else if ( subCoord > 0)
      isLeft = 1
    
    isFaster match {    //изменилась ли скорость?
      case 1 => transLog.add("acceleration")
      case -1 => transLog.add("deceleration")
      case 0 => 
    }
    
    isLeft match { //изменялись ли координаты?
      case -1 => transLog.add("left")
      case 1 => transLog.add("right")
      case 0 => 
    }
     
  }
 
  
  def printLog() = {
    log foreach{
      case (s) => println(s)
    }
  }
  
  def printTransLog() = {
    transLog foreach {
      case (s) => println(s)
    }
  }
  
  def getTransLog() = transLog
 
  def outLoop(i: Int, j: Int) {
    if( j < log.length ){      //если не последняя строка
      curStr = log(i).toString()    //читаем 2 следующие
      nextStr = log(j).toString()
      if ( curStr.equals(nextStr) )    //строки одинаковые?
        outLoop(i + 1, j + 1)    //да => читаем следующие 2
      else {
        translateLog      //нет => транслируем
        outLoop(i + 1, j + 1)
      }
    }
  }
    
 outLoop(0, 1) 
 
}