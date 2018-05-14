package com.peppa.peppamovies.service;


import com.peppa.peppamovies.model.TVInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface TVService {
    TVInfo saveTV(TVInfo tv);

    TVInfo getTV(Long id);

    TVInfo updateTV(Long id, TVInfo tv);

    Page<TVInfo> listTV(String query, Pageable pageable);

    Page<TVInfo> listOpeningTV(Date dateStart, Date dateEnd, Pageable pageable);

    Page<TVInfo> listTopRatedTV(Pageable pageable);

    Page<TVInfo> listCriticTopRatedTV(Pageable pageable);

    TVInfo getTVBySeason(int seasonNum, String movieName);

    void deleteTV(Long id);
}
