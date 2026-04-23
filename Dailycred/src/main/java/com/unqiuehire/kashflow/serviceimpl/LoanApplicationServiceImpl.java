package com.unqiuehire.kashflow.serviceImpl;

import com.unqiuehire.kashflow.constant.ApiStatus;
import com.unqiuehire.kashflow.constant.ApplicationStatus;
import com.unqiuehire.kashflow.constant.LoanPlanStatus;
import com.unqiuehire.kashflow.dto.requestdto.LoanApplicationApprovalRequestDto;
import com.unqiuehire.kashflow.dto.requestdto.LoanApplicationRequestDto;
import com.unqiuehire.kashflow.dto.responsedto.ApiResponse;
import com.unqiuehire.kashflow.dto.responsedto.LoanApplicationResponseDto;
import com.unqiuehire.kashflow.entity.Borrower;
import com.unqiuehire.kashflow.entity.Lender;
import com.unqiuehire.kashflow.entity.LoanApplication;
import com.unqiuehire.kashflow.exception.ResourceNotFoundException;
import com.unqiuehire.kashflow.repository.BorrowerRepository;
import com.unqiuehire.kashflow.repository.LenderRepository;
import com.unqiuehire.kashflow.repository.LoanApplicationRepository;
import com.unqiuehire.kashflow.service.LoanApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanApplicationServiceImpl implements LoanApplicationService {

<<<<<<< HEAD
    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanPlanRepository loanPlanRepository;
    private final BorrowerRepository borrowerRepository;
    private final LenderRepository lenderRepository;
=======
    private final LoanApplicationRepository repository;
>>>>>>> 2c1920e0214c3274d4759c3e604cd0a918f76f21

    @Override
    public ApiResponse<LoanApplicationResponseDto> applyLoan(Long lenderId, LoanApplicationRequestDto requestDto) {

<<<<<<< HEAD
        if (requestDto == null) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Loan application request cannot be null", null);
        }

        if (requestDto.getBorrowerId() == null) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Borrower id is required", null);
        }

        if (requestDto.getPlanId() == null) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Plan id is required", null);
        }
=======
        LoanApplication app = new LoanApplication();

        app.setBorrowerId(dto.getBorrowerId());
        app.setLenderId(dto.getLenderId());
        app.setPlanId(dto.getPlanId());
        app.setLoanAmount(dto.getLoanAmount());
>>>>>>> 2c1920e0214c3274d4759c3e604cd0a918f76f21

        if (requestDto.getLoanAmount() == null || requestDto.getLoanAmount() <= 0) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Loan amount must be greater than zero", null);
        }

        if (requestDto.getAge() == null || requestDto.getAge() <= 0) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Valid age is required", null);
        }

        if (requestDto.getMonthlyIncome() == null || requestDto.getMonthlyIncome() <= 0) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Valid monthly income is required", null);
        }

        if (requestDto.getEmployeeType() == null) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Employee type is required", null);
        }

        if (requestDto.getPinCode() == null || requestDto.getPinCode().trim().isEmpty()) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Pin code is required", null);
        }

        if (requestDto.getIsEducated() == null) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Education value is required", null);
        }

        if (requestDto.getCollateral() == null || requestDto.getCollateral().trim().isEmpty()) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Collateral is required for all borrowers", null);
        }

        if (Boolean.TRUE.equals(requestDto.getIsEducated())
                && (requestDto.getCertificates() == null || requestDto.getCertificates().trim().isEmpty())) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Certificates are required for educated borrower", null);
        }

        Borrower borrower = borrowerRepository.findById(requestDto.getBorrowerId())
                .orElseThrow(() -> new ResourceNotFoundException("Borrower not found"));

        Lender lender = lenderRepository.findById(lenderId)
                .orElseThrow(() -> new ResourceNotFoundException("Lender not found"));

        LoanPlan loanPlan = loanPlanRepository.findById(requestDto.getPlanId())
                .orElseThrow(() -> new ResourceNotFoundException("Loan plan not found"));

        if (Boolean.FALSE.equals(borrower.getIsActive())) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Inactive borrower cannot apply for loan", null);
        }

        if (Boolean.FALSE.equals(lender.getIsActive())) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Inactive lender cannot receive loan applications", null);
        }

        if (loanPlan.getStatus() != LoanPlanStatus.ACTIVE) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Loan plan is not active", null);
        }

        if (loanPlan.getLender() == null || !loanPlan.getLender().getLenderId().equals(lenderId)) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Lender mismatch with loan plan", null);
        }

        if (requestDto.getLenderId() != null && !requestDto.getLenderId().equals(lenderId)) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Request lender id does not match path lender id", null);
        }

        if (requestDto.getLoanAmount() > loanPlan.getAmount()) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Requested loan amount exceeds loan plan amount", null);
        }

//        Integer actualAge = Period.between(borrower.getDateOfBirth(), LocalDate.now()).getYears();
//        if (!actualAge.equals(requestDto.getAge())) {
//            return new ApiResponse<>(ApiStatus.FAILURE, "Provided age does not match borrower date of birth", null);
//        }

        if (requestDto.getAge() < loanPlan.getMinAge() || requestDto.getAge() > loanPlan.getMaxAge()) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Borrower age is not eligible for this plan", null);
        }

        if (requestDto.getMonthlyIncome() < loanPlan.getMinMonthlyIncome()) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Monthly income is below minimum required", null);
        }

        if (!requestDto.getEmployeeType().equals(loanPlan.getEmployeeType())) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Employee type is not matching with loan plan", null);
        }

        if (!requestDto.getPinCode().equals(loanPlan.getServicePinCode())) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Borrower pin code is outside lender service area", null);
        }

        if (!borrower.getPincode().equals(requestDto.getPinCode())) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Application pin code does not match borrower registered pin code", null);
        }

        boolean alreadyPending = loanApplicationRepository.existsByBorrower_BorrowerIdAndLoanPlan_IdAndStatus(
                borrower.getBorrowerId(),
                loanPlan.getId(),
                ApplicationStatus.PENDING
        );

        if (alreadyPending) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Borrower already has a pending application for this plan", null);
        }

        LoanApplication application = new LoanApplication();
        application.setBorrower(borrower);
        application.setLender(lender);
        application.setLoanPlan(loanPlan);
        application.setLoanAmount(requestDto.getLoanAmount());
        application.setAge(requestDto.getAge());
        application.setMonthlyIncome(requestDto.getMonthlyIncome());
        application.setEmploymentType(requestDto.getEmployeeType());
        application.setPinCode(requestDto.getPinCode());
        application.setIsEducated(requestDto.getIsEducated());
        application.setCertificates(requestDto.getCertificates());
        application.setCollateral(requestDto.getCollateral());
        application.setStatus(ApplicationStatus.PENDING);
        application.setApplicationDate(LocalDate.now());
        application.setAppliedAt(LocalDateTime.now());
        application.setCreatedAt(LocalDateTime.now());
        application.setIsLoanCreated(false);
        application.setRejectionReason(null);

        LoanApplication saved = loanApplicationRepository.save(application);

        return new ApiResponse<>(
                ApiStatus.SUCCESS,
                "Loan application submitted successfully",
                mapToDto(saved)
        );
    }

    @Override
<<<<<<< HEAD
    public ApiResponse<LoanApplicationResponseDto> updateLoanDecision(Long applicationId, LoanApplicationApprovalRequestDto requestDto) {

        LoanApplication application = loanApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan application not found"));
=======
    public ApiResponse<LoanApplicationResponseDto> getById(Long id) {
        LoanApplication app = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
>>>>>>> 2c1920e0214c3274d4759c3e604cd0a918f76f21

        if (requestDto == null) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Approval request cannot be null", null);
        }

        if (requestDto.getApplicationStatus() == null) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Application status is required", null);
        }

        if (application.getStatus() != ApplicationStatus.PENDING) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Only pending applications can be approved or rejected", null);
        }

        if (requestDto.getApplicationStatus() != ApplicationStatus.APPROVED
                && requestDto.getApplicationStatus() != ApplicationStatus.REJECTED) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Only APPROVED or REJECTED status is allowed", null);
        }

        application.setStatus(requestDto.getApplicationStatus());

        if (requestDto.getApplicationStatus() == ApplicationStatus.REJECTED) {
            if (requestDto.getRemarks() == null || requestDto.getRemarks().trim().isEmpty()) {
                return new ApiResponse<>(ApiStatus.FAILURE, "Rejection reason is required when application is rejected", null);
            }
            application.setRejectionReason(requestDto.getRemarks());
        } else {
            application.setRejectionReason(null);
        }

        LoanApplication updated = loanApplicationRepository.save(application);

        return new ApiResponse<>(
                ApiStatus.SUCCESS,
                "Loan application decision updated successfully",
                mapToDto(updated)
        );
    }

    @Override
    public ApiResponse<LoanApplicationResponseDto> getApplicationById(Long applicationId) {
        Optional<LoanApplication> optional = loanApplicationRepository.findById(applicationId);
        if (optional.isEmpty()) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Loan application not found", null);
        }

        return new ApiResponse<>(ApiStatus.SUCCESS, "Loan application fetched successfully", mapToDto(optional.get()));
    }

    @Override
    public ApiResponse<List<LoanApplicationResponseDto>> getApplicationsByLenderId(Long lenderId) {
        List<LoanApplicationResponseDto> applications = loanApplicationRepository.findByLender_LenderId(lenderId)
                .stream()
                .map(this::mapToDto)
                .toList();

        return new ApiResponse<>(
                ApiStatus.SUCCESS,
                "Lender loan applications fetched successfully",
                applications
        );
    }

    @Override
    public ApiResponse<List<LoanApplicationResponseDto>> getApplicationsByBorrowerId(Long borrowerId) {
        List<LoanApplicationResponseDto> applications = loanApplicationRepository.findByBorrower_BorrowerId(borrowerId)
                .stream()
                .map(this::mapToDto)
                .toList();

        return new ApiResponse<>(
                ApiStatus.SUCCESS,
                "Borrower loan applications fetched successfully",
                applications
        );
    }

    @Override
    public ApiResponse<LoanApplicationResponseDto> createApplication(LoanApplicationRequestDto dto) {
        if (dto == null) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Loan application request cannot be null", null);
        }

        if (dto.getLenderId() == null) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Lender id is required", null);
        }

        return applyLoan(dto.getLenderId(), dto);
    }

    @Override
    public ApiResponse<LoanApplicationResponseDto> getById(Long id) {
        return getApplicationById(id);
    }

    @Override
    public ApiResponse<List<LoanApplicationResponseDto>> getByBorrower(Long borrowerId) {
        List<LoanApplicationResponseDto> list = loanApplicationRepository.findByBorrower_BorrowerId(borrowerId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return new ApiResponse<>(ApiStatus.SUCCESS, "Borrower applications fetched successfully", list);
    }

    @Override
    public ApiResponse<List<LoanApplicationResponseDto>> getByLender(Long lenderId) {
        List<LoanApplicationResponseDto> list = loanApplicationRepository.findByLender_LenderId(lenderId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

<<<<<<< HEAD
        return new ApiResponse<>(ApiStatus.SUCCESS, "Lender applications fetched successfully", list);
=======
        List<LoanApplicationResponseDto> list = repository.findByLenderId(lenderId)
                .stream()
                .map(this::mapToDto)
                .toList();

        return new ApiResponse<>(ApiStatus.SUCCESS, "Fetched", list);
>>>>>>> 2c1920e0214c3274d4759c3e604cd0a918f76f21
    }

    @Override
    public ApiResponse<String> cancelApplication(Long id) {
        LoanApplication application = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        if (application.getStatus() == ApplicationStatus.APPROVED) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Approved application cannot be cancelled", null);
        }

        if (application.getStatus() == ApplicationStatus.REJECTED) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Rejected application cannot be cancelled", null);
        }

        if (application.getStatus() == ApplicationStatus.CANCELLED) {
            return new ApiResponse<>(ApiStatus.FAILURE, "Application is already cancelled", null);
        }

        application.setStatus(ApplicationStatus.CANCELLED);
        loanApplicationRepository.save(application);

        return new ApiResponse<>(ApiStatus.SUCCESS, "Loan application cancelled successfully", "ID: " + id);
    }

    private LoanApplicationResponseDto mapToDto(LoanApplication app) {
        LoanApplicationResponseDto dto = new LoanApplicationResponseDto();

        dto.setApplicationId(app.getApplicationId());
<<<<<<< HEAD
        dto.setBorrowerId(app.getBorrower() != null ? app.getBorrower().getBorrowerId() : null);
        dto.setLenderId(app.getLender() != null ? app.getLender().getLenderId() : null);
        dto.setPlanId(app.getLoanPlan() != null ? app.getLoanPlan().getId() : null);
=======
        dto.setBorrowerId(app.getBorrowerId());
        dto.setLenderId(app.getLenderId());
        dto.setPlanId(app.getPlanId());
>>>>>>> 2c1920e0214c3274d4759c3e604cd0a918f76f21
        dto.setLoanAmount(app.getLoanAmount());
        dto.setAge(app.getAge());
        dto.setMonthlyIncome(app.getMonthlyIncome());
        dto.setEmployeeType(app.getEmploymentType());
        dto.setPinCode(app.getPinCode());
        dto.setIsEducated(app.getIsEducated());
        dto.setCertificates(app.getCertificates());
        dto.setCollateral(app.getCollateral());
        dto.setApplicationStatus(app.getStatus());
        dto.setRemarks(app.getRejectionReason());
        dto.setAppliedAt(app.getAppliedAt());

        return dto;
    }
}