package com.unqiuehire.kashflow.serviceImpl;

import com.unqiuehire.kashflow.constant.ApiStatus;
import com.unqiuehire.kashflow.constant.EmployeeType;
import com.unqiuehire.kashflow.constant.LoanPlanStatus;
import com.unqiuehire.kashflow.dto.requestdto.LoanPlanRequest;
import com.unqiuehire.kashflow.dto.responsedto.ApiResponse;
import com.unqiuehire.kashflow.dto.responsedto.LoanPlanResponseDto;
import com.unqiuehire.kashflow.entity.LoanPlan;
import com.unqiuehire.kashflow.exception.ResourceNotFoundException;
import com.unqiuehire.kashflow.repository.LoanPlanRepository;
import com.unqiuehire.kashflow.service.LoanPlanService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanPlanServiceImpl implements LoanPlanService {

    private final LoanPlanRepository repository;

    @Override
    public ApiResponse<LoanPlanResponseDto> createLoanPlan(LoanPlanRequest request) {

<<<<<<< HEAD
        Lender lender = lenderRepository.findById(lenderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lender not found: " + lenderId)
                );

        LoanPlan loanPlan = new LoanPlan();

        loanPlan.setPlanName(request.getPlanName());
        loanPlan.setAmount(request.getAmount());
        loanPlan.setInterestPerDay(request.getInterestPerDay());
        loanPlan.setPenaltyAmount(request.getPenaltyAmount());
        loanPlan.setPlanDuration(request.getPlanDuration());

//        loanPlan.setMaxRadius(request.getMaxRadius());

        loanPlan.setMinCibil(request.getMinCibil());
        loanPlan.setMaxAge(request.getMaxAge());
        loanPlan.setMinAge(request.getMinAge());
        loanPlan.setMinMonthlyIncome(request.getMinMonthlyIncome());
        loanPlan.setServicePinCode(request.getServicePinCode());
        loanPlan.setMaxActiveLoans(request.getMaxActiveLoans());
        loanPlan.setEmployeeType(request.getEmployeeType());

        // SAFE ENUM CONVERSION
        loanPlan.setStatus(parseStatus(request.getStatus()));

        //  SET RELATION
        loanPlan.setLender(lender);
=======
        LoanPlan loanPlan = LoanPlan.builder()
                .planName(request.getPlanName())
                .lenderId(request.getLenderId())
                .amount(request.getAmount())
                .interestPerDay(request.getInterestPerDay())
                .penaltyAmount(request.getPenaltyAmount())
                .planDuration(request.getPlanDuration())
                .maxRadius(request.getMaxRadius())
                .minCibil(request.getMinCibil())
                .status(LoanPlanStatus.valueOf(request.getStatus().toUpperCase()))
                .build();
>>>>>>> 2c1920e0214c3274d4759c3e604cd0a918f76f21

        LoanPlan saved = repository.save(loanPlan);

        return new ApiResponse<>(
                ApiStatus.SUCCESS,
                "Loan Plan Created",
                mapToResponse(saved)
        );
    }

    @Override
    public ApiResponse<LoanPlanResponseDto> getLoanPlanById(Long id) {

        LoanPlan loanPlan = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Loan Plan not found: " + id)
                );

        return new ApiResponse<>(
                ApiStatus.SUCCESS,
                "Loan Plan Fetched",
                mapToResponse(loanPlan)
        );
    }

    @Override
    public ApiResponse<List<LoanPlanResponseDto>> getAllLoanPlans() {

        List<LoanPlanResponseDto> list = repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return new ApiResponse<>(
                ApiStatus.SUCCESS,
                "All Loan Plans",
                list
        );
    }

    @Override
    public ApiResponse<LoanPlanResponseDto> updateLoanPlan(Long id, LoanPlanRequest request) {

        LoanPlan plan = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Loan Plan not found: " + id)
                );

        plan.setPlanName(request.getPlanName());
        plan.setLenderId(request.getLenderId());
        plan.setAmount(request.getAmount());
        plan.setInterestPerDay(request.getInterestPerDay());
        plan.setPenaltyAmount(request.getPenaltyAmount());
        plan.setPlanDuration(request.getPlanDuration());

//        plan.setMaxRadius(request.getMaxRadius());

        plan.setMinCibil(request.getMinCibil());
<<<<<<< HEAD
        plan.setMinAge(request.getMinAge());
        plan.setMaxAge(request.getMaxAge());
        plan.setMinMonthlyIncome(request.getMinMonthlyIncome());
        plan.setServicePinCode(request.getServicePinCode());
        plan.setMaxActiveLoans(request.getMaxActiveLoans());
        plan.setEmployeeType(request.getEmployeeType());

        //  SAFE ENUM
        plan.setStatus(parseStatus(request.getStatus()));
=======
        plan.setStatus(LoanPlanStatus.valueOf(request.getStatus().toUpperCase()));
>>>>>>> 2c1920e0214c3274d4759c3e604cd0a918f76f21

        LoanPlan updated = repository.save(plan);

        return new ApiResponse<>(
                ApiStatus.SUCCESS,
                "Loan Plan Updated",
                mapToResponse(updated)
        );
    }

    @Override
    public ApiResponse<String> deleteLoanPlan(Long id) {

        LoanPlan plan = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Loan Plan not found: " + id)
                );

        repository.delete(plan);

        return new ApiResponse<>(
                ApiStatus.SUCCESS,
                "Loan Plan Deleted",
                "Deleted successfully"
        );
    }
<<<<<<< HEAD

    // MAPPING METHOD
    private LoanPlanResponseDto mapToResponse(LoanPlan entity) {

        LoanPlanResponseDto response = new LoanPlanResponseDto();

        response.setId(entity.getId());
        response.setPlanName(entity.getPlanName());
        response.setLenderId(entity.getLender().getLenderId());
        response.setAmount(entity.getAmount());
        response.setInterestPerDay(entity.getInterestPerDay());
        response.setPenaltyAmount(entity.getPenaltyAmount());
        response.setPlanDuration(entity.getPlanDuration());

//        response.setMaxRadius(entity.getMaxRadius());

        response.setMinCibil(entity.getMinCibil());
        response.setMinAge(entity.getMinAge());
        response.setMaxAge(entity.getMaxAge());
        response.setMinMonthlyIncome(entity.getMinMonthlyIncome());
        response.setServicePinCode(entity.getServicePinCode());
        response.setMaxActiveLoans(entity.getMaxActiveLoans());
        response.setEmployeeType(entity.getEmployeeType());
        response.setStatus(entity.getStatus().name());

        return response;
    }

    //  SAFE ENUM PARSER (NEW)
    private LoanPlanStatus parseStatus(String status) {
        try {
            return LoanPlanStatus.valueOf(status.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid LoanPlan status: " + status);
        }
=======
    private LoanPlanResponseDto mapToResponse(LoanPlan loanPlan) {
        return LoanPlanResponseDto.builder()
                .id(loanPlan.getId())
                .planName(loanPlan.getPlanName())
                .lenderId(loanPlan.getLenderId())
                .amount(loanPlan.getAmount())
                .interestPerDay(loanPlan.getInterestPerDay())
                .penaltyAmount(loanPlan.getPenaltyAmount())
                .planDuration(loanPlan.getPlanDuration())
                .maxRadius(loanPlan.getMaxRadius())
                .minCibil(loanPlan.getMinCibil())
                .status(loanPlan.getStatus().name())
                .build();
>>>>>>> 2c1920e0214c3274d4759c3e604cd0a918f76f21
    }
}