package com.example.webcinema.Services.Interfaces;

import com.example.webcinema.Entity.Promotion;


public interface IPromotionService {
    Promotion addNew(Promotion newPromotion);

    Promotion remake(Promotion remakePromotion);

    Promotion delete(String type);
}
