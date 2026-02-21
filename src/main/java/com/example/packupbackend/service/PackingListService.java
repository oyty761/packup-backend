package com.example.packupbackend.service;

import com.example.packupbackend.entity.PackingItem;
import com.example.packupbackend.entity.Trip;
import com.example.packupbackend.entity.User;
import java.util.List;

public interface PackingListService {
    List<PackingItem> generatePackingList(Trip trip, User user);
}
