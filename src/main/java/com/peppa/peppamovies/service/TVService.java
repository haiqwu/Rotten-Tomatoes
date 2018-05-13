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

    Page<TVInfo> listTopTV(Pageable pageable);

    Page<TVInfo> listComingTV(Date date, Pageable pageable);

    void deleteTV(Long id);
}
