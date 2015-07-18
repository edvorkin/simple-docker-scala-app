package rest

import entities.JsonProtocol
import persistence.entities.{SimpleSupplier, Supplier}
import spray.httpx.SprayJsonSupport
import spray.http._
import StatusCodes._
import scala.concurrent.Future
import JsonProtocol._
import SprayJsonSupport._

class RoutesSpec  extends AbstractRestTest {
  sequential

  def actorRefFactory = system

  val modules = new Modules {}

  val suppliers = new SupplierHttpService(modules){
    override def actorRefFactory = system
  }

  "Supplier Routes" should {

    "return an empty array of suppliers" in {
     modules.suppliersDal.getSupplierById(1) returns Future(Vector())

      Get("/service_spray/supplier/1") ~> suppliers.SupplierGetRoute ~> check {
        handled must beTrue
        status mustEqual OK
        responseAs[Seq[Supplier]].length == 0
      }
    }

    "return an array with 2 suppliers" in {
      modules.suppliersDal.getSupplierById(1) returns Future(Vector(Supplier(Some(1),"name 1", "desc 1"),Supplier(Some(2),"name 2", "desc 2")))
      Get("/service_spray/supplier/1") ~> suppliers.SupplierGetRoute ~> check {
        handled must beTrue
        status mustEqual OK
        responseAs[Seq[Supplier]].length == 2
      }
    }

    "create a supplier with the json in post" in {
      modules.suppliersDal.save(Supplier(None,"name 1","desc 1")) returns  Future(1)
      Post("/service_spray/supplier",SimpleSupplier("name 1","desc 1")) ~> suppliers.SupplierPostRoute ~> check {
        handled must beTrue
        status mustEqual Created
      }
    }

    "not handle the invalid json" in {
      Post("/service_spray/supplier","{\"name\":\"1\"}") ~> suppliers.SupplierPostRoute ~> check {
        handled must beFalse
      }
    }

    "not handle an empty post" in {
      Post("/service_spray/supplier") ~> suppliers.SupplierPostRoute ~> check {
        handled must beFalse
      }
    }

  }

}
