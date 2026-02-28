package com.hostelmanagement.hostel.modules.tenant;

import com.hostelmanagement.hostel.modules.bed.Bed;
import com.hostelmanagement.hostel.modules.bed.BedRepository;
import com.hostelmanagement.hostel.modules.bed.BedStatus;
import com.hostelmanagement.hostel.modules.hostel.Hostel;
import com.hostelmanagement.hostel.modules.hostel.HostelRepository;
import com.hostelmanagement.hostel.modules.payment.Payment;
import com.hostelmanagement.hostel.modules.payment.PaymentRepository;
import com.hostelmanagement.hostel.modules.payment.PaymentStatus;
import com.hostelmanagement.hostel.modules.tenant.dto.request.OwnerDashboardFilterDto;
import com.hostelmanagement.hostel.modules.tenant.dto.request.TenantCreateDto;
import com.hostelmanagement.hostel.modules.tenant.dto.request.TenantFilterDto;
import com.hostelmanagement.hostel.modules.tenant.dto.request.TenantOnboardingDto;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.criteria.Predicate;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;
    private final BedRepository bedRepository;
    private final HostelRepository hostelRepository;
    private final PaymentRepository paymentRepository;

    public TenantService(TenantRepository tenantRepository, BedRepository bedRepository, HostelRepository hostelRepository, PaymentRepository paymentRepository) {
        this.tenantRepository = tenantRepository;
        this.bedRepository = bedRepository;
        this.hostelRepository = hostelRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public String createOnboarding(TenantOnboardingDto dto) {

        Bed bed = bedRepository.findById(dto.getBedId())
                .orElseThrow(() -> new RuntimeException("Bed not found"));

        Hostel hostel = bed.getHostel();

        String token = UUID.randomUUID().toString();

        Tenant tenant = new Tenant();
        tenant.setTenantName(dto.getTenantName());
        tenant.setMobile(dto.getMobile());
        tenant.setBed(bed);

        // choose monthly fee
        Double monthlyFee = dto.getMonthlyFee() != null
                ? dto.getMonthlyFee()
                : hostel.getDefaultMonthlyFee();

        // choose maintenance fee
        Double maintenanceFee = dto.getMaintenanceFee() != null
                ? dto.getMaintenanceFee()
                : hostel.getDefaultMaintenanceFee();

        tenant.setMonthlyFee(monthlyFee);
        tenant.setMaintenanceFee(maintenanceFee);

        tenant.setStatus(TenantStatus.PENDING);
        tenant.setToken(token);

        tenantRepository.save(tenant);

        return token;
    }

    @Transactional
    public Tenant completeRegistration(String token, TenantCreateDto dto) {

        Tenant tenant = tenantRepository
                .findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid onboarding link"));

        if (Boolean.TRUE.equals(tenant.getProfileCompleted())) {
            throw new RuntimeException("Link already used");
        }

        Bed bed = tenant.getBed();

        if (bed.getStatus() == BedStatus.OCCUPIED) {
            throw new RuntimeException("Bed already occupied");
        }

        tenant.setEmail(dto.getEmail());
        tenant.setStatus(TenantStatus.ACTIVE);
        tenant.setProfileCompleted(true);

        Tenant updatedTenant = tenantRepository.save(tenant);

        bed.setStatus(BedStatus.OCCUPIED);
        bedRepository.save(bed);

        YearMonth currentMonth = YearMonth.now();

        Payment payment = new Payment();
        payment.setTenant(updatedTenant);
        payment.setMonth(
                currentMonth.format(DateTimeFormatter.ofPattern("yyyy-MM")));
        payment.setAmount(updatedTenant.getMonthlyFee() + updatedTenant.getMaintenanceFee());
        payment.setStatus(PaymentStatus.PENDING);
        paymentRepository.save(payment);

        return updatedTenant;
    }

    public Tenant getOnboarding(String token) {

        return tenantRepository
                .findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid onboarding link"));
    }

    public Page<Tenant> getTenants(
            TenantFilterDto filter,
            int page,
            int size,
            String sortBy,
            String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Tenant> spec = (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // status filter
            if (filter.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }

            // tenant name
            if (filter.getTenantName() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("tenantName")),
                        "%" + filter.getTenantName().toLowerCase() + "%"
                ));
            }

            // mobile
            if (filter.getMobile() != null) {
                predicates.add(cb.equal(root.get("mobile"), filter.getMobile()));
            }

            // email
            if (filter.getEmail() != null) {
                predicates.add(cb.equal(root.get("email"), filter.getEmail()));
            }

            // bed filter
            if (filter.getBedId() != null) {
                predicates.add(cb.equal(root.get("bed").get("id"), filter.getBedId()));
            }

            // hostel filter
            if (filter.getHostelId() != null) {
                predicates.add(cb.equal(root.get("bed").get("hostelId"), filter.getHostelId()));
            }

            // global search
            if (filter.getSearch() != null && !filter.getSearch().isEmpty()) {

                String pattern = "%" + filter.getSearch().toLowerCase() + "%";

                Predicate nameMatch = cb.like(cb.lower(root.get("tenantName")), pattern);
                Predicate mobileMatch = cb.like(root.get("mobile"), pattern);
                Predicate emailMatch = cb.like(cb.lower(root.get("email")), pattern);

                predicates.add(cb.or(nameMatch, mobileMatch, emailMatch));
            }

            // date range
            if (filter.getFromDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), filter.getFromDate()));
            }

            if (filter.getToDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), filter.getToDate()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return tenantRepository.findAll(spec, pageable);
    }

    public Page<Tenant> ownerDashboard(OwnerDashboardFilterDto filter, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Specification<Tenant> spec = (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            Join<Tenant, Bed> bedJoin = root.join("bed", JoinType.LEFT);
            Join<Bed, Hostel> hostelJoin = bedJoin.join("hostel", JoinType.LEFT);

            if (filter.getOwnerId() != null) {
                predicates.add(cb.equal(hostelJoin.get("owner").get("id"), filter.getOwnerId()));
            }

            if (filter.getHostelId() != null) {
                predicates.add(cb.equal(hostelJoin.get("id"), filter.getHostelId()));
            }

            if (filter.getBedId() != null) {
                predicates.add(cb.equal(bedJoin.get("id"), filter.getBedId()));
            }

            if (filter.getTenantStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filter.getTenantStatus()));
            }

            if (filter.getBedStatus() != null) {
                predicates.add(cb.equal(bedJoin.get("status"), filter.getBedStatus()));
            }

            if (filter.getSearch() != null) {
                String pattern = "%" + filter.getSearch().toLowerCase() + "%";

                Predicate nameMatch = cb.like(cb.lower(root.get("tenantName")), pattern);
                Predicate mobileMatch = cb.like(root.get("mobile"), pattern);

                predicates.add(cb.or(nameMatch, mobileMatch));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return tenantRepository.findAll(spec, pageable);
    }
}