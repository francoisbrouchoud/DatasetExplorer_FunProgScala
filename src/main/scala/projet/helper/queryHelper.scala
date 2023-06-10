package projet
import java.{util => ju}

object Queries{
def query1 (properties:Seq[Property]) : Seq[(String, Double, HouseTypeEnum, PostalCodeTrait)] = {
   try{
        properties.map(property => (property.name, property.price/property.area.toDouble, property.houseType, property.address)).sortBy(_._2).take(10)
    }
    catch {
        case ex:Exception =>
        ex.printStackTrace
        Seq()
    }
}

def query2(properties: Seq[Property]): Seq[(String, Long)] = {
  if (properties.nonEmpty) {
    properties.groupBy(_.houseType).map {
      case (houseType, properties) =>
        val totalPrices = properties.foldLeft(0L)((total, property) => total + property.price)
        val avgPriceByProperty = totalPrices / properties.size
        (HouseTypeEnum.toString(houseType), avgPriceByProperty)
    }.toSeq.sortBy(_._2)
  } else {
    Seq.empty
  }
}

def query3 (properties: Seq[Property], filter: HouseTypeEnum) : Seq[Property] = {
    properties.filter(_.houseType == filter)
}


def query4(properties: Seq[Property], area: Int) : Seq[(String, Double)] = {
  properties
    .filter(property => property.area > area)
    .map(property => (property.name, property.nbBedrooms + property.nbReceptions + (0.5 * property.nbBathrooms)))
    .sortBy(_._2)
}


def query5 (properties: Seq[Property], cityName: String) : Seq[(HouseTypeEnum, Int)] = {
    val cityProperties = properties.filter { property =>
        property.address match {
            case cityTrait: CityTrait => cityTrait.city.equals(cityName)
            case _ => false
        }
    }
    val countByHouseType = cityProperties.groupBy(_.houseType).mapValues(_.size)
    countByHouseType.toSeq
}


}