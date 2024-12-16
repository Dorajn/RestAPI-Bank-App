package com.bank.bank_app.service;

import com.bank.bank_app.model.Transfers;
import com.bank.bank_app.model.Users;
import com.bank.bank_app.repo.UserRepo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FileGeneratorService {

    private UserRepo userRepo;
    private TranHistoryService historyService;

    public FileGeneratorService(UserRepo userRepo, TranHistoryService historyService) {
        this.userRepo = userRepo;
        this.historyService = historyService;
    }

    public ResponseEntity<?> createPdfFile(int id) {
        if(userRepo.existsById(id)){
            Users user = userRepo.findById(id).orElseThrow();
            List<Transfers> transactions = historyService.getTransactionsHistory(id).getBody();
            if(!transactions.isEmpty())
                return pdfCreator(transactions, user);
            else
                return new ResponseEntity<>("History is empty", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private ResponseEntity<byte[]> pdfCreator(List<Transfers> transactions, Users user){
        try{
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Paragraph header = new Paragraph(
                    "Transaction raport for " + user.getUsername(),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK));
            header.setAlignment(1);

            document.add(header);
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(5);
            table.addCell("Transaction ID");
            table.addCell("Sender");
            table.addCell("Receiver");
            table.addCell("Amount");
            table.addCell("Date");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

            for (var transaction : transactions) {
                table.addCell(String.valueOf(transaction.getTransferId()));
                table.addCell(String.valueOf(transaction.getSenderId().getUsername()));
                table.addCell(String.valueOf(transaction.getReceiverId().getUsername()));
                table.addCell(String.valueOf(transaction.getAmount()));
                table.addCell(transaction.getTransferTimestamp().format(formatter));
            }

            document.add(table);
            document.close();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transaction_report.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(outputStream.toByteArray());

        }
        catch (DocumentException e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
