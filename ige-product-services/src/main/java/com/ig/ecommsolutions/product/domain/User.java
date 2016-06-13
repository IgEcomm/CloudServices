package com.ig.ecommsolutions.product.domain;

import java.util.Date;
import java.util.Map;

class User {

	String username;
	boolean accountExpired;
	boolean accountLocked;
	boolean passwordExpired;
	String firstName;
	String lastName;
	String email;
	String pwd;
	Integer point;
	String title;
	String middleName;
	Boolean isDeleted = false;
	String phone;
	String mobile;
	String fax;
	Boolean notificationFlag = true;
	String profilePicUrl;
	Integer location;
	Integer retailer;
	Integer userType;

	Integer deviceType;
	String deviceId;
	Map<String, String> rewardDistributionDetail;
	int immediateNoteCount;
	Date createdOn = new Date();
	User createdBy;

	Date lastModifiedOn;
	User modifiedBy;

	String facebookId;
	String twitterId;
	String googlePlusId;
	
	WishList wishList[];
	UserCart myCart[];
	RecentlyViewed recentlyViewed; 	

}
