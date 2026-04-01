package com.cg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


	
	@Entity
	@Table(name = "abes_Loan")
	public class Loan {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private Long Id;

	    @Column(name = "applicantName", length = 25)
	    private String applicantName;

	    @Column(name = "loanAmount")
	    private Double loanAmount;

	    @Column(name = "status")
	    private String status;

		public Long getId() {
			return Id;
		}

		public void setId(Long id) {
			Id = id;
		}

		public String getApplicantName() {
			return applicantName;
		}

		public void setApplicantName(String applicantName) {
			this.applicantName = applicantName;
		}

		public Double getLoanAmount() {
			return loanAmount;
		}

		public void setLoanAmount(Double loanAmount) {
			this.loanAmount = loanAmount;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
		
		public Loan() {
			
		}
		
		public Loan(String applicantName, Double loanAmount, String status) {
			this.applicantName = applicantName;
			this.loanAmount = loanAmount;
			this.status = status;
		}
	    
		
	    

}
