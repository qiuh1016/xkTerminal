package com.cetcme.xkterminal.Sqlite.Bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Table(name="t_other_ship")
public class OtherShipBean {
    @Column(name = "id", isId = true)
    private int id;
    @Column(name="mmsi")
    private int mmsi;
    @Column(name="ship_id")
    private int ship_id;
    @Column(name="ship_name")
    private String ship_name;
    @Column(name="acq_time")
    private Date acq_time;
    private int longitude;
    private int latitude;
    private float cog;
    private float sog;
    private String callsign;
    private int width;
    private int lenght;
    private int shipType;

    private final Map<Integer, String> shipNameMap = new HashMap<>();

    public OtherShipBean() {
        shipNameMap.put(50, "引航船舶");
        shipNameMap.put(51, "搜救船舶");
        shipNameMap.put(52, "拖轮");
        shipNameMap.put(53, "港口补给船");
        shipNameMap.put(54, "安装有防污染设施或设备的船舶");
        shipNameMap.put(55, "执法船舶");
        shipNameMap.put(50, "引航船舶");


        shipNameMap.put(58, "医疗运送船舶");
        shipNameMap.put(59, "非武装冲突参与国的船舶和航空器");
    }

    public int getShipType() {
        return shipType;
    }

    public void setShipType(int shipType) {
        this.shipType = shipType;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLenght() {
        return lenght;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public float getSog() {
        return sog;
    }

    public void setSog(float sog) {
        this.sog = sog;
    }

    public float getCog() {
        return cog;
    }

    public void setCog(float cog) {
        this.cog = cog;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    private boolean show = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMmsi() {
        return mmsi;
    }

    public void setMmsi(int mmsi) {
        this.mmsi = mmsi;
    }

    public int getShip_id() {
        return ship_id;
    }

    public void setShip_id(int ship_id) {
        this.ship_id = ship_id;
    }

    public String getShip_name() {
        return ship_name;
    }

    public void setShip_name(String ship_name) {
        this.ship_name = ship_name;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public Date getAcq_time() {
        return acq_time;
    }

    public void setAcq_time(Date acq_time) {
        this.acq_time = acq_time;
    }

    @Override
    public String toString() {
        return "OtherShipBean{" +
                "id=" + id +
                ", mmsi=" + mmsi +
                ", ship_id=" + ship_id +
                ", ship_name='" + ship_name + '\'' +
                ", acq_time=" + acq_time +
                ", show=" + show +
                '}';
    }
}
