package io.borlandfcsd.cinemasystem.entity;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Ticket;

public class CinemaHall {
    private final static int ROW_NUMB = 8;
    private final static int PLACE_NUMB = 10;
    private final static double TICKET_PRICE = 120.0;
    private Ticket[][] hall;

    public CinemaHall() {
        hall = new Ticket[ROW_NUMB][PLACE_NUMB];
        for (int i = 0; i < hall.length; i++) {
            for (int j = 0; j < hall[i].length; j++) {
                Ticket emptyTicket = new Ticket();
                emptyTicket.setRow(i + 1);
                emptyTicket.setPlace(j + 1);
                emptyTicket.setPrice(TICKET_PRICE);
                emptyTicket.setPlaceStatus(PlaceStatus.EMPTY);
                hall[i][j] = emptyTicket;
            }
        }
    }

    public Ticket[][] getHall() {
        return hall;
    }

    public Ticket getTicket(int row, int place) {
        return hall[row - 1][place - 1];
    }

    public void setPlaceStatus(int row, int place, Ticket ticket) {
        hall[row - 1][place - 1] = ticket;
    }
}
