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

import com.typesafe.config.Config
import scala.sys.process._

import com.stratio.sparkta.driver.models.AggregationPoliciesModel
import com.stratio.sparkta.serving.api.actor.SparkSubmitActor.SubmitJob

class SparkSubmitActor(policy: AggregationPoliciesModel,
                       jobServerConfig: Config) extends InstrumentedActor {

  override def receive: PartialFunction[Any, Unit] = {
    case SubmitJob => doSubmitJob
  }

  def doSubmitJob: Unit = {
    val cmd = "spark-submit --class com.stratio.sparkta.driver.SparktaJob --master  yarn-client " +
      "--num-executors 2 driver/target/driver-plugin.jar " + policy
    cmd.!!
  }
}

object SparkSubmitActor {

  case object SubmitJob

}
