package com.gc.money.currency.bean.net;

import android.os.Parcel;
import android.os.Parcelable;

public class NetCityBean implements Parcelable {

    public boolean isCheck;


	public int address_id;
	public int user_id;
	public String truename;
	public String phone;
	public String prov;
	public String city;
	public String dist;
	public String detail;
	public String id_card;

	public String getId_card() {
		if (id_card.length()<=8){
			return id_card;
		}else {
			StringBuffer stringBuffer = new StringBuffer();
			for (int i=0;i<id_card.length()-8;i++){
				stringBuffer.append("*");
			}
			return id_card.substring(0,3)+stringBuffer.toString()+id_card.substring(id_card.length()-5,id_card.length()-1);
		}
	}

	public int default_address;
	public String createtime;
	public String modifytime;
	public String card_just;
	public String card_reverse;

    public NetCityBean(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public boolean isDefault(){
    	return default_address == 1;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeByte(this.isCheck ? (byte) 1 : (byte) 0);
		dest.writeInt(this.address_id);
		dest.writeInt(this.user_id);
		dest.writeString(this.truename);
		dest.writeString(this.phone);
		dest.writeString(this.prov);
		dest.writeString(this.city);
		dest.writeString(this.dist);
		dest.writeString(this.detail);
		dest.writeString(this.id_card);
		dest.writeInt(this.default_address);
		dest.writeString(this.createtime);
		dest.writeString(this.modifytime);
		dest.writeString(this.card_just);
		dest.writeString(this.card_reverse);
	}

	protected NetCityBean(Parcel in) {
		this.isCheck = in.readByte() != 0;
		this.address_id = in.readInt();
		this.user_id = in.readInt();
		this.truename = in.readString();
		this.phone = in.readString();
		this.prov = in.readString();
		this.city = in.readString();
		this.dist = in.readString();
		this.detail = in.readString();
		this.id_card = in.readString();
		this.default_address = in.readInt();
		this.createtime = in.readString();
		this.modifytime = in.readString();
		this.card_just = in.readString();
		this.card_reverse = in.readString();
	}

	public static final Creator<NetCityBean> CREATOR = new Creator<NetCityBean>() {
		@Override
		public NetCityBean createFromParcel(Parcel source) {
			return new NetCityBean(source);
		}

		@Override
		public NetCityBean[] newArray(int size) {
			return new NetCityBean[size];
		}
	};
}
