package com.rclgroup.dolphin.web.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PortMod {
private String portCode;
private String portName;


public String getPortCode() {
	return portCode;
}
public void setPortCode(String portCode) {
	this.portCode = portCode;
}
public String getPortName() {
	return portName;
}
public void setPortName(String portName) {
	this.portName = portName;
}
@Override
public String toString() {
	return "PortMod [portCode=" + portCode + ", portName=" + portName + "]";
}


}
