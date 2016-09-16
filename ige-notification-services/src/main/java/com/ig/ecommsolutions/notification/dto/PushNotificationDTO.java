package com.ig.ecommsolutions.notification.dto;

public class PushNotificationDTO {

	 String notification;
	 String deviceId;
	public String getNotification() {
		return notification;
	}
	public void setNotification(String notification) {
		this.notification = notification;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
}
