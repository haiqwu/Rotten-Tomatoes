package com.peppa.peppamovies.service;

import com.peppa.peppamovies.dao.TVRepository;

import com.peppa.peppamovies.model.TVInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;



@Service
public class TVServiceImpl implements TVService {
    @Autowired
    private TVRepository tvRepository;

    @Transactional
    @Override
    public TVInfo saveTV(TVInfo tv) {
        return tvRepository.save(tv);
    }
    @Transactional
    @Override
    public TVInfo getTV(Long id) {
        return tvRepository.getOne(id);
    }
    @Transactional
    @Override
    public void deleteTV(Long id) {
        tvRepository.delete( getTV(id)   );
    }
    @Transactional
    @Override
    public TVInfo updateTV(Long id, TVInfo tv) {
        TVInfo findMovie = tvRepository.getOne(id);
        if (findMovie == null) {
            //throw new ChangeSetPersister.NotFoundException("NOT EXIST!");
        }
        BeanUtils.copyProperties(tv, findMovie);
        return tvRepository.save(tv);
    }

    @Override
    public Page<TVInfo> listTV(String query, Pageable pageable) {
        return null;
    }

    @Transactional
    @Override
    public Page<TVInfo> listOpeningTV(Date dateStart, Date dateEnd, Pageable pageable) {
        return null;
    }
    @Transactional
    @Override
    public Page<TVInfo> listTopTV(Pageable pageable) {
        return null;
    }
    @Transactional
    @Override
    public Page<TVInfo> listComingTV(Date date, Pageable pageable) {
        return null;
    }

}