/**
 * Copyright (C) 2015 Stratio (http://stratio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stratio.sparkta.serving.api.actor

import akka.actor.Actor
import akka.event.slf4j.SLF4JLogging
import com.stratio.sparkta.driver.models.{FragmentElementModel, StreamingContextStatusEnum}
import com.stratio.sparkta.sdk.JsoneyStringSerializer
import com.stratio.sparkta.serving.core.AppConstant
import org.apache.curator.framework.CuratorFramework
import org.apache.zookeeper.KeeperException.NoNodeException
import org.json4s.DefaultFormats
import org.json4s.ext.EnumNameSerializer
import org.json4s.native.Serialization._
import spray.httpx.Json4sJacksonSupport

import scala.collection.JavaConversions
import scala.util.Try

/**
 * List of all possible akka messages used to manage fragments.
 */
case class FragmentSupervisorActor_create(fragment: FragmentElementModel)


case class FragmentSupervisorActor_findByType(fragmentType: String)

case class FragmentSupervisorActor_findByTypeAndName(fragmentType: String, name: String)
case class FragmentSupervisorActor_deleteByTypeAndName(fragmentType: String, name: String)
case class FragmentSupervisorActor_response_fragment(fragment: Try[FragmentElementModel])
case class FragmentSupervisorActor_response_fragments(fragments: Try[Seq[FragmentElementModel]])
case class FragmentSupervisorActor_response(status: Try[Unit])

/**
 * Implementation of supported CRUD operations over ZK needed to manage Fragments.
 * @author anistal
 */
class FragmentActor(curatorFramework: CuratorFramework) extends Actor with Json4sJacksonSupport with SLF4JLogging {

  implicit val json4sJacksonFormats = DefaultFormats +
    new EnumNameSerializer(StreamingContextStatusEnum) +
    new JsoneyStringSerializer()

  override def receive: Receive = {
    case FragmentSupervisorActor_findByTypeAndName(fragmentType, name) => doDetail(fragmentType, name)
    case FragmentSupervisorActor_create(fragment) => doCreate(fragment)
    case FragmentSupervisorActor_deleteByTypeAndName(fragmentType, name) => doDeleteByTypeAndName(fragmentType, name)
    case FragmentSupervisorActor_findByType(fragmentType) => doFindByType(fragmentType)
  }

  def doFindByType(fragmentType: String): Unit =
    sender ! FragmentSupervisorActor_response_fragments(Try({
      val children = curatorFramework.getChildren.forPath(FragmentActor.generateFragmentPath(fragmentType))
      JavaConversions.asScalaBuffer(children).toList.map(element =>
        read[FragmentElementModel](new String(curatorFramework.getData.forPath(
          s"${FragmentActor.generateFragmentPath(fragmentType)}/$element")))).toSeq
    }).recover {
      case e: NoNodeException => Seq()
    })

  def doDetail(fragmentType: String, name: String): Unit =
    sender ! new FragmentSupervisorActor_response_fragment(Try({
      log.info(s"> Retrieving information for path: ${FragmentActor.generateFragmentPath(fragmentType)}/$name)")
      read[FragmentElementModel](new String(curatorFramework.getData.forPath(
        s"${FragmentActor.generateFragmentPath(fragmentType)}/$name")))
    }))

  def doCreate(fragment: FragmentElementModel): Unit =
    sender ! FragmentSupervisorActor_response(Try({
      curatorFramework.create().creatingParentsIfNeeded().forPath(
        s"${FragmentActor.generateFragmentPath(fragment.fragmentType)}/${fragment.name}", write(fragment).getBytes())
    }))

  def doDeleteByTypeAndName(fragmentType: String, name: String): Unit =
    sender ! FragmentSupervisorActor_response(Try({
      curatorFramework.delete().forPath(s"${FragmentActor.generateFragmentPath(fragmentType)}/$name")
    }))
}

object FragmentActor {

  def generateFragmentPath(fragmentType: String): String = {
    fragmentType match {
      case "input" => s"${AppConstant.BaseZKPath}/fragments/input"
      case "output" => s"${AppConstant.BaseZKPath}/fragments/output"
      case _ => throw new IllegalArgumentException("The fragment type must be input|output")
    }
  }
}