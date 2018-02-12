/**
 *  Momentary
 *
 *  Copyright 2018 김현승
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Momentary Timed Switch",
    namespace: "sng2c",
    author: "김현승",
    description: "Simple Momentary Timed Switch",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
    section("Subscribe On status for this light") {
        input "theswitch", "capability.switch", required: true
    }
    section("Turn off after from this light is on") {
        input "seconds", "number", required: true, title: "Seconds?"
    }
}

def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
	// TODO: subscribe to attributes, devices, locations, etc.
    subscribe(theswitch, "switch.on", switchOnDetectedHandler)
}

def switchOnDetectedHandler(evt) {
    log.debug "switchOnDetectedHandler called: $evt"
	runIn(seconds, turnOffSwitch)
}

def turnOffSwitch(){
    log.debug "turnOff Switch"
	theswitch.off()
}

