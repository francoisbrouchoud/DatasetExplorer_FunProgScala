package projet

import com.github.tototoshi.csv._
import java.io.FileReader
import java.io.File
import java.{util => ju}

@main def hello() = {println("Hello, World !")

enum HouseType :
    case House, FlatApartment, NewDevelopment, Penthouse, Bungalow, Duplex, Mews, Studio
object HouseType {
  def fromString(value: String): Option[HouseType] =
      value.toLowerCase match {
        case "house" => Some(HouseType.House)
        case "flat / apartment" => Some(HouseType.FlatApartment)
        case "new development" => Some(HouseType.NewDevelopment)
        case "penthouse" => Some(HouseType.Penthouse)
        case "bungalow" => Some(HouseType.Bungalow)
        case "duplex" => Some(HouseType.Duplex)
        case "mews" => Some(HouseType.Mews)
        case "studio" => Some(HouseType.Studio)
        case _ => None
      }
  def toString(houseType: HouseType): String = 
    houseType match {
      case HouseType.House => "house"
      case HouseType.FlatApartment => "flat / apartment"
      case HouseType.NewDevelopment => "new development"
      case HouseType.Penthouse => "penthouse"
      case HouseType.Bungalow => "bungalow"
      case HouseType.Duplex => "duplex"
      case HouseType.Mews => "mews"
      case HouseType.Studio => "studio"
    }
  }
trait PostalCode {
  val postalCode: String
}
trait CityTrait {
  val city: String
}
class Location(location: String, val postalCode: String) extends PostalCode
class City(val city:String, val postalCode: String) extends PostalCode with CityTrait
class Address(val city:String, val location:String, val postalCode: String) extends PostalCode with CityTrait{
    override def toString: String = s"City: $city, Location: $location, PostalCode: $postalCode"

    
}
class OnlyPostalCode(val postalCode: String) extends PostalCode

def choosePostalCode (city:String, location:String, postalCode:String): PostalCode =
{
  if(city!=null && city!="")
  {
    if(location!=null&& location!=""){
      new Address(city,location,postalCode)
    }
    else{
      new City(city,postalCode)
    }
  }else{
    if(location!=null&& location!=""){
      new Location(location,postalCode)
    }

    //TODO à vérifier, car on peut pas instancier directement un trait
    else{
      new OnlyPostalCode(postalCode)
    }
  }
}

class Property(val name:String, val price:Int, val houseType:HouseType, val area:Int, val nbBedrooms:Int, val nbBathrooms:Int, val nbReceptions:Int, val address:PostalCode)
{
  override def toString: String = s"House(name: $name, houseType: $houseType, price: $price)"
}

var properties: Seq[Property] = Seq()
//Load data (CV)
try{
val csvFile = "./08-PropertiesLondon.csv"
val reader = CSVReader.open(new File(csvFile))
    properties = reader.allWithHeaders().map(record => 
    Property(record("Property Name"), 
    record("Price").toInt,
    HouseType.fromString(record("House Type")).getOrElse(HouseType.House), 
    record("Area in sq ft").toInt,
    record("No. of Bedrooms").toInt,
    record("No. of Bathrooms").toInt,
    record("No. of Receptions").toInt,
    choosePostalCode(record("Location"),record("City/County"),record("Postal Code"))
    )
)
}
catch {
  case ex:Exception =>
    ex.printStackTrace
    System.exit(1)
}

// Dans le cas ou l'import csv ne fonctionne pas
/*val properties = Seq(
  Property("name1",12,HouseType.valueOf("Flat / Apartment"),12,1,1,1,PostalCode("test")),
  Property("name2",24,HouseType.valueOf("New development"),12,1,2,1,PostalCode("test2")),
  Property("name3",124,HouseType.valueOf("House"),120,11,2,3,PostalCode("test3"))
  )
*/
//Query1(CV): map: prix par m2 (prix/area) et tri dans l'ordre
//TODO Check pour les adresses
//exception div par 0
val priceM2properties = properties.map(property => (property.name, property.price/property.area, property.houseType, property.address)).sortBy(_._2)
priceM2properties.foreach { case (name, priceM2, houseType, address) =>
  println(s"Name: $name, Price M2: $priceM2, Type: ${HouseType.toString(houseType)}, Address: $address")
}

//Query2(FB):  reduce: moyenne prix ou bathroom au total (plus simple) -> (à voir si on filtre sur City)
//TODO J'ai fait par type de house mais on peut changer avec city
//TODO vérifier bug prix négatif
//diviser le prix par la size, property size si pas de propriété
val avgPriceByProperty = properties.groupBy(_.houseType).map { 
  case (houseType, properties) =>
    val avgPriceByProperty = properties.map(_.price).sum.toLong / properties.size 
    (houseType, avgPriceByProperty)
  }

avgPriceByProperty.foreach { 
  case (houseType, averagePrice) =>
  println(s"HouseType: ${HouseType.toString(houseType)}, Average Price: $averagePrice £")
}



//Query3(CV): filtre: sortir toutes les flat appartment
val filter=HouseType.Duplex
val filteredHouses = properties.filter(_.houseType == filter)
//filteredHouses.foreach(println)

//Query4(FB): aggragate: filtrer sur une ville puis affichage du nombre par HouseType
//TODO corriger problème pour accéder au city 
/*val londonProperties = properties.filter(property => property.address.city.equals("London"))
val countByHouseType = londonProperties.groupBy(_.houseType).mapValues(_.size)
countByHouseType.foreach { case (houseType, count) =>
  println(s"House Type: $houseType, Count: $count")
}
*/



//Query5(FB): calcul du nombre de pièces avec un taux par pièce (badroom 0.5 par ex.)
val roomsPerProperty = properties.map(property => (property.name, property.nbBedrooms + property.nbReceptions + (0.5 * property.nbBathrooms))).sortBy(_._2)
roomsPerProperty.foreach { case (name, nbRooms) =>
  //println(s"Name: $name, NbRooms: $nbRooms")
}

}