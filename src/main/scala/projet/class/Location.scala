package projet

case class Location(location: String, val postalCode: String) extends PostalCodeTrait{
        override def toString: String = s"Location: $location, PostalCode: $postalCode"

}