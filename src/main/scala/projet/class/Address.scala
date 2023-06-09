package projet

class Address(val city:String, val location:String, val postalCode: String) extends PostalCode with CityTrait{
    override def toString: String = s"City: $city, Location: $location, PostalCode: $postalCode"
}