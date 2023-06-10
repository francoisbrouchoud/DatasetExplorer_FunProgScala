package projet

case class City(val city:String, val postalCode: String) extends PostalCodeTrait with CityTrait{
        override def toString: String = s"City: $city, PostalCode: $postalCode"
}