package com.cg;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.cg.entity.Loan;
import com.cg.exception.*;
import com.cg.repo.LoanRepository;
import com.cg.service.LoanService;

@SpringBootTest
public class MockTest {

    private Optional<Loan> optLoan1, optLoan2, emptyLoan;

    @MockitoBean
    private LoanRepository repo;

    @Autowired
    private LoanService service;

    @BeforeEach
    public void setup() {
        optLoan1 = Optional.of(new Loan("Rishabh", 200000.00, "PENDING"));
        optLoan1.get().setId(1L);

        optLoan2 = Optional.of(new Loan("Amit", 300000.00, "APPROVED"));
        optLoan2.get().setId(2L);

        emptyLoan = Optional.empty();
    }

   

    @Test
    public void testCreateLoanSuccess() {

        Mockito.when(repo.findByApplicantNameAndStatus("Rishabh", "PENDING"))
                .thenReturn(Optional.empty());

        Mockito.when(repo.save(Mockito.any(Loan.class)))
                .thenReturn(optLoan1.get());

        Loan loan = new Loan("Rishabh", 200000.00, null);

        Loan result = service.createLoan(loan);

        assertEquals("PENDING", result.getStatus());

        Mockito.verify(repo).findByApplicantNameAndStatus("Rishabh", "PENDING");
        Mockito.verify(repo).save(Mockito.any(Loan.class));
    }

    @Test
    public void testCreateLoanInvalidAmount() {

        Loan loan = new Loan("Rishabh", -100.00, null);

        assertThrows(InvalidLoanAmountException.class,
                () -> service.createLoan(loan));
    }

    @Test
    public void testCreateLoanDuplicate() {

        Mockito.when(repo.findByApplicantNameAndStatus("Rishabh", "PENDING"))
                .thenReturn(optLoan1);

        Loan loan = new Loan("Rishabh", 200000.00, null);

        assertThrows(DuplicateLoanApplicationException.class,
                () -> service.createLoan(loan));
    }

   

    @Test
    public void testGetLoanByIdSuccess() {

        Mockito.when(repo.findById(1L)).thenReturn(optLoan1);

        Loan loan = service.getLoanById(1L);

        assertEquals("Rishabh", loan.getApplicantName());

        Mockito.verify(repo).findById(1L);
    }

    @Test
    public void testGetLoanByIdNotFound() {

        Mockito.when(repo.findById(3L)).thenReturn(emptyLoan);

        assertThrows(LoanNotFoundException.class,
                () -> service.getLoanById(3L));

        Mockito.verify(repo).findById(3L);
    }

   

    @Test
    public void testUpdateStatusSuccess() {

        Mockito.when(repo.findById(1L)).thenReturn(optLoan1);
        Mockito.when(repo.save(Mockito.any(Loan.class)))
                .thenReturn(optLoan1.get());

        Loan updated = service.updateLoanStatus(1L, "APPROVED");

        assertEquals("APPROVED", updated.getStatus());

        Mockito.verify(repo).findById(1L);
        Mockito.verify(repo).save(Mockito.any(Loan.class));
    }

    @Test
    public void testUpdateStatusLoanNotFound() {

        Mockito.when(repo.findById(5L)).thenReturn(emptyLoan);

        assertThrows(LoanNotFoundException.class,
                () -> service.updateLoanStatus(5L, "APPROVED"));

        Mockito.verify(repo).findById(5L);
    }
}