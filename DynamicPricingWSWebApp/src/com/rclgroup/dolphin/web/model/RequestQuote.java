package com.rclgroup.dolphin.web.model;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RequestQuote  extends BaseInputMod{

private static final Map<String,String> statusMapping =new HashMap<>();
	
	static {
		statusMapping.put("S", "Submited");
		statusMapping.put("A", "Approved");
		statusMapping.put("R", "Rejected");
		 
		 	
	}
		private String id;
		private String targetRate;
		private String targetEtd;
		private String freeTime;
		private String weeklyVolume;
		private String voyage;
		private String vessel;
		private String shipper;
		private String pod;
		private String weight;
		private String cmodity;		 
		private String remarks;
		
		private String pol;
		private String requestId;
		private String approvedId;
		private String arppovedBy;
		private String createdDate;
		private String createdBy;
		private String statusId;
		private String stausAsString;
		private String fromDate;
		private String toDate;
		private String approvedDate;
		
		private String roleId;
		
		private String reasonOfReject;
		
		private String contractParty;
		
		 
	   
		public String getContractParty() {
			return contractParty;
		}
		public void setContractParty(String contractParty) {
			this.contractParty = contractParty;
		}
		public String getReasonOfReject() {
			return reasonOfReject;
		}
		public void setReasonOfReject(String reasonOfReject) {
			this.reasonOfReject = reasonOfReject;
		}
		public String getWeight() {
			return weight;
		}
		public void setWeight(String weight) {
			this.weight = weight;
		}
		public String getRemarks() {
			return remarks;
		}
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
		public static Map<String, String> getStatusmapping() {
			return statusMapping;
		}
		public String getCmodity() {
			return cmodity;
		}
		public void setCmodity(String cmodity) {
			this.cmodity = cmodity;
		}
		public String getApprovedDate() {
			return approvedDate;
		}
		public void setApprovedDate(String approvedDate) {
			this.approvedDate = approvedDate;
		}
		public String getRoleId() {
			return roleId;
		}
		public void setRoleId(String roleId) {
			this.roleId = roleId;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
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
		public String getStausAsString() {
			return statusMapping.get(statusId);
		}
		public void setStausAsString(String stausAsString) {
			this.stausAsString = stausAsString;
		}
		 
		public String getStatusId() {
			return statusId;
		}
		public void setStatusId(String statusId) {
			this.statusId = statusId;
		}
		public String getTargetRate() {
			return targetRate;
		}
		public void setTargetRate(String targetRate) {
			this.targetRate = targetRate;
		}
		public String getTargetEtd() {
			return targetEtd;
		}
		public void setTargetEtd(String targetEtd) {
			this.targetEtd = targetEtd;
		}
		public String getFreeTime() {
			return freeTime;
		}
		public void setFreeTime(String freeTime) {
			this.freeTime = freeTime;
		}
		public String getWeeklyVolume() {
			return weeklyVolume;
		}
		public void setWeeklyVolume(String weeklyVolume) {
			this.weeklyVolume = weeklyVolume;
		}
		public String getVoyage() {
			return voyage;
		}
		public void setVoyage(String voyage) {
			this.voyage = voyage;
		}
		public String getVessel() {
			return vessel;
		}
		public void setVessel(String vessel) {
			this.vessel = vessel;
		}
		public String getShipper() {
			return shipper;
		}
		public void setShipper(String shipper) {
			this.shipper = shipper;
		}
		public String getPod() {
			return pod;
		}
		public void setPod(String pod) {
			this.pod = pod;
		}
		public String getPol() {
			return pol;
		}
		public void setPol(String pol) {
			this.pol = pol;
		}
		public String getRequestId() {
			return requestId;
		}
		public void setRequestId(String requestId) {
			this.requestId = requestId;
		}
		public String getApprovedId() {
			return approvedId;
		}
		public void setApprovedId(String approvedId) {
			this.approvedId = approvedId;
		}
		 
		public String getArppovedBy() {
			return arppovedBy;
		}
		public void setArppovedBy(String arppovedBy) {
			this.arppovedBy = arppovedBy;
		}
		public String getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(String createdDate) {
			this.createdDate = createdDate;
		}
		public String getCreatedBy() {
			return createdBy;
		}
		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}
		 
		
		
		
		
}
