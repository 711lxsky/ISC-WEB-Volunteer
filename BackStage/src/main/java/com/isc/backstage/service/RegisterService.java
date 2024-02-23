package com.isc.backstage.service;

import com.isc.backstage.domain.DTO.RegisterVolunteerDTO;
import com.isc.backstage.domain.Result;

/**
 * @Author: 711lxsky
 * @Date: 2024-01-26
 */
public interface RegisterService {

    Result<?> addVolunteer(RegisterVolunteerDTO volunteerDTO);
}
