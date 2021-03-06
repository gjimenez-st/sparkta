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

package com.stratio.sparkta.serving.core

import akka.event.slf4j.SLF4JLogging
import com.typesafe.config.Config

/**
 * Helper with common operations used to create a Sparkta context used to run the application.
 * @author danielcsant
 */
object SparktaConfig extends SLF4JLogging {

  var mainConfig: Config = _

  /**
   * Initializes base configuration.
   * @param currentConfig if it is setted the function tries to load a node from a loaded config.
   * @param node with the node needed to load the configuration.
   * @return the loaded configuration.
   */
  def initConfig(node: String,
                 currentConfig: Option[Config] = None,
                 configFactory: ConfigFactory = new SparktaConfigFactory): Config = {
    log.info(s"> Loading $node configuration")
    val configResult = currentConfig match {
      case Some(config) => Some(config.getConfig(node))
      case _ => configFactory.getConfig(node)
    }
    assert(configResult.isDefined, "Fatal Error: configuration can not be loaded: $node")
    configResult.get
  }

  def initMainConfig(currentConfig: Option[Config] = None,
                     configFactory: ConfigFactory = new SparktaConfigFactory): Config = {
    mainConfig = initConfig(AppConstant.ConfigAppName, currentConfig, configFactory)
    mainConfig
  }
}
