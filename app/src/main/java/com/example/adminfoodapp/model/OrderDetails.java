package com.example.adminfoodapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class OrderDetails implements Parcelable, Serializable {

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

    public OrderDetails() {
        // Empty constructor
    }

    protected OrderDetails(Parcel in) {
        userUid = in.readString();
        userName = in.readString();
        address = in.readString();
        totalPrice = in.readString();
        phoneNumber = in.readString();
        orderAccepted = in.readByte() != 0;
        paymentReceived = in.readByte() != 0;
        itemPushKey = in.readString();
        currentTime = in.readLong();
    }

    public static final Creator<OrderDetails> CREATOR = new Creator<OrderDetails>() {
        @Override
        public OrderDetails createFromParcel(Parcel in) {
            return new OrderDetails(in);
        }

        @Override
        public OrderDetails[] newArray(int size) {
            return new OrderDetails[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userUid);
        dest.writeString(userName);
        dest.writeString(address);
        dest.writeString(totalPrice);
        dest.writeString(phoneNumber);
        dest.writeByte((byte) (orderAccepted ? 1 : 0));
        dest.writeByte((byte) (paymentReceived ? 1 : 0));
        dest.writeString(itemPushKey);
        dest.writeLong(currentTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters và setters (tùy chọn, nhưng nên thêm nếu cần)

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

