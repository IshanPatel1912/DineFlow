package service;

import dao.ReservationDAO;
import model.Reservation;
import java.sql.Timestamp;
import java.util.PriorityQueue;

public class ReservationService {

    private ReservationDAO reservationDAO;

    public ReservationService() {
        this.reservationDAO = new ReservationDAO();
    }

    public boolean bookTable(int customerId, int tableId, Timestamp time) {
        if (customerId <= 0 || tableId <= 0 || time == null) {
            return false;
        }
        Reservation res = new Reservation(0, customerId, tableId, time, "PENDING");
        return reservationDAO.createReservation(res);
    }

    public PriorityQueue<Reservation> viewUpcomingReservations() {
        return reservationDAO.getPendingReservations();
    }

    public boolean confirmReservation(int reservationId) {
        if (reservationId <= 0) {
            return false;
        }
        return reservationDAO.updateReservationStatus(reservationId, "CONFIRMED");
    }

    public boolean cancelReservation(int reservationId) {
        if (reservationId <= 0) {
            return false;
        }
        return reservationDAO.updateReservationStatus(reservationId, "CANCELLED");
    }
}