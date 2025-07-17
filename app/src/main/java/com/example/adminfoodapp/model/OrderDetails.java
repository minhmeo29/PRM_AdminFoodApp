package com.example.adminfoodapp.model;

import java.io.Serializable;
import java.util.List;

public class OrderDetails implements Serializable {
    private String userUid;
    private String userName;
    private List<String> foodNames;
    private List<String> foodImages;
    private List<String> foodPrices;
    private List<Integer> foodQuantities;
    private String address;
    private String totalPrice;
    private String phoneNumber;
    private boolean orderAccepted;
    private boolean paymentReceived;
    private String itemPushKey;
    private long currentTime;

    public OrderDetails() {}

    // Getters and Setters
    public String getUserUid() { return userUid; }
    public void setUserUid(String userUid) { this.userUid = userUid; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public List<String> getFoodNames() { return foodNames; }
    public void setFoodNames(List<String> foodNames) { this.foodNames = foodNames; }

    public List<String> getFoodImages() { return foodImages; }
    public void setFoodImages(List<String> foodImages) { this.foodImages = foodImages; }

    public List<String> getFoodPrices() { return foodPrices; }
    public void setFoodPrices(List<String> foodPrices) { this.foodPrices = foodPrices; }

    public List<Integer> getFoodQuantities() { return foodQuantities; }
    public void setFoodQuantities(List<Integer> foodQuantities) { this.foodQuantities = foodQuantities; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getTotalPrice() { return totalPrice; }
    public void setTotalPrice(String totalPrice) { this.totalPrice = totalPrice; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public boolean isOrderAccepted() { return orderAccepted; }
    public void setOrderAccepted(boolean orderAccepted) { this.orderAccepted = orderAccepted; }

    public boolean isPaymentReceived() { return paymentReceived; }
    public void setPaymentReceived(boolean paymentReceived) { this.paymentReceived = paymentReceived; }

    public String getItemPushKey() { return itemPushKey; }
    public void setItemPushKey(String itemPushKey) { this.itemPushKey = itemPushKey; }

    public long getCurrentTime() { return currentTime; }
    public void setCurrentTime(long currentTime) { this.currentTime = currentTime; }
} 