package com.rclgroup.dolphin.web.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Maa
 *
 */
@XmlRootElement
public class UserMod extends BaseInputMod{
	private static final Map<String,String> roleMapping =new HashMap<>();
	
	static {
		roleMapping.put("1", "Location User");
		roleMapping.put("2", "Location Head");
		roleMapping.put("3", "Region Head");
		roleMapping.put("4", "SuperUser");
		 	
	}
	 
	private String pass;
	private String userName;
	private String companyName;
	private String countryName;
	
	private String phoneNumber;
	private String role;
	private String roleId;
	private String fromDate;
	private String toDate;
	private String location;
	private String loginUserId;
	
	private String propertyName;
	
	private List<PropertyMod> shipperList;
	private List<PropertyMod> pods;
	
	private String roleSearch;
	
	
	public String getRoleSearch() {
		return roleSearch;
	}
	public void setRoleSearch(String roleSearch) {
		this.roleSearch = roleSearch;
	}
	public List<PropertyMod> getPods() {
		return pods;
	}
	public void setPods(List<PropertyMod> pods) {
		this.pods = pods;
	}
	public List<PropertyMod> getShipperList() {
		return shipperList;
	}
	public void setShipperList(List<PropertyMod> shipperList) {
		this.shipperList = shipperList;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	 
	 
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getRole() {
		return roleMapping.get(roleId)==null?roleId:roleMapping.get(roleId);
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLoginUserId() {
		return loginUserId;
	}
	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "user id"+getUserId()+ " ";
	}
	

}
