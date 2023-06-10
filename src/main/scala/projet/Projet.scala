package projet

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import com.github.tototoshi.csv._
import java.io.FileReader
import java.io.File
import java.{util => ju}
import scala.concurrent.Future


@main def hello() = {
  val properties = DataHelper.loadCsv()
  
  //Query 1
  val resultQry1 = Queries.query1(properties)
  var index = 1
  println(s"Query 1 : Top ten properties by price per area")
  resultQry1.foreach { case (name, priceM2, houseType, address) =>
    println(s"-------------------------------")
    println(s"Property ${index}: $name")
    println(s"Price per area : ${"%.2f".format(priceM2)} £/m2")
    println(s"Type: $houseType")
    println(s"Address: $address")
    index +=1
  }

  //Query 2
  val resultQry2 = Queries.query2(properties)
  println(s"\n***********************************\n")
  println(s"Query 2 : Average price by house type")
  resultQry2.foreach { case (houseType, avgPriceByProperty) => 
    println(s"Average price for $houseType = $avgPriceByProperty £")
  }
        
  //Query 3 List of Duplex
  println(s"\n***********************************\n")
  println(s"Query 3 : List of duplex")
  index = 1
  val resultQry3 = Queries.query3(properties, HouseTypeEnum.Duplex)
  resultQry3.foreach { case (property) =>
    println(s"-------------------------------")
    println(s"Property ${index}: ${property.name}")
    println(s"\tArea : ${property.area}m2, ${property.nbBathrooms} bathrooms, ${property.nbBedrooms} bedrooms, ${property.nbReceptions} receptions")
    println(s"\tCost : ${property.price}£")
    println(s"\tAddress: ${property.address}")
    index +=1
  }

  // Query 4: Number of rooms per property with area > 80000
  println(s"\n***********************************\n")
  println(s"Query 4 : Number of room per property > 8000 with rate per room ")
  val resultQry4 = Queries.query4(properties, 8000)
  resultQry4.foreach {
    case (name, score) => println(s"Property name: $name, Total score: $score")
  }
  

  println(s"\n***********************************\n")
  println(s"Query 5 : Number of housetypes in London and Surrey")
  println(s"-------------------------------")
  val futureQr5a = Future(Queries.query5(properties,"London"))
  futureQr5a.onComplete(
  { 
    case scala.util.Failure(ex) => println(s"Erreur : ${ex.getMessage}")
    case scala.util.Success(resultQry5) => {
      resultQry5.foreach { case (houseType, count) =>
        println(s"$count ${HouseTypeEnum.toString(houseType)} in London")
      }
    }
  })
  val futureQr5b = Future(Queries.query5(properties,"Surrey"))
  futureQr5b.onComplete(
  { 
    case scala.util.Failure(ex) => println(s"Erreur : ${ex.getMessage}")
    case scala.util.Success(resultQry5) => {
      resultQry5.foreach { case (houseType, count) =>
        println(s"$count ${HouseTypeEnum.toString(houseType)} in Surrey")
      }
    }
  })

  Thread.sleep(10000)
}