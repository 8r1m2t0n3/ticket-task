package org.andersen.homework.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.andersen.homework.model.entity.ticket.BusTicket;

public class BusTicketFileReader {

  public static List<BusTicket> readTicketsFromFile(String filePath) {
    List<BusTicket> tickets = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();

    mapper.registerModule(new JavaTimeModule());

    try {
      File file = new File(filePath);

      if (!file.exists()) {
        System.out.println("File not found at: " + filePath);
        return tickets;
      }

      tickets = mapper.readValue(file,
          mapper.getTypeFactory().constructCollectionType(List.class, BusTicket.class));

    } catch (IOException e) {
      e.printStackTrace();
    }

    return tickets;
  }
}
