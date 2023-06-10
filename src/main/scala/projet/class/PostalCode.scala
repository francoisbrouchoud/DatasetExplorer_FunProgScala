package projet

case class PostalCode(val postalCode: String) extends PostalCodeTrait{
        override def toString: String = s"PostalCode: $postalCode"

}