package model;

import java.sql.Timestamp;

public class Reservation {
    private int reservationId;
    private int customerId;
    private int tableId;
    private Timestamp reservationTime;
    private String status;

    public Reservation() {}

    public Reservation(int reservationId, int customerId, int tableId, Timestamp reservationTime, String status) {
        this.reservationId = reservationId;
        this.customerId = customerId;
        this.tableId = tableId;
        this.reservationTime = reservationTime;
        this.status = status;
    }

    public int getReservationId() { return reservationId; }
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public int getTableId() { return tableId; }
    public void setTableId(int tableId) { this.tableId = tableId; }

    public Timestamp getReservationTime() { return reservationTime; }
    public void setReservationTime(Timestamp reservationTime) { this.reservationTime = reservationTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}