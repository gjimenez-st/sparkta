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

package com.stratio.sparkta.serving.api

import akka.event.slf4j.SLF4JLogging
import com.stratio.sparkta.serving.api.helpers.SparktaHelper
import com.stratio.sparkta.serving.core.{SparktaConfig, AppConstant}

/**
 * Entry point of the application.
 */
object Sparkta extends App with SLF4JLogging {

  val sparktaHome   = SparktaHelper.initSparktaHome()
  val jars          = SparktaHelper.initJars(AppConstant.JarPaths, sparktaHome)
  val configSparkta = SparktaConfig.initConfig(AppConstant.ConfigAppName)
  val configApi     = SparktaConfig.initConfig(AppConstant.ConfigApi, Some(configSparkta))

  SparktaHelper.initAkkaSystem(configSparkta, configApi, jars, AppConstant.ConfigAppName)
}
