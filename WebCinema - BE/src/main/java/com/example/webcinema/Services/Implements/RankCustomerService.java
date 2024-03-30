package com.example.webcinema.Services.Implements;

import com.example.webcinema.Config.ApplicationConfig;
import com.example.webcinema.Entity.RankCustomer;
import com.example.webcinema.Repository.PromotionRepository;
import com.example.webcinema.Repository.RankCustomerRepository;
import com.example.webcinema.Repository.UserRepository;
import com.example.webcinema.Services.Interfaces.IRankCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RankCustomerService implements IRankCustomerService {
    private final RankCustomerRepository rankRepo;
    private final UserRepository userRepository;
    private final PromotionRepository proRepo;
    private final ApplicationConfig config;

    @Override
    public RankCustomer addNew(RankCustomer newRequest) {
        return rankRepo.save(newRequest);
    }

    @Override
    public RankCustomer remake(RankCustomer remakeRequest) {
        var current = rankRepo.findById(remakeRequest.getId())
                .orElseThrow(() -> new RuntimeException("Data not found !"));
        if ((int) remakeRequest.getPoint() == 0) remakeRequest.setPoint(current.getPoint());
        BeanUtils.copyProperties(remakeRequest, current, config.getNullPropertyNames(remakeRequest));
        return rankRepo.save(current);
    }

    @Override
    public RankCustomer delete(String name) {
        var current = rankRepo.findByName(name)
                .orElseThrow(() -> new RuntimeException("Data not found !"));
        userRepository.findAll().forEach(x -> {
            if (x.getRankCustomer().equals(current)) x.setRankCustomer(null);
        });
        proRepo.findAll().forEach(x -> {
            if (x.getRankCustomer().equals(current)) x.setActive(false);
        });
        current.setActive(false);
        return rankRepo.save(current);
    }
}
